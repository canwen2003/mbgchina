package com.mbg.mbgsupport.demo.java.singleton;


/***
 * java方式线程安全懒汉式创建单例的demo
 * created by canwen2003
 */
public class DemoSingletonThird {
    private static final String TAG="DemoSingletonThird";
    private static DemoSingletonThird instance;
    private DemoSingletonThird(){

    }
    public static synchronized DemoSingletonThird getInstance(){
        if (instance==null){
            instance=new DemoSingletonThird();
        }
        return instance;
    }

    public void logInfo(String str){
        System.out.println(TAG+":"+str);
    }
}
