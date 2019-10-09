package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.common.SlideDirection;
import com.mbg.module.ui.view.layout.SlidFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class SlidingFragment extends BaseFragment implements View.OnClickListener{
    private SlidFrameLayout mViewPager;
    private MyFragmentAdapter mFragmentAdapter;
    private List<String> mData=new ArrayList<>();
    public static void show(Context context){
        TerminalActivity.show(context, SlidingFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_slid;
    }

    @Override
    protected void initView() {
       mViewPager=findViewById(R.id.viewPager);
       for (int i=0;i<20;i++){
           mData.add("fragment "+i);
       }
       mFragmentAdapter=new MyFragmentAdapter(getFragmentManager());
       mViewPager.setAdapter(mFragmentAdapter);

    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }
    }

    private class MyFragmentAdapter extends SlidFrameLayout.SlideFragmentAdapter {
        private int currentIndex = 0;
        public MyFragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment onCreateFragment(Context context) {
            return new SlidDemoFragment();
        }

        @Override
        protected void onBindFragment(Fragment fragment, SlideDirection direction) {
            int index=0;
            switch (direction){
                case Next:
                    index=currentIndex+1;
                    break;
                case Prev:
                    index=currentIndex-1;
                    break;
                case Origin:
                    index=currentIndex;
                    break;
            }


            //bind data to the ui
            if(fragment instanceof SlidDemoFragment){
                ((SlidDemoFragment) fragment).setTitle(mData.get(index));
            }

        }

        @Override
        protected void finishSlide(SlideDirection direction) {
            super.finishSlide(direction);

            // 修正当前的索引
            switch (direction){
                case Next:
                    currentIndex++;
                    break;
                case Prev:
                    currentIndex--;
                    break;
                case Origin:
                    break;
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
            int index;
            switch (direction){
                case Next:
                    index=currentIndex+1;
                    break;
                case Prev:
                    index=currentIndex-1;
                    break;
                default:
                    index=currentIndex;
                    break;
            }
            return index>=0&&index<mData.size();
        }
    }
}
