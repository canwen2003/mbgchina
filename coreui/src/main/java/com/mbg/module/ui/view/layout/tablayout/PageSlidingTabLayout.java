package com.mbg.module.ui.view.layout.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.mbg.module.ui.R;
import com.mbg.module.ui.view.layout.tablayout.listener.OnBackgroundListener;

import java.util.ArrayList;

public final class PageSlidingTabLayout extends FrameLayout {
    private SlidingTabLayout mSlidingTabLayout;
    private LinearLayout mTabsBgContainer;
    private LinearLayout mTabsContainer;
    private OnBackgroundListener mOnBackgroundListener;

    private int mTabColor;
    private float mTabCornerRadius;

    public PageSlidingTabLayout(@NonNull Context context) {
        this(context,null);
    }

    public PageSlidingTabLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageSlidingTabLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        mTabsBgContainer = (LinearLayout) View.inflate(context, R.layout.view_layout_tab_bg,null);
        mTabsContainer=new LinearLayout(context);

        mSlidingTabLayout=new SlidingTabLayout(context,attrs,defStyleAttr);
        mTabsContainer.addView(mSlidingTabLayout);

        addView(mTabsBgContainer);
        addView(mTabsContainer);
        mOnBackgroundListener =new OnBackgroundListener() {
            @Override
            public View getView(int position) {
                if (mTabsBgContainer == null) {
                    return null;
                }

                GradientDrawable mTabDrawable = new GradientDrawable();
                mTabDrawable.setCornerRadius(mTabCornerRadius);
                mTabDrawable.setColor(mTabColor);
                if (position == 0) {
                    View view=mTabsBgContainer.findViewById(R.id.view_item_1);
                    view.setVisibility(VISIBLE);
                    view.setBackground(mTabDrawable);
                    return view;
                } else if (position == 1) {
                    View view=mTabsBgContainer.findViewById(R.id.view_item_2);
                    view.setVisibility(VISIBLE);
                    view.setBackground(mTabDrawable);
                    return view;
                } else if (position == 2) {
                    View view=mTabsBgContainer.findViewById(R.id.view_item_3);
                    view.setVisibility(VISIBLE);
                    view.setBackground(mTabDrawable);
                    return view;
                } else if (position == 3) {
                    View view=mTabsBgContainer.findViewById(R.id.view_item_4);
                    view.setVisibility(VISIBLE);
                    view.setBackground(mTabDrawable);
                    return view;
                }
                else if (position == 4) {
                    View view=mTabsBgContainer.findViewById(R.id.view_item_5);
                    view.setVisibility(VISIBLE);
                    view.setBackground(mTabDrawable);
                    return view;
                }
                else if (position == 5) {
                    View view=mTabsBgContainer.findViewById(R.id.view_item_6);
                    view.setVisibility(VISIBLE);
                    view.setBackground(mTabDrawable);
                    return view;
                }
                else if (position == 6) {
                    View view=mTabsBgContainer.findViewById(R.id.view_item_7);
                    view.setVisibility(VISIBLE);
                    view.setBackground(mTabDrawable);
                    return view;
                }
                else if (position == 7) {
                    View view=mTabsBgContainer.findViewById(R.id.view_item_8);
                    view.setVisibility(VISIBLE);
                    view.setBackground(mTabDrawable);
                    return view;
                }

                return null;
            }
        };


        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingTabLayout);
        if (ta!=null) {
            mTabColor = ta.getColor(R.styleable.SlidingTabLayout_tab_color, Color.TRANSPARENT);
            mTabCornerRadius = ta.getDimension(R.styleable.SlidingTabLayout_indicator_corner_radius, 0);
            ta.recycle();
        }


        mSlidingTabLayout.setTabBackGroundView(mOnBackgroundListener);

    }

    /** 关联ViewPager */
    public void setViewPager(@NonNull ViewPager vp) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setViewPager(vp);
        }
    }

    /** 关联ViewPager,用于不想在ViewPager适配器中设置titles数据的情况 */
    public void setViewPager(@NonNull ViewPager vp, @NonNull String[] titles) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setViewPager(vp,titles);
        }
    }

    /** 关联ViewPager,用于连适配器都不想自己实例化的情况 */
    public void setViewPager(@NonNull ViewPager vp, @NonNull String[] titles, @NonNull FragmentManager fm, @NonNull ArrayList<Fragment> fragments) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setViewPager(vp,titles,fm,fragments);
        }
    }


    //setter and getter
    public void setCurrentTab(int currentTab) {
       if (mSlidingTabLayout!=null){
           mSlidingTabLayout.setCurrentTab(currentTab);
       }

    }

    public void setCurrentTab(int currentTab, boolean smoothScroll) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setCurrentTab(currentTab,smoothScroll);
        }
    }

    public void setIndicatorStyle(int indicatorStyle) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setIndicatorStyle(indicatorStyle);
        }
    }

    public void setTabPadding(float tabPadding) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setTabPadding(tabPadding);
        }
    }

    public void setTabSpaceEqual(boolean tabSpaceEqual) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setTabSpaceEqual(tabSpaceEqual);
        }
    }

    public void setTabWidth(float tabWidth) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setTabWidth(tabWidth);
        }
    }

    public void setIndicatorColor(int indicatorColor) {
        if (mSlidingTabLayout != null) {
            mSlidingTabLayout.setIndicatorColor(indicatorColor);
        }
    }

    public void setIndicatorHeight(float indicatorHeight) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setIndicatorHeight(indicatorHeight);
        }
    }

    public void setIndicatorWidth(float indicatorWidth) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setIndicatorWidth(indicatorWidth);
        }
    }

    public void setIndicatorCornerRadius(float indicatorCornerRadius) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setIndicatorCornerRadius(indicatorCornerRadius);
        }
    }

    public void setIndicatorGravity(int indicatorGravity) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setIndicatorGravity(indicatorGravity);
        }
    }

    public void setIndicatorMargin(float indicatorMarginLeft, float indicatorMarginTop,
                                   float indicatorMarginRight, float indicatorMarginBottom) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setIndicatorMargin(indicatorMarginLeft,indicatorMarginTop,indicatorMarginRight,indicatorMarginBottom);
        }
    }

    public void setIndicatorWidthEqualTitle(boolean indicatorWidthEqualTitle) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setIndicatorWidthEqualTitle(indicatorWidthEqualTitle);
        }
    }

    public void setUnderlineColor(int underlineColor) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setUnderlineColor(underlineColor);
        }
    }

    public void setUnderlineHeight(float underlineHeight) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setUnderlineHeight(underlineHeight);
        }
    }

    public void setUnderlineGravity(int underlineGravity) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setUnderlineGravity(underlineGravity);
        }
    }

    public void setDividerColor(int dividerColor) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setDividerColor(dividerColor);
        }
    }

    public void setDividerWidth(float dividerWidth) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setDividerWidth(dividerWidth);
        }
    }

    public void setDividerPadding(float dividerPadding) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setDividerPadding(dividerPadding);
        }
    }

    public void setTextsize(float textsize) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setTextsize(textsize);
        }
    }

    public void setTextSelectColor(int textSelectColor) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setTextSelectColor(textSelectColor);
        }
    }

    public void setTextUnselectColor(int textUnselectColor) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setTextUnselectColor(textUnselectColor);
        }
    }

    public void setTextBold(int textBold) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setTextBold(textBold);
        }
    }

    public void setTextAllCaps(boolean textAllCaps) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setTextAllCaps(textAllCaps);
        }
    }

    public void setSnapOnTabClick(boolean snapOnTabClick) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.setSnapOnTabClick(snapOnTabClick);
        }
    }


    public void showMsg(int position, int num) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.showMsg(position,num);
        }
    }

    /**
     * 显示未读红点
     *
     * @param position 显示tab位置
     */
    public void showDot(int position) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.showDot(position);
        }
    }

    /** 隐藏未读消息 */
    public void hideMsg(int position) {
        if (mSlidingTabLayout!=null){
            mSlidingTabLayout.hideMsg(position);
        }
    }



}
