package com.runajian2202.tools;

/**
 * @author qingqian
 * 空值校验
 */
public class StringUtil {
    public static boolean isEmpty(String str) {
        return "".equals(str.trim()) || str==null;
    }
}