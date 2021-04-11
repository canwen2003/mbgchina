package com.mbg.mbgsupport.demo.kotlin.singleton
/***
 * Kotlin方式懒汉式创建单例的demo
 * created by canwen2003
 */

//私有构造器是为了防止外面创建对象实例化
class DemoSingletonSecond private constructor(){

    fun logInfo(str: String){
        println("${TAG}:$str")
    }

    companion object{
        private const val TAG="DemoSingletonSecond"
        private var instance :DemoSingletonSecond?=null
        get() {
            if (field==null){
                field= DemoSingletonSecond();
            }
            return field;
        }

        fun get():DemoSingletonSecond{
            return instance!!;
        }
    }

}