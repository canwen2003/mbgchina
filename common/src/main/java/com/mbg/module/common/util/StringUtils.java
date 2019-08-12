package com.mbg.module.common.util;

import java.util.List;

public class StringUtils {
    private StringUtils(){}


    public static <T> boolean notEmpty(List<T> list) {
        return !isEmpty(list);
    }

    public static <T> boolean isEmpty(List<T> list) {

        return (list == null || list.size() == 0) ;
    }

    public static boolean isEmpty(String data){

        return  (data==null||"".equals(data.trim()));
    }

    public static boolean notEmpty(String data){

        return  !isEmpty(data);
    }
}
