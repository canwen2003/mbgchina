package com.mbg.module.ui.view.pudding;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import com.mbg.module.common.util.TypeUtils;
import com.mbg.module.ui.view.listener.OnAnimatorListener;

public class SwipeDismissTouchListener implements View.OnTouchListener {
        private final View mView;
        private final DismissCallbacks mCallbacks;

        // Cached ViewConfiguration and system-wide constant values
        private int mSlop;
        private int mMinFlingVelocity;
        private long mAnimationTime;
        private int mViewWidth = 1 ;// 1 and not 0 to prevent dividing by zero

        // Transient properties
        private float mDownX = 0f;
        private float mDownY = 0f;
        private boolean mSwiping = false;
        private int mSwipingSlop = 0;
        private VelocityTracker mVelocityTracker = null;
        private float mTranslationX=0f;

    public SwipeDismissTouchListener(View view,DismissCallbacks dismissCallbacks) {
        mView=view;
        mCallbacks=dismissCallbacks;
        init();
    }

    void init (){
        ViewConfiguration vc = ViewConfiguration.get(mView.getContext());
            mSlop = vc.getScaledTouchSlop();
            mMinFlingVelocity = vc.getScaledMinimumFlingVelocity() * 16;
            mAnimationTime = mView.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public boolean onTouch(View view,  MotionEvent motionEvent){
        // offset because the view is translated during swipe
        motionEvent.offsetLocation(mTranslationX, 0f);

        if (mViewWidth < 2) {
            mViewWidth = mView.getWidth();
        }
        float deltaX;
        float deltaY;
        switch (motionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = motionEvent.getRawX();
                mDownY = motionEvent.getRawY();
                if (mCallbacks.canDismiss()) {
                    mVelocityTracker = VelocityTracker.obtain();
                    mVelocityTracker.addMovement(motionEvent);
                }
                mCallbacks.onTouch(view, true);
                return false;

            case MotionEvent.ACTION_UP:
                deltaX = motionEvent.getRawX() - mDownX;
                mVelocityTracker.addMovement(motionEvent);
                mVelocityTracker.addMovement(motionEvent);
                mVelocityTracker.computeCurrentVelocity(1000);
                float velocityX = mVelocityTracker.getXVelocity();
                float absVelocityX = Math.abs(velocityX);
                float absVelocityY = Math.abs(mVelocityTracker.getYVelocity());
                boolean dismiss = false;
                boolean dismissRight = false;
                    if (Math.abs(deltaX) > mViewWidth / 2 && mSwiping) {
                        dismiss = true;
                        dismissRight = deltaX > 0;
                    } else if (mMinFlingVelocity <= absVelocityX && absVelocityY < absVelocityX && mSwiping) {
                        // dismiss only if flinging in the same direction as dragging
                        dismiss = velocityX < 0 == deltaX < 0;
                        dismissRight = mVelocityTracker.getXVelocity() > 0;
                    }
                    if (dismiss) {
                        // dismiss
                        mView.animate()
                                .translationX(dismissRight?mViewWidth:-mViewWidth)
                                .alpha(0f)
                                .setDuration(mAnimationTime)
                                .setListener(new OnAnimatorListener() {
                                    @Override
                                    public void onAnimationEnd(Animator animator) {
                                        performDismiss();
                                    }
                                });
                    } else if (mSwiping) {
                        // cancel
                        mView.animate()
                                .translationX(0f)
                                .alpha(1f)
                                .setDuration(mAnimationTime)
                                .setListener(null);
                        mCallbacks.onTouch(view, false);
                    }
                mVelocityTracker.recycle();
                    mVelocityTracker = null;
                    mTranslationX = 0f;
                    mDownX = 0f;
                    mDownY = 0f;
                    mSwiping = false;

            break;
            case MotionEvent.ACTION_CANCEL:
                if (mVelocityTracker!=null){
                    mView.animate()
                            .translationX(0f)
                            .alpha(1f)
                            .setDuration(mAnimationTime)
                            .setListener(null);
                mVelocityTracker.recycle();
                    mVelocityTracker = null;
                    mTranslationX = 0f;
                    mDownX = 0f;
                    mDownY = 0f;
                    mSwiping = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(mVelocityTracker!=null) {
                    mVelocityTracker.addMovement(motionEvent);
                    deltaX = motionEvent.getRawX() - mDownX;
                    deltaY = motionEvent.getRawY() - mDownY;
                    if (Math.abs(deltaX) > mSlop && Math.abs(deltaY) < Math.abs(deltaX) / 2) {
                        mSwiping = true;
                        mSwipingSlop = deltaX > 0? mSlop : -mSlop;
                        mView.getParent().requestDisallowInterceptTouchEvent(true);

                        // Cancel listview's touch
                        MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);
                        cancelEvent.setAction(MotionEvent.ACTION_CANCEL|(motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                        mView.onTouchEvent(cancelEvent);
                        cancelEvent.recycle();
                    }

                    if (mSwiping) {
                        mTranslationX = deltaX;
                        mView.setTranslationX(deltaX - mSwipingSlop);

                        mView.setAlpha( Math.max(0f, Math.min(1f, 1f - 2f * Math.abs(deltaX) / mViewWidth)));
                        return true;
                    }
                }
                break;
            default:
                view.performClick();
                return false;
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void performDismiss() {
        // Animate the dismissed view to zero-height and then fire the dismiss callback.
        // This triggers layout on each animation frame; in the future we may want to do something
        // smarter and more performant.

        final ViewGroup.LayoutParams lp = mView.getLayoutParams();
        final int originalHeight = mView.getHeight();

        ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1).setDuration(mAnimationTime);
        animator.addListener(new OnAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mCallbacks.onDismiss(mView);
                // Reset view presentation
                mView.setAlpha(1f);
                mView.setTranslationX(0f);
                lp.height = originalHeight;
                mView.setLayoutParams(lp);
            }
        });

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                lp.height= TypeUtils.cast(valueAnimator.getAnimatedValue());
                mView.setLayoutParams(lp);
            }
        });

        animator.start();
    }

    /**
     * The callback interface used by [SwipeDismissTouchListener] to inform its client
     * about a successful dismissal of the view for which it was created.
     */
     interface DismissCallbacks {
        /**
         * Called to determine whether the view can be dismissed.
         *
         * @return boolean The view can dismiss.
         */
        boolean canDismiss();

        /**
         * Called when the user has indicated they she would like to dismiss the view.
         *
         * @param view  The originating [View]
         */
        boolean onDismiss(View view);

        /**
         * Called when the user touches the view or release the view.
         *
         * @param view  The originating [View]
         * @param touch The view is being touched.
         */
        boolean onTouch(View view, Boolean touch);
    }
}
