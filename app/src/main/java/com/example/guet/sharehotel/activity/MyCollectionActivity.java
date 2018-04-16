package com.example.guet.sharehotel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.MyCollectionHotelAdapter;
import com.example.guet.sharehotel.model.MyCollectionHotel;

import java.util.ArrayList;

/**
 * 个人中心->我的收藏
 */
public class MyCollectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "MyCollectionActivity";
    private MyCollectionHotelAdapter myCollectionHotelAdapter;
    /**
     * MyCollectionHotel表上的数据
     */
    private ArrayList<MyCollectionHotel> myCollectionHotelArrayList = new ArrayList<MyCollectionHotel>();
    private ProgressDialog progressDialog;
    private TextView fenlei_tv;
    private ImageView close_iv;
    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        initView();

        getSupportActionBar().hide();


    }

    private void initView() {
        fenlei_tv = (TextView) findViewById(R.id.fenlei_tv);
        fenlei_tv.setOnClickListener(this);

        close_iv = (ImageView) findViewById(R.id.close_iv);
        close_iv.setOnClickListener(this);

        MyCollectionHotel myCollectionHotel = new MyCollectionHotel("1", "4", R.mipmap.bg1, 4, "伊维特国际酒店", 239, 449);
        myCollectionHotelArrayList.add(myCollectionHotel);
        myCollectionHotelArrayList.add(myCollectionHotel);
        myCollectionHotelArrayList.add(myCollectionHotel);

        myCollectionHotelAdapter = new MyCollectionHotelAdapter(MyCollectionActivity.this, R.layout.activity_my_collection_item, myCollectionHotelArrayList);
        list_view = (ListView) findViewById(R.id.list_view);
        list_view.setAdapter(myCollectionHotelAdapter);
        list_view.setOnItemClickListener(this);

    }

    /**
     * 初始化我的收藏的ListView数据
     */
    private void initAdapterData() {

        this.showProgressBar();     //显示加载框

//        //从数据库根据用户ID获取酒店ID
//        BmobQuery<CollectionHotel> query = new BmobQuery<CollectionHotel>();
//        query.addWhereEqualTo("userId" , LoginActivity.getAccount());
//        //返回50条数据，如果不加上这条语句，默认返回10条数据
//        query.setLimit(50);
//        //执行查询方法
//        query.findObjects(new FindListener<CollectionHotel>() {
//            @Override
//            public void done(List<CollectionHotel> object, BmobException e) {
//
//                if (e == null) {
//                    for (CollectionHotel collectionHotel : object) {
//                        collectionHotelArrayList.add(collectionHotel);
//                    }
//
//                    getMyCollectionHotel();  //根据酒店ID，获取酒店数据
//
//                } else {
//                    LogUtil.e(TAG, "失败：" + e.getMessage() + "," + e.getErrorCode());//打印失败
//                }
//            }
//        });

        /*MyCollectionHotel myCollectionHotel = new MyCollectionHotel(R.mipmap.collection_hotel1  ,5  , "城市便捷酒店(桂林火车站龙船坪店)" , "象山区中山南路32号" , 120);
        myCollectionHotelArrayList.add(myCollectionHotel);

        MyCollectionHotel myCollectionHotel1 = new MyCollectionHotel(R.mipmap.collection_hotel2 ,4 ,   "7天连锁酒店(桂林兴安乐满地店)" , "象山区中山南路32号" , 160);
        myCollectionHotelArrayList.add(myCollectionHotel1);


        MyCollectionHotel myCollectionHotel2 = new MyCollectionHotel(R.mipmap.collection_hotel3 , 5  , "瓦舍旅行酒店(阳朔西街点)" , "象山区中山南路32号" , 45);
        myCollectionHotelArrayList.add(myCollectionHotel2);

        MyCollectionHotel myCollectionHotel3 = new MyCollectionHotel(R.mipmap.collection_hotel4  ,3  ,  "7天连锁酒店桂林八里街点", "象山区中山南路32号" , 120);
        myCollectionHotelArrayList.add(myCollectionHotel3);

        MyCollectionHotel myCollectionHotel4 = new MyCollectionHotel(R.mipmap.collection_hotel5  , 4 , "阳朔桂林金水湾国际大酒店" , "象山区中山南路32号" , 280);
        myCollectionHotelArrayList.add(myCollectionHotel4);*/
    }


    /**
     * 显示加载框
     */
    private void showProgressBar() {

        progressDialog = new ProgressDialog(MyCollectionActivity.this, 1);//后面的参数是风格，1比较好看
        progressDialog.setMessage("数据正在加载中......");
        progressDialog.show();
    }


    /**
     * 根据酒店ID，获取酒店数据
     */
    private void getMyCollectionHotel() {

        myCollectionHotelArrayList.removeAll(myCollectionHotelArrayList);
//        for (CollectionHotel c:collectionHotelArrayList) {
//            getHotelInfoFromBmob(c); //获取酒店信息
//        }
    }

    /**
     * 获取酒店信息
     */
//    private void getHotelInfoFromBmob(CollectionHotel c) {
//
//        //从数据库根据酒店ID获取酒店数据
//        BmobQuery<Hotel> query = new BmobQuery<Hotel>();
//        query.addWhereEqualTo("hotelId" , c.getHotelId());
//        //返回50条数据，如果不加上这条语句，默认返回10条数据
//        query.setLimit(50);
//        //执行查询方法
//        query.findObjects(new FindListener<Hotel>() {
//            @Override
//            public void done(List<Hotel> object, BmobException e) {
//
//                if (e == null) {
//                    for (Hotel h : object) {
//
//                        MyCollectionHotel myCollectionHotel = new MyCollectionHotel();
//                        myCollectionHotel.setScore(Integer.parseInt(h.getHotelGrade()));
//                        myCollectionHotel.setName(h.getHotelName());
//                        myCollectionHotel.setAddress(h.getHotelAddress());
//                        myCollectionHotel.setMinValue(Integer.parseInt(h.getHotelMinStartPrice()));
//                        myCollectionHotelArrayList.add(myCollectionHotel);
//                    }
//
//                    myCollectionHotelAdapter.notifyDataSetChanged();
//                    progressDialog.cancel();     //加载完数据后使加载框消失。
//
//                } else {
//                    LogUtil.e(TAG, "失败：" + e.getMessage() + "," + e.getErrorCode());//打印失败
//                }
//            }
//        });
//
//    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //点击了我的收藏ListView的item项则弹出酒店信息。
        // parent是识别是哪个listview；
        // view是当前listview的item的view的布局，就是可以用这个view，获取里面的控件的id后操作控件
        // position是当前item在listview中适配器里的位置
        // id是当前item在listview里的第几行的位置

//        MyCollectionHotel myCollectionHotel=myCollectionHotelArrayList.get(position);
//
//        String hotelName = myCollectionHotel.getName();
//        int hotelScore = myCollectionHotel.getScore();
//        int hotelMinPrice = myCollectionHotel.getMinValue();
//
//        Intent intent = new Intent(MyCollectionActivity.this, HotelSelectedInfoActivity.class);
//        intent.putExtra("hotelName", hotelName);
//        intent.putExtra("hotelScore", hotelScore + "");
//        intent.putExtra("hotelMinPrice", hotelMinPrice + "");
//        startActivity(intent);

        Intent intent = new Intent(MyCollectionActivity.this, MyCollectionActivity1.class);
        startActivity(intent);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.close_iv:
                this.finish();
                //提示VM清理缓存
                System.gc();           //指示VM,它将是一个很好的时间来运行垃圾收集器。请注意,这只是一个提示。没有保证垃圾收集器会运行。
                break;
        }

    }


}
