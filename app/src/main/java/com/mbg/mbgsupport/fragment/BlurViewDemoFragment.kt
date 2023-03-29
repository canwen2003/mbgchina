package com.mbg.mbgsupport.fragment

import android.content.Context
import android.os.Bundle
import com.mbg.mbgsupport.databinding.FragmentBlurViewDemoBinding
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoPresenter
import com.mbg.module.ui.kotlin.activity.PhoneActivity
import com.mbg.module.ui.mvp.kotlin.MvpFragment

class BlurViewDemoFragment : MvpFragment<DemoPresenter,FragmentBlurViewDemoBinding>() {

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
    }

    override fun initView() {

    }

    companion object {
        @JvmStatic
        fun show(context: Context) {
            PhoneActivity.show(context, BlurViewDemoFragment::class.java, null)
        }
    }
}