package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.viewPager.HViewPager;
import com.mbg.module.ui.view.viewPager.adapter.SampleFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class SlidingFragment extends BaseFragment implements View.OnClickListener{
    private HViewPager mViewPager;
    private SampleFragmentAdapter mFragmentAdapter;
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
       for (int i=0;i<5;i++){
           mData.add("fragment "+i);
       }
       mFragmentAdapter=new SampleFragmentAdapter(getFragmentManager(),SlidDemoFragment.class,mData);
       mViewPager.setAdapter(mFragmentAdapter);

    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }
    }

}
