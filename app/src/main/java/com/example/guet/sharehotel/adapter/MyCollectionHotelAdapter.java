package com.example.guet.sharehotel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.bean.MyCollectionHotel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2016/7/25.
 */
public class MyCollectionHotelAdapter extends ArrayAdapter<MyCollectionHotel> {

    private int resourcedId;

    private Context myContext;

    private ViewHolder viewHolder;

    private View view;

    private ArrayList<MyCollectionHotel> myCollectionHotelArrayList;

    /**
     * 图片资源id
     */
    private ImageView hotel_iv;
    /**
     * 酒店评分
     */
    private TextView hotelScore_tv;
    /**
     * 酒店红心1
     */
    private ImageView hotelRedHeart1;
    /**
     * 酒店红心2
     */
    private ImageView hotelRedHeart2;
    /**
     * 酒店红心3
     */
    private ImageView hotelRedHeart3;
    /**
     * 酒店红心4
     */
    private ImageView hotelRedHeart4;
    /**
     * 酒店红心5
     */
    private ImageView hotelRedHeart5;
    /**
     * 酒店名称
     */
    private TextView hotelName_tv;
    /**
     * 酒店地址
     */
    private TextView hotelAddrss_tv;
    /**
     * 酒店最低价
     */
    private TextView hotelMinValue_tv;

    class ViewHolder {
        /**
         * 图片资源id
         */
        LinearLayout hotel_bg_linear_layout;
        /**
         * 酒店评价
         */
        TextView hotel_comment_tv;
        /**
         * 酒店红心1
         */
        ImageView start1_iv;
        /**
         * 酒店红心2
         */
        ImageView start2_iv;
        /**
         * 酒店红心3
         */
        ImageView start3_iv;
        /**
         * 酒店红心4
         */
        ImageView start4_iv;
        /**
         * 酒店红心5
         */
        ImageView start5_iv;
        /**
         * 酒店名称
         */
        TextView hotel_name_tv;
        /**
         * 酒店最低价
         */
        TextView price_tv;
    }

    public MyCollectionHotelAdapter(Context context, int resource, List<MyCollectionHotel> objects) {
        super(context, resource, objects);
        myContext = context;
        resourcedId = resource;
        myCollectionHotelArrayList = (ArrayList<MyCollectionHotel>) objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyCollectionHotel myCollectionHotel = this.getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(myContext).inflate(resourcedId, null);
            viewHolder = new ViewHolder();
            viewHolder.hotel_bg_linear_layout = (LinearLayout) view.findViewById(R.id.hotel_bg_linear_layout);
            viewHolder.hotel_comment_tv = (TextView) view.findViewById(R.id.hotel_comment_tv);
            viewHolder.start1_iv = (ImageView) view.findViewById(R.id.start1_iv);
            viewHolder.start2_iv = (ImageView) view.findViewById(R.id.start2_iv);
            viewHolder.start3_iv = (ImageView) view.findViewById(R.id.start3_iv);
            viewHolder.start4_iv = (ImageView) view.findViewById(R.id.start4_iv);
            viewHolder.start5_iv = (ImageView) view.findViewById(R.id.start5_iv);
            viewHolder.hotel_name_tv = (TextView) view.findViewById(R.id.hotel_name_tv);
            viewHolder.price_tv = (TextView) view.findViewById(R.id.price_tv);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

        }

        viewHolder.hotel_bg_linear_layout.setBackgroundResource(R.mipmap.bg1);
        viewHolder.hotel_name_tv.setText(myCollectionHotel.getName());
        viewHolder.price_tv.setText("CNY ￥" + myCollectionHotel.getMinValue() + "/天");    //这里要转换为字符串，否者被认为是字符串资源id

        viewHolder.start1_iv.setVisibility(View.VISIBLE);
        viewHolder.start2_iv.setVisibility(View.VISIBLE);
        viewHolder.start3_iv.setVisibility(View.VISIBLE);
        viewHolder.start4_iv.setVisibility(View.VISIBLE);
        viewHolder.start5_iv.setVisibility(View.VISIBLE);

        viewHolder.hotel_comment_tv.setText("（" + myCollectionHotel.getComment_num() + "条评论）");
        //根据评分来显示星级
        switch (myCollectionHotel.getScore()) {
            case 1:
                viewHolder.start2_iv.setVisibility(View.INVISIBLE);
                viewHolder.start3_iv.setVisibility(View.INVISIBLE);
                viewHolder.start4_iv.setVisibility(View.INVISIBLE);
                viewHolder.start5_iv.setVisibility(View.INVISIBLE);
                break;
            case 2:
                viewHolder.start3_iv.setVisibility(View.INVISIBLE);
                viewHolder.start4_iv.setVisibility(View.INVISIBLE);
                viewHolder.start5_iv.setVisibility(View.INVISIBLE);
                break;
            case 3:
                viewHolder.start4_iv.setVisibility(View.INVISIBLE);
                viewHolder.start5_iv.setVisibility(View.INVISIBLE);
                break;
            case 4:
                viewHolder.start5_iv.setVisibility(View.INVISIBLE);
                break;
        }

        return view;
    }
}
