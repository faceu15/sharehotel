package com.example.guet.sharehotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.utils.CheckVersion;
import com.example.guet.sharehotel.utils.ClearDataManagerUtil;

/**
 * 个人中心->右上角：设置
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "SettingActivity";
    private ImageView close_iv;
    private LinearLayout exit_login_linear_layout;
    // private LinearLayout change_password_linear_layout;
    private LinearLayout clear_data_linear_layout;
    private LinearLayout check_up_update_linear_layout;
    private TextView cache_tv;
    private LinearLayout about_us_linear_layout;
    private LinearLayout recommentd_to_friend_linear_layout;

    public static TextView getMobile_phone_tv() {
        return mobile_phone_tv;
    }

    private static TextView mobile_phone_tv;
    private LinearLayout mobile_phone_linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_setting);

        //this.getSupportActionBar().hide();
        initView();
    }

    private void initView() {
        close_iv = (ImageView) findViewById(R.id.iv_back);
        close_iv.setOnClickListener(this);
        exit_login_linear_layout = (LinearLayout) findViewById(R.id.exit_login_linear_layout);
        exit_login_linear_layout.setOnClickListener(this);
        //change_password_linear_layout = (LinearLayout) findViewById(R.id.change_password_linear_layout);
        //change_password_linear_layout.setOnClickListener(this);
        clear_data_linear_layout = (LinearLayout) findViewById(R.id.clear_data_linear_layout);
        clear_data_linear_layout.setOnClickListener(this);
        check_up_update_linear_layout = (LinearLayout) findViewById(R.id.check_up_update_linear_layout);
        check_up_update_linear_layout.setOnClickListener(this);
        cache_tv = (TextView) findViewById(R.id.cache_tv);
        try {
            cache_tv.setText(ClearDataManagerUtil.getTotalCacheSize(this) + "");   //显示缓存大小
        } catch (Exception e) {
            e.printStackTrace();
        }
        //修改密码
        LinearLayout modifyLL = findViewById(R.id.ll_change_password);
        modifyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().getAccount() == null) {
                    Toast.makeText(SettingActivity.this, "您未登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SettingActivity.this, ModifyActivity.class);
                    startActivity(intent);
                }
            }
        });

        about_us_linear_layout = (LinearLayout) findViewById(R.id.about_us_linear_layout);
        about_us_linear_layout.setOnClickListener(this);
        recommentd_to_friend_linear_layout = (LinearLayout) findViewById(R.id.recommentd_to_friend_linear_layout);
        recommentd_to_friend_linear_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:   //关闭界面
                this.finish();
                break;
            case R.id.exit_login_linear_layout: //退出登录
                Intent back = new Intent();
                back.putExtra("Logout", 1);
                setResult(2, back);
                this.finish();
                break;
            case R.id.clear_data_linear_layout:  //清除缓存
                ClearDataManagerUtil.clearAllCache(this);
                cache_tv.setText("0K");
                Toast.makeText(this, "清理完成", Toast.LENGTH_SHORT).show();
                break;
            case R.id.check_up_update_linear_layout:   //检查更新
                //在这里做检查业务逻辑
                Toast.makeText(this, "检查更新中......", Toast.LENGTH_LONG).show();
                CheckVersion checkVersion = new CheckVersion(SettingActivity.this);
                new Thread(checkVersion).start();
                break;
            case R.id.about_us_linear_layout:   //关于我们
                Intent intent2 = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent2);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (data != null) {
                    setResult(2, data);
                    this.finish();
                }

            default:
        }
    }


}