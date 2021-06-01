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

public class MyClock extends View {

    //时钟的背景颜色
    private int bgColor = Color.WHITE;

    //一刻钟的颜色
    private int quaterMarkColor = Color.parseColor("#B5B5B5");

    //普通刻度的颜色
    private int miniterMarkColor = Color.parseColor("#EBEBEB");

    //时针颜色
    private int hourColor = Color.BLACK;

    //分针颜色
    private int minColor = Color.BLACK;

    //秒针颜色
    private int senColor = Color.RED;

    //背景画笔
    private Paint bgPaint;

    //刻度画笔
    private Paint markPaint;

    //时针画笔
    private Paint hourPaint;

    //分针画笔
    private Paint minPaint;

    //秒针画笔
    private Paint senPaint;

    private Paint centerPaint;
    private float markTop = 12;

    private float markWidth = 8;

    private float markHeight = 20;

    private Bitmap hourBitmap;
    private Bitmap minBitmap;
    private Bitmap senBitmap;

    private Canvas hourCanvas;
    private Canvas minCanvas;
    private Canvas senCanvas;

    private int hourLength;
    private int minLength;
    private int senLength;

    public MyClock(Context context) {
        this(context,null);
    }

    public MyClock(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        markPaint.setColor(miniterMarkColor);
        markPaint.setStyle(Paint.Style.FILL);
        markPaint.setStrokeCap(Paint.Cap.ROUND);

        hourPaint = new Paint();
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(hourColor);
        hourPaint.setStyle(Paint.Style.FILL);
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        hourPaint.setStrokeWidth(12);

        minPaint = new Paint();
        minPaint.setAntiAlias(true);
        minPaint.setColor(hourColor);
        minPaint.setStyle(Paint.Style.FILL);
        minPaint.setStrokeCap(Paint.Cap.ROUND);
        minPaint.setStrokeWidth(9);

        markPaint = new Paint();
        markPaint.setAntiAlias(true);
        markPaint.setColor(minColor);
        markPaint.setStyle(Paint.Style.FILL);
        markPaint.setStrokeCap(Paint.Cap.ROUND);
        markPaint.setStrokeWidth(9);

        senPaint = new Paint();
        senPaint.setAntiAlias(true);
        senPaint.setColor(senColor);
        senPaint.setStyle(Paint.Style.FILL);
        senPaint.setStrokeCap(Paint.Cap.ROUND);
        senPaint.setStrokeWidth(6);

        centerPaint = new Paint();
        centerPaint.setAntiAlias(true);
        centerPaint.setColor(Color.BLACK);
        centerPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width>height?height:width,width>height?height:width);

        hourBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        hourCanvas = new Canvas(hourBitmap);

        minBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        minCanvas = new Canvas(minBitmap);

        senBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        senCanvas = new Canvas(senBitmap);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth()/2;
        int centerY = getHeight()/2;
        int radius = getWidth()/2;

        hourLength = radius/2;
        minLength = radius *3 /4;
        senLength = radius *3 /4;


        //绘画背景圆
        canvas.drawCircle(centerX,centerY,radius,bgPaint);


        //绘画刻度线
        for(int i=0;i<12;i++){
            if(i%3==0){
                markPaint.setColor(quaterMarkColor);
            }else{
                markPaint.setColor(miniterMarkColor);
            }
            //绘画刻度线
            canvas.drawLine(centerX,centerY-radius + markTop,
                            centerX,centerY - radius +markTop+ markHeight,markPaint);
            //画布旋转
            canvas.rotate(30,centerX,centerY);
        }
        canvas.save();

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        hourCanvas.save();
        //清空画布
        // 一个小时 30° 一分钟 6°
        hourCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        hourCanvas.rotate(hour*30 + minute*0.5f,centerX,centerY);
        hourCanvas.drawLine(centerX,centerY,
                centerX,centerY-hourLength,hourPaint );
        hourCanvas.restore();


        minCanvas.save();
        //清空画布
        minCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        minCanvas.rotate(minute*6 +minute*0.1f,centerX,centerY);
        minCanvas.drawLine(centerX,centerY,
                centerX,centerY-minLength,minPaint );
        minCanvas.restore();

        senCanvas.save();
        //清空画布
        senCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        senCanvas.rotate(second*6,centerX,centerY);
        senCanvas.drawLine(centerX,centerY,
                centerX,centerY-senLength,senPaint );
        senCanvas.restore();

        canvas.drawBitmap(hourBitmap, 0, 0, null);
        canvas.drawBitmap(minBitmap, 0, 0, null);
        canvas.drawBitmap(senBitmap, 0, 0, null);

        canvas.drawCircle(centerX,centerY,radius/16,centerPaint);

        postInvalidateDelayed(1000);
    }
}
