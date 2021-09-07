package com.mbg.mbgsupport.demo.kotlin.proxy

import kotlin.properties.Delegates

class ProxyObservable {
    var observer: String by Delegates.observable("initData",{ pro,old,new ->

        println("属性：${pro.name}")
        println("旧值：$old")
        println("新值：$new")
    })

    fun set(value:String){
        observer=value
    }
}



fun main(){
    val demo= ProxyObservable();
    demo.set("First")
    demo.set("second")

}
