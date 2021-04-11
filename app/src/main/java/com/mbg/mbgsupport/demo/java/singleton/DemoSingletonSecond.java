package com.mbg.mbgsupport.demo.java.singleton;

/***
 * java方式懒汉式创建单例的demo
 * created by canwen2003
 */
public class DemoSingletonSecond {
    private static final String TAG="DemoSingletonSecond";
    private static DemoSingletonSecond instance;
    private DemoSingletonSecond(){

    }
    public static DemoSingletonSecond getInstance(){
        if (instance==null){
            instance=new DemoSingletonSecond();
        }
        return instance;
    }

    public void logInfo(String str){
        System.out.println(TAG+":"+str);
    }
}
