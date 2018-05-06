package com.example.guet.sharehotel.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.MyCollectionHotelAdapter1;
import com.example.guet.sharehotel.bean.MyCollectionHotel1;
import com.example.guet.sharehotel.view.LoadingDialog;

import java.util.ArrayList;

/**
 * 个人中心->我的收藏->点击酒店
 */
public class MyCollectionActivity1 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MyCollectionActivity1";
    private MyCollectionHotelAdapter1 myCollectionHotelAdapter;
    /**
     * MyCollectionHotel表上的数据
     */
    private ArrayList<MyCollectionHotel1> myCollectionHotelArrayList = new ArrayList<MyCollectionHotel1>();

    private ImageView close_iv;
    private LoadingDialog loading_dialog;
    private TextView fenlei_tv;
    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection1);
        //this.getSupportActionBar().hide();
        initView();


    }

    private void initView() {

        close_iv = (ImageView) findViewById(R.id.iv_back);
        close_iv.setOnClickListener(this);
        fenlei_tv = (TextView) findViewById(R.id.fenlei_tv);
        fenlei_tv.setOnClickListener(this);


        MyCollectionHotel1 myCollectionHotel = new MyCollectionHotel1("1", "4", R.mipmap.bg1, 4, "伊维特国际酒店", "新华大街中山路", 239, 449);
        myCollectionHotelArrayList.add(myCollectionHotel);
        myCollectionHotelArrayList.add(myCollectionHotel);
        myCollectionHotelArrayList.add(myCollectionHotel);

        myCollectionHotelAdapter = new MyCollectionHotelAdapter1(MyCollectionActivity1.this, R.layout.activity_my_collection1_item, myCollectionHotelArrayList);
        list_view = (ListView) findViewById(R.id.list_view);
        list_view.setAdapter(myCollectionHotelAdapter);
        list_view.setOnItemClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
