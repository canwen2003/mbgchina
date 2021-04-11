package com.mbg.mbgsupport.demo.java.singleton;


/***
 * java方式饿汉式创建单例的demo
 * created by canwen2003
 */
public class DemoSingletonFirst {

    private static final String TAG="DemoSingletonFirst";
    private static final DemoSingletonFirst instance=new DemoSingletonFirst();
    private DemoSingletonFirst(){

    }
    public static DemoSingletonFirst getInstance(){
        return instance;
    }

    public void logInfo(String str){
        System.out.println(TAG+":"+str);
    }

}
