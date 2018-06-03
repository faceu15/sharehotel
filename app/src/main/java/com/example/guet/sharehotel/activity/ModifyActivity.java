package com.example.guet.sharehotel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.model.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ModifyActivity extends AppCompatActivity {

    @BindView(R.id.ll_modify_back)
    LinearLayout mBackLinearLayout;
    @BindView(R.id.et_modify_pw)
    EditText mExPasswordEditText;
    @BindView(R.id.et_modify_password)
    EditText mPasswordEditText1;
    @BindView(R.id.et_modify_password2)
    EditText mPasswordEditText2;
    @BindView(R.id.btn_modify)
    Button mModifyButton;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modify);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.ll_modify_back, R.id.btn_modify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_modify_back:
                finish();
                break;
            case R.id.btn_modify:
                modify();
                break;
        }
    }

    private void modify() {
        User user = MyApplication.getInstance().getUser();
        String pw = mExPasswordEditText.getText().toString();
        String password1 = mPasswordEditText1.getText().toString();
        String password2 = mPasswordEditText2.getText().toString();
        if (!pw.equals(user.getPassword())) {
            Toast.makeText(this, "原密码错误", Toast.LENGTH_SHORT).show();
        } else {
            if (!password1.equals("") && password1.equals(password2)) {
                showLoading("请稍等...");
                user.setPassword(password1);
                user.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(ModifyActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                            Intent back = new Intent();
                            back.putExtra("Logout", 1);
                            setResult(2, back);
                            hideLoading();
                            finish();
                        } else {
                            Toast.makeText(ModifyActivity.this, "修改密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "两次密码不一样", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void showLoading(String msg) {
        progressDialog = new ProgressDialog(this, 1);//后面的参数是风格，1比较好看
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    public void hideLoading() {
        progressDialog.hide();
    }


}
