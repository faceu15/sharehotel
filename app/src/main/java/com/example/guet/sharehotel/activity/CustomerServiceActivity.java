package com.example.guet.sharehotel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.guet.sharehotel.R;


/**
 * 个人中心->客服
 */
public class CustomerServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView close_iv;
    private LinearLayout demestic_linear_layout;
    private LinearLayout abrod_linear_layout;
    private LinearLayout onlie_linear_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        initView();

        //this.getSupportActionBar().hide();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.close_iv:
                finish();
                break;
        }

    }

    private void initView() {
        close_iv = (ImageView) findViewById(R.id.close_iv);
        close_iv.setOnClickListener(this);
        demestic_linear_layout = (LinearLayout) findViewById(R.id.demestic_linear_layout);
        abrod_linear_layout = (LinearLayout) findViewById(R.id.abrod_linear_layout);
        onlie_linear_layout = (LinearLayout) findViewById(R.id.onlie_linear_layout);
    }
}
