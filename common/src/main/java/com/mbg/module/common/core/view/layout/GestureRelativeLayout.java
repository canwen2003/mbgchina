package com.mbg.module.common.core.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.mbg.module.common.core.view.detector.GestureDetector;
import com.mbg.module.common.core.view.listener.OnScrollListener;

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
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureEnable) {
            return mGestureDetector.onTouchEvent(event);
        }else {
            return super.onTouchEvent(event);
        }
    }


    private void init(Context context){
        mGestureDetector=new GestureDetector(context);
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
