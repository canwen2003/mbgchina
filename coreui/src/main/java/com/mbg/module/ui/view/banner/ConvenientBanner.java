package com.mbg.module.ui.view.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.R;
import com.mbg.module.ui.view.banner.adapter.CBPageAdapter;
import com.mbg.module.ui.view.banner.helper.CBLoopScaleHelper;
import com.mbg.module.ui.view.banner.holder.CBViewHolderCreator;
import com.mbg.module.ui.view.banner.listener.CBPageChangeListener;
import com.mbg.module.ui.view.banner.listener.OnItemClickListener;
import com.mbg.module.ui.view.banner.listener.OnPageChangeListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面翻转控件，极方便的广告栏
 * 支持无限循环，自动翻页，翻页特效
 *
 * @author Sai 支持自动翻页
 */
public class ConvenientBanner extends RelativeLayout {
    private List<?> mData;
    private int[] mIndicatorIds;
    private ArrayList<ImageView> mIndicatorViews = new ArrayList<>();
    private CBPageAdapter mPageAdapter;
    private RecyclerView mRecyclerView;
    private ViewGroup mIndicatorContainer;
    private long autoTurningTime;
    private boolean turning;
    private boolean canTurn = false;
    private boolean canLoop;
    private CBLoopScaleHelper cbLoopScaleHelper;
    private CBPageChangeListener pageChangeListener;
    private OnPageChangeListener onPageChangeListener;
    private AdSwitchTask adSwitchTask;

    public ConvenientBanner(Context context) {
        this(context,null);
    }

