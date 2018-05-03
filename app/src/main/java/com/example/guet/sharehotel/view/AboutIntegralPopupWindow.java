package com.example.guet.sharehotel.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.guet.sharehotel.R;


/**
 * Created by feng on 2016/8/10.
 * 关于积分弹窗
 */
public class AboutIntegralPopupWindow extends PopupWindow implements View.OnClickListener {

    private static Context mContext;
    private View view;
    private View myParentView;

    /**
     * @param context
     */
    public AboutIntegralPopupWindow(Context context) {

        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.pop_up_window, null);

        setOutsideTouchable(true);     //设置外部可点击
        this.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                int height = view.findViewById(R.id.pop_up_window_linear_layout).getTop();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        //设置弹出窗口的视图
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.popup_anim);
    }


    @Override
    public void onClick(View view) {


    }


    public static Context getmContext() {
        return mContext;
    }

}