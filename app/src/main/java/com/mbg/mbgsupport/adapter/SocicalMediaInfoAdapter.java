package com.mbg.mbgsupport.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mbg.mbgsupport.R;
import com.mbg.mbgsupport.model.SocialMediaInfo;
import com.mbg.module.ui.adapter.holder.BaseViewHolder;

import java.util.List;

public class SocicalMediaInfoAdapter extends BaseQuickAdapter<SocialMediaInfo,BaseViewHolder> {

    public SocicalMediaInfoAdapter(@Nullable List<SocialMediaInfo> data) {
        super(R.layout.two_side_left_recycle_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SocialMediaInfo item) {
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_detail,item.getDetail());
        helper.setBackgroundColor(R.id.btn_go,item.getColor());
        helper.setBackgroundColor(R.id.btn_write,item.getColor());
    }
}
