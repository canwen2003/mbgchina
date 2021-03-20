package com.mbg.module.ui.kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jaeger.library.StatusBarUtil
import com.mbg.module.common.util.LocaleUtils

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleUtils.setLocale(this)
    }

    fun setStatusBarColor(color: Int, statusBarAlpha: Int) {
        StatusBarUtil.setColor(this, color, statusBarAlpha)
    }
}