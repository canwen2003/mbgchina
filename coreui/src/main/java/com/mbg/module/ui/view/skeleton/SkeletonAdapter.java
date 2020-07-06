package com.mbg.module.ui.view.skeleton;


import android.animation.ValueAnimator;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.module.ui.view.shimmer.Shimmer;
import com.mbg.module.ui.view.shimmer.ShimmerFrameLayout;


/**
 * Created by ethanhua on 2017/7/29.
 */

public class SkeletonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int mItemCount;
    private int mLayoutReference;
    private int[] mLayoutArrayReferences;
    private int mColor;
    private boolean mShimmer;
    private int mShimmerDuration;
    private int mShimmerAngle;
    private  float mShimmerBaseAlpha=1.0f;
    private  float mShimmerHighlightAlpha=1.0f;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (doesArrayOfLayoutsExist()) {
            mLayoutReference = viewType;
        }
        if (mShimmer) {
            return new ShimmerViewHolder(inflater, parent, mLayoutReference);
        }

        return new RecyclerView.ViewHolder(inflater.inflate(mLayoutReference, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mShimmer) {
            ShimmerFrameLayout layout = (ShimmerFrameLayout) holder.itemView;

            Shimmer.AlphaHighlightBuilder alphaHighlightBuilder=new Shimmer.AlphaHighlightBuilder();
            alphaHighlightBuilder.setAutoStart(false);
            alphaHighlightBuilder.setDirection(Shimmer.Direction.LEFT_TO_RIGHT);
            alphaHighlightBuilder.setClipToChildren(true);
            alphaHighlightBuilder.setRepeatCount(ValueAnimator.INFINITE);
            alphaHighlightBuilder.setRepeatMode(ValueAnimator.RESTART);
            alphaHighlightBuilder.setBaseAlpha(mShimmerBaseAlpha);//设置基础View的透明度
            alphaHighlightBuilder.setHighlightAlpha(mShimmerHighlightAlpha);
            alphaHighlightBuilder.setDropoff(0.6f);
            alphaHighlightBuilder.setTilt(mShimmerAngle);
            alphaHighlightBuilder.setDuration(mShimmerDuration);
            layout.setShimmer(alphaHighlightBuilder.build());
            layout.startShimmer();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(doesArrayOfLayoutsExist()) {
            return getCorrectLayoutItem(position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public void setLayoutReference(int layoutReference) {
        this.mLayoutReference = layoutReference;
    }

    public void setArrayOfLayoutReferences(int[] layoutReferences) {
        this.mLayoutArrayReferences = layoutReferences;
    }

    public void setItemCount(int itemCount) {
        this.mItemCount = itemCount;
    }


    /**
     * @param baseAlpha Sets the base alpha, which is the alpha of the underlying children, amount in the range [0, 1].
     *
     */
    public void setBaseAlpha(@FloatRange(from = 0, to = 1) float baseAlpha) {
        this.mShimmerBaseAlpha = baseAlpha;
    }

    /**
     * @param highLightAlpha Sets the shimmer alpha amount in the range [0, 1].
     */
    public void setHighLightAlpha(@FloatRange(from = 0, to = 1) float highLightAlpha) {
        this.mShimmerHighlightAlpha = highLightAlpha;
    }


    public void shimmer(boolean shimmer) {
        this.mShimmer = shimmer;
    }

    public void setShimmerDuration(int shimmerDuration) {
        this.mShimmerDuration = shimmerDuration;
    }

    public void setShimmerAngle(@IntRange(from = 0, to = 30) int shimmerAngle) {
        this.mShimmerAngle = shimmerAngle;
    }

    public int getCorrectLayoutItem(int position) {
        if(doesArrayOfLayoutsExist()) {
            return mLayoutArrayReferences[position % mLayoutArrayReferences.length];
        }
        return mLayoutReference;
    }

    private boolean doesArrayOfLayoutsExist() {
        return mLayoutArrayReferences != null && mLayoutArrayReferences.length != 0;
    }
}
