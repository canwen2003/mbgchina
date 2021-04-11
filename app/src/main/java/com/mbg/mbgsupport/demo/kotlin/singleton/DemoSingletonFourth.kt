package com.mbg.mbgsupport.demo.kotlin.singleton

/***
 * Kotlin方式双重认证锁创建单例的demo ---推荐使用方式
 * created by canwen2003
 */
class DemoSingletonFourth private constructor(){
    fun logInfo(str: String){
        println("${TAG}:$str")
    }

    companion object{
        private const val TAG="DemoSingletonFourth"
        private val instance: DemoSingletonFourth by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DemoSingletonFourth()
        }

        fun get():DemoSingletonFourth{
            return instance;
        }
    }
}