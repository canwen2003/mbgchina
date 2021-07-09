package com.mbg.mbgsupport.fragment.tab;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.layout.tab.PageSlidingTabLayout;
import com.mbg.module.ui.view.layout.tab.SlidingTabLayout;
import com.mbg.module.ui.view.layout.tab.listener.OnTabSelectListener;
import com.mbg.module.ui.view.layout.tab.widget.MsgView;

import java.util.ArrayList;

public class SlidingTabFragment extends BaseFragment implements View.OnClickListener, OnTabSelectListener {
    
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "热门", "iOS", "Android"
            , "前端"/*, "后端", "设计", "工具资源"*/
    };
   /* private final String[] mTitles = {
            "热门", "Android","iOS","工具资源"
    };*/
   
    public static void show(Context context){
        TerminalActivity.show(context, SlidingTabFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_sliding_tab;
    }

    @Override
    protected void initView() {

        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }

        
        ViewPager viewPager = findViewById(R.id.vp);
        MyPagerAdapter mAdapter = new MyPagerAdapter(getFragmentManager());
        viewPager.setAdapter(mAdapter);

        /** 默认 */
        SlidingTabLayout tabLayout_1 = findViewById( R.id.tl_1);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = findViewById( R.id.tl_2);
        /** 字体加粗,大写 */
        SlidingTabLayout tabLayout_3 = findViewById( R.id.tl_3);
        /** tab固定宽度 */
        SlidingTabLayout tabLayout_4 = findViewById( R.id.tl_4);
        /** indicator固定宽度 */
        SlidingTabLayout tabLayout_5 = findViewById( R.id.tl_5);
        tabLayout_5.setIndicatorColor(getResources().getColor(R.color.blue_500),getResources().getColor(R.color.blue_200));
        tabLayout_5.setIndicatorCornerRadius(2.0f);
        tabLayout_5.setIndicatorHeight(4.0f);
        //tabLayout_5.setIndicatorWidth(32.0f);
        /** indicator圆 */
        SlidingTabLayout tabLayout_6 = findViewById( R.id.tl_6);
        /** indicator矩形圆角 */
        final PageSlidingTabLayout tabLayout_7 = findViewById( R.id.tl_7);
        /** indicator三角形 */
        SlidingTabLayout tabLayout_8 = findViewById( R.id.tl_8);
        /** indicator圆角色块 */
        PageSlidingTabLayout tabLayout_9 = findViewById( R.id.tl_9);
        /** indicator圆角色块 */
        PageSlidingTabLayout tabLayout_10 = findViewById( R.id.tl_10);

        tabLayout_1.setViewPager(viewPager);
        tabLayout_2.setViewPager(viewPager);
        tabLayout_2.setOnTabSelectListener(this);
        tabLayout_3.setViewPager(viewPager);
        tabLayout_4.setViewPager(viewPager);
        tabLayout_5.setViewPager(viewPager);
        tabLayout_6.setViewPager(viewPager);
        tabLayout_7.setViewPager(viewPager, mTitles);
        tabLayout_8.setViewPager(viewPager, mTitles, getChildFragmentManager(), mFragments);
        tabLayout_9.setViewPager(viewPager);
        tabLayout_10.setViewPager(viewPager);

        viewPager.setCurrentItem(4);

        tabLayout_1.showDot(4);
        tabLayout_3.showDot(4);
        tabLayout_2.showDot(4);
        tabLayout_10.showDot(2);

        tabLayout_2.showMsg(3, 5);
        tabLayout_2.setMsgMargin(3, 0, 10);
        MsgView rtv_2_3 = tabLayout_2.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }

        tabLayout_2.showMsg(5, 5);
        tabLayout_2.setMsgMargin(5, 0, 10);

//        tabLayout_7.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//                mFragments.add(SimpleCardFragment.getInstance("后端"));
//                mAdapter.notifyDataSetChanged();
//                tabLayout_7.addNewTab("后端");
//            }
//        });
    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(getContext(), "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(getContext(), "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }

    }
}
