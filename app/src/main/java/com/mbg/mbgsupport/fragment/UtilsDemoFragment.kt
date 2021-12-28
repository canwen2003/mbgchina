package com.mbg.mbgsupport.fragment

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProviders
import com.blued.android.module.serviceloader.Router
import com.mbg.mbgsupport.MainActivity
import com.mbg.mbgsupport.R
import com.mbg.mbgsupport.databinding.FragmentUtilsDemoBinding
import com.mbg.mbgsupport.demo.kotlin.mvp.DemoPresenter
import com.mbg.mbgsupport.router.service.IBaseService
import com.mbg.mbgsupport.router.service.ServiceKey
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel
import com.mbg.mbgsupport.viewmodel.LoadingStateViewModel.LoadingState
import com.mbg.module.common.core.LifecycleHandler
import com.mbg.module.common.core.WeakHandler
import com.mbg.module.common.core.manager.CoroutineManager.Companion.get
import com.mbg.module.common.core.net.manager.HttpManager
import com.mbg.module.common.core.net.tool.HttpUtils
import com.mbg.module.common.core.net.wrapper.response.DefaultHttpResponse
import com.mbg.module.common.core.sharedpreference.FastSharedPreferences
import com.mbg.module.common.util.*
import com.mbg.module.common.util.SpannableUtils.SpannableFilter
import com.mbg.module.common.util.consts.PermissionConsts
import com.mbg.module.ui.kotlin.activity.PhoneActivity
import com.mbg.module.ui.mvp.kotlin.MvpFragment
import com.mbg.module.ui.view.inflate.AsyncLayoutInflatePlus
import java.util.*

class UtilsDemoFragment : MvpFragment<DemoPresenter,FragmentUtilsDemoBinding>() {
    private val weakHandler = WeakHandler(this)
    private val lifecycleHandler = LifecycleHandler(this)
    private var mLoadingStateViewModel:LoadingStateViewModel?=null

    override fun onPresenterResult(resultCode: Int, vararg params: Any?) {

    }

    override fun onLoadMoreView(isHasMore: Boolean) {

    }

    override fun onLoadFinish() {

    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        initView()
    }

