package com.runajian2202.Model;

import java.math.BigDecimal;

/**
 * @author qingqian
 * 成绩信息类
 */
public class Score {
    private int sid;
    private String sname;
    private double chinese;
    private double math;
    private double english;
    private double sum;
    private double rank;
    public Score(){}
    public Score(int sid,String sname,double chinese,double math,double english,double sum){
        this.sid=sid;
        this.sname=sname;
        this.math=math;
        this.english=english;
        this.chinese=chinese;
        this.sum = sum;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public double getChinese() {
        return chinese;
    }

    public void setChinese(double chinese) {
        this.chinese = chinese;
    }

    public double getMath() {
        return math;
    }

    public void setMath(double math) {
        this.math = math;
    }

    public double getEnglish() {
        return english;
    }

    public void setEnglish(double english) {
        this.english = english;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }
    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }


}
