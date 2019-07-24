package com.mbg.module.common.core.view.callback;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.customview.widget.ViewDragHelper;

public class DragViewCallback extends ViewDragHelper.Callback {

    private ViewDragHelper mViewDragHelper;
    private View mRootView;

    public DragViewCallback(ViewDragHelper viewDragHelper,View rootView){
        this.mViewDragHelper=viewDragHelper;
        this.mRootView=rootView;
    }

    //child 表示想要滑动的view
    //pointerId 表示触摸点的id, 比如多点按压的那个id
    //返回值表示,是否可以capture,也就是是否可以滑动.可以根据不同的child决定是否可以滑动
    @Override
    public boolean tryCaptureView(@NonNull View child, int pointerId) {
        return false;
    }


    //child 表示当前正在移动的view
    //left 表示当前的view正要移动到左边距为left的地方
    //dx 表示和上一次滑动的距离间隔
    //返回值就是child要移动的目标位置.可以通过控制返回值,从而控制child只能在ViewGroup的范围中移动.
    @Override
    public int clampViewPositionHorizontal(View child, int left, int dx) {

        return left;
    }

    //child 表示当前正在移动的view
    //top 表示当前的view正要移动到上边距为top的地方
    //dx 表示和上一次滑动的距离间隔
    @Override
    public int clampViewPositionVertical(View child, int top, int dy) {

        return top;
    }


    //释放的时候, 会回调下面的方法
    @Override
    public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
        //调用这个方法,就可以设置releasedChild回弹得位置.
        //调用这个方法,就可以设置releasedChild回弹得位置.
        mViewDragHelper.settleCapturedViewAt(0, 100);//参数就是x,y的坐标
        mRootView.postInvalidate();
    }
}
