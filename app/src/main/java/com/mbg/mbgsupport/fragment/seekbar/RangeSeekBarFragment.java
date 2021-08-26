package com.mbg.mbgsupport.fragment.seekbar;


import android.content.Context;

import com.mbg.mbgsupport.R;
import com.mbg.module.common.util.LogUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.FilterScopeItemView;
import com.mbg.module.ui.view.seekBar.OnRangeChangedListener;
import com.mbg.module.ui.view.seekBar.RangeSeekBar;
import com.mbg.module.ui.view.seekBar.SeekBar;


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
        seekBar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float leftValue, float rightValue, boolean isFromUser) {
                LogUtils.d("onRangeChanged:leftValue="+leftValue+" rightValue="+rightValue+" isFromUser="+isFromUser);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {//开始拖动
                LogUtils.d("onStartTrackingTouch:isLeft="+isLeft);
            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {//拖动结束
                LogUtils.d("onStopTrackingTouch:isLeft="+isLeft);
            }
        });

        seekBar.setProgressDrawableId(R.drawable.progress);
        seekBar.setProgressDefaultDrawableId(R.drawable.progress_default);

        seekBar.setProgress(30,30);

        FilterScopeItemView filterScopeItemView=findViewById(R.id.filter_view);
        filterScopeItemView.setRange(18,80);
        filterScopeItemView.setDefaultValue(20,40);
        filterScopeItemView.setOnValueChangeListener(new FilterScopeItemView.OnValueChangeListener() {
            @Override
            public void onValueChange(int start, int end) {
                LogUtils.d("onRangeChanged:leftValue="+start+" rightValue="+end);
            }
        });
        filterScopeItemView.setIndicatorTextDecimalFormat(new SeekBar.OnIndicatorShowListener(){
            @Override
            public String getFormatText(float value) {
                return value+" ft";
            }
        });
    }
}
