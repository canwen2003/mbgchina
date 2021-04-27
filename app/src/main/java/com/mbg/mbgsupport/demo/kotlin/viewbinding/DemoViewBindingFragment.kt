package com.mbg.mbgsupport.demo.kotlin.viewbinding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.FragmentDemoViewBindingBinding
import com.mbg.module.common.util.ToastUtils
import com.mbg.module.ui.viewbinding.CreateMethod
import com.mbg.module.ui.viewbinding.viewBinding


class DemoViewBindingFragment : Fragment(R.layout.fragment_demo_view_binding) {
    private val viewBindingRef : FragmentDemoViewBindingBinding by viewBinding(CreateMethod.INFLATE)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBindingRef){
            tvTest1.text="初始化 Test1 控件"
            tvTest1.setOnClickListener {
                ToastUtils.debugShow("别点击我");
            }
            tvTest2.text="初始化 Text2 控件"
            tvTest2.setOnClickListener {
                ToastUtils.debugShow("来点击我");
            }
        }
    }
}