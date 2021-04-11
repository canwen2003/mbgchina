package com.mbg.mbgsupport.demo.java.singleton;

/***
 * java方式静态内部类方式创建单例的demo
 * created by canwen2003
 */
public class DemoSingleton5 {
    private static final String TAG="DemoSingletonThird";
    private DemoSingleton5(){
    }

    public static DemoSingleton5 getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private final static  DemoSingleton5 instance=new DemoSingleton5();
    }

    public void logInfo(String str){
        System.out.println(TAG+":"+str);
    }

}
