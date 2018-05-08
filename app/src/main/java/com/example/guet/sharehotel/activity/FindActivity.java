package com.example.guet.sharehotel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.CommonAdapter;
import com.example.guet.sharehotel.adapter.ViewHolder;
import com.example.guet.sharehotel.base.BaseActivity;
import com.example.guet.sharehotel.bean.Hotel;
import com.example.guet.sharehotel.bean.MyCollectionHotel;
import com.example.guet.sharehotel.presenter.FindHotelPrester;
import com.example.guet.sharehotel.view.IFindView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页->搜索
 */
public class FindActivity extends BaseActivity<IFindView, FindHotelPrester<IFindView>> implements IFindView, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "FindActivity";

    /**
     * MyCollectionHotel表上的数据
     */
    private ArrayList<MyCollectionHotel> myCollectionHotelArrayList = new ArrayList<MyCollectionHotel>();

    private ProgressDialog progressDialog;

    private ListView listView;

    private List<Hotel> mCities = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        initView();
        showLoading("加载中...");
        mPresenter.fetch(getCity(), getSearchText());
    }

    @Override
    protected FindHotelPrester<IFindView> createPresenter() {
        return new FindHotelPrester<>((IFindView) this);
    }

    private void initView() {
        LinearLayout backLinearLayout = findViewById(R.id.find_back_ll);
        TextView filterTextView = findViewById(R.id.tv_filter);
        listView = findViewById(R.id.list_view);

        backLinearLayout.setOnClickListener(this);
        filterTextView.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Hotel hotel = mCities.get(position);
        Log.i(TAG, "选择：" + hotel.getName());
        Bundle bundle = new Bundle();
        bundle.putSerializable("Hotel", hotel);
        Intent intent = new Intent(FindActivity.this, HotelSelectedInfoActivity.class);
        intent.putExtra("HotelBundle", bundle);
        intent.putExtra("CheckInDate", getIntent().getExtras().getString("CheckInDate"));
        intent.putExtra("CheckOutDate", getIntent().getExtras().getString("CheckOutDate"));
        intent.putExtra("RoomNumber", getIntent().getExtras().getString("RoomNumber"));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_back_ll:
                this.finish();
                //提示VM清理缓存
                System.gc();           //指示VM,它将是一个很好的时间来运行垃圾收集器。请注意,这只是一个提示。没有保证垃圾收集器会运行。
                break;
            case R.id.tv_filter:
                Intent intent = new Intent(FindActivity.this, FilterHotelActivity.class);

                startActivity(intent);
                break;
        }
    }


    @Override
    public void showResult(List<Hotel> datas) {
        mCities = datas;
        listView.setAdapter(new CommonAdapter<Hotel>(this, datas, R.layout.find_listview_item) {
            @Override
            public void convert(ViewHolder viewHolder, Hotel hotel) {
                viewHolder.setImageResource(R.id.iv_find_hotel, getResources().getIdentifier("collection1", "mipmap", getPackageName()));
                viewHolder.setText(R.id.tv_name, hotel.getName());
                viewHolder.setText(R.id.tv_address, hotel.getAddress());
                viewHolder.setText(R.id.tv_grade, hotel.getGrade().toString());
                viewHolder.setText(R.id.tv_comment_num, hotel.getComment().toString());
                viewHolder.setText(R.id.tv_price, hotel.getPrice().toString());
            }
        });
        hideLoading();
        listView.setOnItemClickListener(this);
    }

    @Override
    public String getCity() {
        return getIntent().getStringExtra("City");
    }

    @Override
    public String getSearchText() {
        return getIntent().getStringExtra("Search");
    }

    @Override
    public void showLoading(String msg) {
        progressDialog = new ProgressDialog(FindActivity.this, 1);//后面的参数是风格，1比较好看
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        progressDialog.hide();
    }

    @Override
    public void showError(String msg) {
        hideLoading();
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }
}
