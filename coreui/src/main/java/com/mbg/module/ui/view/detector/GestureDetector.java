package com.mbg.module.ui.view.detector;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.mbg.module.ui.view.listener.OnScrollListener;
import com.mbg.module.ui.view.listener.OnSuperListener;

/**
 * Created by Gap
 * 手势识别器
 */
public class GestureDetector {
    private float mPosX;//开始位置
    private float mPosY;
    private float mCurPosX;//目前位置
    private float mCurPosY;
    private GestureType mGestureType= GestureType.UNKNOWN;
    private OnScrollListener mOnScrollListener;
    private OnSuperListener mOnSuperListener;
    private final float mMovingThreshold;//滑动阀值

    public GestureDetector(Context context){
        mMovingThreshold= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float focusX=ev.getX();
        float focusY=ev.getY();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                return down(ev,focusX,focusY);
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
               return up(ev,focusX,focusY);
            case MotionEvent.ACTION_MOVE:
                return move(ev,focusX,focusY);
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        if (mOnSuperListener!=null){
            return mOnSuperListener.dispatchTouchEvent(ev);
        }else {
            return false;
        }
    }
    private boolean down(MotionEvent ev,float x,float y){
        mPosX=x;
        mPosY=y;
        mGestureType= GestureType.DOWN;
        if (mOnSuperListener!=null){
            return mOnSuperListener.dispatchTouchEvent(ev);
        }else {
            return false;
        }
    }

    private boolean move(MotionEvent ev,float x,float y){
        mCurPosX=x;
        mCurPosY=y;
        float absDeltaX=Math.abs(mCurPosX-mPosX);
        float absDeltaY=Math.abs(mCurPosY-mPosY);

        if (absDeltaX<mMovingThreshold&&absDeltaY<mMovingThreshold){
            if (mOnSuperListener!=null){
                return mOnSuperListener.dispatchTouchEvent(ev);
            }else {
                return false;
            }
        }

        if (mGestureType== GestureType.DOWN){
            mGestureType= GestureType.START;
            if (mOnScrollListener!=null){
                mOnScrollListener.onScrollStart(absDeltaX<= absDeltaY);
            }
        }else {
            mGestureType= GestureType.MOVING;
        }

        if (absDeltaX>absDeltaY){
            if (mOnScrollListener!=null){
                mOnScrollListener.onHorizontalScroll(mPosX-mCurPosX);
            }
        }else {
            if (mOnScrollListener!=null){
                mOnScrollListener.onVerticalScroll(mPosY-mCurPosY);
            }
        }
        mPosX=mCurPosX;
        mPosY=mCurPosY;

        return true;
    }

    private boolean up(MotionEvent ev,float x,float y){
        mCurPosX=x;
        mCurPosY=y;
        if (mGestureType== GestureType.MOVING){
            mGestureType= GestureType.STOP;

            if (mOnScrollListener!=null){
                mOnScrollListener.onScrollStop();
                mPosX=mCurPosX;
                mPosY=mCurPosY;
            }
            return true;
        }else {
            if (mOnSuperListener!=null){
                return mOnSuperListener.dispatchTouchEvent(ev);
            }else {
                return false;
            }
        }
    }


    private enum GestureType{
        DOWN,//手指按下
        START,//第一次MOVE
        MOVING,//滑动中
        STOP,//滑动结束
        UNKNOWN//未知状态
    }

    public void setOnScrollListener(OnScrollListener listener){
        mOnScrollListener=listener;
    }

    public void setOnSuperListener(OnSuperListener listener){
        mOnSuperListener=listener;
    }

}
