package com.example.guet.sharehotel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.CommonAdapter;
import com.example.guet.sharehotel.adapter.ListDropDownAdapter;
import com.example.guet.sharehotel.adapter.ViewHolder;
import com.example.guet.sharehotel.base.BaseActivity;
import com.example.guet.sharehotel.model.bean.Hotel;
import com.example.guet.sharehotel.model.bean.MyCollectionHotel;
import com.example.guet.sharehotel.presenter.FindHotelPrester;
import com.example.guet.sharehotel.view.IFindView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.yyydjk.library.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
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

    private DisplayImageOptions mOptions;

    private List<View> popupViews = new ArrayList<>();
    private DropDownMenu mDropDownMenu;
    private String headers[] = {"类型", "价格", "户型"};
    private ListDropDownAdapter styleAdapter;
    private ListDropDownAdapter priceAdapter;
    private ListDropDownAdapter modeAdapter;
    private String style[] = {"不限", "1室0厅1卫", "1室1厅1卫", "2室1厅1卫", "3室1厅1卫", "3室1厅2卫", "4室1厅2卫"};
    private String price[] = {"不限", "1000以下", "1000-2000", "2000-3000", "3000以上"};
    private String mode[] = {"不限", "整租", "合租"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_find);

        initView();
        showLoading("加载中...");
        mPresenter.fetch(getCity(), getSearchText(), getIntent().getExtras().getString("SearchMode"));
    }

    @Override
    protected FindHotelPrester<IFindView> createPresenter() {
        return new FindHotelPrester<>((IFindView) this);
    }

    private void initView() {
        LinearLayout backLinearLayout = findViewById(R.id.find_back_ll);
        TextView filterTextView = findViewById(R.id.tv_filter);
        listView = findViewById(R.id.list_view);
        mDropDownMenu = findViewById(R.id.ddm_find_filter);
        backLinearLayout.setOnClickListener(this);
        filterTextView.setOnClickListener(this);

        //户型
        final ListView styleView = new ListView(this);
        styleView.setDividerHeight(0);
        styleAdapter = new ListDropDownAdapter(this, Arrays.asList(style));
        styleView.setAdapter(styleAdapter);

        //价格
        final ListView priceView = new ListView(this);
        priceView.setDividerHeight(0);
        priceAdapter = new ListDropDownAdapter(this, Arrays.asList(price));
        priceView.setAdapter(priceAdapter);

        //模式
        final ListView modeView = new ListView(this);
        modeView.setDividerHeight(0);
        modeAdapter = new ListDropDownAdapter(this, Arrays.asList(mode));
        modeView.setAdapter(modeAdapter);

        popupViews.add(styleView);
        popupViews.add(priceView);
        popupViews.add(modeView);

        styleView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                styleAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : style[position]);
                String msg = style[position];

                mDropDownMenu.closeMenu();
            }
        });

        priceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                priceAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : price[position]);
                mDropDownMenu.closeMenu();
            }
        });

        modeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                modeAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : mode[position]);
                mDropDownMenu.closeMenu();
            }
        });


        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
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
        mOptions = createOptions();
        listView.setAdapter(new CommonAdapter<Hotel>(this, datas, R.layout.find_listview_item) {
            @Override
            public void convert(ViewHolder viewHolder, Hotel hotel) {
                viewHolder.setImageBitmap(R.id.iv_find_hotel, hotel.getUrl(), mOptions);
                viewHolder.setText(R.id.tv_name, hotel.getName());
                viewHolder.setText(R.id.tv_address, hotel.getAddress());
                viewHolder.setText(R.id.tv_find_mode, hotel.getMode());
                viewHolder.setText(R.id.tv_find_housetype, hotel.getHouseType());
                viewHolder.setText(R.id.tv_find_area, hotel.getArea().toString());
                viewHolder.setText(R.id.tv_price, hotel.getPrice().toString());
            }
        });
        hideLoading();
        listView.setOnItemClickListener(this);
    }

    private DisplayImageOptions createOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.image_loading)
                .showImageForEmptyUri(R.mipmap.image_loading)
                .showImageOnFail(R.mipmap.image_loading)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                // .delayBeforeLoading(100)
                .build();
        return options;
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
