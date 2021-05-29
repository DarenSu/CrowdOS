package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author:DarenSu
 * @Date: 2020/04/28 修改
 * @Time: 14:42
 */



public class Liveness {
    private Integer livenessId;

    private Integer userId;
    private Integer taskId;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date onlineTime;
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date deadlineTime;

    private Integer temp;                       //0/1，Whether the record is capped

    private Integer totalWeek;                 //  record online total one week
    private Integer totalMouth;                //  record online total one month
    private Integer totalYear;                 //  record online total one year
    private Integer total;                     //  record online total in total

    public Liveness() {
        super();
    }

    public Liveness(Integer livenessId, Integer userId, Integer taskId, Date onlineTime, Date deadlineTime, Integer temp, Integer totalWeek, Integer totalMouth, Integer totalYear, Integer total) {
        this.livenessId = livenessId;
        this.userId = userId;
        this.taskId = taskId;
        this.onlineTime = onlineTime;
        this.deadlineTime = deadlineTime;
        this.temp = temp;
        this.totalWeek = totalWeek;
        this.totalMouth = totalMouth;
        this.totalYear = totalYear;
        this.total = total;
    }

    public Integer getLivenessId() {
        return livenessId;
    }

    public void setLivenessId(Integer livenessId) {
        this.livenessId = livenessId;
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

    public Date getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(Date deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getTotalWeek() {
        return totalWeek;
    }

    public void setTotalWeek(Integer totalWeek) {
        this.totalWeek = totalWeek;
    }

    public Integer getTotalMouth() {
        return totalMouth;
    }

    public void setTotalMouth(Integer totalMouth) {
        this.totalMouth = totalMouth;
    }

    public Integer getTotalYear() {
        return totalYear;
    }

    public void setTotalYear(Integer totalYear) {
        this.totalYear = totalYear;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    @Override
    public String toString() {
        return "Liveness{" +
                "livenessId=" + livenessId +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", onlineTime=" + onlineTime +
                ", deadlineTime=" + deadlineTime +
                ", temp=" + temp +
                ", totalWeek=" + totalWeek +
                ", totalMouth=" + totalMouth +
                ", totalYear=" + totalYear +
                ", total=" + total +
                '}';
    }
}
