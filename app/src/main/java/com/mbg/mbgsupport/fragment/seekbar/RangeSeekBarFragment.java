package com.mbg.mbgsupport.fragment.seekbar;


import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.seekBar.RangeSeekBar;


public class RangeSeekBarFragment extends BaseFragment {

    public static void show(Context context){
        TerminalActivity.show(context, RangeSeekBarFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_range;
    }

    @Override
    protected void initView() {
        RangeSeekBar seekBar=findViewById(R.id.sb_range_1);
        seekBar.setRange(0,100,3);

        seekBar.setProgressDrawableId(R.drawable.progress);
        seekBar.setProgressDefaultDrawableId(R.drawable.progress_default);

        seekBar.setProgress(30,30);
    }
}
