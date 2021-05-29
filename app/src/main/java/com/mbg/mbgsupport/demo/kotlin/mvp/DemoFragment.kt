package com.mbg.mbgsupport.demo.kotlin.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.FragmentAnimBinding
import com.mbg.mbgsupport.databinding.FragmentDemoViewBindingBinding
import com.mbg.module.ui.dialog.screenshot.ScreenShotDialog

import com.mbg.module.ui.mvp.kotlin.MvpFragment



class DemoFragment:MvpFragment<DemoPresenter, FragmentDemoViewBindingBinding>() {

    private var mOrig = StringBuilder()
    private var mResult = StringBuffer()
    override fun initView() {

    }

    /*override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentAnimBinding {
        return FragmentAnimBinding.inflate(inflater,container,false)
    }*/

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        mViewBinding?.apply {
            btn0.setOnClickListener{
                mOrig.append("0")
                tvOrig.text=mOrig.toString()
            }
            btn1.setOnClickListener{
                mOrig.append("1")
                tvOrig.text=mOrig.toString()
            }
            btn2.setOnClickListener{
                mOrig.append("2")
                tvOrig.text=mOrig.toString()
            }
            btn3.setOnClickListener{
                mOrig.append("3")
                tvOrig.text=mOrig.toString()
            }
            btn4.setOnClickListener{
                mOrig.append("4")
                tvOrig.text=mOrig.toString()
            }
            btn5.setOnClickListener{
                mOrig.append("5")
                tvOrig.text=mOrig.toString()
            }
            btn6.setOnClickListener{
                mOrig.append("6")
                tvOrig.text=mOrig.toString()
            }
            btn7.setOnClickListener{
                mOrig.append("7")
                tvOrig.text=mOrig.toString()
            }
            btn8.setOnClickListener{
                mOrig.append("8")
                tvOrig.text=mOrig.toString()
            }
            btn9.setOnClickListener{
                mOrig.append("9")
                tvOrig.text=mOrig.toString()
            }
            btnChange.setOnClickListener{
                if (!mOrig.isEmpty()) {
                    val origin = mOrig.toString().toInt();
                    val base = origin * 255 / 100
                    tvValue.text = base.toString(16)
                }
            }
            btnClear.setOnClickListener{
                mOrig.clear()
                tvOrig.text="0"
                tvValue.text="0"
            }
        }
    }

    override fun onPresenterResult(resultCode: Int, vararg params: Any?) {

    }


    override fun onLoadMoreView(isHasMore: Boolean) {

    }

    override fun onLoadFinish() {

    }
}