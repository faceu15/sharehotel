package com.example.guet.sharehotel.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.CommonAdapter;
import com.example.guet.sharehotel.adapter.ViewHolder;
import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.model.bean.Hotel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyHouseActivity extends AppCompatActivity {

    private static final String TAG = "MyHouseActivity";

    @BindView(R.id.lv_myhouse)
    ListView mMyHouseListView;
    @BindView(R.id.tv_myhouse_back)
    TextView backTextView;


    private AlertDialog mAlertDialog;
    AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_my_house);
        ButterKnife.bind(this);
        mBuilder = new AlertDialog.Builder(this);
        initView(this);

    }

    private void initView(final Context context) {
        BmobQuery<Hotel> query = new BmobQuery<>("Hotel");
        query.addWhereEqualTo("host", MyApplication.getInstance().getUser());
        query.findObjects(new FindListener<Hotel>() {
            @Override
            public void done(List<Hotel> list, BmobException e) {
                if (e == null) {
                    if (list.size() != 0) {
                        mMyHouseListView.setAdapter(new CommonAdapter<Hotel>(context, list, R.layout.myhouse_item) {
                            @Override
                            public void convert(ViewHolder viewHolder, final Hotel hotel) {
                                viewHolder.setImageBitmap(R.id.iv_myhouse_photo, hotel.getUrl(), createOptions());
                                viewHolder.setText(R.id.tv_myhouse_name, hotel.getName());
                                viewHolder.setText(R.id.tv_myhouse_address, hotel.getAddress());
                                viewHolder.setText(R.id.tv_myhouse_price, hotel.getPrice().toString());
                                viewHolder.setText(R.id.tv_myhouse_mode, hotel.getMode());
                                viewHolder.setText(R.id.tv_myhouse_housetype, hotel.getHouseType());
                                viewHolder.setText(R.id.tv_myhouse_area, hotel.getArea().toString());
                                final Button editButton = viewHolder.getView(R.id.btn_myhouse_edit);
                                final Button rentalButton = viewHolder.getView(R.id.btn_myhouse_rental);
                                if (hotel.getAvailable() == 1) {
                                    rentalButton.setText("下架");
                                    editButton.setVisibility(View.INVISIBLE);
                                }
                                rentalButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (hotel.getAvailable() == 0) {
                                            mBuilder.setTitle("出租");
                                            mBuilder.setMessage("确定将房子出租吗？");
                                        } else if (hotel.getAvailable() == 1) {
                                            mBuilder.setTitle("下架");
                                            mBuilder.setMessage("确定要将房子下架吗？");
                                        }
                                        mBuilder.setCancelable(true);
                                        mBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, int which) {
                                                if (hotel.getAvailable() == 1) {
                                                    hotel.setAvailable(0);
                                                } else {
                                                    hotel.setAvailable(1);
                                                }

                                                hotel.update(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if (e == null) {
                                                            Log.i("PostHouseActivity", "发布出租");
                                                            dialog.dismiss();
                                                            if (hotel.getAvailable() == 1) {
                                                                Toast.makeText(context, "已发布房子出租信息", Toast.LENGTH_SHORT).show();
                                                                rentalButton.setText("下架");
                                                                editButton.setVisibility(View.INVISIBLE);
                                                            } else {
                                                                Toast.makeText(context, "成功下架房子", Toast.LENGTH_SHORT).show();
                                                                rentalButton.setText("出租");
                                                                editButton.setVisibility(View.VISIBLE);
                                                            }


                                                        } else {
                                                            Log.i("PostHouseActivity", "出租失败" + e.toString());
                                                        }
                                                    }
                                                });

                                            }
                                        });
                                        mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        mAlertDialog = mBuilder.create();
                                        mAlertDialog.show();


                                    }
                                });

                            }
                        });
                    }
                } else {
                    Log.i(TAG, "加载我的房子失败" + e.toString());
                }
            }
        });


    }

    @OnClick({R.id.tv_myhouse_back})
    public void onClick(View View) {
        finish();
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
