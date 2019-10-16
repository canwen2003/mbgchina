package com.mbg.module.ui.view.viewPager.common;

/**
 *
 * created by Gap
 * 定义状态类型
 */
public enum State {
    /**
     * 静止状态
     */
    IDLE(Mask.IDLE),
    /**
     * 正在向下一页拖动
     */
    SLIDE_NEXT(Mask.SLIDE | Mask.NEXT),
    /**
     * 正在向上一页拖动
     */
    SLIDE_PREV(Mask.SLIDE | Mask.PREV),
    /**
     * 无法拖动到下一页
     */
    SLIDE_REJECT_NEXT(Mask.REJECT | Mask.SLIDE | Mask.NEXT),
    /**
     * 无法拖动到上一页
     */
    SLIDE_REJECT_PREV(Mask.REJECT | Mask.SLIDE | Mask.PREV),
    /**
     * 手指离开，惯性滑行到下一页
     */
    FLING_NEXT(Mask.FLING | Mask.NEXT),
    /**
     * 手指离开，惯性滑行到上一页
     */
    FLING_PREV(Mask.FLING | Mask.PREV);

    private int flag;

    State(int flag) {
        this.flag = flag;
    }

    public boolean satisfy(int mask) {
        return ((flag & mask) == mask);
    }

    public static State of(int... mask) {
        int flag = 0;
        for (int tag : mask) {
            flag = flag | tag;
        }

        State state = IDLE;
        for (State item : State.values()) {
            if (item.flag == flag) {
                state = item;
                break;
            }
        }

        return state;
    }
}
