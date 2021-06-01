package com.xlkj.custom_view.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class MyClock2 extends View {
    //背景颜色
    private int bgColor = Color.WHITE;
    //一刻钟颜色
    private int squareMakrColor = Color.parseColor("#B5B5B5");
    //普通刻度线颜色
    private int minMarkColor =  Color.parseColor("#EBEBEB");
    //时钟颜色
    private int hourColor = Color.BLACK;
    //分钟颜色
    private int minColor = Color.BLACK;
    //秒钟颜色
    private int senColor = Color.RED;
    //间隙
    private int markTop = 15;
    //刻度长度
    private int markLength = 21;

    private int hourLength;
    private int minLength;
    private int senLength;

    private Paint bgPaint;

    private Paint markPaint;

    private Paint hourPaint;

    private Paint minPaint;

    private Paint senPaint;

    private Paint pointPaint;

    private int radius=0;

    private Canvas hourCanvas;

    private Canvas minCanvas;

    private Canvas senCanvas;

    private Bitmap hourBitmap;

    private Bitmap minBitmap;

    private Bitmap senBitmap;

    private int centerX;
    private int centerY;

    public MyClock2(Context context) {
        this(context,null);
    }

    public MyClock2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyClock2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(bgColor);
        bgPaint.setStyle(Paint.Style.FILL);

        markPaint = new Paint();
        markPaint.setAntiAlias(true);
        markPaint.setColor(minMarkColor);
        markPaint.setStyle(Paint.Style.FILL);
        markPaint.setStrokeCap(Paint.Cap.ROUND);
        markPaint.setStrokeWidth(12);

        hourPaint = new Paint();
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(hourColor);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPaint.setStrokeWidth(12);

        minPaint = new Paint();
        minPaint.setAntiAlias(true);
        minPaint.setColor(minColor);
        minPaint.setStyle(Paint.Style.FILL);
        minPaint.setStrokeCap(Paint.Cap.ROUND);
        minPaint.setStrokeWidth(9);

        senPaint = new Paint();
        senPaint.setAntiAlias(true);
        senPaint.setColor(senColor);
        senPaint.setStyle(Paint.Style.FILL);
        senPaint.setStrokeCap(Paint.Cap.ROUND);
        senPaint.setStrokeWidth(6);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(hourColor);
        pointPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        radius = Math.min(width,height)/2;
        setMeasuredDimension(width>height?height:width,width>height?height:width);

        hourBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        hourCanvas = new Canvas(hourBitmap);

        minBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        minCanvas = new Canvas(minBitmap);

        senBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        senCanvas = new Canvas(senBitmap);

        centerX = getWidth()/2;
        centerY = getHeight()/2;

        hourLength = radius/2;
        minLength = radius*3/4;
        senLength = radius-markTop;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(centerX,centerY,radius,bgPaint);
        for(int i=0;i<12;i++){
            if(i%3==0){
                markPaint.setColor(squareMakrColor);
            }else{
                markPaint.setColor(minMarkColor);
            }
            canvas.drawLine(centerX,centerY - radius + markTop ,centerX,centerY - radius + markTop + markLength,markPaint);
            canvas.rotate(30,centerX,centerY);
        }
        canvas.save();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        //绘画时针

        hourCanvas.save();
        //清空画布
        hourCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        hourCanvas.rotate(hour * 30+ minute *0.5f,centerX,centerY);

        hourCanvas.drawLine(centerX,centerY,centerX,centerY-hourLength,hourPaint);

        hourCanvas.restore();


        minCanvas.save();
        //清空画布
        minCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        minCanvas.rotate(minute * 6+ second*0.1f,centerX,centerY);
        minCanvas.drawLine(centerX,centerY,centerX,centerY-minLength,minPaint);


        minCanvas.restore();


        senCanvas.save();
        //清空画布
        senCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        senCanvas.rotate(second * 6,centerX,centerY);

        senCanvas.drawLine(centerX,centerY,centerX,centerY-senLength,senPaint);


        senCanvas.restore();

        canvas.drawBitmap(hourBitmap,0,0,null);
        canvas.drawBitmap(minBitmap,0,0,null);
        canvas.drawBitmap(senBitmap,0,0,null);

        canvas.drawCircle(centerX,centerY,radius/21,pointPaint);
        postInvalidateDelayed(1000);
    }
}
