package com.mbg.module.ui.view.viewPager;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;


import com.mbg.module.ui.view.adapter.SlideAdapter;
import com.mbg.module.ui.view.listener.OnSlidListener;
import com.mbg.module.ui.view.common.SlideDirection;
import com.mbg.module.ui.view.holder.FragmentViewHolder;
import com.mbg.module.ui.view.holder.SlideViewHolder;
import com.mbg.module.ui.view.listener.OnAnimatorListener;

import java.util.ArrayList;
import java.util.List;

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
    private Context context;
    private LayoutInflater mInflater;
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
        mInflater = LayoutInflater.from(context);
        mGestureCallback = new MyOnGestureListener();
        mGestureDetector = new GestureDetector(context, mGestureCallback);
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


                if (!mViewHolderDelegate.adapter.canSlideTo(direction)) {
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

private enum State {
    /**
     * 静止状态
     */
    IDLE(Mask.IDLE),
    /**
     * 正在向下一页拖动
     */
    SLIDE_NEXT(Mask.SLIDE | Mask.NEXT),
    /**
     * 正在向上一页拖动
     */
    SLIDE_PREV(Mask.SLIDE | Mask.PREV),
    /**
     * 无法拖动到下一页
     */
    SLIDE_REJECT_NEXT(Mask.REJECT | Mask.SLIDE | Mask.NEXT),
    /**
     * 无法拖动到上一页
     */
    SLIDE_REJECT_PREV(Mask.REJECT | Mask.SLIDE | Mask.PREV),
    /**
     * 手指离开，惯性滑行到下一页
     */
    FLING_NEXT(Mask.FLING | Mask.NEXT),
    /**
     * 手指离开，惯性滑行到上一页
     */
    FLING_PREV(Mask.FLING | Mask.PREV);

    private int flag;

    State(int flag) {
        this.flag = flag;
    }

    public boolean satisfy(int mask) {
        return ((flag & mask) == mask);
    }

    public static State of(int... mask) {
        int flag = 0;
        for (int tag : mask) {
            flag = flag | tag;
        }

        State state = IDLE;
        for (State item : State.values()) {
            if (item.flag == flag) {
                state = item;
                break;
            }
        }

        return state;
    }
}

private interface Mask {
    int IDLE = 0x000001;
    int NEXT = 0x000010;
    int PREV = 0x000100;
    int SLIDE = 0x001000;
    int FLING = 0x010000;
    int REJECT = 0x100000;
}

private class ViewHolderDelegate<T extends SlideViewHolder> {
    private SlideAdapter<T> adapter;

    public ViewHolderDelegate(SlideAdapter<T> adapter) {
        this.adapter = adapter;
    }

    T currentViewHolder = null;

    T backupViewHolder = null;

    T prepareCurrent(SlideDirection direction) {
        if (currentViewHolder == null) {
            currentViewHolder = adapter.onCreateViewHolder(context, VViewPager.this, mInflater);
        }
        if (currentViewHolder.view.getParent() == null) {
            addView(currentViewHolder.view, 0);
        }
        adapter.onBindView(currentViewHolder, direction);
        return currentViewHolder;
    }

    T prepareBackup(SlideDirection direction) {
        if (backupViewHolder == null) {
            backupViewHolder = adapter.onCreateViewHolder(context, VViewPager.this, mInflater);
        }
        if (backupViewHolder.view.getParent() == null) {
            addView(backupViewHolder.view, 0);
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
            adapter.onViewDismiss(backupViewHolder, VViewPager.this, direction);
        }
    }

    public void swap() {
        T tmp = currentViewHolder;
        currentViewHolder = backupViewHolder;
        backupViewHolder = tmp;
    }
}

    private void requestParentDisallowInterceptTouchEvent() {
        if (getParent()!=null){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
    }

    /**
     * 设置适配器。
     */
    public void setAdapter(SlideAdapter<? extends SlideViewHolder> adapter ) {
        removeAllViews();
        mViewHolderDelegate=new ViewHolderDelegate<>(adapter);
        mViewHolderDelegate.prepareCurrent(SlideDirection.Origin);
        mViewHolderDelegate.onCompleteCurrent(SlideDirection.Origin, true);
    }

public static abstract class SlideFragmentAdapter implements SlideAdapter<FragmentViewHolder> {
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


abstract class SlideViewAdapter implements SlideAdapter<SlideViewHolder> {

    /**
     * 创建 [View] 。
     * 一般来说，该方法会在 [SlidFrameLayout.setAdapter] 方法调用时触发一次，创建当前显示的 [View]，
     * 会在首次开始滑动时触发第二次，创建滑动目标的 [View]。
     */
    protected abstract View onCreateView(Context context, ViewGroup parent, LayoutInflater inflater);

    /**
     * 当 [view] 开始滑动到可见时触发，在这个方法中实现数据和 [view] 的绑定。
     *
     * @param direction 滑动的方向
     */
    protected abstract void onBindView(View view, SlideDirection direction);

    /**
     * 当滑动完成时触发。
     *
     * @param direction 滑动的方向
     */
    protected void finishSlide(SlideDirection direction) {
    }

    /**
     * 当滑动完成时触发。
     *
     * @param direction 滑动的方向
     */
    protected void finishSlide(View dismissView, View visibleView, SlideDirection direction) {
    }

    /**
     * 当滑动完成时，离开的 [view] 会触发，在这个方法中实现对 [view] 的清理。
     *
     * @param direction 滑动的方向
     */
    protected void onViewDismiss(View view, ViewGroup parent, SlideDirection direction) {
        parent.removeView(view);
    }

    /**
     * 当 [view] 完全出现时触发。
     * 这个时机可能是 [SlidFrameLayout.setAdapter] 后 [view] 的第一次初始化，
     * 也可能是完成一次滑动，在 [finishSlide] 后 **而且** 滑到了一个新的 [view]。
     * <p>
     * 也就是说，如果 [finishSlide] 的 [SlideDirection] 是 [SlideDirection.Origin] ，
     * 也就是滑动回弹到本来的界面上，是不会触发 [onViewComplete] 的。
     * <p>
     * 在这个方法中实现当 [view] 第一次完全出现时才做的业务。比如开始播放视频。
     */
    protected void onViewComplete(View view, SlideDirection direction) {
    }

    public final SlideViewHolder onCreateViewHolder(Context context, ViewGroup parent, LayoutInflater inflater) {
        return new SlideViewHolder(onCreateView(context, parent, inflater));
    }

    @Override
    public final void onBindView(SlideViewHolder viewHolder, SlideDirection direction) {
        View v = viewHolder.view;
        onBindView(v, direction);
        if (v instanceof OnSlidListener) {
            ((OnSlidListener) v).startVisible(direction);
        }
    }

    @Override
    public final void onViewDismiss(SlideViewHolder viewHolder, ViewGroup parent, SlideDirection direction) {
        View v = viewHolder.view;
        if (v instanceof OnSlidListener) {
            ((OnSlidListener) v).invisible(direction);
        }
        onViewDismiss(v, parent, direction);
    }

    @Override
    public final void onViewComplete(SlideViewHolder viewHolder, SlideDirection direction) {
        View v = viewHolder.view;
        onViewComplete(v, direction);
        if (v instanceof OnSlidListener) {
            ((OnSlidListener) v).completeVisible(direction);
        }
    }

    @Override
    public final void finishSlide(SlideViewHolder dismissViewHolder, SlideViewHolder visibleViewHolder, SlideDirection direction) {
        finishSlide(direction);
        finishSlide(dismissViewHolder.view, visibleViewHolder.view, direction);
        if (dismissViewHolder.view instanceof OnSlidListener) {
            ((OnSlidListener) dismissViewHolder.view).preload(direction);
        }
    }
}


}