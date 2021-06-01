package com.xlkj.custom_view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class PercentView extends View {
    private int radis=0;
    private float[] radises = {0.3f,0.6f,1.0f};
    private Paint paint;
    private Paint sweapPaint;
    private Shader sweapShader;
    private Matrix matrix;
    private int rotation =0;
    public PercentView(Context context) {
        this(context,null);
    }

    public PercentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PercentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        sweapPaint = new Paint();
        sweapPaint.setAntiAlias(true);
        sweapPaint.setColor(Color.RED);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height= MeasureSpec.getSize(heightMeasureSpec);
        radis = (width>height?height:width)/2;
        setMeasuredDimension(width>height?height:width,width>height?height:width);

        sweapShader = new SweepGradient(radis,radis,Color.TRANSPARENT,Color.RED);
        sweapPaint.setShader(sweapShader);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<radises.length;i++){
            canvas.drawCircle(radis,radis,radises[i]*radis,paint);
        }
        canvas.drawLine(getWidth()/2,0,getWidth()/2,getHeight(),paint);
        canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,paint);
        canvas.concat(matrix);
        canvas.drawCircle(radis,radis,radis,sweapPaint);
    }
    public void starScan(){
        ScanThread scanThread = new ScanThread(this);
        scanThread.start();
    }
    protected class ScanThread extends Thread{
        private PercentView view;
        public ScanThread(PercentView view){
            this.view = view;
        }
        @Override
        public void run() {
            super.run();
            while (true){
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        rotation+=3;
                        matrix = new Matrix();
                        matrix.preRotate(rotation,radis,radis);
                        view.invalidate();
                    }
                });
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
