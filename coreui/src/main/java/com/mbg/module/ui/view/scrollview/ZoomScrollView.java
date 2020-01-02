package com.mbg.module.ui.view.scrollview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.core.view.ViewCompat;

/***
 * created by zhaozhiyang in 20191201
 * 支持ScrolView在顶部时下拉View变大，释放返回的ScrollView
 */
public class ZoomScrollView extends ScrollView {
    //缩放控件
    private View mZoomView;
    private int mZoomViewWidth;
    private int mZoomViewHeight;

    //滑动禁止滑动
    private boolean mScrollEnable=true;

    //缩放信息
    private float mStartYPos;//记录第一次按下的Y值的位置
    private boolean mScaling;//是否正在缩放
    private float mScrollRate = 0.3f;//缩放系数，缩放系数越大，变化的越大
    private float mReplyRate = 0.5f;//回调系数，越大，回调越慢
    private float mMovingThreshold;//滑动阀值

    //滑动监听
    private OnScrollChangeListener mOnScrollChangeListener;

    private boolean mCanZoom;//是否可以进行缩放
    private float mDownY =0;
    private float mDownX =0;


    private EnlargeType mEnlargeType = EnlargeType.Centered;

    public ZoomScrollView(Context context) {
        super(context);
    }

    public ZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setZoomView(View mZoomView) {
        this.mZoomView = mZoomView;
    }

    public void setScrollRate(float mScrollRate) {
        this.mScrollRate = mScrollRate;
    }

    public void setReplyRate(float mReplyRate) {
        this.mReplyRate = mReplyRate;
    }


    public void setScrollChangeListener(OnScrollChangeListener onScrollChangeListener){
        this.mOnScrollChangeListener=onScrollChangeListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        mMovingThreshold= ViewConfiguration.get(getContext()).getScaledTouchSlop();
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        //禁止滑动
        if (!mScrollEnable){
            return true;
        }

        if (mZoomView==null){
            return super.onTouchEvent(ev);
        }
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            mZoomViewWidth = mZoomView.getMeasuredWidth();
            mZoomViewHeight = mZoomView.getMeasuredHeight();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 滚动到顶部时记录位置，否则正常返回
                if (isScrolledTop()) {
                    mStartYPos = ev.getY();
                    mCanZoom =true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指离开后恢复图片
                mScaling = false;
                if (mCanZoom) {
                    replyImage();
                    mCanZoom =false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mCanZoom){
                    break;
                }
                float dy=ev.getY() - mStartYPos;
                if (!mScaling) {
                    if (isScrolledTop()) {
                        //如果不是向下滑动或非滑动，正常返回
                        if (Math.abs(dy)<=mMovingThreshold||dy<0){
                            break;
                        }
                    } else {
                        break;
                    }
                }
                // 处理放大
                mScaling = true;
                setZoom(dy * mScrollRate);
                return true; // 返回true表示已经完成触摸事件，不再处理
        }
        return super.onTouchEvent(ev);
    }
    //回弹动画
    private void replyImage() {
        if (mZoomView==null){
            return;
        }
        if (mEnlargeType == EnlargeType.RIGHT_BOTTOM) {
            float distance = mZoomView.getMeasuredWidth() - mZoomViewWidth;
            long duration = (long) Math.abs(distance * mReplyRate);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(distance, 0f).setDuration(duration);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setZoom((Float) animation.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }else if (mEnlargeType == EnlargeType.Centered){
            float scaleX=mZoomView.getScaleX();
            float scaleY=mZoomView.getScaleY();

            float distance = mZoomView.getMeasuredWidth() - mZoomViewWidth;
            long duration = (long) Math.abs(distance * mReplyRate);
            ObjectAnimator animatorX=ObjectAnimator.ofFloat(mZoomView,"scaleX",scaleX,1);
            ObjectAnimator animatorY=ObjectAnimator.ofFloat(mZoomView,"scaleY",scaleY,1);

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(distance, 0f).setDuration(duration);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setZoom((Float) animation.getAnimatedValue());
                }
            });

            AnimatorSet animatorSet=new AnimatorSet();
            animatorSet.play(animatorX).with(animatorY).with(valueAnimator);
            animatorSet.setDuration(duration);
            animatorSet.start();
        }

    }

    //是否滑动到了顶部
    private boolean isScrolledTop(){
        return getScrollY()==0;
    }

    //是否滑动到了底部
    private boolean isScrolledBottom(){
        return getScrollY() == getChildAt(0).getMeasuredHeight()- getMeasuredHeight();
    }


    /***
     * 缩放函数
     * @param zoom 缩放值 0不缩放、负数为缩小、正数为放大
     */
    public void setZoom(float zoom) {
        if (mZoomView==null||mZoomViewWidth <= 0 || mZoomViewHeight <= 0||zoom<0) {
            return;
        }
        if (mEnlargeType == EnlargeType.RIGHT_BOTTOM) {
            ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
            lp.width = (int) (mZoomViewWidth + zoom);
            lp.height = (int) (mZoomViewHeight * ((mZoomViewWidth + zoom) / mZoomViewWidth));
            mZoomView.setLayoutParams(lp);
        }else if (mEnlargeType == EnlargeType.Centered){
            float scale=zoom/mZoomViewWidth+1;
            mZoomView.setScaleX(scale);
            mZoomView.setScaleY(scale);

            ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
            lp.height = (int) (mZoomViewHeight * ((mZoomViewWidth + zoom) / mZoomViewWidth));
            mZoomView.setLayoutParams(lp);
        }
    }




    /**
     * 是否允许滑动
     * @param enable
     */
    public void setScrollEnable(boolean enable){
        mScrollEnable=enable;
    }

    @Override
    protected void onScrollChanged(int x , int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (mOnScrollChangeListener!=null){
            mOnScrollChangeListener.onScrollChanged(x, y, oldX, oldY);
        }

    }

    public interface OnScrollChangeListener {
        void onScrollChanged(int x, int y, int oldX, int oldY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                mDownX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY =  ev.getY()-mDownY;
                float moveX =  ev.getX()-mDownX;
                // 判断是否滑动，若滑动就拦截事件
                if (isScrolledTop() && Math.abs(moveY)> Math.abs(moveX)&& Math.abs(moveY) > mMovingThreshold&&moveY>0) {
                    if (!mCanZoom){
                        mCanZoom=true;
                        mStartYPos = ev.getY();
                    }
                    return true;
                }
                break;
            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//垂直方向支持嵌套滑动
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        boolean hiddenTop = dy > 0 &&!isScrolledBottom();//如果上滑并且没有滑动到底部
        boolean showTop = dy < 0 && getScrollY() > 0 && !target.canScrollVertically(-1);//下滑，父View没有到顶部、子view到顶部时

        if (hiddenTop || showTop){
            scrollBy(0, dy);
            consumed[1] = dy;
        }
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {

        boolean hiddenTop = velocityY > 0 &&!isScrolledBottom();//如果上滑并且没有滑动到底部
        boolean showTop = velocityY < 0 && getScrollY() > 0 && !target.canScrollVertically(-1);//下滑，父View没有到顶部、子view到顶部时

        if (hiddenTop||showTop) {
            fling((int) velocityY);
            return true;
        }

        return super.onNestedPreFling(target,velocityX,velocityY);
    }

    /**
     * 放大类型
     */
    public enum EnlargeType {
        Centered, //居中放大
        RIGHT_BOTTOM //右下方向放大
    }
}
