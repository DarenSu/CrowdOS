package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author:DarenSu
 * @Date: 2020/04/28 修改
 * @Time: 14:42
 */



public class Liveness {
    private Integer livenessId;                //唯一识别码

    private Integer userId;                    //用户ID
    private Integer taskId;                    //任务ID

    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date onlineTime;                  //上线日期
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date deadlineTime;                 //下线日期

    private Integer temp;                       //0-1变量，记录是否上限

    private Integer totalWeek;                 //记录一周的上线数目
    private Integer totalMouth;                //记录一月的上线数目
    private Integer totalYear;                 //记录一年的上线数目
    private Integer total;                      //记录上线的总数

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
