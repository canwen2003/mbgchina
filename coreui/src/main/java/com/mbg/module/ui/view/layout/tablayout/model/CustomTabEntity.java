package com.mbg.module.ui.view.layout.tablayout.model;


import androidx.annotation.DrawableRes;

/**
 * 定义Tab layout对应的tab 的实体类
 */
public interface CustomTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}