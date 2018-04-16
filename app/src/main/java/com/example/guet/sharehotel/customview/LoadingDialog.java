package com.example.guet.sharehotel.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by feng on 2016/10/24.
 * 加载框
 */

public class LoadingDialog extends RelativeLayout {
    /**
     * 像素密度
     */
    float scale;
    /**
     * 控件高度
     */
    float height;
    /**
     * 控件宽度
     */
    float with;
    /**
     * 球半径
     */
    private float CIRCLE_R;
    /**
     * 球间距
     */
    private float CIRCLE_SPACE;
    /**
     * 球1半径
     */
    private float CIRCLE_R1;
    /***球2半径*/
    private float CIRCLE_R2;
    /**
     * 球3半径
     */
    private float CIRCLE_R3;
    /**
     * 球4半径
     */
    private float CIRCLE_R4;
    /**
     * 球5半径
     */
    private float CIRCLE_R5;
    /**
     * 球1状态，true缩小false增大
     */
    private boolean CIRCLE_R1_STATE = true;
    /**
     * 球2状态，true缩小false增大
     */
    private boolean CIRCLE_R2_STATE = true;
    /**
     * 球3状态，true缩小false增大
     */
    private boolean CIRCLE_R3_STATE = true;
    /**
     * 球4状态，true缩小false增大
     */
    private boolean CIRCLE_R4_STATE = true;
    /**
     * 球5状态，true缩小false增大
     */
    private boolean CIRCLE_R5_STATE = true;

    public LoadingDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        //LayoutInflater.from(context).inflate(R.layout.dialog_load, this, true);
        scale = context.getResources().getDisplayMetrics().density;       //获得像素密度
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        height = this.getHeight();
        with = this.getWidth();
        CIRCLE_R = with / 3 / 3 / 2;
        float temp = CIRCLE_R / 6;
        CIRCLE_R1 = CIRCLE_R;
        CIRCLE_R2 = CIRCLE_R - temp;
        CIRCLE_R3 = CIRCLE_R - 2 * temp;
        CIRCLE_SPACE = 1 * scale;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(CIRCLE_R);
        paint.setStyle(Paint.Style.FILL);
        //画三个圆
        //第一个
        canvas.drawCircle(with / 2 - 2 * CIRCLE_R - CIRCLE_SPACE,
                height / 3,
                CIRCLE_R1,
                paint
        );
        if (CIRCLE_R1_STATE) {
            CIRCLE_R1 = CIRCLE_R1 - 0.5f;
            if (CIRCLE_R1 <= CIRCLE_R / 2) {
                CIRCLE_R1_STATE = false;
            }
        } else {
            CIRCLE_R1 = CIRCLE_R1 + 0.5f;
            if (CIRCLE_R1 >= CIRCLE_R) {
                CIRCLE_R1_STATE = true;
            }
        }
        //第二个
        canvas.drawCircle(with / 2,
                height / 3,
                CIRCLE_R2,
                paint
        );
        if (CIRCLE_R2_STATE) {
            CIRCLE_R2 = CIRCLE_R2 - 0.5f;
            if (CIRCLE_R2 <= CIRCLE_R / 2) {
                CIRCLE_R2_STATE = false;
            }
        } else {
            CIRCLE_R2 = CIRCLE_R2 + 0.5f;
            if (CIRCLE_R2 >= CIRCLE_R) {
                CIRCLE_R2_STATE = true;
            }
        }
        //第三个
        canvas.drawCircle(with / 2 + 2 * CIRCLE_R + CIRCLE_SPACE,
                height / 3,
                CIRCLE_R3,
                paint
        );
        if (CIRCLE_R3_STATE) {
            CIRCLE_R3 = CIRCLE_R3 - 0.5f;
            if (CIRCLE_R3 <= CIRCLE_R / 2) {
                CIRCLE_R3_STATE = false;
            }
        } else {
            CIRCLE_R3 = CIRCLE_R3 + 0.5f;
            if (CIRCLE_R3 >= CIRCLE_R) {
                CIRCLE_R3_STATE = true;
            }
        }
        postInvalidateDelayed(30);
    }


}
