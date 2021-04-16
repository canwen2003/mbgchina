package com.mbg.mbgsupport.demo.kotlin.singleton
/***
 * Kotlin方式懒汉式创建单例的demo
 * created by canwen2003
 */

//私有构造器是为了防止外面创建对象实例化
class DemoSingletonThird private constructor(){
    fun logInfo(str: String){
        println("${TAG}:$str")
    }

    companion object{
        private const val TAG="DemoSingletonThird"
        private var instance :DemoSingletonThird?=null
            get() {
                if (field==null){
                    field= DemoSingletonThird();
                }
                return field;
            }

        @JvmStatic
        @Synchronized
        fun get():DemoSingletonThird{
            return instance!!;
        }
    }
}