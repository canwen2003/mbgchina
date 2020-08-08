package com.mbg.module.ui.view.scrollview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.ArrayList;
import java.util.List;

public class ZoomCoordinatorLayout extends CoordinatorLayout {
    private View mZoomView;
    private int mZoomViewWidth;
    private int mZoomViewHeight;
    private boolean mCanDragUp=false;

    private float mFirstPositionX;//记录第一次按下的位置
    private float mFirstPositionY;//记录第一次按下的位置
    private boolean mIsZooming;//是否正在缩放
    private boolean mIsDraggingUp;//是否正在向上

    private static final float mScrollRate = 0.5f;//缩放系数，缩放系数越大，变化的越大
    private static final float mReplyRate = 0.3f;//回调系数，越大，回调越慢


    private List<MoveView> mMoveViews;
    private List<DragView> mDragViews;
    private int mMovingThreshold;
    private OnDragUpListener mDragUpListener;

    public ZoomCoordinatorLayout(@NonNull Context context) {
        this(context,null);
    }

    public ZoomCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomCoordinatorLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mMovingThreshold= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setZoomView(View mZoomView) {
        this.mZoomView = mZoomView;
    }

    public void addMoveView(View moveView) {
        MoveView view=new MoveView();
        view.moveView=moveView;
        view.height=moveView.getMeasuredHeight();
        if (mMoveViews==null){
            mMoveViews=new ArrayList<>();
        }
        mMoveViews.add(view);
    }

    public void setMoveView(View moveView){
        if (mMoveViews!=null){
            mMoveViews.clear();
        }
        addMoveView(moveView);
    }

    public void setDragUpView(View view){
        if (mDragViews!=null){
            mDragViews.clear();
        }
        addDragUpView(view);
    }

    public void addDragUpView(View view){
        if (mDragViews==null){
            mDragViews=new ArrayList<>();
        }

        if (view==null){
            mCanDragUp=false;
            mDragViews.clear();
            return;
        }

        mCanDragUp=true;
        DragView dragView=new DragView();
        dragView.dragView=view;
        dragView.translationY=view.getTranslationY();
        mDragViews.add(dragView);
    }

    public void setOnDragListener(OnDragUpListener dragListener){
        this.mDragUpListener=dragListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int[] location = new int[2];
        mZoomView.getLocationOnScreen(location);
        int y = location[1];


        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            mZoomViewWidth = mZoomView.getMeasuredWidth();
            mZoomViewHeight = mZoomView.getMeasuredHeight();
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                mIsDraggingUp=false;
                mIsZooming=false;
                if (getScrollY() == 0) {
                    mFirstPositionY = ev.getY();// 滚动到顶部时记录位置，否则正常返回
                    mFirstPositionX = ev.getX();
                }
                break;
            case MotionEvent.ACTION_UP:
                //恢复上拉拖动
                if (mCanDragUp&&mIsDraggingUp) {
                    replyDragViews();
                    mIsDraggingUp=false;
                    if (mDragUpListener!=null){
                        mDragUpListener.onDragFinish();
                    }
                    break;
                }

                //恢复下拉拖动
                if (mIsZooming) {
                    replyImage();
                    mIsZooming=false;
                }

                break;
            case MotionEvent.ACTION_MOVE:
                if (y != 0) {
                    return super.dispatchTouchEvent(ev);
                }

                float distanceY = (ev.getY() - mFirstPositionY);
                float distanceX = (ev.getX() - mFirstPositionX);

                boolean movedY=Math.abs(distanceX) < Math.abs(distanceY) && Math.abs(distanceY)>mMovingThreshold;
                if (distanceY < 0) { // 处理向上拖动
                    if (!mIsDraggingUp) {
                        if (getScrollY() != 0) {
                            break;
                        }
                    }
                    if (mCanDragUp &&movedY ) {
                        mIsDraggingUp=true;
                        setDragUp(distanceY * mScrollRate);// 滚动距离乘以一个系数
                        if (mDragUpListener!=null){
                            mDragUpListener.onDragging();
                        }
                    }
                    break;
                } else {
                    if (!mIsZooming) {
                        if (getScrollY() != 0) {
                            break;
                        }
                    }

                    if (movedY) {
                        // 处理放大
                        mIsZooming = true;
                        setZoom(distanceY * mScrollRate);// 滚动距离乘以一个系数
                    }

                    return super.dispatchTouchEvent(ev);
                }

        }
        return super.dispatchTouchEvent(ev);

    }

    //回弹动画
    private void replyDragViews() {
        if (!mCanDragUp||mDragViews==null){
            return;
        }

        for (DragView dragView:mDragViews){
            dragView.dragView.setTranslationY(dragView.translationY);
        }
    }

    //回弹动画
    private void replyImage() {
        float distance = mZoomView.getMeasuredWidth() - mZoomViewWidth;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distance, 0f).setDuration((long) (distance * mReplyRate));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }

        });
        valueAnimator.start();

      if (mMoveViews!=null){
          for (MoveView moveView:mMoveViews){
              moveView.moveView.setScrollY(moveView.height);
          }
      }
    }


    private void setDragUp(float dragUp) {
        if (!mCanDragUp||mDragViews==null){
            return;
        }

        for (DragView dragView:mDragViews){
            dragView.dragView.setTranslationY(dragUp);
            //dragView.dragView.invalidate();
        }
    }

    private void setZoom(float zoom) {
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        lp.width = (int) (mZoomViewWidth * ((mZoomViewWidth + zoom) / mZoomViewWidth));
        lp.height = (int) (mZoomViewHeight * ((mZoomViewWidth + zoom) / mZoomViewWidth));
        ((MarginLayoutParams) lp).setMargins(-(lp.width - mZoomViewWidth) / 2, 0, 0, 0);
        mZoomView.setLayoutParams(lp);
        try {
          if (mMoveViews!=null){
                for (MoveView moveView:mMoveViews){
                    ViewGroup parent = (ViewGroup) moveView.moveView.getParent();
                    ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
                    layoutParams.height = lp.height;
                    parent.setLayoutParams(layoutParams);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static class MoveView{
        private View moveView;
        private int height;
    }

    private static class DragView{
        private View dragView;
        private float translationY;
    }

    public interface OnDragUpListener{
        void onDragging();
        void onDragFinish();
    }

}
