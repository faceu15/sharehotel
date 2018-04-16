package com.example.guet.sharehotel.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.guet.sharehotel.model.LogUtil;
import com.example.guet.sharehotel.model.UpdateInfo;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by feng on 2017/8/2.
 * 更新APP版本
 */
public class CheckVersion implements Runnable {

    //VERSIONINFO_URL是访问服务器拿到查询更新json数据的url,一般这个是在单独的一个数据类中写的。那样的话让CheckVersion继承自那个类。拿到url来用。现在这里设为空是为了方便看，还有以后更改url。
    private static final String VERSIONINFO_URL = "http://104.224.142.218/art/user/app_info.json";
    private static final int HAVE_NEW_VERSION = 0;
    private static final int ALREADY_NEW_VERSION = 1;
    private Context context = null;
    private UpdateInfo updateInfo;

    //获取到主线程的looper,对UI操作
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HAVE_NEW_VERSION:
                    LogUtil.e("CheckVersion", "准备下载");
                    openUpdateDialog();
                    break;
                case ALREADY_NEW_VERSION:
                    Toast.makeText(context, "已经是最新版本", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    public CheckVersion(Context context) {
        this.context = context;
    }

    private void openUpdateDialog() {

        //新版本的Dialog，参考：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2017/0226/7160.html
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("版本有更新");
        builder.setMessage(updateInfo.description);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadNewVersion();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();
    }

    private void downloadNewVersion() {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setTitle("更新进度");
        pd.setCancelable(false);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.show();

        new Thread(new Runnable() {
            InputStream is;
            BufferedInputStream bis;
            FileOutputStream fos;

            @Override
            public void run() {
                //这里完成下载
                try {
                    URL downloadUrl = new URL(updateInfo.url);
                    HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);

                    int maxlength = connection.getContentLength();
                    pd.setMax(maxlength / 1024);

                    is = connection.getInputStream();
                    bis = new BufferedInputStream(is);

                    File file = new File(Environment.getExternalStorageDirectory(), "艺品连.apk");
                    fos = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];
                    int len;
                    int loaded = 0;

                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        loaded += len;
                        pd.setProgress(loaded / 1024);
                    }

                    installApk(file);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        is.close();
                        bis.close();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    private void installApk(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    @Override
    public void run() {

        try {
            String s = VERSIONINFO_URL;
            URL url = new URL(s);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(20 * 1000);
            connection.setReadTimeout(20 * 1000);

            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            Gson gson = new Gson();
            updateInfo = gson.fromJson(br, UpdateInfo.class);
            //后台版本
            int serverVersionCode = updateInfo.versionCode;

            //本地版本获取
            int localVersionCode = getLocalVersionCode();

            if (serverVersionCode > localVersionCode) {
                //后台版本新！所以弹框提醒用户有新版本，让用户操作dialog更新
                Message msg = new Message();
                msg.what = HAVE_NEW_VERSION;
                mHandler.sendMessage(msg);

            } else {
                //后台没有新版本，所以在界面反馈用户不用更新
                Message msg = new Message();
                msg.what = ALREADY_NEW_VERSION;
                mHandler.sendMessage(msg);
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getLocalVersionCode() throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
        int versionCode = info.versionCode;
        return versionCode;
    }
}