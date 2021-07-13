package com.mbg.mbgsupport.demo.kotlin.mvp

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.mbg.mbgsupport.databinding.FragmentDemoGestureBinding
import com.mbg.module.common.core.manager.CoroutineManager
import com.mbg.module.common.datastore.AppDataStore
import com.mbg.module.common.util.LogUtils
import com.mbg.module.common.util.ToastUtils
import com.mbg.module.ui.mvp.kotlin.MvpFragment
import com.mbg.module.ui.view.listener.OnScrollListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map


class DemoGestureFragment:MvpFragment<DemoPresenter, FragmentDemoGestureBinding>() {
    override fun initView() {

    }

    override fun onInitView(savedInstanceState: Bundle?) {
        super.onInitView(savedInstanceState)
        mViewBinding?.apply {
            gestureView.setGestureStatus(true)

            tvBgView.setOnClickListener {
                ToastUtils.show("你点击了我")
            }

            tvMovedView.setOnClickListener {
                ToastUtils.show("不要点击我")
            }
            gestureView.setOnScrollListener(object : OnScrollListener {
                var mIsUp:Boolean=false
                var mTranslationY:Float=0f

                override fun onHorizontalScroll(distance: Float) {

                }

                override fun onScrollStart(isVerticalScroll: Boolean) {
                    val EXAMPLE_COUNTER = intPreferencesKey("example_counter")

                    CoroutineManager.get().start {
                        AppDataStore.get().getMain().edit {
                                settings->
                            val currentCounterValue = settings[EXAMPLE_COUNTER] ?: 0
                            settings[EXAMPLE_COUNTER] = currentCounterValue + 1
                        }
                    }

                }

                override fun onVerticalScroll(distance: Float) {
                    tvMovedView.translationY = mTranslationY-distance*0.8f
                    LogUtils.d("zzy:$distance")
                    mIsUp=distance>0
                }

                override fun onScrollStop() {
                    if (mIsUp) {
                        val animator: ObjectAnimator = ObjectAnimator.ofFloat(
                            tvMovedView,
                            "translationY",
                            tvMovedView.translationY,
                            -260f
                        )
                        animator.duration = 360
                        animator.start()
                        mTranslationY=-260f
                    }else{
                        val animator: ObjectAnimator = ObjectAnimator.ofFloat(
                            tvMovedView,
                            "translationY",
                            tvMovedView.translationY,
                            0f
                        )
                        animator.duration = 360
                        animator.start()
                        mTranslationY=0f
                    }

                    val EXAMPLE_COUNTER = intPreferencesKey("example_counter")

                    CoroutineManager.get().start {

                        AppDataStore.get().getMain().data.map {
                            it[EXAMPLE_COUNTER]?:0
                        }.collect {
                            LogUtils.d("zzy=$it");
                        }

                    }
                }
            })
        }
    }

    override fun onPresenterResult(resultCode: Int, vararg params: Any?) {

    }


    override fun onLoadMoreView(isHasMore: Boolean) {

    }

    override fun onLoadFinish() {

    }
}