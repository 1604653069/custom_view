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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.xlkj.custom_view.R;

public class TelescopeView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private Bitmap bitmapBg;
    private int dx =-1;
    private int dy =-1;
    public TelescopeView(Context context) {
        this(context,null);
    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TelescopeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(bitmapBg==null){
            bitmapBg = Bitmap.createBitmap(getWidth(),getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas1 = new Canvas(bitmapBg);
            canvas1.drawBitmap(bitmap,null,new Rect(0,0,getWidth(),getHeight()    ),paint);
        }
        if(dx!=-1&&dy!=-1){
            paint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
            canvas.drawCircle(dx,dy,150,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                dx = (int) event.getX();
                dy = (int) event.getY();
                postInvalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                dx = (int) event.getX();
                dy = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                dx =-1;
                dy =-1;

                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}
