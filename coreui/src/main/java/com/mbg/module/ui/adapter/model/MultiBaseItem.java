package com.mbg.module.ui.adapter.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * created by Gap
 * 创建默认的多item model
 *
 */
public class MultiBaseItem implements MultiItemEntity {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_CELL = 2;
    public static final int TYPE_LINE = 3;
    public static final int TYPE_LIST = 4;


    private int itemType;
    private int spanSize;


    public MultiBaseItem(int itemType, int spanSize){
        this.itemType=itemType;
        this.spanSize=spanSize;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @SuppressWarnings("unused")
    public int getSpanSize() {
        return spanSize;
    }

    @SuppressWarnings("unused")
    public void setTypeSpanSize(int itemType,int spanSize){
        this.itemType=itemType;
        this.spanSize=spanSize;
    }
}
