package com.mbg.mbgsupport.demo.kotlin.singleton
/***
 * Kotlin方式静态内部类方式创建单例的demo ---推荐使用方式
 * created by canwen2003
 */
class DemoSingleton5 private constructor(){

    companion object {
        private const val TAG="DemoSingletonThird"

        @JvmStatic
        fun get():DemoSingleton5{
            return SingletonHolder.holder;
        }
    }

    private object SingletonHolder {
        val holder= DemoSingleton5()
    }

    fun logInfo(str: String){
        println("${TAG}:$str")
    }
}