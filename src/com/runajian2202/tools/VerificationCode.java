package com.runajian2202.tools;

/**
 * 随机生成验证码
 *
 * @author qingqian
 */
public class VerificationCode {

    public static String code() {
        StringBuilder result = new StringBuilder();
        int k = 4;
        for (int i = 0; i < k; i++) {
            int intVar = (int) (Math.random() * 27 + 79);
           result.append((char) intVar);
        }
        return result.toString();
    }
}
