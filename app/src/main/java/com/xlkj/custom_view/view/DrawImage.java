package com.xlkj.custom_view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.xlkj.custom_view.R;

public class DrawImage extends View {
    private Bitmap bitmap;
    private Paint paint;
    public DrawImage(Context context) {
        this(context,null);
    }

    public DrawImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DrawImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(100,100,100,paint);
    }
}
