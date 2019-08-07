package com.mbg.module.ui.view.detector;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.mbg.module.ui.view.listener.OnScrollListener;

/**
 * Created by Gap
 * 手势识别器
 */
public class GestureDetector {
    private float mPosX;//开始位置
    private float mPosY;
    private float mCurPosX;//目前位置
    private float mCurPosY;
    private GestureType mGestureType= GestureType.UNKNOW;
    private OnScrollListener mOnScrollListener;
    private float mMovingThreshold;//滑动阀值

    public GestureDetector(Context context){
        mMovingThreshold= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float focusX=ev.getX();
        float focusY=ev.getY();

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                down(focusX,focusY);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                up(focusX,focusY);
                break;
            case MotionEvent.ACTION_DOWN:
                down(focusX,focusY);
                break;
            case MotionEvent.ACTION_MOVE:
                move(focusX,focusY);
                break;
            case MotionEvent.ACTION_UP:
                up(focusX,focusY);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }
    private void down(float x,float y){
        mPosX=x;
        mPosY=y;
        mGestureType= GestureType.DOWN;
    }

    private void move(float x,float y){
        mCurPosX=x;
        mCurPosY=y;
        if (Math.abs(mCurPosX-mPosX)<mMovingThreshold&&Math.abs(mCurPosY-mPosY)<mMovingThreshold){
            return;
        }

        if (mGestureType== GestureType.DOWN){
            mGestureType= GestureType.START;
            if (mOnScrollListener!=null){
                mOnScrollListener.onScrollStart(Math.abs(mCurPosX-mPosX)<= Math.abs(mCurPosY-mPosY));
            }
        }else {
            mGestureType= GestureType.MOVING;
        }

        if (Math.abs(mCurPosX-mPosX)> Math.abs(mCurPosY-mPosY)){
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

    }

    private void up(float x,float y){
        mCurPosX=x;
        mCurPosY=y;
        if (mGestureType== GestureType.MOVING){
            mGestureType= GestureType.STOP;

            if (mOnScrollListener!=null){
                mOnScrollListener.onScrollStop();
                mPosX=mCurPosX;
                mPosY=mCurPosY;
            }
        }
    }


    private enum GestureType{
        DOWN,//手指按下
        START,//第一次MOVE
        MOVING,//滑动中
        STOP,//滑动结束
        UNKNOW//未知状态
    }

    public void setOnScrollListener(OnScrollListener listener){
        mOnScrollListener=listener;
    }

}
