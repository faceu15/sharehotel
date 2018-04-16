package com.example.guet.sharehotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.model.LogUtil;
import com.example.guet.sharehotel.utility.CheckVersion;
import com.example.guet.sharehotel.utility.ClearDataManagerUtil;

/**
 * 个人中心->右上角：设置
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "SettingActivity";
    private ImageView close_iv;
    private LinearLayout exit_login_linear_layout;
    private LinearLayout change_password_linear_layout;
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
        setContentView(R.layout.activity_setting);

        this.getSupportActionBar().hide();
        initView();
    }

    private void initView() {


        close_iv = (ImageView) findViewById(R.id.close_iv);
        close_iv.setOnClickListener(this);
        exit_login_linear_layout = (LinearLayout) findViewById(R.id.exit_login_linear_layout);
        exit_login_linear_layout.setOnClickListener(this);
        change_password_linear_layout = (LinearLayout) findViewById(R.id.change_password_linear_layout);
        change_password_linear_layout.setOnClickListener(this);
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


        about_us_linear_layout = (LinearLayout) findViewById(R.id.about_us_linear_layout);
        about_us_linear_layout.setOnClickListener(this);
//        mobile_phone_tv = (TextView) findViewById(R.id.mobile_phone_tv);
//        if (LoginState.ACCOUNT == 1)  //如果是手机号码登录，那么直接显示绑定的手机号码
//        {
//            mobile_phone_tv.setText(LoginActivity.getAccount());
//        } else if(LoginState.QQ == 1){
//            mobile_phone_tv.setText(LoginActivity.getAccount());
//        }
//        else
//        {
//            mobile_phone_tv.setText("未绑定手机");
//        }
//        mobile_phone_linear_layout = (LinearLayout) findViewById(R.id.mobile_phone_linear_layout);
//        mobile_phone_linear_layout.setOnClickListener(this);
        recommentd_to_friend_linear_layout = (LinearLayout) findViewById(R.id.recommentd_to_friend_linear_layout);
        recommentd_to_friend_linear_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_iv:   //关闭界面
                this.finish();
                break;
            case R.id.exit_login_linear_layout: //退出登录
//                exitLogin();
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
//            case R.id.change_password_linear_layout:  //修改密码
//                Intent intent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
//                startActivityForResult(intent, 1);
//                break;
            case R.id.about_us_linear_layout:   //关于我们
                Intent intent2 = new Intent(SettingActivity.this, AboutUsActivity.class);
                startActivity(intent2);
                break;
//            case R.id.mobile_phone_linear_layout:
//                if (LoginState.ACCOUNT == 0)  //如果不是手机号码登录，而是微信，QQ，微博登录，那么需要绑定手机号码
//                {
//                    Intent intent2 = new Intent(SettingActivity.this, BindMobilePhoneActivity.class);
//                    startActivity(intent2);
//                }
//                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        finish();

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {  //当修改完成功密码后，关闭设置activity。
                    finish();
                    LogUtil.e(TAG, "运行到这里");

                }
                break;
            default:
        }
    }

//    /**
//     * ChangePasswordActivity中也有这个方法：退出登录
//     */
//    private void exitLogin() {
//        //各个平台的授权名称
//        SHARE_MEDIA qq = SHARE_MEDIA.QQ;
//        SHARE_MEDIA sina = SHARE_MEDIA.SINA;
//        SHARE_MEDIA weixin = SHARE_MEDIA.WEIXIN;
//
//        if (LoginState.QQ == 1) {   //如果是QQ登录
//
//            boolean isauth = true;
//            //是否授权
//            isauth = UMShareAPI.get(this).isAuthorize(this, qq.toSnsPlatform().mPlatform);
//            if (isauth) {
//                //取消授权，即退出登录
//                UMShareAPI.get(this).deleteOauth(this, qq.toSnsPlatform().
//                        mPlatform, authListener);
//            }
//
//        } else if (LoginState.WEIBO == 1) {
//            boolean isauth = true;
//            //是否授权
//            isauth = UMShareAPI.get(this).isAuthorize(this, sina.toSnsPlatform().mPlatform);
//            if (isauth) {
//                //取消授权，即退出登录
//                UMShareAPI.get(this).deleteOauth(this, sina.toSnsPlatform().mPlatform, authListener);
//            }
//        } else if (LoginState.WEIXIN == 1) {
//            boolean isauth = true;
//            //是否授权
//            isauth = UMShareAPI.get(this).isAuthorize(this, weixin.toSnsPlatform().mPlatform);
//            if (isauth) {
//                //取消授权，即退出登录
//                UMShareAPI.get(this).deleteOauth(this, weixin.toSnsPlatform().mPlatform, authListener);
//            }
//        } else {
//            //退出账号登录
//
//        }
//        //清空标记登录的类型
//        LoginState.clear();
//        //清空登录状态
//        PersonalFragment.getLogin_state_tv().setText("登录/注册");
//        PersonalFragment.getUser_icon_iv().setImageResource(R.mipmap.login_head1);
//        LoginActivity.getUsers().setQq_login("");
//        LoginActivity.getUsers().setMobile_phone_login("");
//        LoginActivity.getUsers().setWeixin_login("");
//        LoginActivity.getUsers().setWeibo_login("");
//        LoginActivity.setUsersNull();
//        LoginActivity.setUserHeadNull();
//        LoginActivity.setUserIdNull();
//
//
//    }


//    UMAuthListener authListener = new UMAuthListener() {
//        /**
//         * @desc 授权开始的回调
//         * @param platform 平台名称
//         */
//        @Override
//        public void onStart(SHARE_MEDIA platform) {
//        }
//
//        /**
//         * @desc 授权成功的回调
//         * @param platform 平台名称
//         * @param action 行为序号，开发者用不上
//         * @param data 用户资料返回
//         */
//        @Override
//        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//            Toast.makeText(SettingActivity.this, "退出登录成功!", Toast.LENGTH_LONG).show();
//        }
//
//        /**
//         * @desc 授权失败的回调
//         * @param platform 平台名称
//         * @param action 行为序号，开发者用不上
//         * @param t 错误原因
//         */
//        @Override
//        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//            Toast.makeText(SettingActivity.this, "退出登录失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
//        }
//
//        /**
//         * @desc 授权取消的回调
//         * @param platform 平台名称
//         * @param action 行为序号，开发者用不上
//         */
//        @Override
//        public void onCancel(SHARE_MEDIA platform, int action) {
//
//            Toast.makeText(SettingActivity.this, "退出登录成功", Toast.LENGTH_LONG).show();
//        }
//    };


}