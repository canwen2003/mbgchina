package com.mbg.mbgsupport.fragment.timeline.DoubleTimeLine;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.adapter.DateInfoAdapter;
import com.mbg.mbgsupport.itemDecoration.timeline.doubleTimeLine.DateInfoTimeLineDecoration;
import com.mbg.mbgsupport.model.DateInfo;
import com.mbg.module.common.util.UiUtils;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.itemDecoration.timeline.TimeLine;
import com.mbg.module.ui.view.itemDecoration.timeline.manager.DoubleSideLayoutManager;

import java.util.List;

public class DateInfoDTLFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private DateInfoAdapter mAdapter;

    public static void show(Context context) {
        TerminalActivity.show(context, DateInfoDTLFragment.class, null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.common_fragment;
    }

    @Override
    protected void initView() {

        List<DateInfo> timeItems = DateInfo.initDateInfo();

        mRecyclerView = findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new DoubleSideLayoutManager(DoubleSideLayoutManager.START_LEFT, UiUtils.dip2px(40)));
        mAdapter = new DateInfoAdapter(timeItems);
        mRecyclerView.setAdapter(mAdapter);

        TimeLine timeLine = provideTimeLine(timeItems);
        mRecyclerView.addItemDecoration(timeLine);
    }


    private TimeLine provideTimeLine(List<DateInfo> timeItems) {
        return new TimeLine.Builder(getContext(), timeItems)
                .setTitleStyle(TimeLine.FLAG_TITLE_POS_NONE, 0)
                .setLine(TimeLine.FLAG_LINE_BEGIN_TO_END, 60, Color.parseColor("#757575"), 2)
                .setDot(TimeLine.FLAG_DOT_DRAW)
                .build(DateInfoTimeLineDecoration.class);
    }
}
