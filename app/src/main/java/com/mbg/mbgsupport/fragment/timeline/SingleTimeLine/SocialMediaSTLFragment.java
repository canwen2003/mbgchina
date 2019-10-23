package com.mbg.mbgsupport.fragment.timeline.SingleTimeLine;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.adapter.SocicalMediaInfoAdapter;
import com.mbg.mbgsupport.itemDecoration.timeline.singleTimeLine.SocialMediaSTL;
import com.mbg.mbgsupport.model.SocialMediaInfo;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.itemDecoration.timeline.TimeLine;

import java.util.List;
public class SocialMediaSTLFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private SocicalMediaInfoAdapter mAdapter;
    public static void show(Context context){
        TerminalActivity.show(context, SocialMediaSTLFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.common_fragment;
    }

    @Override
    protected void initView() {

        List<SocialMediaInfo> timeItems = SocialMediaInfo.initSocialMediaInfo();
        mRecyclerView=findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SocicalMediaInfoAdapter(timeItems);
        mRecyclerView.setAdapter(mAdapter);

        TimeLine timeLine = provideTimeLine(timeItems);
        mRecyclerView.addItemDecoration(timeLine);
    }


    private TimeLine provideTimeLine(List<SocialMediaInfo> timeItems) {
        return  new TimeLine.Builder(getContext(), timeItems)
                .setTitleStyle(TimeLine.FLAG_TITLE_TYPE_TOP, 52)
                .setTitle(Color.parseColor("#000000"), 22)
                .setLine(TimeLine.FLAG_LINE_DIVIDE, 80, Color.parseColor("#757575"), 1)
                .setDot(TimeLine.FLAG_DOT_DRAW)
                .setSameTitleHide()
                .build(SocialMediaSTL.class);
    }
}
