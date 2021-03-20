package com.mbg.module.ui.kotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mbg.module.ui.kotlin.fragment.BaseFragment


class PhoneActivity : BaseFragmentActivity() {
    companion object{
        fun show(context: Context, baseFragment: Class<out BaseFragment?>, bundle: Bundle?) {
            val intent = Intent(context, PhoneActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra(ARG_FRAGMENT_CLASS_NAME, baseFragment.name)
            intent.putExtra(ARG_FRAGMENT_ARGS, bundle)
            context.startActivity(intent)
        }
    }
}