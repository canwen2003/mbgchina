package com.mbg.mbgsupport.demo.kotlin.mvp

import android.os.Bundle
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.FragmentDemoViewBindingBinding
import com.mbg.module.ui.mvp.kotlin.MvpFragment
import kotlinx.android.synthetic.main.fragment_anim.*
import kotlinx.android.synthetic.main.fragment_textbanner.*
import kotlinx.android.synthetic.main.view_merge_demo.*


class DemoFragment:MvpFragment<DemoPresenter,FragmentDemoViewBindingBinding>() {

    override fun onRequestLayout(): Int {
        return R.layout.fragment_anim
    }

    override fun initView() {

    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        mViewBinding?.apply {
            btn_pop_out.text="onInitView"
            tv_mr_view1.text="Merge child";
        }
    }

    override fun onPresenterResult(resultCode: Int, vararg params: Any?) {

    }

    override fun onLoadMoreView(isHasMore: Boolean) {

    }

    override fun onLoadFinish() {

    }
}