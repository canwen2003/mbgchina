package com.mbg.module.ui.view.viewPager.common;

public interface Mask {
    int IDLE = 0x000001;
    int NEXT = 0x000010;
    int PREV = 0x000100;
    int SLIDE = 0x001000;
    int FLING = 0x010000;
    int REJECT = 0x100000;
}
