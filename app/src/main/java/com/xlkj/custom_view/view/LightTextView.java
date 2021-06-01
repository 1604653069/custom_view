package com.xlkj.custom_view.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LightTextView extends TextView {
    private Paint paint;
    private LinearGradient linearGradient;
    private int[] colors = {0xffff0000,0xffffff00,0xff00ffff,0xff0000ff};
    private float[] pos = {0.0f,0.2f,0.6f,1.0f};
    private int len =0;
    private int dx =0;
    public LightTextView(Context context) {
        this(context,null);
    }

    public LightTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LightTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = getPaint();
        len = (int) paint.measureText(getText().toString());
        crateAnim(len);
        createLinearGradient(len);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(dx,0);
        linearGradient.setLocalMatrix(matrix);
        paint.setShader(linearGradient);
        super.onDraw(canvas);
    }

    private void crateAnim(int len){
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,len*2);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                  dx = (int) animation.getAnimatedValue();
                  postInvalidate();
            }
        });
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setDuration(2000);
        valueAnimator.start();
    }
    private void createLinearGradient(int length){
        linearGradient = new LinearGradient(-length,0,0,0,new int[]{
                getCurrentTextColor(),0xff00ff00,getCurrentTextColor()
        },new float[]{
                0.0f,
                0.5f,
                1.0f
        }, Shader.TileMode.CLAMP);
    }
}
