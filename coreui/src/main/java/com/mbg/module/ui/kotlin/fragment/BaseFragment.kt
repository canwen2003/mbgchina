package com.mbg.module.ui.kotlin.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.jaeger.library.StatusBarUtil
import com.mbg.module.ui.kotlin.activity.BaseFragmentActivity

abstract class BaseFragment : Fragment() {
    var mRootView: View? = null
    private var mContainer = 0

    @JvmOverloads
    fun showContent(fragmentClass: Class<out BaseFragment?>?, bundle: Bundle? = null) {
        val activity = activity as BaseFragmentActivity?
        activity?.showContent(fragmentClass!!, bundle)
    }

    fun setStatusBarColor(color: Int, statusBarAlpha: Int) {
        StatusBarUtil.setColor(activity, color, statusBarAlpha)
    }


    //返回Layout Id
    @LayoutRes
    abstract fun onRequestLayout(): Int

    //返回View
    open fun onCreateView(savedInstanceState: Bundle?): View? {
        return mRootView
    }

    abstract fun initView()
    fun <T : View?> findViewById(@IdRes id: Int): T {
        return mRootView!!.findViewById(id)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tryGetContainerId()
        try {
            if (view.context is Activity) {
                (view.context as Activity).window.decorView.requestLayout()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = super.onCreateView(inflater, container, savedInstanceState)
        val id = onRequestLayout()
        if (id > 0 && mRootView == null) {
            mRootView = inflater.inflate(id, container, false)
            initView()
        }
        if (mRootView == null) {
            mRootView = onCreateView(savedInstanceState)
            initView()
        } else if (mRootView!!.parent != null) {
            (mRootView!!.parent as ViewGroup).removeView(mRootView)
        }
        if (interceptTouchEvents()) {
            mRootView?.setOnTouchListener{ view, motionEvent -> true
            }
        }
        return mRootView
    }

    private fun tryGetContainerId() {
        if (mRootView != null) {
            val parent = mRootView!!.parent as View
            mContainer = parent.id
        }
    }

    open fun interceptTouchEvents(): Boolean {
        return false
    }

    val container: Int
        get() {
            if (mContainer == 0) {
                tryGetContainerId()
            }
            return mContainer
        }

    fun onBackPressed(): Boolean {
        return false
    }

    fun finish() {
        val activity = activity as BaseFragmentActivity?
        activity?.doBack(this)
    }
}