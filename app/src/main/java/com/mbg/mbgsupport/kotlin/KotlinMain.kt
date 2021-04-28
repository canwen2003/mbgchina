package com.mbg.mbgsupport.kotlin

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mbg.mbgsupport.R
import com.mbg.module.common.util.LogUtils

class KotlinMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d("zzy:onCreate")
        setContentView(R.layout.activity_kotlin_main)
        initView()
    }

    fun initView(){

    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        LogUtils.d("zzy:onCreateView")
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d("zzy:onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("zzy:onResume")
    }

}
