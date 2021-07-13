package com.mbg.mbgsupport.kotlin

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mbg.mbgsupport.R
import com.mbg.module.common.core.manager.CoroutineManager
import com.mbg.module.common.util.LogUtils
import kotlinx.coroutines.delay

class KotlinMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d("zzy:onCreate")
        setContentView(R.layout.activity_kotlin_main)
        initView()


    }

    fun initView(){
        findViewById<TextView>(R.id.tv_test1).setOnClickListener {
            CoroutineManager.get().start {
                CoroutineManager.get().start {
                    LogUtils.d("zzy=In")
                }

                delay(200)
                LogUtils.d("zzy=out")

            }
        }
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
