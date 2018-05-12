package com.example.guet.sharehotel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.guet.sharehotel.R;

/**
 * 主页-.搜索->右上角筛选
 */
public class FilterHotelActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView cancle;
    private TextView sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_filter);
        initView();

    }


    private void initView() {
        cancle = findViewById(R.id.tv_filter_cancle);
        sure = findViewById(R.id.sure);

        cancle.setOnClickListener(this);
        sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_filter_cancle:
                finish();
                break;
        }
    }

}