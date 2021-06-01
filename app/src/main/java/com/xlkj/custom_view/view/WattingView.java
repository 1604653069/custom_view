package com.xlkj.custom_view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class WattingView extends View {
    private Paint paint;
    private int cirColor = Color.BLUE;
    private int squColor = Color.YELLOW;
    private int rtanColor = Color.RED;
    private ShapeType shapeType = ShapeType.Cir;
    public enum ShapeType{
        Cir,
        Sque,
        Rtan
    }
    public WattingView(Context context) {
        this(context,null);
    }

    public WattingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WattingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width>height?height:width,width>height?height:width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setAntiAlias(true);
        switch (shapeType){
            case Cir:
                int center = getWidth()/2;
                paint.setColor(cirColor);
                canvas.drawCircle(center,center,center,paint);
                shapeType = ShapeType.Sque;
                break;
            case Sque:
                Rect rect = new Rect(0,0,getWidth(),getHeight());
                paint.setColor(squColor);
                canvas.drawRect(rect,paint);
                shapeType = ShapeType.Rtan;
                break;
            case Rtan:
                Path path = new Path();
                path.moveTo(getWidth()/2,0);
                path.lineTo(0, (float) (getWidth()/2*Math.sqrt(3)));
                path.lineTo(getWidth(),(float) (getWidth()/2*Math.sqrt(3)));
                path.close();
                paint.setColor(rtanColor);
                canvas.drawPath(path,paint);
                shapeType = ShapeType.Cir;
                break;
        }
    }
    public void change(){
        invalidate();
    }

    public ShapeType getShapeType() {
        return shapeType;
    }
}
