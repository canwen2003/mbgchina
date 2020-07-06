package com.mbg.module.ui.view.skeleton;


import androidx.annotation.ArrayRes;
import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.module.ui.R;

/**
 * Created by ethanhua on 2017/7/29.
 */

public class RecyclerViewSkeletonScreen implements SkeletonScreen {

    private final RecyclerView mRecyclerView;
    private final RecyclerView.Adapter mActualAdapter;
    private final SkeletonAdapter mSkeletonAdapter;
    private final boolean mRecyclerViewFrozen;
    private  float mShimmerBaseAlpha=1.0f;
    private  float mShimmerHighlightAlpha=1.0f;

    private RecyclerViewSkeletonScreen(Builder builder) {
        mRecyclerView = builder.mRecyclerView;
        mActualAdapter = builder.mActualAdapter;
        mSkeletonAdapter = new SkeletonAdapter();
        mSkeletonAdapter.setItemCount(builder.mItemCount);
        mSkeletonAdapter.setLayoutReference(builder.mItemResID);
        mSkeletonAdapter.setArrayOfLayoutReferences(builder.mItemsResIDArray);
        mSkeletonAdapter.shimmer(builder.mShimmer);
        mSkeletonAdapter.setBaseAlpha(builder.mShimmerBaseAlpha);
        mSkeletonAdapter.setHighLightAlpha(builder.mShimmerHighlightAlpha);
        mSkeletonAdapter.setShimmerAngle(builder.mShimmerAngle);
        mSkeletonAdapter.setShimmerDuration(builder.mShimmerDuration);
        mRecyclerViewFrozen = builder.mFrozen;
    }

    @Override
    public void show() {
        mRecyclerView.setAdapter(mSkeletonAdapter);
        if (!mRecyclerView.isComputingLayout() && mRecyclerViewFrozen) {
            mRecyclerView.setLayoutFrozen(true);
        }
    }

    @Override
    public void hide() {
        mRecyclerView.setAdapter(mActualAdapter);
    }

    public static class Builder {
        private RecyclerView.Adapter mActualAdapter;
        private final RecyclerView mRecyclerView;
        private boolean mShimmer = true;
        private int mItemCount = 10;
        private int mItemResID = R.layout.view_layout_default_item_skeleton;
        private int[] mItemsResIDArray;


        private int mShimmerDuration = 1000;
        private int mShimmerAngle = 20;
        private boolean mFrozen = true;
        private  float mShimmerBaseAlpha=1.0f;
        private  float mShimmerHighlightAlpha=1.0f;

        public Builder(RecyclerView recyclerView) {
            this.mRecyclerView = recyclerView;

        }

        /**
         * @param adapter the target recyclerView actual adapter
         */
        public Builder adapter(RecyclerView.Adapter adapter) {
            this.mActualAdapter = adapter;
            return this;
        }

        /**
         * @param itemCount the child item count in recyclerView
         */
        public Builder count(int itemCount) {
            this.mItemCount = itemCount;
            return this;
        }

        /**
         * @param baseAlpha Sets the base alpha, which is the alpha of the underlying children, amount in the range [0, 1].
         *
         */
        public Builder setBaseAlpha(@FloatRange(from = 0, to = 1) float baseAlpha) {
            this.mShimmerBaseAlpha = baseAlpha;
            return this;
        }

        /**
         * @param highLightAlpha Sets the shimmer alpha amount in the range [0, 1].
         */
        public Builder setHighLightAlpha(@FloatRange(from = 0, to = 1) float highLightAlpha) {
            this.mShimmerHighlightAlpha = highLightAlpha;
            return this;
        }

        /**
         * @param shimmer whether show shimmer animation
         */
        public Builder shimmer(boolean shimmer) {
            this.mShimmer = shimmer;
            return this;
        }

        /**
         * the duration of the animation , the time it will take for the highlight to move from one end of the layout
         * to the other.
         *
         * @param shimmerDuration Duration of the shimmer animation, in milliseconds
         */
        public Builder duration(int shimmerDuration) {
            this.mShimmerDuration = shimmerDuration;
            return this;
        }


        /**
         * @param shimmerAngle the angle of the shimmer effect in clockwise direction in degrees.
         */
        public Builder angle(@IntRange(from = 0, to = 30) int shimmerAngle) {
            this.mShimmerAngle = shimmerAngle;
            return this;
        }

        /**
         * @param skeletonLayoutResID the loading skeleton layoutResID
         */
        public Builder load(@LayoutRes int skeletonLayoutResID) {
            this.mItemResID = skeletonLayoutResID;
            return this;
        }

        /**
         * @param skeletonLayoutResIDs the loading array of skeleton layoutResID
         */
        public Builder loadArrayOfLayouts(@ArrayRes int[] skeletonLayoutResIDs) {
            this.mItemsResIDArray = skeletonLayoutResIDs;
            return this;
        }

        /**
         * @param frozen whether frozen recyclerView during skeleton showing
         * @return
         */
        public Builder frozen(boolean frozen) {
            this.mFrozen = frozen;
            return this;
        }

        public RecyclerViewSkeletonScreen show() {
            RecyclerViewSkeletonScreen recyclerViewSkeleton = new RecyclerViewSkeletonScreen(this);
            recyclerViewSkeleton.show();
            return recyclerViewSkeleton;
        }
    }
}
