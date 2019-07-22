package com.mbg.module.common.util;


import java.util.List;

/**
 * 解决转换警告问题
 * created by Gap
 */
public class TypeUtils {

    @SuppressWarnings("unchecked")
    public static <T> T cast(List<?> obj) {
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
