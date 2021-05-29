package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author:DarenSu
 * @Date: 2020/05/28 修改
 * @Time: 14:42
 */



public class Sensor_Byte {
    private Integer sensor_messageId;     // primary key
    private Integer userId;
    private Integer taskId;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date onlineTime;     // data upload time

    private String fileByte;     // binary stream
    private String temp1;     // test & reserve data
    private String temp2;     // test & reserve data
    private String temp3;     // test & reserve data
    private String temp4;     // test & reserve data

//    private String sensorFilePath;


    public Sensor_Byte() {
        super();
    }

//    public Sensor_Byte(Integer sensor_messageId, Integer userId, Integer taskId, Date onlineTime, String fileByte) {
//        this.sensor_messageId = sensor_messageId;
//        this.userId = userId;
//        this.taskId = taskId;
//        this.onlineTime = onlineTime;
//        this.fileByte = fileByte;
//    }
//
//    public Integer getSensor_messageId() {
//        return sensor_messageId;
//    }
//
//    public void setSensor_messageId(Integer sensor_messageId) {
//        this.sensor_messageId = sensor_messageId;
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Integer userId) {
//        this.userId = userId;
//    }
//
//    public Integer getTaskId() {
//        return taskId;
//    }
//
//    public void setTaskId(Integer taskId) {
//        this.taskId = taskId;
//    }
//
//    public Date getOnlineTime() {
//        return onlineTime;
//    }
//
//    public void setOnlineTime(Date onlineTime) {
//        this.onlineTime = onlineTime;
//    }
//
//    public String getFileByte() {
//        return fileByte;
//    }
//
//    public void setFileByte(String fileByte) {
//        this.fileByte = fileByte;
//    }
//
//    @Override
//    public String toString() {
//        return "Sensor_Byte{" +
//                "sensor_messageId=" + sensor_messageId +
//                ", userId=" + userId +
//                ", taskId=" + taskId +
//                ", onlineTime=" + onlineTime +
//                ", fileByte='" + fileByte + '\'' +
//                '}';
//    }


        public Sensor_Byte(Integer sensor_messageId, Integer userId, Integer taskId, Date onlineTime, String fileByte, String temp1, String temp2, String temp3, String temp4) {
        this.sensor_messageId = sensor_messageId;
        this.userId = userId;
        this.taskId = taskId;
        this.onlineTime = onlineTime;
        this.fileByte = fileByte;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.temp3 = temp3;
        this.temp4 = temp4;
    }

    public Integer getSensor_messageId() {
        return sensor_messageId;
    }

    public void setSensor_messageId(Integer sensor_messageId) {
        this.sensor_messageId = sensor_messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getFileByte() {
        return fileByte;
    }

    public void setFileByte(String fileByte) {
        this.fileByte = fileByte;
    }

    public String getTemp1() {
        return temp1;
    }

    public void setTemp1(String temp1) {
        this.temp1 = temp1;
    }

    public String getTemp2() {
        return temp2;
    }

    public void setTemp2(String temp2) {
        this.temp2 = temp2;
    }

    public String getTemp3() {
        return temp3;
    }

    public void setTemp3(String temp3) {
        this.temp3 = temp3;
    }

    public String getTemp4() {
        return temp4;
    }

    public void setTemp4(String temp4) {
        this.temp4 = temp4;
    }

    @Override
    public String toString() {
        return "Sensor_Byte{" +
                "sensor_messageId=" + sensor_messageId +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", onlineTime=" + onlineTime +
                ", fileByte='" + fileByte + '\'' +
                ", temp1='" + temp1 + '\'' +
                ", temp2='" + temp2 + '\'' +
                ", temp3='" + temp3 + '\'' +
                ", temp4='" + temp4 + '\'' +
                '}';
    }
}

