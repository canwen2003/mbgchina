package com.mbg.mbgsupport.fragment;

import android.content.Context;
import android.view.View;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.fragment.timeline.DoubleTimeLine.DateInfoDTLFragment;
import com.mbg.mbgsupport.fragment.timeline.DoubleTimeLine.WeekPlanDTLFragment;
import com.mbg.mbgsupport.fragment.timeline.SingleTimeLine.NoteInfoSTLFragment;
import com.mbg.mbgsupport.fragment.timeline.SingleTimeLine.SocialMediaSTLFragment;
import com.mbg.mbgsupport.fragment.timeline.SingleTimeLine.StepSTLFragment;
import com.mbg.mbgsupport.fragment.timeline.SingleTimeLine.WeekPlanSTLFragment;
import com.mbg.module.common.util.ClickUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;


public class TimeLineFragment extends BaseFragment implements View.OnClickListener {

    public static void show(Context context) {
        TerminalActivity.show(context, TimeLineFragment.class, null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.fragment_timeline;
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_test1).setOnClickListener(this);
        findViewById(R.id.btn_test2).setOnClickListener(this);
        findViewById(R.id.btn_test3).setOnClickListener(this);
        findViewById(R.id.btn_test4).setOnClickListener(this);
        findViewById(R.id.btn_test5).setOnClickListener(this);
        findViewById(R.id.btn_test6).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();
        if(ClickUtils.isFastDoubleClick(viewId)){
            return;
        }
        switch (viewId){
            case R.id.btn_test1:
                onTest1();
                break;
            case R.id.btn_test2:
                onTest2();
                break;
            case R.id.btn_test3:
                onTest3();
                break;
            case R.id.btn_test4:
                onTest4();
                break;
            case R.id.btn_test5:
                onTest5();
                break;
            case R.id.btn_test6:
                onTest6();
                break;
        }

    }

    private void onTest1(){
        WeekPlanSTLFragment.show(getActivity());
    }
    private void onTest2(){
        StepSTLFragment.show(getActivity());
    }
    private void onTest3() {
        SocialMediaSTLFragment.show(getActivity());
    }
    private void onTest4(){
        NoteInfoSTLFragment.show(getActivity());
    }

    private void onTest5(){
        DateInfoDTLFragment.show(getActivity());
    }
    private void onTest6(){
        WeekPlanDTLFragment.show(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
