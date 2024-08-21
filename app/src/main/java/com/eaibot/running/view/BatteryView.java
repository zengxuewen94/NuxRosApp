package com.eaibot.running.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class BatteryView extends View {

    private RectF mMainRect;

    private RectF mHeadRect;

    private int mMargin = 1;    //电池内芯与边框的距离

    private int mBoder = 2;     //电池外框的宽带

    private int mWidth = 15;       //总长   //    private int mWidth = 30;

    private int mHeight = 21;       //总高  //    private int mHeight = 17;

    private int mHeadWidth = 5;     //    private int mHeadWidth = 3;

    private int mHeadHeight = 3;    //    private int mHeadHeight = 7;

    private float mRadius = 2f;     //圆角

    private boolean isCharging = false;     //是否在充电

    private boolean isVertical = true;

    private float mPower = 0.2f;

    public void setmMargin(int mMargin){
        this.mMargin = mMargin;
    }

    public void setmBoder(int mBoder) {
        this.mBoder = mBoder;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
        initView();
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public void setmHeadWidth(int mHeadWidth) {
        this.mHeadWidth = mHeadWidth;
    }

    public void setmHeadHeight(int mHeadHeight) {
        this.mHeadHeight = mHeadHeight;
    }

    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public void setIsCharging(boolean isCharging){
        this.isCharging = isCharging;
        invalidate();
    }

    public void setIsVertical(boolean isVertical){
        this.isVertical = isVertical;
    }

    public void setPower(float power) {
        mPower = power;
        invalidate();
    }

    public BatteryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BatteryView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        if(isVertical){
            mHeadRect = new RectF((mWidth - mHeadWidth)/2, 0, (mWidth + mHeadWidth)/2, mHeadHeight);

            float left = mBoder;
            float top = mHeadRect.height();
            float right = mWidth-mBoder;
            float bottom = mHeight-mBoder;
            mMainRect = new RectF(left, top, right, bottom);
        }else {
            mHeadRect = new RectF(0, (mHeight - mHeadHeight)/2, mHeadWidth, (mHeight + mHeadHeight)/2);

            float left = mHeadRect.width();
            float top = mBoder;
            float right = mWidth-mBoder;
            float bottom = mHeight-mBoder;
            mMainRect = new RectF(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint1 = new Paint();

        //画电池头
        paint1.setStyle(Paint.Style.FILL);  //实心
        paint1.setColor(Color.parseColor("#666666"));
        canvas.drawRect(mHeadRect, paint1);

        //画外框
        paint1.setStyle(Paint.Style.STROKE);    //设置空心矩形
        paint1.setStrokeWidth(mBoder);          //设置边框宽度
        paint1.setColor(Color.parseColor("#666666"));
        canvas.drawRoundRect(mMainRect, mRadius, mRadius, paint1);

        //画电池芯
        Paint paint = new Paint();
        if (isCharging) {
            paint.setColor(Color.GREEN);
        } else {
            if (mPower <= 0.2f) {
                paint.setColor(Color.RED);
            }else if(mPower <= 0.3f){
                paint.setColor(Color.parseColor("#ff8c00"));
            } else if(mPower <= 0.5f){
                paint.setColor(Color.parseColor("#FFCE84"));
            }else {
                paint.setColor(Color.parseColor("#c0c0c0"));
            }
        }

        int height,width,top,bottom,left,right;
        if (isVertical){
            height   = (int) (mPower * (mMainRect.height() - mMargin*2));
            top    = (int) (mMainRect.bottom - mMargin - height);
            bottom   = (int) (mMainRect.bottom - mMargin);
            left     = (int) (mMainRect.left + mMargin);
            right  = (int) (mMainRect.right - mMargin);
        }else{
            width   = (int) (mPower * (mMainRect.width() - mMargin*2));
            left    = (int) (mMainRect.right - mMargin - width);
            right   = (int) (mMainRect.right - mMargin);
            top     = (int) (mMainRect.top + mMargin);
            bottom  = (int) (mMainRect.bottom - mMargin);
        }

        Rect rect = new Rect(left,top,right, bottom);
        canvas.drawRect(rect, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }

}
