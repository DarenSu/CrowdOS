package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author:DarenSu
 * @Date: 2021/04/21
 * @Time: 14:42
 */


public class Server {
    private Integer serverId;
    private Integer cpu;
    private Integer memory;
    private Integer disk;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date time;                //access time
    private String temp;                  //backup

    public Server() { super();}

    public Server(Integer serverId, Integer cpu, Integer memory, Integer disk, Date postTime, String temp) {
        this.serverId = serverId;
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
        this.time = postTime;
        this.temp = temp;
    }

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public Integer getCpu() {
        return cpu;
    }

    public void setCpu(Integer cpu) {
        this.cpu = cpu;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date postTime) {
        this.time = postTime;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @Override
    public String toString() {
        return "Server{" +
                "serverId=" + serverId +
                ", cpu=" + cpu +
                ", memory=" + memory +
                ", disk=" + disk +
                ", postTime=" + time +
                ", temp='" + temp + '\'' +
                '}';
    }
}
