package com.mbg.module.ui.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.mbg.module.ui.adapter.holder.BaseViewHolder;
import com.mbg.module.ui.adapter.model.MultiBaseItem;

import java.util.List;

/**
 *
 *created by Gap
 * 创建默认的多Item的adapter
 *
 */
public abstract class BaseMultiItemAdapter<T extends MultiBaseItem> extends BaseMultiItemQuickAdapter<MultiBaseItem, BaseViewHolder> {
    public BaseMultiItemAdapter(List<MultiBaseItem> data) {
        super(data);
    }

    public void setTitle(@LayoutRes int layoutId){
        addItemType(MultiBaseItem.TYPE_TITLE,layoutId);
    }

    public void setCell(@LayoutRes int layoutId){
        addItemType(MultiBaseItem.TYPE_CELL,layoutId);
    }

    public void setLine(@LayoutRes int layoutId){
        addItemType(MultiBaseItem.TYPE_LINE,layoutId);
    }

    public void setList(@LayoutRes int layoutId){
        addItemType(MultiBaseItem.TYPE_LIST,layoutId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MultiBaseItem item) {
        switch (item.getItemType()){
            case MultiBaseItem.TYPE_TITLE:
                initTitle(helper,item);
                break;
            case MultiBaseItem.TYPE_CELL:
                initCell(helper,item);
                break;
            case MultiBaseItem.TYPE_LINE:
                initLine(helper,item);
                break;
            case MultiBaseItem.TYPE_LIST:
                initList(helper,item);
                break;
            default:
                initOther(helper,item);
                break;
        }
    }

    protected  void initTitle(BaseViewHolder helper, MultiBaseItem item){}

    protected  void initCell(BaseViewHolder helper, MultiBaseItem item){}

    protected  void initLine(BaseViewHolder helper, MultiBaseItem item){}

    protected  void initList(BaseViewHolder helper, MultiBaseItem item){}

    protected  void initOther(BaseViewHolder helper, MultiBaseItem item){}
}
