package com.mbg.module.ui.view.viewPager.holder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mbg.module.ui.view.viewPager.adapter.SlideAdapter;
import com.mbg.module.ui.view.viewPager.common.SlideDirection;

public class ViewHolderDelegate<T extends SlideViewHolder> {
    private ViewGroup vViewPager;
    private SlideAdapter<T> adapter;
    private LayoutInflater mInflater;

    public ViewHolderDelegate(ViewGroup vViewPager, SlideAdapter<T> adapter) {
        this.vViewPager = vViewPager;
        this.adapter = adapter;
        mInflater = LayoutInflater.from(vViewPager.getContext());
    }

    public T currentViewHolder = null;

    public T backupViewHolder = null;

    public T prepareCurrent(SlideDirection direction) {
        if (currentViewHolder == null) {
            currentViewHolder = adapter.onCreateViewHolder(vViewPager.getContext(), vViewPager, mInflater);
        }
        if (currentViewHolder.view.getParent() == null) {
            vViewPager.addView(currentViewHolder.view, 0);
        }
        adapter.onBindView(currentViewHolder, direction);
        return currentViewHolder;
    }

    public T prepareBackup(SlideDirection direction) {
        if (backupViewHolder == null) {
            backupViewHolder = adapter.onCreateViewHolder(vViewPager.getContext(), vViewPager, mInflater);
        }
        if (backupViewHolder.view.getParent() == null) {
            vViewPager.addView(backupViewHolder.view, 0);
        }
        adapter.onBindView(backupViewHolder, direction);
        return backupViewHolder;
    }

    public void onCompleteCurrent(final SlideDirection direction, boolean isInit) {
        if (currentViewHolder != null) {
            if (isInit) {
                currentViewHolder.view.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.onViewComplete(currentViewHolder, direction);
                    }
                });
            } else {
                adapter.onViewComplete(currentViewHolder, direction);
            }
        }
    }

    public void finishSlide(SlideDirection direction) {
        T visible = currentViewHolder;
        T dismiss = backupViewHolder;
        if (visible != null && dismiss != null) {
            adapter.finishSlide(dismiss, visible, direction);
        }
    }

    public void onDismissBackup(SlideDirection direction) {
        if (backupViewHolder != null) {
            adapter.onViewDismiss(backupViewHolder, vViewPager, direction);
        }
    }

    public void swap() {
        T tmp = currentViewHolder;
        currentViewHolder = backupViewHolder;
        backupViewHolder = tmp;
    }

    public SlideAdapter<T> getAdapter(){
        return adapter;
    }
}