    public ConvenientBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
        if (typedArray!=null) {
            canLoop = typedArray.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
            autoTurningTime = typedArray.getInteger(R.styleable.ConvenientBanner_autoTurningTime, -1);
            typedArray.recycle();
        }
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(R.layout.view_convenient_include_viewpager, this, true);
        mRecyclerView = hView.findViewById(R.id.banner_recycler_view);
        mIndicatorContainer = hView.findViewById(R.id.banner_indicator_view);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        cbLoopScaleHelper = new CBLoopScaleHelper();
        adSwitchTask = new AdSwitchTask(this);
    }

    @SuppressWarnings("unused")
    public ConvenientBanner setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
        return this;
    }

    @SuppressWarnings("unused")
    public ConvenientBanner setPages(CBViewHolderCreator holderCreator, List<?> datas) {
        this.mData = datas;
        mPageAdapter = new CBPageAdapter(holderCreator, mData, canLoop);
        mRecyclerView.setAdapter(mPageAdapter);

        if (mIndicatorIds != null)
            setPageIndicator(mIndicatorIds[1],mIndicatorIds[0]);

        cbLoopScaleHelper.setFirstItemPos(canLoop ? mData.size() : 0);
        cbLoopScaleHelper.attachToRecyclerView(mRecyclerView);

        return this;
    }

    @SuppressWarnings("unused")
    public ConvenientBanner setCanLoop(boolean canLoop){
        this.canLoop = canLoop;
        mPageAdapter.setCanLoop(canLoop);
        notifyDataSetChanged();
        return this;
    }

    @SuppressWarnings("unused")
    public boolean isCanLoop(){
        return canLoop;
    }


    /**
     * 通知数据变化
     */
    public void notifyDataSetChanged() {
        RecyclerView.Adapter adapter= mRecyclerView.getAdapter();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        if (mIndicatorIds != null) {
            setPageIndicator(mIndicatorIds[1],mIndicatorIds[0]);
        }

        cbLoopScaleHelper.setCurrentItem(canLoop ? mData.size() : 0);
    }

    /**
     * 设置底部指示器是否可见
     *
     * @param visible 设置底部的Point指示器是否可见
     */
    @SuppressWarnings("unused")
    public ConvenientBanner setPointViewVisible(boolean visible) {
        mIndicatorContainer.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ConvenientBanner setPageIndicator(int indicatorSelectedId,int indicatorUnselectedId){
        return setPageIndicator(indicatorSelectedId,indicatorUnselectedId, UiUtils.dip2px(5), UiUtils.dip2px(10));
    }

    /***
     * 底部指示器资源图片
     * @param indicatorSelectedId 选择状态的指示器
     * @param indicatorUnselectedId 非选择状态的指示器
     * @param indicatorPadding 指示器左右直接的padding
     * @param indicatorMarginBottom 指示器距离底部的距离
     * @return 控件
     */
    public ConvenientBanner setPageIndicator(int indicatorSelectedId,int indicatorUnselectedId,int indicatorPadding,int indicatorMarginBottom) {
        mIndicatorContainer.removeAllViews();
        mIndicatorViews.clear();
        this.mIndicatorIds = new int[]{indicatorUnselectedId,indicatorSelectedId};

        if (mData == null){
            return this;
        }

        setPageIndicatorMarginBottom(indicatorMarginBottom);
        for (int count = 0; count < mData.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(indicatorPadding, 0, indicatorPadding, 0);
            if (cbLoopScaleHelper.getFirstItemPos()% mData.size()==count)
                pointView.setImageResource(mIndicatorIds[1]);
            else
                pointView.setImageResource(mIndicatorIds[0]);
            mIndicatorViews.add(pointView);
            mIndicatorContainer.addView(pointView);
        }
        pageChangeListener = new CBPageChangeListener(mIndicatorViews, mIndicatorIds);
        cbLoopScaleHelper.setOnPageChangeListener(pageChangeListener);
        if (onPageChangeListener != null) {
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);
        }

        return this;
    }

    @SuppressWarnings("unused")
    public OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    /**
     * 设置翻页监听器
     *
     * @param onPageChangeListener  翻页监听器
     * @return
     */
    @SuppressWarnings("unused")
    public ConvenientBanner setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if (pageChangeListener != null) {
            pageChangeListener.setOnPageChangeListener(onPageChangeListener);
        }
        else {
            cbLoopScaleHelper.setOnPageChangeListener(onPageChangeListener);
        }
        return this;
    }

    /**
     * 监听item点击
     *
     * @param onItemClickListener
     */
    @SuppressWarnings("unused")
    public ConvenientBanner setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            mPageAdapter.setOnItemClickListener(null);
            return this;
        }
        mPageAdapter.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 获取当前页对应的position
     * @return
     */
    @SuppressWarnings("unused")
    public int getCurrentItem() {
        return cbLoopScaleHelper.getRealCurrentItem();
    }
    /**
     * 设置当前页对应的position
     * @return
     */
    @SuppressWarnings("unused")
    public ConvenientBanner setCurrentItem(int position, boolean smoothScroll) {
        cbLoopScaleHelper.setCurrentItem(canLoop ? mData.size()+position : position, smoothScroll);
        return this;
    }

    /**
     * 设置第一次加载当前页对应的position
     * setPageIndicator之前使用
     * @return
     */
    @SuppressWarnings("unused")
    public ConvenientBanner setFirstItemPos(int position) {
        cbLoopScaleHelper.setFirstItemPos(canLoop ? mData.size()+position : position);
        return this;
    }
    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     * @return
     */
    @SuppressWarnings("unused")
    public ConvenientBanner setPageIndicatorAlign(PageIndicatorAlign align) {
        LayoutParams layoutParams = (LayoutParams) mIndicatorContainer.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        mIndicatorContainer.setLayoutParams(layoutParams);
        return this;
    }

    @SuppressWarnings("unused")
    public ConvenientBanner setPageIndicatorMarginBottom(int marginBottom) {
        LayoutParams layoutParams = (LayoutParams) mIndicatorContainer.getLayoutParams();
        layoutParams.bottomMargin=marginBottom;
        mIndicatorContainer.setLayoutParams(layoutParams);
        return this;
    }

    @SuppressWarnings("unused")
    public ConvenientBanner setPageIndicatorMarginTop(int marginTop) {
        LayoutParams layoutParams = (LayoutParams) mIndicatorContainer.getLayoutParams();
        layoutParams.topMargin=marginTop;
        mIndicatorContainer.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 是否开启了翻页
     * @return true 开启翻页
     */
    @SuppressWarnings("unused")
    public boolean isTurning() {
        return turning;
    }

    /***
     * 开始翻页
     * @param autoTurningTime 自动翻页时间
     * @return 获取banner对象
     */
    public ConvenientBanner startTurning(long autoTurningTime) {
        if (autoTurningTime < 0) {
            return this;
        }
        //如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        //设置可以翻页并开启翻页
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(adSwitchTask, autoTurningTime);
        return this;
    }

    @SuppressWarnings("unused")
    public ConvenientBanner startTurning() {
        startTurning(autoTurningTime);
        return this;
    }


    public void stopTurning() {
        turning = false;
        removeCallbacks(adSwitchTask);
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn) {
                startTurning(autoTurningTime);
            }
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn) {
                stopTurning();
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    private static class AdSwitchTask implements Runnable {

        private final WeakReference<ConvenientBanner> reference;

        AdSwitchTask(ConvenientBanner convenientBanner) {
            this.reference = new WeakReference<>(convenientBanner);
        }

        @Override
        public void run() {
            ConvenientBanner convenientBanner = reference.get();
            if (convenientBanner == null) {
                return;
            }

            if (convenientBanner.mRecyclerView != null && convenientBanner.turning) {
                int page = convenientBanner.cbLoopScaleHelper.getCurrentItem() + 1;
                convenientBanner.cbLoopScaleHelper.setCurrentItem(page, true);
                convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
            }
        }
    }

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

}
