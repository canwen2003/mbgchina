package com.mbg.module.ui.view.banner.listener;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Sai on 15/7/29.
 * 翻页指示器适配器
 */
public class CBPageChangeListener implements OnPageChangeListener{
    private ArrayList<ImageView> indicatorViews;
    private int[] page_indicatorId;
    private OnPageChangeListener onPageChangeListener;
    public CBPageChangeListener(ArrayList<ImageView> indicatorViews,@NonNull int[] indicatorIds){
        this.indicatorViews =indicatorViews;
        this.page_indicatorId = indicatorIds;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if(onPageChangeListener != null)onPageChangeListener.onScrollStateChanged(recyclerView, newState);
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(onPageChangeListener != null)onPageChangeListener.onScrolled(recyclerView,dx,dy);
    }

    public void onPageSelected(int index) {
        for (int i = 0; i < indicatorViews.size(); i++) {
            indicatorViews.get(index).setImageResource(page_indicatorId[1]);
            if (index != i) {
                indicatorViews.get(i).setImageResource(page_indicatorId[0]);
            }
        }
        if(onPageChangeListener != null){
            onPageChangeListener.onPageSelected(index);
        }

    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }
}
