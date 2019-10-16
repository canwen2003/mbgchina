package com.mbg.module.ui.view.viewPager.common;

/**
 *
 * created by Gap
 * 定义状态标识
 */
public interface Mask {
    /**
     * 静止状态
     */
    int IDLE = 0x000001;

    /**
     * 正在向下一页拖动
     */
    int NEXT = 0x000010;

    /**
     * 正在向上一页拖动
     */
    int PREV = 0x000100;

    /**
     * 拖动
     */
    int SLIDE = 0x001000;

    /**
     * FLING
     */
    int FLING = 0x010000;

    /**
     * 拒绝
     */
    int REJECT = 0x100000;
}
