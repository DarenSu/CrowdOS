package com.example.entity;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:39
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;



// this task class is used directly by application
public class Task {
    private Integer taskId;
    private String taskName;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date postTime;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date deadLine;
    private Integer userId;               //Id of the task publisher
    private String userName;              //Name of the task publisher
    private Float coin;                   //incentive
    private String describe_task;
    private Integer totalNum;                 //The total number of people performing the task
    private Integer taskStatus;               //The execution status of the task
    private Integer taskKind;             //task type，map the integer to a task type through mapping table, values
    // from 0-4 each of which represents public security, environment survey, daily life, business application and
    // normal task
    private Integer temp;         // whether the task has been performed
    //20201008  newly added attributes
    private Float longitude;
    private Float latitude;

    private String sensorTypes;



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
