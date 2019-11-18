package com.mbg.module.ui.view.layout.tablayout.listener;

public interface OnTabSelectListener {
    /**
     * 当Tab选择更改时回掉相关的接口
     * @param position 新的tab index
     */
    void onTabSelect(int position);

    /**
     * 当点击当前选择的Tab时的回掉
     * @param position 该tab重新选择的index
     */
    void onTabReselect(int position);
}