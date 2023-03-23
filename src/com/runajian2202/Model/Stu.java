package com.runajian2202.Model;

public class Stu {
    public Stu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String id;

    public Stu(String id, String password) {
        this.id = id;
        this.password = password;
    }

    private String password;

}
