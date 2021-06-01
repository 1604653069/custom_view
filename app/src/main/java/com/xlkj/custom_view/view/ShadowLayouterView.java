package com.xlkj.custom_view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xlkj.custom_view.R;

public class ShadowLayouterView extends View {
    private Paint mPaint = new Paint();
    private Bitmap mDogBmp;
    public ShadowLayouterView(Context context) {
        this(context,null);
    }

    public ShadowLayouterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShadowLayouterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE,null);//关闭硬件加速
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(25);
        mPaint.setShadowLayer(1,3,3,Color.GRAY);
        mDogBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        mPaint.clearShadowLayer();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawText("张小兰",100,100,mPaint);
        canvas.drawCircle(300,100,50,mPaint);
        canvas.drawBitmap(mDogBmp,null,new Rect(500,50,500+mDogBmp.getWidth(),50+mDogBmp.getHeight()),mPaint);

    }
}
