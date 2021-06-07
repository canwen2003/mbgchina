package com.mbg.mbgsupport.kotlin.frament

import android.widget.Button
import android.widget.TextView
import com.mbg.mbgsupport.R
import com.mbg.module.ui.fragment.BaseFragment


class DemoFragment : BaseFragment(){
    private var tvS: TextView?=null

    override fun onRequestLayout(): Int {
       return R.layout.fragment_anim
    }

    override fun initView() {
        tvS=findViewById(R.id.btn_pop_out)

    }
}