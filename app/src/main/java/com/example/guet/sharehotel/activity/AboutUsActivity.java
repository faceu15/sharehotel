package com.example.guet.sharehotel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.guet.sharehotel.R;

/**
 * 个人中心->关于共享酒店
 */
public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView close_iv;
    private LinearLayout about_us_linear_layout;
    private LinearLayout customer_service_linear_layout;
    private LinearLayout change_password_linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_about_us);
        initView();

        //this.getSupportActionBar().hide();
    }

    private void initView() {
        close_iv = (ImageView) findViewById(R.id.iv_back);
        close_iv.setOnClickListener(this);
        about_us_linear_layout = (LinearLayout) findViewById(R.id.about_us_linear_layout);
        customer_service_linear_layout = (LinearLayout) findViewById(R.id.customer_service_linear_layout);
        change_password_linear_layout = (LinearLayout) findViewById(R.id.change_password_linear_layout);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
