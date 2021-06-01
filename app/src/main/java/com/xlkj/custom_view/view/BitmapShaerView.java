package com.xlkj.custom_view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xlkj.custom_view.R;

public class BitmapShaerView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    public BitmapShaerView(Context context) {
        this(context,null);
    }

    public BitmapShaerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BitmapShaerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        mPaint.setShader(new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);
    }
}
