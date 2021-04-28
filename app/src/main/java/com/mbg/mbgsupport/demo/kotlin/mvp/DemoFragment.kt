package com.mbg.mbgsupport.demo.kotlin.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.FragmentAnimBinding

import com.mbg.module.ui.mvp.kotlin.MvpFragment



class DemoFragment:MvpFragment<DemoPresenter,FragmentAnimBinding>() {

    override fun onRequestLayout(): Int {
        return R.layout.fragment_anim
    }

    override fun initView() {

    }

    /*override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAnimBinding {
        return FragmentAnimBinding.inflate(inflater,container,false)
    }*/

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        mViewBinding?.apply {
            btnPopOut.text="onInitView"
        }
    }

    override fun onPresenterResult(resultCode: Int, vararg params: Any?) {

    }

    override fun onLoadMoreView(isHasMore: Boolean) {

    }

    override fun onLoadFinish() {

    }
}