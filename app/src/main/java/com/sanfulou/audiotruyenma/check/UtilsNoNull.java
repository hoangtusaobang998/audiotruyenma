package com.sanfulou.audiotruyenma.check;

public class UtilsNoNull {

    public static <T> T isNullorIsEmpty(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }

}
