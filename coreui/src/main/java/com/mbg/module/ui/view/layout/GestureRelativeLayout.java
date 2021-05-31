package com.mbg.module.ui.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.mbg.module.ui.view.detector.GestureDetector;
import com.mbg.module.ui.view.listener.OnScrollListener;
import com.mbg.module.ui.view.listener.OnSuperListener;

/**
 * created by Gap
 * 支持手势识别的layout
 */
public class GestureRelativeLayout extends RelativeLayout {
    private GestureDetector mGestureDetector;
    private boolean mGestureEnable;

    public GestureRelativeLayout(Context context) {
        super(context);
        init(context);
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GestureRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mGestureEnable) {
            return mGestureDetector.dispatchTouchEvent(ev);
        }else {
            return super.dispatchTouchEvent(ev);
        }
    }

    private boolean dispatchTouchEventEx(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private void init(Context context){
        mGestureDetector=new GestureDetector(context);
        mGestureDetector.setOnSuperListener(new OnSuperListener() {
            @Override
            public boolean dispatchTouchEvent(MotionEvent ev) {
                return GestureRelativeLayout.this.dispatchTouchEventEx(ev);
            }
        });
        mGestureEnable=true;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        if (mGestureDetector!=null){
            mGestureDetector.setOnScrollListener(onScrollListener);
        }
    }

    public void setGestureStatus(boolean enable){
        mGestureEnable=enable;
    }

}
