package com.mbg.mbgsupport.fragment.timeline.DoubleTimeLine;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.adapter.WeekPlanDTLAdapter;
import com.mbg.mbgsupport.adapter.WeekPlanSTLAdapter;
import com.mbg.mbgsupport.itemDecoration.timeline.dtl.WeekPlanDTL;
import com.mbg.mbgsupport.model.TimeItem;
import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.itemDecoration.timeline.TimeLine;
import com.mbg.module.ui.view.itemDecoration.timeline.manager.DoubleSideLayoutManager;

import java.util.List;

public class WeekPlanDTLFragment extends BaseFragment {


    private RecyclerView mRecyclerView;
    private WeekPlanDTLAdapter mAdapter;
    public static void show(Context context){
        TerminalActivity.show(context, WeekPlanDTLFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.common_fragment;
    }

    @Override
    protected void initView() {

        List<TimeItem> timeItems =TimeItem.initTimeInfo();

        mRecyclerView=findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new DoubleSideLayoutManager(DoubleSideLayoutManager.START_LEFT, UiUtils.dip2px(40)));
        mAdapter = new WeekPlanDTLAdapter(timeItems);
        mRecyclerView.setAdapter(mAdapter);

        TimeLine timeLine = provideTimeLine(timeItems);
        mRecyclerView.addItemDecoration(timeLine);
    }


    private TimeLine provideTimeLine(List<TimeItem> timeItems) {
        return  new TimeLine.Builder(getContext(), timeItems)
                .setTitle(Color.parseColor("#8d9ca9"), 14)
                .setTitleStyle(TimeLine.FLAG_TITLE_TYPE_LEFT, 0)
                .setLine(TimeLine.FLAG_LINE_BEGIN_TO_END, 60, Color.parseColor("#757575"),3)
                .setDot(TimeLine.FLAG_DOT_RES)
                .build(WeekPlanDTL.class);
    }

}

