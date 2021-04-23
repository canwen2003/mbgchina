package com.mbg.module.common.core.manager

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 协程管理类
 *
 */
class CoroutineManager {

    fun start(runnable: Runnable){
        GlobalScope.launch (Dispatchers.IO){
            runnable.run()
        }
    }

    fun start(runnable: ThreadPoolRunnable){
        GlobalScope.launch (Dispatchers.Default){
            runnable.run()
        }
    }

    companion object{
        private val coroutineManager:CoroutineManager by lazy(mode=LazyThreadSafetyMode.SYNCHRONIZED){
            CoroutineManager()
        }

        @JvmStatic
        fun get():CoroutineManager= coroutineManager

    }
}