package com.mbg.mbgsupport.fragment

import android.content.Context
import android.os.Bundle
import com.mbg.mbgsupport.databinding.FragmentProgressDemoBinding
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoPresenter
import com.mbg.module.ui.kotlin.activity.PhoneActivity
import com.mbg.module.ui.mvp.kotlin.MvpFragment
import com.opensource.svgaplayer.utils.log.SVGALogger.setLogEnabled

class ProgressBarFragment : MvpFragment<DemoPresenter,FragmentProgressDemoBinding>() {

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        setLogEnabled(true)
        initView()
    }

    override fun initView() {
        mViewBinding?.run {
            liveProgressView.initView(this@ProgressBarFragment)

            liveProgressView.setProgress(0,0)
        }

    }

    override fun onDestroyView() {
        mViewBinding?.run {
            liveProgressView.onReleaseView()

        }
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun show(context: Context) {
            PhoneActivity.show(context, ProgressBarFragment::class.java, null)
        }
    }
}