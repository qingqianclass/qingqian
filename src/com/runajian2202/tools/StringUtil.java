package com.runajian2202.tools;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return "".equals(str.trim()) || str==null;
    }
}