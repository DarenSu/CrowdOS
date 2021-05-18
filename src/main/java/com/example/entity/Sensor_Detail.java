package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author:DarenSu
 * @Date: 2020/05/28 修改
 * @Time: 14:42
 */

//      传感器相关的类
//      后续需要和前端进行协商，这部分没有统一
//      暂时就做最简单的

//      20200625   增加数据存储方式
//     类似与之前的文件存储，具体的需要进一步与前端协商。切记

public class Sensor_Detail {
    private Integer sensor_detailId;          //主键
    private Integer userId;                    //用户ID
    private Integer taskId;                    //任务ID

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date onlineTime;        // 数据上传日期
    private String fileName;        //  文件名字
    private String sensorType;      //   传感器种类或者传感器名字
    private String acquisitionTime; //    数据采集时间
    private String sensorValue;     //  数据值   可能一个传感器有多个数据，上传的文件中使用“-”进行分割多个数据

    private String temp1;           // 测试& 预留数据
    private String temp2;           // 测试& 预留数据


    public Sensor_Detail(){
        super();
    }

    public Sensor_Detail(Integer sensor_detailId, Integer userId, Integer taskId, Date onlineTime, String fileName, String sensorType, String acquisitionTime, String sensorValue, String temp1, String temp2) {
        this.sensor_detailId = sensor_detailId;
        this.userId = userId;
        this.taskId = taskId;
        this.onlineTime = onlineTime;
        this.fileName = fileName;
        this.sensorType = sensorType;
        this.acquisitionTime = acquisitionTime;
        this.sensorValue = sensorValue;
        this.temp1 = temp1;
        this.temp2 = temp2;
    }

    public Integer getSensor_detailId() {
        return sensor_detailId;
    }

    public void setSensor_detailId(Integer sensor_detailId) {
        this.sensor_detailId = sensor_detailId;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getAcquisitionTime() {
        return acquisitionTime;
    }

    public void setAcquisitionTime(String acquisitionTime) {
        this.acquisitionTime = acquisitionTime;
    }

    public String getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(String sensorValue) {
        this.sensorValue = sensorValue;
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

    @Override
    public String toString() {
        return "Sensor_Detail{" +
                "sensor_detailId=" + sensor_detailId +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", onlineTime=" + onlineTime +
                ", fileName='" + fileName + '\'' +
                ", sensorType='" + sensorType + '\'' +
                ", acquisitionTime='" + acquisitionTime + '\'' +
                ", sensorValue='" + sensorValue + '\'' +
                ", temp1='" + temp1 + '\'' +
                ", temp2='" + temp2 + '\'' +
                '}';
    }
}

