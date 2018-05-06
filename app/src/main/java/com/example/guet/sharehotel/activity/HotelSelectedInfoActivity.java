package com.example.guet.sharehotel.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.bean.Hotel;

import me.shihao.library.XRadioGroup;

/**
 * 主页-.搜索->选择酒店->酒店详情
 */
public class HotelSelectedInfoActivity extends AppCompatActivity implements View.OnClickListener, XRadioGroup.OnCheckedChangeListener {

    /**
     * 当地定位到的城市
     */
    private TextView mPriceTextView;
    private XRadioGroup mXRadioGroup;
    private Hotel mHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_selected_info);
        mHotel = (Hotel) getIntent().getBundleExtra("HotelBundle").getSerializable("Hotel");
        initView();  //初始化控件

    }

    /**
     * 初始化控件
     */
    @SuppressLint("SetTextI18n")
    private void initView() {
        LinearLayout backLinearLayout = findViewById(R.id.hotel_back_ll);
        backLinearLayout.setOnClickListener(this);
        TextView hotelNameTextView = findViewById(R.id.tv_hotel_name);
        TextView commnetTextView = findViewById(R.id.tv_hotel_commnent);
        TextView gradeTextView = findViewById(R.id.tv_hotel_grade);
        mPriceTextView = findViewById(R.id.tv_hotel_price);
        Button bookHotelButton = findViewById(R.id.bt_hotel_book);
        bookHotelButton.setOnClickListener(this);
        hotelNameTextView.setText(mHotel.getName());
        commnetTextView.setText(mHotel.getComment().toString());
        gradeTextView.setText(mHotel.getGrade().toString());
        mPriceTextView.setText(mHotel.getPrice().toString());
        mXRadioGroup = findViewById(R.id.rd_hotel_type);
        mXRadioGroup.setOnCheckedChangeListener(this);
        if (mHotel.getType() != 1) {
            mXRadioGroup.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_hotel_book:
                if (MyApplication.getInstance().isLogin()) {
                    Intent intent = new Intent(this, OrderDetailActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.hotel_back_ll:
                this.finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(XRadioGroup xRadioGroup, int i) {
        switch (i) {
            case R.id.radioButton:
                mPriceTextView.setText(String.valueOf(mHotel.getPrice()));
                break;
            case R.id.radioButton2:
                mPriceTextView.setText(String.valueOf(mHotel.getPrice() + 50));
                break;
            case R.id.radioButton3:
                mPriceTextView.setText(String.valueOf(mHotel.getPrice() + 100));
                break;
            case R.id.radioButton4:
                mPriceTextView.setText(String.valueOf(mHotel.getPrice() + 80));
                break;
        }
    }
}
