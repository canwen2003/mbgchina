package com.mbg.module.common.core.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.customview.widget.ViewDragHelper;

import com.mbg.module.common.core.view.callback.DragViewCallback;

public class DragRelativeLayout extends RelativeLayout {
    private ViewDragHelper mViewDragHelper;
    private DragViewCallback mDragViewCallback;

    public DragRelativeLayout(Context context) {
        super(context);
    }

    public DragRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mDragViewCallback=new DragViewCallback(mViewDragHelper,this);
        //中间参数表示灵敏度,比如滑动了多少像素才视为触发了滑动.值越大越灵敏.
        mViewDragHelper = ViewDragHelper.create(this, 1f, mDragViewCallback);
    }
}
