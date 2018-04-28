package com.example.guet.sharehotel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guet.sharehotel.R;

/**
 * 主页-.搜索->右上角筛选
 */
public class FilterHotelActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView cancle;
    private TextView sure;
    private TextView first_tv;
    private TextView second_tv;
    private TextView third_tv;
    private ImageView imageView;
    private ImageView iv_cwtx;
    private ImageView iv_zffb;
    private ImageView iv_cqxz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        initView();

        //getSupportActionBar().hide();


    }


    private void initView() {
        cancle = (TextView) findViewById(R.id.cancle);
        sure = (TextView) findViewById(R.id.sure);
        first_tv = (TextView) findViewById(R.id.first_tv);
        second_tv = (TextView) findViewById(R.id.second_tv);
        third_tv = (TextView) findViewById(R.id.third_tv);
        imageView = (ImageView) findViewById(R.id.imageView);
        iv_cwtx = (ImageView) findViewById(R.id.iv_cwtx);
        iv_zffb = (ImageView) findViewById(R.id.iv_zffb);
        iv_cqxz = (ImageView) findViewById(R.id.iv_cqxz);

        iv_cwtx.setOnClickListener(this);
        iv_zffb.setOnClickListener(this);
        iv_cqxz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancle:
                finish();
                break;
        }
    }

}