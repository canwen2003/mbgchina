package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;


public class AvatarFrameLayout extends FrameLayout{
    private ViewPager2 mViewPager2;

    public AvatarFrameLayout(@NonNull Context context) {
        this(context,null);
    }

    public AvatarFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AvatarFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mViewPager2 !=null){
             return mViewPager2.onTouchEvent(event);
        }
       return super.onTouchEvent(event);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mViewPager2 !=null){

            return mViewPager2.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mViewPager2 !=null){

            return mViewPager2.onInterceptTouchEvent(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setmViewPager2(ViewPager2 viewPager2){
        this.mViewPager2 =viewPager2;
    }
}
