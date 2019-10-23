package com.mbg.mbgsupport.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.model.NoteInfo;
import com.mbg.module.ui.adapter.holder.BaseViewHolder;

import java.util.List;

public class NoteInfoAdapter extends BaseQuickAdapter<NoteInfo,BaseViewHolder> {

    public NoteInfoAdapter(@Nullable List<NoteInfo> data) {
        super(R.layout.two_side_left_recycle_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NoteInfo item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_detail,item.getDetail());
        helper.setBackgroundColor(R.id.btn_go,item.getColor());
        helper.setBackgroundColor(R.id.btn_write,item.getColor());
    }
}
