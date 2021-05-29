package com.example.entity;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */

//The current task is mainly directly connected to the direct use of the application
public class Combine_u_ut {
    private User u;                      //user
    private User_Task ut;                //user_task

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
