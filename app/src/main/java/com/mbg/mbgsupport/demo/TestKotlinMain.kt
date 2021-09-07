package com.mbg.mbgsupport.demo

import com.mbg.mbgsupport.demo.kotlin.proxy.BaseImpl
import com.mbg.mbgsupport.demo.kotlin.proxy.Derived
import com.mbg.mbgsupport.demo.thisLabel.ThisDemo

/**
 * kotlin 测试类
 * Created by canwen2003
 */
class TestKotlinMain {

    fun main(){
       /* DemoSingletonFirst.logInfo("Kotlin demo 1")
        DemoSingletonSecond.get().logInfo("Kotlin demo 2")
        DemoSingletonThird.get().logInfo("Kotlin demo 3")
        DemoSingletonFourth.get().logInfo("Kotlin demo 4")
        DemoSingleton5.get().logInfo("Kotlin demo 5");

        com.mbg.mbgsupport.demo.java.singleton.DemoSingletonFirst.getInstance().logInfo("Java demo 1")
        com.mbg.mbgsupport.demo.java.singleton.DemoSingletonSecond.getInstance().logInfo("Java demo 2")
        com.mbg.mbgsupport.demo.java.singleton.DemoSingletonThird.getInstance().logInfo("Java demo 3")
        com.mbg.mbgsupport.demo.java.singleton.DemoSingletonFourth.getInstance().logInfo("Java demo 4")
        com.mbg.mbgsupport.demo.java.singleton.DemoSingleton5.getInstance().logInfo("Java demo 5")*/

        ThisDemo().InnerClass().test();

        //调用代理类
        Derived(BaseImpl(5)).print()

    }
}