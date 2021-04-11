package com.mbg.mbgsupport.demo.kotlin.singleton

/***
 * Kotlin方式饿汉式创建单例的demo
 * created by canwen2003
 */

object DemoSingletonFirst {
    private const val TAG="DemoSingletonFirst"

    fun logInfo(str: String){
        println("$TAG:$str")

    }

}