package com.mbg.mbgsupport.fragment

import android.content.Context
import android.os.Bundle
import com.mbg.mbgsupport.databinding.FragmentBlurViewBinding
import com.mbg.mbgsupport.databinding.FragmentSvgaDemoBinding
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoPresenter
import com.mbg.module.common.core.manager.CoroutineManager.Companion.get
import com.mbg.module.ui.kotlin.activity.PhoneActivity
import com.mbg.module.ui.mvp.kotlin.MvpFragment

class SvgaDemoFragment : MvpFragment<DemoPresenter,FragmentSvgaDemoBinding>() {

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        initView()
    }

    override fun initView() {


    }


    override fun onDestroyView() {
        super.onDestroyView()

        get().clear()
    }

    companion object {
        @JvmStatic
        fun show(context: Context) {
            PhoneActivity.show(context, SvgaDemoFragment::class.java, null)
        }
    }
}