package com.runajian2202.tools;

public class PasswordFormatCheck {
    //密码格式限制
   public static char[] AA={'a','b','c','d','e','f','g','h','i','j',
            'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static  char[]BB={'A','B','C','D','E','F','G','H','I','J',
            'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    public static  char []CC={'1','2','3','4','5','6','7','8','9','0'};
    public static  int find=0,find2 = 0,find3 = 0;
    public static boolean check(String A){
        char [] a=A.toCharArray();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j <26 ; j++) {
                if (a[i]==AA[j]){
                    find=1;
                    break;
                }
            }
        }
        for (int i = 0; i < a.length ; i++) {
            for (int j = 0; j <26 ; j++) {
                if (a[i] == BB[j]){
                    find2=1;
                    break;
                }
            }
        }
        for (int i = 0; i < a.length ; i++) {
            for (int j = 0; j <10 ; j++) {
                if (a[i]==CC[j]){
                    find3=1;
                    break;
                }
            }
        }
        return find==1&&find2==1&&find3==1;
    }
}
