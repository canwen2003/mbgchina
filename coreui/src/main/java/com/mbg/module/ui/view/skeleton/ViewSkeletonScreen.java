package com.mbg.module.ui.view.skeleton;


import android.animation.ValueAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;

import com.mbg.module.ui.R;
import com.mbg.module.ui.view.shimmer.Shimmer;
import com.mbg.module.ui.view.shimmer.ShimmerFrameLayout;


/**
 * Created by ethanhua on 2017/7/29.
 */

public class ViewSkeletonScreen implements SkeletonScreen {
    private static final String TAG = ViewSkeletonScreen.class.getName();
    private final ViewReplacer mViewReplacer;
    private final View mActualView;
    private final int mSkeletonResID;
    private final float mShimmerBaseAlpha;
    private final float mShimmerHighlightAlpha;
    private final boolean mShimmer;
    private final int mShimmerDuration;
    private final int mShimmerAngle;


    private ViewSkeletonScreen(Builder builder) {
        mActualView = builder.mView;
        mSkeletonResID = builder.mSkeletonLayoutResID;
        mShimmer = builder.mShimmer;
        mShimmerDuration = builder.mShimmerDuration;
        mShimmerAngle = builder.mShimmerAngle;
        mShimmerBaseAlpha = builder.mShimmerBaseAlpha;
        mShimmerHighlightAlpha=builder.mShimmerHighlightAlpha;
        mViewReplacer = new ViewReplacer(builder.mView);
    }

    private ShimmerFrameLayout generateShimmerContainerLayout(ViewGroup parentView) {
        final ShimmerFrameLayout shimmerLayout = (ShimmerFrameLayout) LayoutInflater.from(mActualView.getContext()).inflate(R.layout.view_layout_shimmer, parentView, false);

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

        shimmerLayout.setShimmer(alphaHighlightBuilder.build());

        View innerView = LayoutInflater.from(mActualView.getContext()).inflate(mSkeletonResID, shimmerLayout, false);
        ViewGroup.LayoutParams lp = innerView.getLayoutParams();
        if (lp != null) {
            shimmerLayout.setLayoutParams(lp);
        }
        shimmerLayout.addView(innerView);
        shimmerLayout.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                shimmerLayout.startShimmer();
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                shimmerLayout.startShimmer();
            }
        });
        //shimmerLayout.startShimmerAnimation();
        return shimmerLayout;
    }

    private View generateSkeletonLoadingView() {
        ViewParent viewParent = mActualView.getParent();
        if (viewParent == null) {
            Log.e(TAG, "the source view have not attach to any view");
            return null;
        }
        ViewGroup parentView = (ViewGroup) viewParent;
        if (mShimmer) {
            return generateShimmerContainerLayout(parentView);
        }
        return LayoutInflater.from(mActualView.getContext()).inflate(mSkeletonResID, parentView, false);
    }

    @Override
    public void show() {
        View skeletonLoadingView = generateSkeletonLoadingView();
        if (skeletonLoadingView != null) {
            mViewReplacer.replace(skeletonLoadingView);
        }
    }

    @Override
    public void hide() {
        if (mViewReplacer.getTargetView() instanceof ShimmerFrameLayout) {
            ((ShimmerFrameLayout) mViewReplacer.getTargetView()).stopShimmer();
        }
        mViewReplacer.restore();
    }

    public static class Builder {
        private final View mView;
        private int mSkeletonLayoutResID;
        private boolean mShimmer = true;
        private int mShimmerDuration = 1000;
        private int mShimmerAngle = 20;
        private float mShimmerBaseAlpha;
        private float mShimmerHighlightAlpha;

        public Builder(View view) {
            this.mView = view;
            this.mShimmerBaseAlpha=1.0f;
            this.mShimmerHighlightAlpha=1.0f;
        }

        /**
         * @param skeletonLayoutResID the loading skeleton layoutResID
         */
        public Builder load(@LayoutRes int skeletonLayoutResID) {
            this.mSkeletonLayoutResID = skeletonLayoutResID;
            return this;
        }

        /**
         * @param baseAlpha Sets the base alpha, which is the alpha of the underlying children, amount in the range [0, 1].
         *
         */
        public ViewSkeletonScreen.Builder setBaseAlpha(@FloatRange(from = 0, to = 1) float baseAlpha) {
            this.mShimmerBaseAlpha = baseAlpha;
            return this;
        }

        /**
         * @param highLightAlpha Sets the shimmer alpha amount in the range [0, 1].
         */
        public ViewSkeletonScreen.Builder setHighLightAlpha(@FloatRange(from = 0, to = 1) float highLightAlpha) {
            this.mShimmerHighlightAlpha = highLightAlpha;
            return this;
        }

        /**
         * @param shimmer whether show shimmer animation
         */
        public ViewSkeletonScreen.Builder shimmer(boolean shimmer) {
            this.mShimmer = shimmer;
            return this;
        }

        /**
         * the duration of the animation , the time it will take for the highlight to move from one end of the layout
         * to the other.
         *
         * @param shimmerDuration Duration of the shimmer animation, in milliseconds
         */
        public ViewSkeletonScreen.Builder duration(int shimmerDuration) {
            this.mShimmerDuration = shimmerDuration;
            return this;
        }

        /**
         * @param shimmerAngle the angle of the shimmer effect in clockwise direction in degrees.
         */
        public ViewSkeletonScreen.Builder angle(@IntRange(from = 0, to = 30) int shimmerAngle) {
            this.mShimmerAngle = shimmerAngle;
            return this;
        }

        public ViewSkeletonScreen show() {
            ViewSkeletonScreen skeletonScreen = new ViewSkeletonScreen(this);
            skeletonScreen.show();
            return skeletonScreen;
        }

    }
}
