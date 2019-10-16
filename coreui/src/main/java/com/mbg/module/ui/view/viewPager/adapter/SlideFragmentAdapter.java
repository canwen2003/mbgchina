package com.mbg.module.ui.view.viewPager.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mbg.module.ui.view.viewPager.OnSlidListener;
import com.mbg.module.ui.view.viewPager.common.SlideDirection;
import com.mbg.module.ui.view.viewPager.holder.FragmentViewHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class SlideFragmentAdapter implements SlideAdapter<FragmentViewHolder> {
    private FragmentManager fm;

    public SlideFragmentAdapter(FragmentManager fragmentManager) {
        fm = fragmentManager;
    }

    private List<FragmentViewHolder> viewHolderList = new ArrayList<>();

    /**
     * 创建要显示的 [Fragment]。
     * 一般来说，该方法会在 [SlidFrameLayout.setAdapter] 调用时触发一次，创建当前显示的 [Fragment]，
     * 会在首次开始滑动时触发第二次，创建滑动目标的 [Fragment]。
     */
    public abstract Fragment onCreateFragment(Context context);

    protected void onBindFragment(Fragment fragment, SlideDirection direction) {
    }

    protected void finishSlide(SlideDirection direction) {
    }

    @Override
    public final FragmentViewHolder onCreateViewHolder(Context context, ViewGroup parent, LayoutInflater inflater) {
        FrameLayout viewGroup = new FrameLayout(context);
        viewGroup.setId(ViewCompat.generateViewId());
        Fragment fragment = onCreateFragment(context);
        fm.beginTransaction().add(viewGroup.getId(), fragment).commitAllowingStateLoss();
        FragmentViewHolder viewHolder = new FragmentViewHolder(viewGroup, fragment);
        viewHolderList.add(viewHolder);
        return viewHolder;
    }

    @Override
    public final void onBindView(FragmentViewHolder viewHolder, final SlideDirection direction) {
        final Fragment fragment = viewHolder.fragment;
        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
        viewHolder.view.post(new Runnable() {
            @Override
            public void run() {
                onBindFragment(fragment, direction);
                if (fragment instanceof OnSlidListener) {
                    ((OnSlidListener) fragment).startVisible(direction);
                }
            }
        });
    }

    @Override
    public final void onViewComplete(FragmentViewHolder viewHolder, SlideDirection direction) {
        Fragment fragment = viewHolder.fragment;
        fragment.setMenuVisibility(true);
        fragment.setUserVisibleHint(true);
        if (fragment instanceof OnSlidListener) {
            ((OnSlidListener) fragment).completeVisible(direction);
        }

        for (FragmentViewHolder fragmentViewHolder : viewHolderList) {
            if (fragmentViewHolder != viewHolder) {
                fragmentViewHolder.fragment.setUserVisibleHint(false);
                fragmentViewHolder.fragment.setMenuVisibility(false);
            }
        }
    }

    @Override
    public final void onViewDismiss(FragmentViewHolder viewHolder, ViewGroup parent, SlideDirection direction) {
        Fragment fragment = viewHolder.fragment;
        fm.beginTransaction().hide(fragment).commitAllowingStateLoss();
        if (fragment instanceof OnSlidListener) {
            ((OnSlidListener) fragment).invisible(direction);
        }
    }

    @Override
    public final void finishSlide(FragmentViewHolder dismissViewHolder, FragmentViewHolder visibleViewHolder, SlideDirection direction) {
        finishSlide(direction);
        if (dismissViewHolder.fragment instanceof OnSlidListener) {
            ((OnSlidListener) dismissViewHolder.fragment).preload(direction);
        }
    }
}
