package com.mbg.module.common.core.manager

import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.concurrent.ConcurrentHashMap

/**
 * 协程管理类
 *
 */
class CoroutineManager {

    private val mJobPool = ConcurrentHashMap<String, Job>()

    fun start(runnable: Runnable) {
        val hashKey=runnable.hashCode().toString()
        start(runnable,hashKey)
    }

    fun start(runnable: Runnable,jobKey:String) {

        val job = GlobalScope.launch(Dispatchers.Default) {
            runnable.run()
            mJobPool.remove(jobKey)
        }
        mJobPool[jobKey] = job
    }

    fun start(runnable: ThreadPoolRunnable) {
        val hashKey=runnable.hashCode().toString()
        start(runnable,hashKey)
    }

    fun start(runnable: ThreadPoolRunnable,jobKey: String) {
        val job =  GlobalScope.launch(Dispatchers.Default) {
            runnable.run()
            mJobPool.remove(jobKey)
        }
        mJobPool[jobKey] = job
    }

    fun start(block: suspend () -> Unit) {
        val hashKey=block.toString()
        start(block,hashKey)
    }

    fun start(block: suspend () -> Unit,jobKey: String) {
        val job=GlobalScope.launch(Dispatchers.Default) {
            block()
            mJobPool.remove(jobKey)
        }
        mJobPool[jobKey] = job
    }

    fun clear(){
        mJobPool.forEach{
            if (it.value.isActive) {
                it.value.cancel()
            }
        }
        mJobPool.clear()
    }

    companion object {
        private val coroutineManager: CoroutineManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CoroutineManager()
        }

        @JvmStatic
        fun get(): CoroutineManager = coroutineManager

    }
}