package com.example.guet.sharehotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.base.BaseActivity;
import com.example.guet.sharehotel.base.BasePresenter;
import com.example.guet.sharehotel.bean.User;
import com.example.guet.sharehotel.model.LogUtil;
import com.example.guet.sharehotel.presenter.RegisterPresenter;
import com.example.guet.sharehotel.view.IRegisterView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class RegisterActivity extends BaseActivity implements IRegisterView, View.OnClickListener {


    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private EditText mVerifyEditText;
    private Button mGetVerifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    @Override
    protected BasePresenter createPresenter() {
        return new RegisterPresenter();
    }

    private void initView() {
        mAccountEditText = findViewById(R.id.et_register_account);
        mPasswordEditText = findViewById(R.id.et_register_password);
        mVerifyEditText = findViewById(R.id.et_register_verification);
        mGetVerifyButton = findViewById(R.id.bt_register_verification);
        ImageView passwordVisibleImageView = findViewById(R.id.iv_password_visible);
        ImageView backImageView = findViewById(R.id.iv_register_back);
        Button registerButton = findViewById(R.id.register_btn);

        passwordVisibleImageView.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        mGetVerifyButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    public void getVerification() {
        try {
            if (mAccountEditText.getText() != null) {
                Toast.makeText(this, "获取验证码中....", Toast.LENGTH_SHORT).show();
                BmobSMS.requestSMSCode(mAccountEditText.getText().toString().trim(), "verify",
                        new QueryListener<Integer>() {
                            @Override
                            public void done(Integer integer, BmobException e) {
                                if (e == null) {//短信发送成功
                                    mGetVerifyButton.setText("已发送");
                                } else {
                                    showError("验证码获取失败");
                                }
                            }
                        });
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public String getAccount() {
        return mAccountEditText.getText().toString().trim();
    }

    @Override
    public String getPassWord() {
        return mPasswordEditText.getText().toString().trim();
    }

    @Override
    public void showResult(String result) {
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("Account", getAccount());
        intent.putExtra("Password", getPassWord());
        setResult(1, intent);
        this.finish();

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();
                break;
            case R.id.iv_password_visible:
                changeInputType();
                break;

            case R.id.bt_register_verification:
                getVerification();//获取验证码并验证
                break;
            case R.id.register_btn:
                verify();
                break;
        }

    }

    private void changeInputType() {
        if (mPasswordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        if (mPasswordEditText.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            mPasswordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private void verify() {
        if (mVerifyEditText.getText() != null) {
            BmobSMS.verifySmsCode(getAccount(), mVerifyEditText.getText().toString().trim(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {//验证通过
                        if (mPasswordEditText.getText() != null) {
                            createAccount(getAccount(), getPassWord());
                        }

                    } else {//验证失败
                        showError("验证码错误");
                    }
                }
            });
        } else {
            showError("请输入验证码");
        }

    }

    public void createAccount(final String ac, final String pw) {
        BmobQuery<User> query = new BmobQuery<>();
        //查询Bmob中account字段叫account的数据
        query.addWhereEqualTo("account", ac);
        query.setLimit(1);      //只需返回一个数据，因为也只有一条数据
        //执行查询方法
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    if (object.size() == 0)    //没有创建账号
                    {//保存注册信息，创建账号
                        User user = new User();
                        user.setAccount(ac);
                        user.setPassword(pw);
                        user.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    showResult("注册成功,可直接点击登录");
                                } else {
                                    Log.i("Register", "用户注册失败");
                                    showError("用户注册失败");
                                }
                            }
                        });
                    } else {
                        User user = object.get(0);
                        //获得account的信息
                        if (getAccount() == object.get(0).getAccount()) {
                            showError("账号已存在");
                        }

                    }

                } else {
                    LogUtil.e("TAG", "服务器繁忙，失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

    }

}
