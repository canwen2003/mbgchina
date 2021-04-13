package com.mbg.mbgsupport.kotlin

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.kotlin.frament.DemoByKotlinFragment
import com.mbg.mbgsupport.kotlin.frament.DemoFragment
import com.mbg.module.common.util.LogUtils
import com.mbg.module.common.util.ToastUtils
import com.mbg.module.ui.activity.TerminalActivity
import com.mbg.module.ui.kotlin.activity.PhoneActivity
import kotlinx.android.synthetic.main.activity_kotlin_main.*

class KotlinMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d("zzy:onCreate")
        setContentView(R.layout.activity_kotlin_main)
        initView()
    }

    fun initView(){
        tv_test1.text="test1"
        tv_test1.setOnClickListener {
            LogUtils.d("test1 clicked")
            ToastUtils.show("test1 clicked")
            TerminalActivity.show(this, DemoFragment::class.java, null)
        }

        tv_test2.text="test2"
        tv_test2.setOnClickListener {
            LogUtils.d("test2 clicked")
            ToastUtils.show("test2 clicked")
            PhoneActivity.show(this, DemoByKotlinFragment::class.java, null)
        }

        tv_test3.text="test3"
        tv_test3.setOnClickListener{
            LogUtils.d("test3 clicked")
            ToastUtils.show("test3 clicked")
        }

        showInfo.text=""+intent.getStringExtra("dowork")
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
