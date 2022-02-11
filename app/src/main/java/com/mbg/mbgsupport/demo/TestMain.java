package com.mbg.mbgsupport.demo;

import com.mbg.mbgsupport.demo.java.singleton.DemoSingletonFirst;
import com.mbg.mbgsupport.demo.java.singleton.DemoSingletonFourth;
import com.mbg.mbgsupport.demo.java.singleton.DemoSingletonSecond;
import com.mbg.mbgsupport.demo.java.singleton.DemoSingletonThird;
import com.mbg.mbgsupport.demo.thisLabel.ThisDemo;

/**
 * java 测试类
 * Created by canwen2003
 */
public class TestMain{

     public static void main(String[] args) {
         DemoSingletonFirst.getInstance().logInfo("Java demo 1");
         DemoSingletonSecond.getInstance().logInfo("java demo 2");
         DemoSingletonThird.getInstance().logInfo("java demo 3");
         DemoSingletonFourth.getInstance().logInfo("java demo 4");
         DemoSingletonFourth.getInstance().logInfo("java demo 5");

         com.mbg.mbgsupport.demo.kotlin.singleton.DemoSingletonFirst.logInfo("Kotlin demo 1");
         com.mbg.mbgsupport.demo.kotlin.singleton.DemoSingletonSecond.get().logInfo("Kotlin Demo 2");
         com.mbg.mbgsupport.demo.kotlin.singleton.DemoSingletonThird.get().logInfo("Kotlin Demo 3");
         com.mbg.mbgsupport.demo.kotlin.singleton.DemoSingletonFourth.get().logInfo("Kotlin Demo 4");
         com.mbg.mbgsupport.demo.kotlin.singleton.DemoSingleton5.get().logInfo("Kotlin Demo 5");

        ThisDemo demo=new ThisDemo();
        demo.new InnerClass().test();

        new FilledRectangle().draw();
        
     }
}
