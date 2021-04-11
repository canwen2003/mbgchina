package com.mbg.mbgsupport.demo.java.singleton;

/***
 * java方式双重认证锁创建单例的demo ---推荐使用方式
 * created by canwen2003
 */
public class DemoSingletonFourth {
    private static final String TAG="DemoSingletonFourth";
    private static DemoSingletonFourth instance;
    private DemoSingletonFourth(){

    }
    public static DemoSingletonFourth getInstance(){
        if (instance==null){
            synchronized (DemoSingletonFourth.class) {
                if (instance==null){
                    instance = new DemoSingletonFourth();
                }
            }
        }
        return instance;
    }

    public void logInfo(String str){
        System.out.println(TAG+":"+str);
    }
}
