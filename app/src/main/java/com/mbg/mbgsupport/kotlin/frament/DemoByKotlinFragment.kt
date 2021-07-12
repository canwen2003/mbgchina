package com.mbg.mbgsupport.kotlin.frament

import android.widget.TextView
import com.mbg.mbgsupport.R
import com.mbg.module.ui.kotlin.fragment.BaseFragment


class DemoByKotlinFragment : BaseFragment(){
    private var tvS: TextView?=null

    override fun onRequestLayout(): Int {
       return R.layout.fragment_anim
    }

    override fun initView() {
        tvS=findViewById(R.id.btn_pop_out)

    }
}