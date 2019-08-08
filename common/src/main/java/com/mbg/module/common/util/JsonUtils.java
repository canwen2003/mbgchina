package com.mbg.module.common.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonUtils {
   private static Gson mExecuter=new Gson();
   private JsonUtils(){}


   /***
    * 将map对象转换为json字符串
    *
    * @param params map对象
    * @return json字符串
    */
   public static String toJson(Map<String, ?> params) {
      if (params == null) {
         return null;
      }

      return mExecuter.toJson(params);
   }

   /***
    * 将Json字符串转换为对象类型
    * @param json 字符串
    * @param type 对象类型
    * @param <T>  转换后的对象类型
    * @return 对象
    */
   public static  <T>T fromJson(String json, Type type){
      if (json==null||type==null){
         return null;
      }

      return mExecuter.fromJson(json,type);
   }


   /***
    * 将Json字符串转换为对象类型
    * @param json 字符串
    * @param tClass 实体类类型
    * @param <T>  转换后的对象类型
    * @return 对象
    */
   public static  <T>T fromJson(String json, Class<T> tClass){
      if (json==null||tClass==null){
         return null;
      }

      return mExecuter.fromJson(json,tClass);
   }


}
