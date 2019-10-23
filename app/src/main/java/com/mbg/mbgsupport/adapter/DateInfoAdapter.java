package com.mbg.mbgsupport.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.model.DateInfo;
import com.mbg.module.ui.adapter.holder.BaseViewHolder;

import java.util.List;

public class DateInfoAdapter extends BaseQuickAdapter<DateInfo,BaseViewHolder> {

    public DateInfoAdapter(@Nullable List<DateInfo> data) {
        super(R.layout.date_info_recycle_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, DateInfo item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_detail,item.getDetail());
    }
}
