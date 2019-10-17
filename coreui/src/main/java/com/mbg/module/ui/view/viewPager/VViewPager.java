package com.mbg.module.ui.view.viewPager;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild2;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;


import com.mbg.module.ui.view.viewPager.adapter.SlideAdapter;
import com.mbg.module.ui.view.viewPager.common.Mask;
import com.mbg.module.ui.view.viewPager.common.SlideDirection;
import com.mbg.module.ui.view.viewPager.common.State;
import com.mbg.module.ui.view.viewPager.holder.SlideViewHolder;
import com.mbg.module.ui.view.listener.OnAnimatorListener;
import com.mbg.module.ui.view.viewPager.holder.ViewHolderDelegate;


/*****
 *
 * created by Gap
 * 实现垂直方向的滑动切换页面的功能
 */
public class VViewPager extends FrameLayout implements NestedScrollingChild2 {
    private final static float MIN_FLING_VELOCITY = 400; // dips
    private final static int MAX_DURATION = 800; //最大滑行时间ms
    private NestedScrollingChildHelper mNestedScrollingChildHelper;
    private float mMinFlingSpeed;
    private int mTouchSlop;
    private boolean isNestedScrollingEnabled;
    private State mState;
    private Scroller mScroller;
    private ValueAnimator mValueAnimator;
    public Context context;
    private ViewHolderDelegate<? extends SlideViewHolder> mViewHolderDelegate = null;

    private View mCurrentView = null;
    private View mBackupView = null;

    private float downY = 0f;
    private float downX = 0f;
    private int[] mScrollConsumed = new int[2];
    private int[] mScrollOffset = new int[2];
    private GestureDetector mGestureDetector;
    private MyOnGestureListener mGestureCallback;


    public VViewPager(Context context) {
        this(context, null);
    }

