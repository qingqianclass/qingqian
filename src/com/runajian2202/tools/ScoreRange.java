package com.runajian2202.tools;

public class ScoreRange {
    //成绩范围限制
    public static boolean chekRange(double x){
        boolean panduan=true;
        if (x>=0&&x<=150){
            panduan=false;
        }
        return panduan;
    }
}
