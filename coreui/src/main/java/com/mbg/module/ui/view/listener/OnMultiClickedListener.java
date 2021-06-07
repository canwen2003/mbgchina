package com.mbg.module.ui.view.listener;

import android.view.View;

/***
 * 各种点击控件的监听
 *  包括点击、长按、双击
 */
public interface OnMultiClickedListener {
    /***
     * 点击
     * @param view 事件View对象
     */
    void onClicked(View view);

    /***
     * 长按
     * @param view 事件View对象
     */
    void onLongClicked(View view);

    /***
     * 双击
     * @param view  事件View对象
     */
    void onDoubleClicked(View view);
}
