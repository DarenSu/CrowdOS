package com.example.entity;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;


//当前task主要直接对接应用的直接使用
public class Combine_u_ut {
    private User u;                      //user类
    private User_Task ut;                //user_task类

    public Combine_u_ut(User u, User_Task ut) {
        this.u = u;
        this.ut = ut;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public User_Task getUt() {
        return ut;
    }

    public void setUt(User_Task ut) {
        this.ut = ut;
    }


    @Override
    public String toString() {
        return "Combine_u_ut{" +
                "u=" + u +
                ", ut=" + ut +
                '}';
    }
}
