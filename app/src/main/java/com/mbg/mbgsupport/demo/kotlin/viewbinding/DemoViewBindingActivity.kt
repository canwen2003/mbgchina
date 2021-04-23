package com.mbg.mbgsupport.demo.kotlin.viewbinding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.ActivityDemoViewBindingBinding
import com.mbg.module.ui.viewbinding.viewBinding


class DemoViewBindingActivity : AppCompatActivity(R.layout.activity_demo_view_binding) {
    private val viewBindingActivity by viewBinding(ActivityDemoViewBindingBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewBindingActivity){
            profileFragmentContainer
        }
    }
}