/*
package com.mbg.mbgsupport.fragment.timeline.dtl;


import android.graphics.Color;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mbg.mbgsupport.R;
import com.mbg.module.ui.fragment.BaseFragment;
import com.mbg.module.ui.view.itemDecoration.timeline.TimeLine;


*/
/**
 * 一周计划的Fragment
 * 两侧分布的时间轴 样式一
 *//*

public class WeekPlanDTLFragment extends BaseFragment {

    RecyclerView mRecyclerView;

    private RecyclerAdapter<TimeItem> mAdapter;

    @Override
    protected int onRequestLayout() {
        return R.layout.common_fragment;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new DoubleSideLayoutManager(DoubleSideLayoutManager.START_LEFT, UIUtils.dip2px(40)));
        mRecyclerView.setAdapter(mAdapter = new RecyclerAdapter<TimeItem>() {
            @Override
            public ViewHolder<TimeItem> onCreateViewHolder(View root, int viewType) {
                return new WeekPlanViewHolder(root);
            }

            @Override
            public int getItemLayout(TimeItem s, int position) {
                return R.layout.two_side_left_recycle_item;
            }
        });

        List<TimeItem> timeItems = TimeItem.initTimeInfo();
        mAdapter.addAllData(timeItems);

        TimeLine timeLine = provideTimeLine(timeItems);
        mRecyclerView.addItemDecoration(timeLine);
    }


    private TimeLine provideTimeLine(List<TimeItem> timeItems) {
        return new TimeLine.Builder(getContext(), timeItems)
                .setTitle(Color.parseColor("#8d9ca9"), 14)
                .setTitleStyle(TimeLine.FLAG_TITLE_TYPE_LEFT, 0)
                .setLine(TimeLine.FLAG_LINE_BEGIN_TO_END, 60, Color.parseColor("#757575"),3)
                .setDot(TimeLine.FLAG_DOT_RES)
                .build(WeekPlanDTL.class);
    }

    class WeekPlanViewHolder extends RecyclerAdapter.ViewHolder<TimeItem> {


        TextView mNameTv;


        TextView mDetailTv;


        TextView mGoBtn;

        TextView mWriteBtn;

        WeekPlanViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(TimeItem timeItem) {
            mNameTv.setText(timeItem.getName());
            mDetailTv.setText(timeItem.getDetail());

            setColor(timeItem.getColor());
        }

        private void setColor(int color){
            mGoBtn.setBackgroundColor(color);
            mWriteBtn.setBackgroundColor(color);
        }
    }


}
*/
