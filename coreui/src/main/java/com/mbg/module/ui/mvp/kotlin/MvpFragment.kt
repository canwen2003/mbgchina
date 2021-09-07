package com.mbg.module.ui.mvp.kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.mbg.module.common.util.ClassUtils
import com.mbg.module.common.util.TypeUtils
import com.mbg.module.ui.kotlin.fragment.BaseFragment
import com.mbg.module.ui.mvp.kotlin.base.IntView
import com.mbg.module.ui.mvp.kotlin.holder.PresenterHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class MvpFragment< T : MvpPresenter<out IntView>, VB : ViewBinding> : BaseFragment(), IntView,CoroutineScope by MainScope() {
    private val logTag = "_MVP_" + this@MvpFragment.javaClass.simpleName
    var mIsVisibleToUser = false
    var mViewInitialized = false
    var mMvpRegistered = false
    var mPresenter: T? = null
    var mViewBinding:VB?=null

    override fun onRequestLayout(): Int {
        return 0
    }

    @CallSuper
    open fun onInitView(savedInstanceState: Bundle?) {

    }


    /***
     * 注意：如果layout中存在merge标签并且设置了id反射就会失败
     */
    protected open fun getBinding(): VB? {
        try {
            val superClass = javaClass.genericSuperclass
            val type = (superClass as ParameterizedType?)!!.actualTypeArguments[1]
            val clazz = ClassUtils.getRawType(type)
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            return TypeUtils.cast(method.invoke(null, layoutInflater))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    @CallSuper
    fun onReleaseView() {
        mIsVisibleToUser = false
        mRootView = null
        mViewInitialized = false
        mViewBinding=null
    }

    fun getPresenterId(): String {
        val presenterClass: Class<*>? = getPresenterClass()
        return if (presenterClass != null) {
            presenterClass.simpleName + "_" + System.currentTimeMillis()
        } else {
            javaClass.simpleName + "_" + System.currentTimeMillis()
        }
    }


    private fun onCreatePresenter(): T {
        val presenterId = getPresenterId()
        var mvpPresenter = PresenterHolder.get().getPresenter(presenterId)
        if (mvpPresenter == null) {
            val clazz: Class<*>? = getPresenterClass()
            if (clazz != null) {
                try {
                    mvpPresenter = clazz.newInstance() as T
                    mvpPresenter.setPresenterId(presenterId)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (mvpPresenter != null) {
                PresenterHolder.get().addPresenter(mvpPresenter, lifecycle)
            }
        }
        return mvpPresenter as T
    }

    open fun getPresenterClass(): Class<*>? {
        try {
            // 通过反射获取model的真实类型
            val superClass: Type = this.javaClass.genericSuperclass as Type
            if (superClass is ParameterizedType) {
                val pt = this.javaClass.genericSuperclass as ParameterizedType
                return pt.actualTypeArguments[0] as Class<*>
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
        return null
    }


    fun getPresenter(): T {
        return mPresenter as T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(logTag, " onCreate()")
        mPresenter = onCreatePresenter()
        mPresenter?.init(requireActivity())
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding=getViewBinding(inflater,container)?:getBinding()


        return mViewBinding?.root
    }

    open fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB?=null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(logTag, " onViewCreated()")
        if (!mViewInitialized) {
            onInitView(savedInstanceState)
            mViewInitialized = true
        }
        if (!mMvpRegistered) {
            mMvpRegistered = true
            mPresenter?.run {
                register(this@MvpFragment, arguments)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!onCacheView()) {
            // MVP解注册
            mPresenter?.run {
                unRegister(this@MvpFragment)
            }

            onReleaseView()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        mIsVisibleToUser = isVisibleToUser
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.v(logTag, " onDestroy()")
        cancel() // cancel is extension on CoroutineScope
        mMvpRegistered = false
        mPresenter=null
    }


    /**
     * 是否缓存View对象
     */
    fun onCacheView(): Boolean {
        return false
    }


    /**
     * finish属主Activity
     */
    override fun finish() {
        activity?.run {
            finish()
        }
    }

    /**
     * 对属主Activity设置Result
     * @param resultCode
     */
    fun setResult(resultCode: Int) {
        activity?.apply {
            setResult(resultCode)
        }
    }

    /**
     * 对属主Activity设置Result
     * @param resultCode
     * @param data
     */
    fun setResult(resultCode: Int, data: Intent?) {
        activity?.apply {
            setResult(resultCode, data)
        }
    }


    // 判断父fragment是否可见
    open fun isParentVisible(): Boolean {
        val fragment = parentFragment
        return fragment !is MvpFragment<*,*> || (fragment as MvpFragment<T,VB>).mIsVisibleToUser
    }

}