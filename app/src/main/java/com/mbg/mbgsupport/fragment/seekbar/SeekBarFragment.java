package com.mbg.mbgsupport.fragment.seekbar;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.layout.tablayout.PageSlidingTabLayout;

import java.util.ArrayList;
import java.util.List;


public class SeekBarFragment extends BaseFragment {

    private static String[] types = new String[]{"SINGLE", "RANGE", "STEP","VERTICAL"};

    List<BaseFragment> fragments = new ArrayList<>();


    public static void show(Context context){
        TerminalActivity.show(context, SeekBarFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_seek_bar;
    }

    @Override
    protected void initView() {
        fragments.clear();
        fragments.add(new SingleSeekBarFragment());
        fragments.add(new RangeSeekBarFragment());
        fragments.add(new StepsSeekBarFragment());
        fragments.add(new VerticalSeekBarFragment());

        ViewPager viewPager = findViewById(R.id.viewPager);
        PageSlidingTabLayout tabLayout = findViewById(R.id.tab_view);
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
        tabLayout.setViewPager(viewPager,types);
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return types.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return types[position];
        }
    }

}
