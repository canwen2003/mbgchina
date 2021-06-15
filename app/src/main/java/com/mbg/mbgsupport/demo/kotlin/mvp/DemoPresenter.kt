package com.mbg.mbgsupport.demo.kotlin.mvp

import com.mbg.module.ui.mvp.kotlin.MvpPresenter

class DemoPresenter :MvpPresenter<AlphaTranFragment>() {

    override fun requestData(requestCode: Int, vararg params: Any?) {
        super.requestData(requestCode, *params)
    }
}