package com.example.guet.sharehotel.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.model.bean.Hotel;
import com.example.guet.sharehotel.model.bean.HotelRoomType;
import com.example.guet.sharehotel.model.bean.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import me.shihao.library.XRadioGroup;

/**
 * 主页-.搜索->选择酒店->酒店详情
 */
public class HotelSelectedInfoActivity extends AppCompatActivity implements View.OnClickListener, XRadioGroup.OnCheckedChangeListener {

    private static final String TAG = "HotelSelectedInfo";
    /**
     * 当地定位到的城市
     */
    private TextView mPriceTextView;
    private Intent mIntent;
    private Hotel mHotel;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String mCheckInDate;
    private String mCheckOutDate;
    private XRadioGroup mXRadioGroup;
    private RadioButton mRadioButton, mRadioButton2, mRadioButton3, mRadioButton4;
    private List<RadioButton> mRadioButtons = new ArrayList<>();
    private List<HotelRoomType> mRoomTypelist;
    private Integer mRoomNum;
    private Integer mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_hotel_selected_info);
        mIntent = getIntent();
        mHotel = (Hotel) mIntent.getBundleExtra("HotelBundle").getSerializable("Hotel");
        mCheckInDate = mIntent.getExtras().getString("CheckInDate");
        mCheckOutDate = mIntent.getExtras().getString("CheckOutDate");
        initView();  //初始化控件

    }

    /**
     * 初始化控件
     */
    @SuppressLint("SetTextI18n")
    private void initView() {
        LinearLayout backLinearLayout = findViewById(R.id.hotel_back_ll);
        backLinearLayout.setOnClickListener(this);
        //评论
        Button commentButton = findViewById(R.id.hotelmessage_btn_evaluation);
        commentButton.setOnClickListener(this);
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
        //天数和房间数
        try {
            mDays = getCountDays(sdf.parse(mCheckInDate), sdf.parse(mCheckOutDate));
            mRoomNum = Integer.valueOf(getIntent().getExtras().getString("RoomNumber"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //类型单选框
        mXRadioGroup = findViewById(R.id.rd_hotel_type);
        mRadioButton = findViewById(R.id.radioButton);
        mRadioButtons.add(mRadioButton);
        mRadioButton2 = findViewById(R.id.radioButton2);
        mRadioButtons.add(mRadioButton2);
        mRadioButton3 = findViewById(R.id.radioButton3);
        mRadioButtons.add(mRadioButton3);
        mRadioButton4 = findViewById(R.id.radioButton4);
        mRadioButtons.add(mRadioButton4);
        mRoomTypelist = new ArrayList<>();
        initXRadioGroup();

        mXRadioGroup.setOnCheckedChangeListener(this);

    }

    private void initXRadioGroup() {
        BmobQuery<HotelRoomType> query = new BmobQuery<HotelRoomType>("HotelRoomType");
        query.addWhereEqualTo("hotel", mHotel);
        query.setLimit(4);
        query.findObjects(new FindListener<HotelRoomType>() {
            @Override
            public void done(List<HotelRoomType> list, BmobException e) {
                if (e == null) {
                    mRoomTypelist = list;
                    if (list.size() == 1) {
                        mRadioButton.setText(list.get(0).getType());
                        mRadioButton2.setVisibility(View.GONE);
                        mRadioButton3.setVisibility(View.GONE);
                        mRadioButton4.setVisibility(View.GONE);
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            mRadioButtons.get(i).setText(list.get(i).getType());
                        }
                    }
                } else {
                    Log.i(TAG, "获取房型失败" + e.toString());
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_hotel_book:
                if (MyApplication.getInstance().isLogin()) {
                    //创建订单
                    createOrder(this, OrderDetailActivity.class);
                } else {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.hotel_back_ll:
                this.finish();
                break;
            case R.id.hotelmessage_btn_evaluation:
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("HotelId", mHotel.getObjectId());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void createOrder(final Context context, final Class activity) {
        final Order order = new Order();
        order.setUser(MyApplication.getInstance().getUser());
        order.setHotel(mHotel);
        order.setPrice(mHotel.getPrice() * mRoomNum * mDays);
        order.setDays(mDays);
        order.setRooms(mRoomNum);
        order.setState(1);
        try {
            BmobDate bmobDateIn = new BmobDate(sdf.parse(mCheckInDate));
            order.setCheckInTime(bmobDateIn);
            BmobDate bmobDateOut = new BmobDate(sdf.parse(mCheckOutDate));
            order.setCheckOutTime(bmobDateOut);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        order.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Log.i(TAG, "订单号：" + objectId);
                    Intent intent = new Intent(context, activity);
                    intent.putExtra("RoomNumber", getIntent().getExtras().getString("RoomNumber"));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Order", order);
                    intent.putExtra("OrderBundle", bundle);
                    startActivity(intent);
                } else {
                    Log.i(TAG, "预定失败" + e.toString());
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(XRadioGroup xRadioGroup, int i) {
        switch (i) {
            case R.id.radioButton:
                mPriceTextView.setText(String.valueOf(mRoomTypelist.get(0).getPrice()));
                mHotel.setPrice(mRoomTypelist.get(0).getPrice());
                break;
            case R.id.radioButton2:
                mPriceTextView.setText(String.valueOf(mRoomTypelist.get(1).getPrice()));
                mHotel.setPrice(mRoomTypelist.get(1).getPrice());
                break;
            case R.id.radioButton3:
                mPriceTextView.setText(String.valueOf(mRoomTypelist.get(2).getPrice()));
                mHotel.setPrice(mRoomTypelist.get(2).getPrice());
                break;
            case R.id.radioButton4:
                mPriceTextView.setText(String.valueOf(mRoomTypelist.get(3).getPrice()));
                mHotel.setPrice(mRoomTypelist.get(3).getPrice());
                break;
            default:
                break;
        }
    }

    public Integer getCountDays(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

}
