package com.example.guet.sharehotel.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.base.BaseActivity;
import com.example.guet.sharehotel.presenter.LoginPresenter;
import com.example.guet.sharehotel.view.ILoginView;

public class LoginActivity extends BaseActivity<ILoginView,LoginPresenter<ILoginView>> implements ILoginView,View.OnClickListener{


    private EditText mAccountEditText;
    private EditText mPasswordEditText;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        Button loginButton = findViewById(R.id.btn_login);
        Button registerButton = findViewById(R.id.register_btn);
        ImageView backImageView = findViewById(R.id.back_iv);
        mToast=new Toast(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);
        backImageView.setOnClickListener(this);
        mAccountEditText=findViewById(R.id.login_account_et);
        mPasswordEditText=findViewById(R.id.login_pwd_et);
    }

    @Override
    protected LoginPresenter<ILoginView> createPresenter() {
        Log.i("LoginActivity","创建LoginPresenter");
        return new LoginPresenter<>((ILoginView) this);
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
        SharedPreferences sp=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("Account",getAccount());
        editor.putString("Password",getPassWord());
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        Intent intent=new Intent();
        intent.putExtra("Account",getAccount());
        intent.putExtra("Password",getPassWord());
        intent.putExtra("Login",1);
        this.setResult(1,intent);
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
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.makeText(this,msg ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login://登录
                mPresenter.Login(getAccount(),getPassWord());
                break;
            case R.id.register_btn://注册
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.back_iv:
                Intent intent1=new Intent();
                intent1.putExtra("MSG",0);
                setResult(1,intent1);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
           String ac= data.getExtras().getString("Account");
           String pw=data.getExtras().getString("Password");
           mAccountEditText.setText(ac);
           mPasswordEditText.setText(pw);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mToast.cancel();
    }
}
