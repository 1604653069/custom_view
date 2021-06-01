package com.xlkj.custom_view.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LetterSildeView extends View {
    private Paint paint;
    private Paint touchPaint;
    private char[] letters = new char[26];
    private int textWitdh=0;
    private int currentNum = -1;
    public LetterSildeView(Context context) {
        this(context,null);
    }

    public LetterSildeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterSildeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(sp2px(12));

        touchPaint = new Paint();
        touchPaint.setAntiAlias(true);
        touchPaint.setColor(Color.RED);
        touchPaint.setTextSize(sp2px(12));
        getLetters();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textWitdh = (int) paint.measureText("A");//测量单个字母的宽度,这里以A为例子
        int width = getPaddingLeft() + textWitdh + getPaddingRight();//整个控件的宽度应该是A字母+paddingLeft左边的距离+paddingRight右边的距离
        setMeasuredDimension(width,MeasureSpec.getMode(heightMeasureSpec));//设置控件宽高度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int itemHeight = getHeight()/letters.length;//每个控件的宽度
        int dx = getPaddingLeft();//绘画字母x轴的位置
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom-fontMetrics.top)/2 - fontMetrics.bottom);//获取字母中线
        for(int i=0;i<letters.length;i++){
            int baseLine = i * itemHeight + itemHeight/2 +dy; //获取每个字母的基线
            if(currentNum==i){ //判断这个字母是否被触摸
                canvas.drawText(String.valueOf(letters[i]),dx,baseLine,touchPaint);//绘画被触摸的字母
            }else{//没有被触摸
                canvas.drawText(String.valueOf(letters[i]),dx,baseLine,paint);//绘画未触摸的字母
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                    float y = event.getY();
                    float itemHeight = getHeight()/26;
                    currentNum =  (int) (y/itemHeight);
                    if(currentNum>=letters.length){
                        currentNum = letters.length-1;
                    }if(currentNum<0){
                        currentNum = 0;
                    }
                    if(onLetterStrBackListener!=null)
                        onLetterStrBackListener.onLetterTouchStrBack(String.valueOf(letters[currentNum]),getLeft()-getWidth()-30,y>getHeight()?getHeight()-25:y-25);
                    invalidate();
                    break;
            case MotionEvent.ACTION_UP:
                    if(onLetterStrBackListener!=null)
                        onLetterStrBackListener.onTouchCanleListener();
                    currentNum = -1;
                    invalidate();
                    break;
        }
        return true;
    }


    private float sp2px(int sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    private void getLetters(){
        for(int i=0;i<26;i++){
            letters[i] = (char)(i+65);
        }
    }

    OnLetterStrBackListener onLetterStrBackListener;
    public void setOnLetterStrBackListener(OnLetterStrBackListener onLetterStrBackListener2){
        this.onLetterStrBackListener = onLetterStrBackListener2;
    }
    public interface OnLetterStrBackListener{
        void onLetterTouchStrBack(String letter,float x,float y);
        void onTouchCanleListener();
    }
}
