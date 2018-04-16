package com.example.guet.sharehotel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.model.Coupon;

import java.util.List;

/**
 * Created by feng on 2017/11/13.
 */

public class CouponAdapter extends ArrayAdapter<Coupon> {

    private Context context;

    private int resourceId;

    private View view;

    private ViewHolder viewHolder;


    public static class ViewHolder {
        public View rootView;
        public FrameLayout coupon_frame_layout;
        public TextView time_validate_tv;


        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.coupon_frame_layout = (FrameLayout) rootView.findViewById(R.id.coupon_frame_layout);
            this.time_validate_tv = (TextView) rootView.findViewById(R.id.time_validate_tv);


        }

    }

    public CouponAdapter(Context context, int resource, List<Coupon> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Coupon coupon = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        //如果优惠券金额为200且没有过期
        if (coupon.getPrice().equals("200") && coupon.getState().equals("1")) {
            viewHolder.coupon_frame_layout.setBackgroundResource(R.mipmap.coupon_200);
        } else if (coupon.getPrice().equals("100") && coupon.getState().equals("1"))  //如果优惠券金额为100且没有过期
        {
            viewHolder.coupon_frame_layout.setBackgroundResource(R.mipmap.coupon_100);
        }

        viewHolder.time_validate_tv.setText(coupon.getTime_validate());

        return view;
    }

}
