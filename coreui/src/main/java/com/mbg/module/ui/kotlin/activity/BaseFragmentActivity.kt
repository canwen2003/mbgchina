package com.mbg.module.ui.kotlin.activity


import android.os.Bundle
import com.mbg.module.ui.kotlin.fragment.BaseFragment
import java.util.*

abstract class BaseFragmentActivity : BaseActivity() {
    private val mFragments = ArrayDeque<BaseFragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        var fragmentClass: Class<out BaseFragment>? = null
        var bundle: Bundle? = null
        if (intent != null) {
            val fragmentName = intent.getStringExtra(ARG_FRAGMENT_CLASS_NAME)
            if (fragmentName != null) {
                try {
                    fragmentClass = classLoader.loadClass(fragmentName) as Class<out BaseFragment>
                } catch (e: Exception) {
                    e.printStackTrace()
                    finish()
                }
            }
            bundle = intent.getBundleExtra(ARG_FRAGMENT_ARGS)
        }
        if (fragmentClass != null) {
            showContent(fragmentClass, bundle)
        } else {
            finish()
        }
    }

    @JvmOverloads
    fun showContent(target: Class<out BaseFragment>, bundle: Bundle? = null) {
        try {
            val fragment = target.newInstance()
            if (bundle != null) {
                fragment.arguments = bundle
            }
            val fm = supportFragmentManager
            val fragmentTransaction = fm.beginTransaction()
            fragmentTransaction.add(android.R.id.content, fragment)
            mFragments.push(fragment)
            fragmentTransaction.addToBackStack("")
            fragmentTransaction.commit()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (!mFragments.isEmpty()) {
            val fragment = mFragments.first
            if (!fragment.onBackPressed()) {
                mFragments.removeFirst()
                super.onBackPressed()
                if (mFragments.isEmpty()) {
                    finish()
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    fun doBack(fragment: BaseFragment) {
        if (mFragments.contains(fragment)) {
            mFragments.remove(fragment)
            val fm = supportFragmentManager
            fm.popBackStack()
            if (mFragments.isEmpty()) {
                finish()
            }
        }
    }

    companion object {
         const val ARG_FRAGMENT_CLASS_NAME = "arg_fragment_class_name"
         const val ARG_FRAGMENT_ARGS = "arg_fragment_args"
    }
}