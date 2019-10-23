package com.mbg.mbgsupport.fragment.timeline.SingleTimeLine;


import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.adapter.WeekPlanSTLAdapter;
import com.mbg.mbgsupport.itemDecoration.timeline.singleTimeLine.WeekPlanTimeLineDecoration;
import com.mbg.mbgsupport.model.TimeItem;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.itemDecoration.timeline.TimeLine;

import java.util.List;


/**
 * 一周计划的Fragment
 * 两侧分布的时间轴 样式一
 */
public class WeekPlanSTLFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private WeekPlanSTLAdapter mAdapter;
    public static void show(Context context){
        TerminalActivity.show(context, WeekPlanSTLFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
         return R.layout.common_fragment;
    }

    @Override
    protected void initView() {

        List<TimeItem> timeItems =TimeItem.initTimeInfo();

        mRecyclerView=findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WeekPlanSTLAdapter(timeItems);
        mRecyclerView.setAdapter(mAdapter);

        TimeLine timeLine = provideTimeLine(timeItems);
        mRecyclerView.addItemDecoration(timeLine);
    }


    private TimeLine provideTimeLine(List<TimeItem> timeItems) {
        return new TimeLine.Builder(getContext(), timeItems)
                .setTitle(Color.parseColor("#8d9ca9"), 12)
                .setTitleStyle(TimeLine.FLAG_TITLE_DRAW_BG, 50)
                .setLine(TimeLine.FLAG_LINE_BEGIN_TO_END, 40, Color.parseColor("#757575"),2)
                .setDot(TimeLine.FLAG_DOT_RES)
                .build(WeekPlanTimeLineDecoration.class);
    }

}
