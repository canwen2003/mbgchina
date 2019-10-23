package com.mbg.mbgsupport.fragment.timeline.SingleTimeLine;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.adapter.NoteInfoAdapter;
import com.mbg.mbgsupport.itemDecoration.timeline.singleTimeLine.NoteInfoTimeLineDecoration;
import com.mbg.mbgsupport.model.NoteInfo;
import com.mbg.module.ui.activity.TerminalActivity;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.itemDecoration.timeline.SingleTimeLineDecoration;
import com.mbg.module.ui.view.itemDecoration.timeline.TimeLine;

import java.util.List;
public class NoteInfoSTLFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private NoteInfoAdapter mAdapter;
    public static void show(Context context){
        TerminalActivity.show(context, NoteInfoSTLFragment.class,null);
    }

    @Override
    protected int onRequestLayout() {
        return R.layout.common_fragment;
    }

    @Override
    protected void initView() {

        List<NoteInfo> timeItems =NoteInfo.initNoteInfo();

        mRecyclerView=findViewById(R.id.rv_content);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new NoteInfoAdapter(timeItems);
        mRecyclerView.setAdapter(mAdapter);

        TimeLine timeLine = provideTimeLine(timeItems);
        mRecyclerView.addItemDecoration(timeLine);
    }


    private TimeLine provideTimeLine(List<NoteInfo> timeItems) {
        return  new TimeLine.Builder(getContext(), timeItems)
                .setTitle(Color.parseColor("#ffffff"), 20)
                .setTitleStyle(SingleTimeLineDecoration.FLAG_TITLE_TYPE_LEFT, 80)
                .setLine(SingleTimeLineDecoration.FLAG_LINE_CONSISTENT, 0, Color.parseColor("#00000000"))
                .setDot(SingleTimeLineDecoration.FLAG_DOT_DRAW)
                .setSameTitleHide()
                .build(NoteInfoTimeLineDecoration.class);
    }

}
