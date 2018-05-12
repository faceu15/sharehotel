package com.example.guet.sharehotel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.model.bean.IntegralRecord;

import java.util.List;

/**
 * Created by feng on 2017/11/13.
 */

public class IntegralRecordAdapter extends ArrayAdapter<IntegralRecord> {

    private Context context;

    private int resourceId;

    private View view;

    private ViewHolder viewHolder;


    public static class ViewHolder {
        public View rootView;
        public ImageView image_url_iv;
        public TextView home_address_tv;
        public TextView home_name_tv;
        public TextView integral_tv;
        public TextView time_tv;


        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.image_url_iv = (ImageView) rootView.findViewById(R.id.image_url_iv);
            this.home_address_tv = (TextView) rootView.findViewById(R.id.home_address_tv);
            this.home_name_tv = (TextView) rootView.findViewById(R.id.home_name_tv);
            this.integral_tv = (TextView) rootView.findViewById(R.id.integral_tv);
            this.time_tv = (TextView) rootView.findViewById(R.id.time_tv);


        }

    }

    public IntegralRecordAdapter(Context context, int resource, List<IntegralRecord> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        IntegralRecord integralRecord = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        if (position == 0) {
            viewHolder.image_url_iv.setImageResource(R.mipmap.home1);
        } else if (position == 1) {
            viewHolder.image_url_iv.setImageResource(R.mipmap.home2);
        } else if (position == 2) {
            viewHolder.image_url_iv.setImageResource(R.mipmap.home3);
        } else if (position == 3) {
            viewHolder.image_url_iv.setImageResource(R.mipmap.home4);
        }

        viewHolder.home_address_tv.setText(integralRecord.getHome_address());
        viewHolder.home_name_tv.setText(integralRecord.getHome_name());
        viewHolder.time_tv.setText(integralRecord.getTime());
        if (integralRecord.getState().equals("1")) {
            viewHolder.integral_tv.setText("+" + integralRecord.getIntegral());
        } else {
            viewHolder.integral_tv.setText("-" + integralRecord.getIntegral());
            viewHolder.integral_tv.setTextColor(context.getResources().getColor(R.color.integral));
        }
        return view;
    }

}
