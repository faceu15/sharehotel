package com.example.guet.sharehotel.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.CommonAdapter;
import com.example.guet.sharehotel.adapter.ViewHolder;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.model.bean.Collection;
import com.example.guet.sharehotel.utils.DateUtil;
import com.example.guet.sharehotel.view.dialog.AlertDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 个人中心->我的收藏->点击酒店
 */
public class MyCollectionActivity1 extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final String TAG = "MyCollectionActivity1";

    @BindView(R.id.tv_collection_back)
    TextView mBack;
    @BindView(R.id.list_view)
    ListView mListView;

    private AlertDialog mAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_collection1);
        ButterKnife.bind(this);
        initView();
        initData(this);

    }

    private void initData(final Context context) {
        BmobQuery<Collection> query = new BmobQuery<>("Collection");
        query.include("user,hotel");
        query.addWhereEqualTo("user", MyApplication.getInstance().getUser());
        query.findObjects(new FindListener<Collection>() {
            @Override
            public void done(final List<Collection> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        mListView.setAdapter(new CommonAdapter<Collection>(context, list, R.layout.activity_my_collection1_item) {
                            @Override
                            public void convert(ViewHolder viewHolder, final Collection item) {
                                viewHolder.setImageBitmap(R.id.iv_collection_photo, item.getHotel().getUrl(), createOptions());
                                viewHolder.setText(R.id.tv_collection_name, item.getHotel().getName());
                                viewHolder.setText(R.id.tv_collection_address, item.getHotel().getAddress());
                                viewHolder.setText(R.id.tv_collection_mode, item.getHotel().getMode());
                                viewHolder.setText(R.id.tv_collection_housetype, item.getHotel().getHouseType());
                                viewHolder.setText(R.id.tv_collection_area, item.getHotel().getArea().toString());
                                viewHolder.setText(R.id.tv_collection_price, item.getHotel().getPrice().toString());
                                ImageView likeImageView = viewHolder.getView(R.id.iv_collection_like);
                                likeImageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mAlertDialog = new AlertDialog(context, true, "", "取消收藏？",
                                                true, 2, new AlertDialog.OnDialogButtonClickListener() {
                                            @Override
                                            public void onDialogButtonClick(int requestCode, boolean isPositive) {
                                                if (requestCode == 2) {
                                                    item.delete(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                            if (e == null) {
                                                                list.remove(item);
                                                                notifyDataSetChanged();
                                                                Log.i(TAG, "删除成功");
                                                            } else {
                                                                Log.i(TAG, "删除失败");
                                                            }

                                                        }
                                                    });
                                                }
                                            }
                                        });
                                        mAlertDialog.show();
                                    }
                                });
                                Button bookButton = viewHolder.getView(R.id.btn_collection_book);
                                bookButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(context, HotelSelectedInfoActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("Hotel", item.getHotel());
                                        intent.putExtra("HotelBundle", bundle);
                                        intent.putExtra("CheckInDate", DateUtil.getTomorro());
                                        intent.putExtra("CheckOutDate", DateUtil.getDayNextMonth());
                                        startActivity(intent);
                                    }
                                });


                            }
                        });
                    } else {
                        Toast.makeText(context, "没有收藏", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.i(TAG, "收藏加载失败");
                }
            }
        });

    }

    private void initView() {
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_collection_back:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
}
