package com.mbg.mbgsupport.demo.kotlin.proxy


// 创建接口
interface IBase {
    fun print()
}

// 实现此接口的被委托的类
class BaseImpl(val x:Int):IBase{
    override fun print() {
        println("outPutData:$x")
    }
}

//过关键字 by 建立委托类
class Derived(base:IBase):IBase by base

fun main(){

    //调用代理类
    Derived(BaseImpl(5)).print()
}