    public VViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        mMinFlingSpeed = context.getResources().getDisplayMetrics().density * MIN_FLING_VELOCITY;
        mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        isNestedScrollingEnabled = true;
        mState = State.IDLE;
        mScroller = new Scroller(context);
        mGestureCallback = new MyOnGestureListener();
        mGestureDetector = new GestureDetector(context, mGestureCallback);
    }

    /**
     * 设置适配器。
     */
    public void setAdapter(SlideAdapter<? extends SlideViewHolder> adapter ) {
        removeAllViews();
        mViewHolderDelegate=new ViewHolderDelegate<>(this, adapter);
        mViewHolderDelegate.prepareCurrent(SlideDirection.Origin);
        mViewHolderDelegate.onCompleteCurrent(SlideDirection.Origin, true);
    }


    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (mState.satisfy(Mask.FLING)) {
                return waitForFling(distanceX, distanceY);
            }

            if (mViewHolderDelegate.currentViewHolder!=null) {
                mCurrentView = mViewHolderDelegate.currentViewHolder.view;
            }
            if (mViewHolderDelegate.backupViewHolder!=null) {
                mBackupView = mViewHolderDelegate.backupViewHolder.view;
            }

            if (mCurrentView == null) {
                return false;
            }
            View topView = mCurrentView;


            float dyFromDownY = e2.getY() - downY;
            float dxFromDownX = e2.getX() - downX;
            SlideDirection direction;
            if (dyFromDownY < 0) {
                direction = SlideDirection.Next;
            } else if (dyFromDownY > 0) {
                direction = SlideDirection.Prev;
            } else {
                direction = SlideDirection.Origin;
            }


            boolean startToMove = mState.satisfy(Mask.IDLE) && Math.abs(dyFromDownY) > 2 * Math.abs(dxFromDownX);
            boolean changeDirectionToNext = mState.satisfy(Mask.PREV) && dyFromDownY < 0;
            boolean changeDirectionToPrev = mState.satisfy(Mask.NEXT) && dyFromDownY > 0;

            int dx = (int) distanceX;
            int dy = (int) distanceY;
            if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset)) {
                dx -= mScrollConsumed[0];
                dy -= mScrollConsumed[1];
                dxFromDownX -= mScrollConsumed[0];
                dyFromDownY -= mScrollConsumed[1];
            }

            if (startToMove) {
                requestParentDisallowInterceptTouchEvent();
            }

            if (startToMove || changeDirectionToNext || changeDirectionToPrev) {
                int directionMask;
                if (direction == SlideDirection.Next)
                    directionMask = Mask.NEXT;
                else
                    directionMask = Mask.PREV;


                if (!mViewHolderDelegate.getAdapter().canSlideTo(direction)) {
                    mState = State.of(directionMask, Mask.SLIDE, Mask.REJECT);
                } else {
                    mState = State.of(directionMask, Mask.SLIDE);
                    mViewHolderDelegate.prepareBackup(direction);
                }
            }
            if (mState.satisfy(Mask.REJECT)) {
                return dispatchNestedScroll(0, 0, dx, dy, mScrollOffset);

            } else if (mState.satisfy(Mask.SLIDE)) {
                if (mBackupView == null) {
                    return false;
                }
                topView.setY(dyFromDownY);
                if (mState.satisfy(Mask.NEXT)) {
                    mBackupView.setY(dyFromDownY + getMeasuredHeight());
                } else {
                    mBackupView.setY(dyFromDownY - getMeasuredHeight());
                }
                return dispatchNestedScroll(0, dy, dx, 0, mScrollOffset);
            }
            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            onUp(velocityX, velocityY);
            return true;
        }

        private boolean onUp(float velocityX, float velocityY) {
            if (!(mState.satisfy(Mask.SLIDE))) {
                stopNestedScroll();
                return false;
            }

            final View topView = mCurrentView;
            if (topView == null) {
                return resetTouch();
            }
            int currentOffsetY = (int) topView.getY();
            // if state is reject, don't consume the fling.
            boolean consumedFling = !(mState.satisfy(Mask.REJECT)) || currentOffsetY != 0;
            if (!dispatchNestedPreFling(velocityX, velocityY)) {
                dispatchNestedFling(velocityX, velocityY, consumedFling);
            }
            stopNestedScroll();

            final View backView = mBackupView;
            if (backView == null) {
                return resetTouch();
            }
            if (mViewHolderDelegate == null) {
                return resetTouch();
            }

            SlideDirection direction = null;
            int duration = 0;

            final int widgetHeight = getMeasuredHeight();
            if (consumedFling) {
                int dy = 0;
                boolean highSpeed = Math.abs(velocityY) >= mMinFlingSpeed;
                boolean sameDirection = (mState == State.SLIDE_NEXT && velocityY < 0) ||
                        (mState == State.SLIDE_PREV && velocityY > 0);
                boolean moveLongDistance = Math.abs(currentOffsetY) > widgetHeight / 3;
                if ((highSpeed && sameDirection) || (!highSpeed && moveLongDistance)) { //fling
                    if (mState == State.SLIDE_NEXT) {
                        direction = SlideDirection.Next;
                        dy = -currentOffsetY - widgetHeight;
                    } else if (mState == State.SLIDE_PREV) {
                        direction = SlideDirection.Prev;
                        dy = widgetHeight - currentOffsetY;
                    }
                } else { //back to origin
                    direction = SlideDirection.Origin;
                    dy = -currentOffsetY;
                }

                if (dy != 0) {
                    duration = calculateDuration(velocityY, widgetHeight, dy);
                    mScroller.startScroll(0, currentOffsetY, 0, dy, duration);
                }
            }

            if (direction != null && duration != 0) { //perform fling animation
                if (mValueAnimator != null) {
                    mValueAnimator.cancel();
                }
                mValueAnimator = ValueAnimator.ofFloat(1f).setDuration(duration);
                mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        if (mScroller.computeScrollOffset()) {
                            float offset = mScroller.getCurrY();
                            topView.setY(offset);
                            if (mState == State.FLING_NEXT)
                                backView.setY(offset + widgetHeight);
                            else
                                backView.setY(offset - widgetHeight);
                        }
                    }
                });
                final SlideDirection slideDirection = direction;
                mValueAnimator.addListener(new OnAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        if (slideDirection != SlideDirection.Origin) {
                            mViewHolderDelegate.swap();
                        }
                        mViewHolderDelegate.onDismissBackup(slideDirection);
                        mState = State.of(Mask.IDLE);
                        if (slideDirection != SlideDirection.Origin) {
                            mViewHolderDelegate.onCompleteCurrent(slideDirection, false);
                        }
                        mViewHolderDelegate.finishSlide(slideDirection);
                    }
                });
                mValueAnimator.start();


                int directionMask;
                if (mState.satisfy(Mask.NEXT)) {
                    directionMask = Mask.NEXT;
                } else {
                    directionMask = Mask.PREV;
                }
                mState = State.of(directionMask, Mask.FLING);
                return true;
            } else {
                return resetTouch();
            }
        }


        private boolean resetTouch() {
            mState = State.of(Mask.IDLE);
            if (mBackupView != null && mBackupView.getParent() != null) {
                ((ViewGroup)mBackupView.getParent()).removeView(mBackupView);
            }

            return false;
        }


        @Override
        public boolean onDown(MotionEvent e) {
            downY = e.getY();
            downX = e.getX();
            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
            return true;
        }


        private boolean waitForFling(float dx, float dy) {
            //eat all the dy
            int unconsumedX = (int) dx;
            int consumedY = (int) dy;
            if (!dispatchNestedPreScroll(unconsumedX, consumedY, mScrollConsumed,
                    mScrollOffset, ViewCompat.TYPE_NON_TOUCH)) {
                dispatchNestedScroll(0, consumedY, unconsumedX, 0,
                        mScrollOffset, ViewCompat.TYPE_NON_TOUCH);
            }
            return true;
        }
    }


    private float distanceInfluenceForSnapDuration(float f) {
        double t = (double) f;
        t -= 0.5; // center the values about 0.
        t *= 0.3 * Math.PI / 2.0;
        return (float) Math.sin(t);
    }

    private int calculateDuration(float velocity, int maxDistance, int currentDistance) {

        // We want the duration of the page snap animation to be influenced by the distance that
        // the screen has to travel, however, we don't want this duration to be effected in a
        // purely linear fashion. Instead, we use this method to moderate the effect that the distance
        // of travel has on the overall snap duration.

        int half = maxDistance / 2;
        float distanceRatio = Math.min(1f, (float) Math.abs(currentDistance) / maxDistance);
        float distance = half + half * distanceInfluenceForSnapDuration(distanceRatio);

        float v = Math.abs(velocity);
        int duration;
        if (v > 0) {
            duration = 4 * Math.round(1000 * Math.abs(distance / v));
        } else {
            float pageDelta = (float) Math.abs(currentDistance) / maxDistance;

            duration = (int) ((pageDelta + 1f) * 100);
        }
        return Math.min(duration, MAX_DURATION);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mNestedScrollingChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mNestedScrollingChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mNestedScrollingChildHelper.isNestedScrollingEnabled();
    }


    @Override
    public boolean startNestedScroll(int axes) {
        return mNestedScrollingChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mNestedScrollingChildHelper.stopNestedScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        if (mGestureDetector.onTouchEvent(event)) {
            return true;
        } else if (action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_CANCEL) {

            if (mGestureCallback.onUp(0, 0)) {
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction()&MotionEvent.ACTION_MASK;

        boolean intercept = false;

        if (action != MotionEvent.ACTION_MOVE) {
            if (mState != State.IDLE) {
                intercept = true;
            }
        }
        if (action==MotionEvent.ACTION_DOWN) {
                downX = event.getX();
                downY = event.getY();
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
        }
         if (action== MotionEvent.ACTION_MOVE){
                float dy = Math.abs(event.getY() - downY);
                float dx = Math.abs(event.getX() - downX);
                if (dy > mTouchSlop && dy > 2 * dx) {
                    requestParentDisallowInterceptTouchEvent();
                    intercept = true;
                }
         }

        return intercept||super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mNestedScrollingChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mNestedScrollingChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return mNestedScrollingChildHelper.startNestedScroll(axes, type);
    }

    @Override
    public void stopNestedScroll(int type) {
        mNestedScrollingChildHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return mNestedScrollingChildHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return mNestedScrollingChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return mNestedScrollingChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow, type);
    }

    private void requestParentDisallowInterceptTouchEvent() {
        if (getParent()!=null){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

}