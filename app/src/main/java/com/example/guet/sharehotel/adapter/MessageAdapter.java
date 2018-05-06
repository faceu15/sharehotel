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
import com.example.guet.sharehotel.bean.Message;

import java.util.List;

/**
 * Created by feng on 2017/11/13.
 */

public class MessageAdapter extends ArrayAdapter<Message> {

    private Context context;

    private int resourceId;

    private View view;

    private ViewHolder viewHolder;


    public static class ViewHolder {
        public View rootView;
        public ImageView image_url_iv;
        public TextView title_tv;
        public TextView time_tv;
        public TextView easy_content_tv;


        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.image_url_iv = (ImageView) rootView.findViewById(R.id.image_url_iv);
            this.title_tv = (TextView) rootView.findViewById(R.id.title_tv);
            this.time_tv = (TextView) rootView.findViewById(R.id.time_tv);
            this.easy_content_tv = (TextView) rootView.findViewById(R.id.easy_content_tv);


        }

    }

    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        if (position == 0) {
            viewHolder.image_url_iv.setImageResource(R.mipmap.message_icon1);
        } else if (position == 1) {
            viewHolder.image_url_iv.setImageResource(R.mipmap.message_icon2);
        }


        viewHolder.title_tv.setText(message.getTitle());
        viewHolder.time_tv.setText(message.getTime());
        viewHolder.easy_content_tv.setText(message.getEasy_content());

        return view;
    }

}
