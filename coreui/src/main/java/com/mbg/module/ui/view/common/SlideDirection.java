package com.mbg.module.ui.view.common;

public enum SlideDirection {
    /**
     * 滑到下一个
     */
    Next {
        @Override
        int moveTo(int index) {
            return index + 1;
        }
    },
    /**
     * 滑到上一个
     */
    Prev {
        @Override
        int moveTo(int index) {
            return index - 1;
        }
    },
    /**
     * 回到原点
     */
    Origin {
        @Override
        int moveTo(int index) {
            return index;
        }
    };

    /**
     * 计算index的变化
     */
    abstract int moveTo(int index);
}
