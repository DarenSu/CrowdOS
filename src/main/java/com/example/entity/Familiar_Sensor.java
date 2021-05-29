package com.example.entity;

/**
 * @Author:DarenSu
 * @Date: 2020/05/28
 * @Time: 14:42
 */



//      20200625   add data storage method

public class Familiar_Sensor {
    private Integer familiar_sensorId;          //primary key
    private Integer userId;
    private Integer taskId;

    private Float longitude;
    private Float latitude;
    private Float speed;

    private String sensorType;

    private String sensorFile;   //memory address

    public Familiar_Sensor() {
        super();
    }

    public Familiar_Sensor(Integer familiar_sensorId, Integer userId, Integer taskId, Float longitude, Float latiude, Float speed, String sensorType, String sensorFile) {
        this.familiar_sensorId = familiar_sensorId;
        this.userId = userId;
        this.taskId = taskId;
        this.longitude = longitude;
        this.latitude = latiude;
        this.speed = speed;
        this.sensorType = sensorType;
        this.sensorFile = sensorFile;
    }

    public Integer getFamiliar_sensorId() {
        return familiar_sensorId;
    }

    public void setFamiliar_sensorId(Integer familiar_sensorId) {
        this.familiar_sensorId = familiar_sensorId;
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

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatiude() {
        return latitude;
    }

    public void setLatiude(Float latiude) {
        this.latitude = latiude;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorFile() {
        return sensorFile;
    }

    public void setSensorFile(String sensorFile) {
        this.sensorFile = sensorFile;
    }

    @Override
    public String toString() {
        return "Familiar_Sensor{" +
                "familiar_sensorId=" + familiar_sensorId +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", speed=" + speed +
                ", sensorType='" + sensorType + '\'' +
                ", sensorFile='" + sensorFile + '\'' +
                '}';
    }
}
