package com.mbg.module.common.util;


import androidx.annotation.Nullable;

public class HashCodeUtils {
    private static final int HASH_MULTIPLIER = 31;//倍数、乘数
    private static final int HASH_ACCUMULATOR = 17;//累加器

    private HashCodeUtils(){}


    public static long hashCode(String value) {
        return hashCode(value, HASH_ACCUMULATOR);
    }

    public static long hashCode(int value) {
        return hashCode(value, HASH_ACCUMULATOR);
    }

    public static int hashCode(int value, int accumulator) {
        return accumulator * HASH_MULTIPLIER + value;
    }

    public static long hashCode(float value) {
        return hashCode(value, HASH_ACCUMULATOR);
    }

    public static long hashCode(float value, int accumulator) {
        return hashCode(Float.floatToIntBits(value), accumulator);
    }

    public static long hashCode(@Nullable Object object, int accumulator) {
        return hashCode(object == null ? 0 : object.hashCode(), accumulator);
    }

    public static long hashCode(boolean value, int accumulator) {
        return hashCode(value ? 1 : 0, accumulator);
    }

    public static long hashCode(boolean value) {
        return hashCode(value, HASH_ACCUMULATOR);
    }
}
