package com.mbg.module.ui.view.viewPager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.viewPager.common.SlideDirection;
import com.mbg.module.ui.view.viewPager.fragment.SampleSlidFragment;

import java.util.List;

public final class SampleFragmentAdapter extends SlideFragmentAdapter {
    private Class<? extends BaseFragment> fragmentClass;
    private List<?> mData;

    public SampleFragmentAdapter(FragmentManager fragmentManager, Class<? extends SampleSlidFragment> fragmentClass ,@NonNull List<?> data) {
        super(fragmentManager);
        this.fragmentClass=fragmentClass;
        this.mData=data;
    }

    @Override
    public Fragment onCreateFragment(Context context) {
        BaseFragment fragment=null;
        try {
             fragment = fragmentClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

    /**
     * 当 [View] 开始滑动到可见时触发，在这个方法中实现数据和 [View] 的绑定。
     *
     * @param fragment 持有 [View] 的 [SlideViewHolder]
     * @param direction  滑动的方向
     */
    @Override
    protected void onBindFragment(Fragment fragment, SlideDirection direction) {
        int nextPos=0;
        switch (direction){
            case Next:
                nextPos=mCurrentPosition+1;
                break;
            case Prev:
                nextPos=mCurrentPosition-1;
                break;
            case Origin:
                nextPos=mCurrentPosition;
                break;
        }

        //bind data to the ui
        if(fragment instanceof SampleSlidFragment){
            ((SampleSlidFragment) fragment).setData(mData.get(nextPos));
        }

    }


    /**
     * 能否向 [direction] 的方向滑动。
     *
     * @param direction 滑动的方向
     * @return 返回 true 表示可以滑动， false 表示不可滑动。
     * 如果有嵌套其他外层滑动布局（比如下拉刷新），当且仅当返回 false 时会触发外层的嵌套滑动。
     */
    @Override
    public boolean canSlideTo(SlideDirection direction) {
        int nextPos;
        switch (direction){
            case Next:
                nextPos=mCurrentPosition+1;
                break;
            case Prev:
                nextPos=mCurrentPosition-1;
                break;
            default:
                nextPos=mCurrentPosition;
                break;
        }
        return nextPos>=0&&nextPos<mData.size();
    }

    /**
     * 当滑动完成时触发。
     *
     * @param direction 滑动的方向
     */
    @Override
    protected void finishSlide(SlideDirection direction) {
        super.finishSlide(direction);
    }
}
