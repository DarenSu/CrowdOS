package com.example.entity;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;



//当前task主要直接对接应用的直接使用
public class Task {
    private Integer taskId;               //任务ID
    private String taskName;              //任务名称

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date postTime;                //发布日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date deadLine;                //截止日期
    private Integer userId;               //任务发布者的ID
    private String userName;              //任务发布者的名字
    private Float coin;                   //激励金
    private String describe_task;              //任务描述
    private Integer totalNum;                 //该任务的执行总人数
    private Integer taskStatus;               //该任务的执行状态
    private Integer taskKind;             //任务的类型，通过整数型和对应的约定映射表来规定任务类型,数值范围0、1、2、3、4
                                            //公共安全、环境调研、民生日常、商业应用、普通任务
    private Integer temp;         //任务是否执行   （届时可能有错误，需要进行修改）
    //20201008  新增属性
    private Float longitude;  //经度
    private Float latitude;  //维度

    private String sensorTypes;  //类型



    public Task() {
        super();
    }

    public Task(Integer taskId, String taskName, Date postTime, Date deadLine, Integer userId, String userName, Float coin, String describe_task, Integer totalNum, Integer taskStatus, Integer taskKind, Integer temp, Float longitude, Float latitude, String sensorTypes) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.postTime = postTime;
        this.deadLine = deadLine;
        this.userId = userId;
        this.userName = userName;
        this.coin = coin;
        this.describe_task = describe_task;
        this.totalNum = totalNum;
        this.taskStatus = taskStatus;
        this.taskKind = taskKind;
        this.temp = temp;
        this.longitude = longitude;
        this.latitude = latitude;
        this.sensorTypes = sensorTypes;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getCoin() {
        return coin;
    }

    public void setCoin(Float coin) {
        this.coin = coin;
    }

    public String getDescribe_task() {
        return describe_task;
    }

    public void setDescribe_task(String describe_task) {
        this.describe_task = describe_task;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskKind() {
        return taskKind;
    }

    public void setTaskKind(Integer taskKind) {
        this.taskKind = taskKind;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getSensorTypes() {
        return sensorTypes;
    }

    public void setSensorTypes(String sensorTypes) {
        this.sensorTypes = sensorTypes;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", postTime=" + postTime +
                ", deadLine=" + deadLine +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", coin=" + coin +
                ", describe_task='" + describe_task + '\'' +
                ", totalNum=" + totalNum +
                ", taskStatus=" + taskStatus +
                ", taskKind=" + taskKind +
                ", temp=" + temp +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", sensorTypes='" + sensorTypes + '\'' +
                '}';
    }
}
