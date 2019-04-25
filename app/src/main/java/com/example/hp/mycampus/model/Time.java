package com.example.hp.mycampus.model;

import java.io.Serializable;

public class Time implements Serializable {
    private String name;// 考试名
    private String date;// 日期
    private String countDown;//倒计时

    // 构造函数
    public Time(String name, String date,String countDown) {
        this.name = name;
        this.date = date;
        this.countDown = countDown;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountDown() { return countDown; }

    public void setCountDown(String countDown) {
        this.countDown = countDown;
    }
}
