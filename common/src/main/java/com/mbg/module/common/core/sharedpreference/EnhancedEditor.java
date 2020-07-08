package com.mbg.module.common.core.sharedpreference;

import android.content.SharedPreferences;


import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by liaohailiang on 2018/10/25.
 */
public interface EnhancedEditor extends SharedPreferences.Editor {

    EnhancedEditor putSerializable(String key, @Nullable Serializable value);
}
