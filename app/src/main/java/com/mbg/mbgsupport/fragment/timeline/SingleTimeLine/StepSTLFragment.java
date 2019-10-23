package com.mbg.mbgsupport.fragment.timeline.SingleTimeLine;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.adapter.WeekPlanSTLAdapter;
import com.mbg.mbgsupport.itemDecoration.timeline.singleTimeLine.StepSTL;
import com.mbg.mbgsupport.model.TimeItem;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.itemDecoration.timeline.SingleTimeLineDecoration;
import com.mbg.module.ui.view.itemDecoration.timeline.TimeLine;

import java.util.List;

public class StepSTLFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private WeekPlanSTLAdapter mAdapter;
    public static void show(Context context){
        TerminalActivity.show(context, StepSTLFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.common_fragment;
    }

    @Override
    protected void initView() {

        List<TimeItem> timeItems =TimeItem.initStepInfo();

        mRecyclerView=findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WeekPlanSTLAdapter(timeItems);
        mRecyclerView.setAdapter(mAdapter);

        TimeLine timeLine = provideTimeLine(timeItems);
        mRecyclerView.addItemDecoration(timeLine);
    }


    private TimeLine provideTimeLine(List<TimeItem> timeItems) {
        return  new SingleTimeLineDecoration.Builder(getContext(), timeItems)
                .setTitle(Color.parseColor("#ffffff"), 20)
                .setTitleStyle(SingleTimeLineDecoration.FLAG_TITLE_TYPE_TOP, 40)
                .setLine(SingleTimeLineDecoration.FLAG_LINE_DIVIDE, 50, Color.parseColor("#8d9ca9"))
                .setDot(SingleTimeLineDecoration.FLAG_DOT_DRAW)
                .setSameTitleHide()
                .build(StepSTL.class);
    }
}
