package com.mbg.mbgsupport.demo.kotlin.proxy

import kotlin.reflect.KProperty

class ProxyPropertyDemo {
    var member:String by Delegate()
}

class Delegate{

    var value:String="init"
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("调用委托类的getValue")
        return "$thisRef, 这里委托了 ${property.name} 属性,值为$value"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$thisRef 的 ${property.name} 属性赋值为 $value")
        this.value=value
    }
}

fun main(args:Array<String>){
    val demo:ProxyPropertyDemo= ProxyPropertyDemo();
    println(demo.member)
    demo.member="新的值";
    println(demo.member)

}