    override fun initView() {
        PermissionUtils.checkStoragePermissson(activity,
            object : PermissionUtils.PermissionCallbacks {
                override fun onPermissionsGranted(requestCodes: Int, perms: List<String>) {}
                override fun onPermissionsDenied(requestCodes: Int, perms: List<String>) {}
            })
        if (!PermissionHelper.isPermissionGranted(PermissionConsts.CAMERA)) {
            PermissionHelper.permission(PermissionConsts.CAMERA)
                .callback(object : PermissionHelper.SimpleCallback {
                    override fun onGranted() {
                        LogUtils.d("onGranted")
                    }

                    override fun onDenied() {
                        LogUtils.d("onGranted")
                    }
                }).request()
        }
        if (!PermissionHelper.isPermissionGranted(PermissionConsts.LOCATION)) {
            PermissionHelper.permission(PermissionConsts.LOCATION)
                .callback(object : PermissionHelper.SimpleCallback {
                    override fun onGranted() {
                        LogUtils.d("onGranted")
                    }

                    override fun onDenied() {
                        LogUtils.d("onGranted")
                    }
                }).request()
        }
        mViewBinding?.run {

            val inputRoot = tvTestInput
            var spannableFilter = SpannableFilter()
            val spannableFilters: MutableList<SpannableFilter> = ArrayList()
            spannableFilter.keywords = "测试"
            spannableFilter.keywordsColor = Color.RED
            spannableFilter.isUnderLine = false
            spannableFilter.onClickListener = View.OnClickListener { ToastUtils.debugShow("测试") }
            spannableFilters.add(spannableFilter)
            spannableFilter = SpannableFilter()
            spannableFilter.keywords = "键盘"
            spannableFilter.keywordsColor = Color.GRAY
            spannableFilter.isUnderLine = true
            spannableFilter.onClickListener = View.OnClickListener { ToastUtils.debugShow("键盘") }
            spannableFilters.add(spannableFilter)
            SpannableUtils.setViewSpannable(activity, inputRoot, spannableFilters)

            KeyboardUtils.registerSoftInputChangedListener(requireActivity()) { height ->
                ToastUtils.debugShow("onSoftInputChanged:height=$height")
                if (height > 0) {
                    inputRoot.translationY = -height.toFloat()
                }
            }

            mLoadingStateViewModel = ViewModelProviders.of(requireActivity()).get("LoadingState", LoadingStateViewModel::class.java)
            mLoadingStateViewModel?.loadingState?.observe(requireActivity(), { loadingState ->
                if (loadingState != null) {
                    when (loadingState) {
                        LoadingState.START -> {
                        }
                        LoadingState.FINISH -> onDataLoadingFinish()
                    }
                }
            })
            ThreadUtils.postInUIThreadDelayed({ mLoadingStateViewModel!!.setLoadingSate(LoadingState.FINISH) }, 8000)
            AsyncLayoutInflatePlus(requireActivity()).inflate(R.layout.view_async_load, null) { view, resId, parent -> root.addView(view) }

            val mGlobalView = View.inflate(activity, R.layout.view_global_demo, null)

            btnTest1.setOnClickListener {
                onTest1()
                FastSharedPreferences.get("FSP_DATA_USER").edit().putBoolean("keyBool", true)
                FastSharedPreferences.get("FSP_DATA_USER").edit()
                    .putString("keyString", "btn_test1").apply()
            }
            btnTest2.setOnClickListener {
                FastSharedPreferences.get("FSP_DATA_USER").edit().putBoolean("keyBool", false)
                FastSharedPreferences.get("FSP_DATA_USER").edit()
                    .putString("keyString", "btn_test2").apply()
                onTest2()
            }

            btnTest3.setOnClickListener {
                ToastUtils.show("keyBool=" + FastSharedPreferences.get("FSP_DATA_USER")
                    .getBoolean("keyBool", false))
            }

            btnTest4.setOnClickListener {
                ToastUtils.show("keyString=" + FastSharedPreferences.get("FSP_DATA_USER")
                    .getString("keyString", ""))
            }

            btnTest5.setOnClickListener {

                onTest5()
            }

            btnTest6.setOnClickListener {

            }

            btnTest7.setOnClickListener {
                onTest7()
            }

            btnTest8.setOnClickListener {
                onTest8()
            }

            btnTest9.setOnClickListener {
                onTest9()
            }

            btnShowGlobal.setOnClickListener {
                UiUtils.showGlobal(requireActivity(), mGlobalView, R.id.view_global, 500) {
                    true //返回true,表示不重新加入
                }
                ThreadUtils.postInUIThreadDelayed({
                    UiUtils.hideGlobal(requireActivity(),
                        R.id.view_global,
                        500)
                }, 2000)
            }

            btnHideGlobal.setOnClickListener {
                UiUtils.hideGlobal(requireActivity(), R.id.view_global, 500)
            }

            btnAr.setOnClickListener {
                onAR()
            }

            btnCoroutine.setOnClickListener {
                var i = 0
                while (i < 1000) {
                    get().start(Runnable {
                        try {
                            Thread.sleep(400)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    })
                    i++
                }
            }

            btnShowAnim.setOnClickListener {
                showAnimal()
            }

            btnHideAnim.setOnClickListener {
                hideAnimal()
            }

            btnWeakHandle.setOnClickListener {
                var i = 0
                while (i < 1000) {
                    weakHandler.postDelayed({
                        btnWeakHandle.text = "我3秒前被点击了"
                        btnWeakHandle.setTextColor(Color.GREEN)
                    }, 10000)
                    i++
                }
            }

            btnLifeHandle.setOnClickListener {
                lifecycleHandler.postDelayed({
                    btnLifeHandle.text = "我3秒前被点击了"
                    btnLifeHandle.setTextColor(Color.RED)
                }, 3000)
            }

            btnAnimal.setOnClickListener {

            }


        }






    }

    private fun onDataLoadingFinish() {
        LogUtils.d("LoadingStateViewModel:onDataLoadingFinish")
    }


    private fun showAnimal() {
        mViewBinding?.run {
            val animator = ValueAnimator.ofInt(-btnAnimal.height, 0)
            animator.duration = 300
            animator.interpolator = OvershootInterpolator()
            val layoutParams = btnAnimal.layoutParams as LinearLayout.LayoutParams
            animator.addUpdateListener { valueAnimator ->
                layoutParams.topMargin = valueAnimator.animatedValue as Int
                btnAnimal.layoutParams = layoutParams
            }
            animator.start()
        }
    }

    private fun hideAnimal() {
        mViewBinding?.run {
            val animator = ValueAnimator.ofInt(0, -btnAnimal.height)
            animator.duration = 300
            animator.interpolator = OvershootInterpolator()
            val layoutParams = btnAnimal.layoutParams as LinearLayout.LayoutParams
            animator.addUpdateListener { valueAnimator ->
                layoutParams.topMargin = valueAnimator.animatedValue as Int
                btnAnimal.layoutParams = layoutParams
            }
            animator.start()
        }
    }

    private fun onTest1() {

        mLoadingStateViewModel!!.setLoadingSate(LoadingState.START)
    }

    private fun onTest2() {
        Thread {
            val service = Router.getService(IBaseService::class.java, ServiceKey.KEY_SERVICE)
            service.doInBackground()
        }.start()
        mLoadingStateViewModel!!.setLoadingSate(LoadingState.FINISH)
    }

    private fun onTest3() {
        val key = "Test"
        val str = FileCacheUtils.getContent(key)
        if (StringUtils.isEmpty(str)) {
            FileCacheUtils.saveContent(key, "Fisrt save,大中国。。")
            ToastUtils.show("Cache is empty!")
        } else {
            ToastUtils.show("Cache:$str")
        }
    }

    private fun onTest4() {
        val response: DefaultHttpResponse = object : DefaultHttpResponse() {
            override fun onUpdate(data: String) {
                super.onUpdate(data)
                LogUtils.e("onUpdate:$data")
            }

            override fun onUIStart() {
                super.onUIStart()
                LogUtils.e("onUIStart")
            }

            override fun onUICache(data: String) {
                super.onUICache(data)
                LogUtils.e("onUICache:$data")
            }

            override fun onUIError(error: Exception) {
                super.onUIError(error)
                LogUtils.e("onUIError:" + error.message)
            }

            override fun onUIFinish() {
                super.onUIFinish()
                LogUtils.e("onUIFinish")
            }
        }
        val url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=18701539645"
        HttpManager.get(url, response).addHeader(HttpUtils.buildBaseHeader(false)).execute()
    }

    private fun onTest5() {
        LocaleUtils.setLocale(activity,
            Locale(LocaleUtils.Language.ZH.code, LocaleUtils.CountryArea.China.code))
        AppUtils.rebootApplication(MainActivity::class.java)
    }

    private fun onTest6() {
        LocaleUtils.setLocale(activity,
            Locale(LocaleUtils.Language.EN.code, LocaleUtils.CountryArea.America.code))
        AppUtils.rebootApplication(MainActivity::class.java)
    }

    private fun onAR() {
        LocaleUtils.setLocale(activity,
            Locale(LocaleUtils.Language.AR.code, LocaleUtils.CountryArea.Arab.code))
        AppUtils.rebootApplication(MainActivity::class.java)
    }

    private fun onTest7() {
        KeyboardUtils.showSoftInput(requireActivity())
    }

    private fun onTest8() {
        KeyboardUtils.hideSoftInput(requireActivity())
    }

    private fun onTest9() { //showContent(ImageLoaderFragment.class);
        val map = Thread.getAllStackTraces()
        for ((mapKey) in map) {
            println("Thread:" + mapKey.name + " ,isAlive:" + mapKey.isAlive + " ,ThreadID:" + mapKey.id)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()

        get().clear()
    }

    companion object {
        @JvmStatic
        fun show(context: Context) {
            PhoneActivity.show(context, UtilsDemoFragment::class.java, null)
        }
    }
}