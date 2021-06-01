package com.xlkj.custom_view.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

import com.xlkj.custom_view.R;

public class RatingBar extends View {
    private Bitmap normalStar;
    private Bitmap selectedStar;
    private int normalResoureceId;
    private int selectedResouceId;
    private int starPadding =0;
    private int currentStarNum =5;
    private int slideNum = 0;
    public RatingBar(Context context) {
        this(context,null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        normalResoureceId = typedArray.getResourceId(R.styleable.RatingBar_normal,0);
        selectedResouceId = typedArray.getResourceId(R.styleable.RatingBar_selected,0);
        currentStarNum = typedArray.getInteger(R.styleable.RatingBar_starNum,currentStarNum);
        slideNum = typedArray.getInt(R.styleable.RatingBar_defaultSelectedNum,slideNum);
        starPadding = (int) typedArray.getDimension(R.styleable.RatingBar_star_padding,starPadding);
        if(normalResoureceId==0){
            throw new RuntimeException("你没有配置星星资源");
        }
        if(selectedResouceId==0){
            throw new RuntimeException("你没有配置行星资源");
        }
        normalStar = BitmapFactory.decodeResource(getResources(),normalResoureceId);
        selectedStar = BitmapFactory.decodeResource(getResources(),selectedResouceId);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = normalStar.getHeight();
        int width = (normalStar.getWidth()+starPadding)*currentStarNum;
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i=0;i<currentStarNum;i++){
            int x = i*(normalStar.getWidth()+starPadding);
            if(slideNum>i)
                canvas.drawBitmap(selectedStar,x,0,null);
            else
                canvas.drawBitmap(normalStar,x,0,null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int current =  (int) ((x/(normalStar.getWidth()+starPadding))+1);
                if(current==slideNum){
                    break;
                }else{
                    slideNum = current;
                    if(slideNum>currentStarNum)
                        slideNum = currentStarNum;
                    if(slideNum<=0)
                        slideNum =1;
                    invalidate();
                }
                break;
        }
        return true;
    }

}
