package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;

public class BubbleViewFragment extends BaseFragment implements View.OnClickListener{

    public static void show(Context context){
        TerminalActivity.show(context, BubbleViewFragment.class,null);
    }
    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_bubble_view;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View view) {

    }
}
