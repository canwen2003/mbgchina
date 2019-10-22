package com.mbg.mbgsupport.adapter;


import com.mbg.module.ui.adapter.BaseMultiItemAdapter;
import com.mbg.module.ui.adapter.holder.BaseViewHolder;
import com.mbg.module.ui.adapter.model.MultiBaseItem;

import java.util.List;

public class MyMultiAdapter extends BaseMultiItemAdapter {

    public MyMultiAdapter(List<MultiBaseItem> data) {
        super(data);
    }

    @Override
    protected void initTitle(BaseViewHolder helper, MultiBaseItem item) {

    }

    @Override
    protected void initCell(BaseViewHolder helper, MultiBaseItem item) {

    }

    @Override
    protected void initLine(BaseViewHolder helper, MultiBaseItem item) {
    }

    @Override
    protected void initList(BaseViewHolder helper, MultiBaseItem item) {

    }

    @Override
    protected void initOther(BaseViewHolder helper, MultiBaseItem item) {

    }
}
