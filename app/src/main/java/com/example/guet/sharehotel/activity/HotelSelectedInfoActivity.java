package com.example.guet.sharehotel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.bean.Hotel;

/**
 * 主页-.搜索->选择酒店->酒店详情
 */
public class HotelSelectedInfoActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 当地定位到的城市
     */
    private String localCity;

    private Hotel mHotel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selected_info);

        initView();  //初始化控件

    }

    /**
     * 初始化控件
     */
    private void initView() {
        TextView hotelNameTextView = findViewById(R.id.tv_hotel_name);
        TextView commnetTextView = findViewById(R.id.tv_hotel_commnent);
        TextView gradeTextView = findViewById(R.id.tv_hotel_grade);

    }

    @Override
    public void onClick(View v) {

    }
}
