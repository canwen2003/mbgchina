package com.mbg.module.ui.mvp.kotlin.holder

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.mbg.module.ui.mvp.kotlin.MvpPresenter
import com.mbg.module.ui.mvp.kotlin.base.IntView

import java.util.concurrent.ConcurrentHashMap

class PresenterHolder private constructor(){

    //存储Presenter
    private val pool: MutableMap<String, MvpPresenter<out IntView>> = ConcurrentHashMap()


    /**
     * 保存presenter
     *
     * @param presenter p
     * @param lifecycle l
     */
    fun addPresenter(presenter: MvpPresenter<out IntView>, lifecycle: Lifecycle) {
        if (pool.containsKey(presenter.getPresenterId())) {
            pool.remove(presenter.getPresenterId())
        }

        pool[presenter.getPresenterId()] = presenter
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                if (pool.containsKey(presenter.getPresenterId())) {
                    pool.remove(presenter.getPresenterId())
                }
            }
        })
    }

    /**
     * 获取presenter
     *
     * @param presenterId id
     */
    fun getPresenter(presenterId: String): MvpPresenter<out IntView>? {
        return pool[presenterId]
    }

    companion object{
        private val holder:PresenterHolder by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            PresenterHolder()
        }

        @JvmStatic
        fun get():PresenterHolder{
            return holder
        }
    }
}