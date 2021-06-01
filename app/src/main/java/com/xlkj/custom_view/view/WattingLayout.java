package com.xlkj.custom_view.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.xlkj.custom_view.R;

public class WattingLayout extends LinearLayout {
    private WattingView shapeView;
    private View shapeShaowView;
    private Animator fallAnimation;
    private Animator scaleAnim;
    private AnimatorSet fallSet;
    private AnimatorSet upSet;
    public WattingLayout(Context context) {
        this(context,null);
    }

    public WattingLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WattingLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    private void initLayout() {
        inflate(getContext(), R.layout.watting_view,this);
        shapeView = findViewById(R.id.shape_view);
        shapeShaowView = findViewById(R.id.shape_shadow);
        startAnim();
    }

    private void startAnim() {
        fallAnim();
    }

    private void playFall() {
        fallSet = new AnimatorSet();
        fallSet.playTogether(fallAnimation,scaleAnim);
        fallSet.setDuration(350);
        fallSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                upAnim();
            }
        });
        fallSet.start();
    }

    private void fallAnim() {
        fallAnimation = ObjectAnimator.ofFloat(shapeView,"translationY",0,75);
        fallAnimation.setInterpolator(new AccelerateInterpolator());
        scaleAnim = ObjectAnimator.ofFloat(shapeShaowView,"scaleX",0.6f,1.2f);
        scaleAnim.setInterpolator(new AccelerateInterpolator());
        playFall();
    }
    private void upAnim(){
        fallAnimation = ObjectAnimator.ofFloat(shapeView,"translationY",75,0);
        fallAnimation.setInterpolator(new DecelerateInterpolator());
        scaleAnim = ObjectAnimator.ofFloat(shapeShaowView,"scaleX",1.2f,0.6f);
        scaleAnim.setInterpolator(new DecelerateInterpolator());
        playUp();
    }

    private void playUp() {
        upSet = new AnimatorSet();
        upSet.playTogether(fallAnimation,scaleAnim);
        upSet.setDuration(350);
        upSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                fallAnim();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                shapeView.change();
                ratation(shapeView.getShapeType());
            }
        });
        upSet.start();
    }
    private void ratation(WattingView.ShapeType shapeType){
        Animator rotation = null;
        switch (shapeType){
            case Cir:
            case Sque:
                rotation = ObjectAnimator.ofFloat(shapeView,"rotation",0,90);
                break;
            case Rtan:
                rotation = ObjectAnimator.ofFloat(shapeView,"rotation",0,120);
                break;
        }
        rotation.setDuration(350);
        rotation.start();
    }
}
