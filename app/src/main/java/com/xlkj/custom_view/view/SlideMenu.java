package com.xlkj.custom_view.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

public class SlideMenu extends ViewGroup {
    private int screenWidth; //屏幕的宽度
    private int screenHeight;//屏幕的高度
    private int mMenuRightPadding;//侧滑菜单与主屏幕的间距
    private View menuView;//菜单view
    private View contentView;//内容view
    private int menuWidth;//菜单宽度
    private int contentWidth;//内容宽度
    private int lastX,lastY;//最后一次触摸的x，y的位置
    private Scroller scroller;//滑动类
    private int mLastXIntercept;
    private int mLastYIntercept;

    public SlideMenu(Context context) {
        this(context,null,0);
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics metrics = new DisplayMetrics();//测量类
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);//屏幕管理器
        wm.getDefaultDisplay().getMetrics(metrics);//将屏幕信息封装到metric类中
        screenWidth =metrics.widthPixels; //获取屏幕宽度
        screenHeight = metrics.heightPixels;//获取屏幕高度
        mMenuRightPadding = px2dp(context,100);//菜单与主业的间距
        scroller = new Scroller(context);//自动滑动
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        menuView = getChildAt(0);//菜单布局
        contentView = getChildAt(1);//主布局
        menuWidth = menuView.getLayoutParams().width = screenWidth - mMenuRightPadding;//设置菜单的宽度
        contentWidth = contentView.getLayoutParams().width = screenWidth;//设置主界面的宽度为屏幕的宽度

        //测量menu
        measureChild(menuView,widthMeasureSpec,heightMeasureSpec);
        //测量content
        measureChild(contentView,widthMeasureSpec,heightMeasureSpec);

        //测量自己 menu+content
        setMeasuredDimension(menuWidth+contentWidth,screenHeight);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放menu
        menuView.layout(-menuWidth,0,0,screenHeight);
        //摆放content
        contentView.layout(0,0,contentWidth,screenHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                Log.e("TAG","lastX:"+lastX);
                Log.e("TAG","lastY:"+lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                int currentX = (int) event.getX();
                int currentY = (int) event.getY();

                int offsetX = currentX - lastX;//偏移量
                Log.e("TAG","offsetX:"+offsetX);
                if(offsetX>0){ //向右滑动
                    if(getScrollX()-offsetX<=-menuWidth){
                        scrollTo(-menuWidth,0);
                    }else {
                        scrollBy(-offsetX,0);
                    }
                }else{
                    //向左滑动
                    if (getScrollX() + Math.abs(offsetX) >= 0) {
                        //直接移动到（0，0）位置，不会出现白边
                        scrollTo(0, 0);

                    } else {//Menu没有完全显示呢
                        //其实这里dx还是-dx，大家不用刻意去记
                        //大家可以先使用dx，然后运行一下，发现
                        //移动的方向是相反的，那么果断这里加个负号就可以了
                        scrollBy(-offsetX, 0);
                    }
                }
                lastX = currentX;
                lastY = currentY;
                break;
            case MotionEvent.ACTION_UP:
                if(getScrollX()<-menuWidth/2){
                    scroller.startScroll(getScrollX(),0,- menuWidth - getScrollX(),0,300);
                }else{
                    scroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 300);
                }
                invalidate();
                break;

        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) ev.getX() - mLastXIntercept;
                int deltaY = (int) ev.getY() - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)){//横向滑动
                    intercept = true;
                }else{//纵向滑动
                    intercept = false;
                }
                break;
        }
        lastX = x;
        lastY = y;
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercept;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 将px转换成dp
     * @param context
     * @param num
     * @return
     */
    private int px2dp(Context context,int num){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,num,context.getResources().getDisplayMetrics());
    }
}
