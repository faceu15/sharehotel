package com.example.guet.sharehotel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.view.AboutIntegralPopupWindow;

/**
 * 个人中心->信用积分
 */
public class CreditScoreActivity extends AppCompatActivity implements android.view.GestureDetector.OnGestureListener, View.OnClickListener {


    //定义手势检测器实例
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_score);

        //this.getSupportActionBar().hide();
        iniView();

        //创建手势检测器
        detector = new GestureDetector(this, this);
    }

    private void iniView() {

        ImageView close_iv = (ImageView) findViewById(R.id.iv_back);
        close_iv.setOnClickListener(this);
        TextView record_tv = (TextView) findViewById(R.id.record_tv);
        record_tv.setOnClickListener(this);
    }

    //将该activity上的触碰事件交给GestureDetector处理
    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return detector.onTouchEvent(me);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    /**
     * 滑屏监测
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        float minMove = 120;         //最小滑动距离
        float minVelocity = 0;      //最小滑动速度
        float beginX = e1.getX();
        float endX = e2.getX();
        float beginY = e1.getY();
        float endY = e2.getY();

        if (beginY - endY > minMove && Math.abs(velocityY) > minVelocity) {   //上滑

            //显示关于积分的弹出窗口
            AboutIntegralPopupWindow aboutIntegralPopupWindow = new AboutIntegralPopupWindow(CreditScoreActivity.this);
            aboutIntegralPopupWindow.showAtLocation(findViewById(R.id.parent_linear_lay), Gravity.BOTTOM, 0, 0);          //是的弹窗在其父布局的底部，坐标为0,0开始
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.record_tv:
                Intent intent = new Intent(CreditScoreActivity.this, IntegralRecordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
