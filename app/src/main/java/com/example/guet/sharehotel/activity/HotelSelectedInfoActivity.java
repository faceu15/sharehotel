package com.example.guet.sharehotel.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.model.bean.Collection;
import com.example.guet.sharehotel.model.bean.Hotel;
import com.example.guet.sharehotel.model.bean.Order;
import com.example.guet.sharehotel.view.dialog.AlertDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 主页-.搜索->选择酒店->酒店详情
 */
public class HotelSelectedInfoActivity extends AppCompatActivity implements View.OnClickListener {

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
    private Integer mDays;
    private Button mCollectButton;
    private AlertDialog mAlertDialog;

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
        //联系
        Button callButton = findViewById(R.id.hotelmessage_btn_phone);
        callButton.setOnClickListener(this);
        //评论
        Button commentButton = findViewById(R.id.hotelmessage_btn_evaluation);
        commentButton.setOnClickListener(this);
        //收藏
        mCollectButton = findViewById(R.id.btn_detail_colection);
        mCollectButton.setOnClickListener(this);
        //地图
        Button locationButton = findViewById(R.id.btn_detail_location);
        locationButton.setOnClickListener(this);

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
        TextView modeTextView = findViewById(R.id.tv_detail_mode);
        modeTextView.setText(mHotel.getMode());
        TextView houseTypeTextView = findViewById(R.id.tv_detail_house_type);
        houseTypeTextView.setText(mHotel.getHouseType());
        TextView areaTextView = findViewById(R.id.tv_detail_area);
        areaTextView.setText(String.valueOf(mHotel.getArea()));
        TextView addressTextView = findViewById(R.id.tv_detail_address);
        addressTextView.setText(mHotel.getAddress());
        TextView descTextView = findViewById(R.id.tv_detail_description);
        if (mHotel.getDescription() == null) {
            descTextView.setText("暂无数据");
        } else {
            descTextView.setText(mHotel.getDescription());
        }

        //天数和房间数
        try {
            mDays = getCountDays(sdf.parse(mCheckInDate), sdf.parse(mCheckOutDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
                //查看评价
                Intent intent = new Intent(this, CommentActivity.class);
                intent.putExtra("HotelId", mHotel.getObjectId());
                startActivity(intent);
                break;
            case R.id.btn_detail_colection:
                //收藏
                if (!MyApplication.getInstance().isLogin()) {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Drawable star = getResources().getDrawable(R.mipmap.star_choice);
                    star.setBounds(0, 0, star.getMinimumWidth(), star.getMinimumHeight());
                    mCollectButton.setCompoundDrawables(null, star, null, null);
                    collect(this);
                }
                break;
            case R.id.btn_detail_location:
                //地图
                Intent mapIntent = new Intent(HotelSelectedInfoActivity.this, MapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Hotel", mHotel);
                mapIntent.putExtra("HotelBundle", bundle);
                startActivity(mapIntent);
                break;
            case R.id.hotelmessage_btn_phone:
                //打电话
                mAlertDialog = new AlertDialog(this, true, "联系", "拨打房主电话？",
                        true, 1, new AlertDialog.OnDialogButtonClickListener() {
                    @Override
                    public void onDialogButtonClick(int requestCode, boolean isPositive) {
                        if (requestCode == 1 && isPositive) {
                            callHost();
                        }
                    }
                });
                mAlertDialog.show();
                break;

            default:
                break;
        }
    }

    private void callHost() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mHotel.getHost().getAccount()));
            this.startActivity(callIntent);
        } else {
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mHotel.getHost().getAccount()));
            this.startActivity(callIntent);
        }
    }


    private void collect(final Context context) {
        if (MyApplication.getInstance().getUser() == null) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
        } else {
            Collection collection = new Collection(MyApplication.getInstance().getUser(), mHotel);
            collection.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Toast.makeText(context, "已添收藏", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "已收藏，请勿重复提交", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "收藏失败");
                    }
                }
            });
        }
    }

    private void createOrder(final Context context, final Class activity) {
        final Order order = new Order();
        order.setUser(MyApplication.getInstance().getUser());
        order.setHotel(mHotel);
        order.setPrice(mHotel.getPrice() * mDays);
        order.setDays(mDays);
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
                    order.getHotel().setAvailable(0);
                    order.getHotel().update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(context, "预定成功", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, activity);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Order", order);
                                intent.putExtra("OrderBundle", bundle);
                                startActivity(intent);
                            } else {
                                Log.i(TAG, "预定失败" + e.toString());
                            }
                        }
                    });
                } else {
                    Log.i(TAG, "预定失败" + e.toString());
                }
            }
        });
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
