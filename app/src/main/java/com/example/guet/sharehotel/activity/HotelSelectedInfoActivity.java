package com.example.guet.sharehotel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.guet.sharehotel.R;

/**
 * 主页-.搜索->选择酒店->酒店详情
 */
public class HotelSelectedInfoActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 当地定位到的城市
     */
    private String localCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selected_info);
        this.getSupportActionBar().hide();

        initView();  //初始化控件

    }

    /**
     * 初始化控件
     */
    private void initView() {


    }

    @Override
    public void onClick(View v) {

    }
}
