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

public class Sensor_Message {
    private Integer sensor_messageId;          //主键
    private Integer userId;                    //用户ID
    private Integer taskId;                    //任务ID
    private String fileName;                    // 文件名字


    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date onlineTime;     // 数据上传日期     1
    private String longitude; // 经度
    private String latitude;  //维度
    private String accelerated;  //加速度
    private String direction;  //方向传感器          5

    private String temperature;  //温度
    private String humidity;  //湿度
    private String gravity;  // 重力传感器
    private String airPressure;  // 气压
    private String light;  // 光线传感器             10

    private String distance;  // 距离传感器
    private String magnetic;  // 磁场传感器
    private String gyroscope;  // 陀螺仪
    private String voice;  // 声音
    private String picture;  // 图像                  15

    private String fingerprint;  // 指纹
    private String heartRate;  //心率传感器
    private String bloodOxygen;  //血氧传感器
    private String ultravioletRay;  //紫外线传感器
    private String stepNumber;  //步数探测              20

    private String stepCount;  //计步器
    private String temp1;  //其他1
    private String temp2;  //其他2
    private String temp3;  //其他3
    private String sensorFilePath;  //文件存储位置        25


    public Sensor_Message() {
        super();
    }

    public Sensor_Message(Integer sensor_messageId, Integer userId, Integer taskId, String fileName, Date onlineTime, String longitude, String latitude, String accelerated, String direction, String temperature, String humidity, String gravity, String airPressure, String light, String distance, String magnetic, String gyroscope, String voice, String picture, String fingerprint, String heartRate, String bloodOxygen, String ultravioletRay, String stepNumber, String stepCount, String temp1, String temp2, String temp3, String sensorFilePath) {
        this.sensor_messageId = sensor_messageId;
        this.userId = userId;
        this.taskId = taskId;
        this.fileName = fileName;
        this.onlineTime = onlineTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.accelerated = accelerated;
        this.direction = direction;
        this.temperature = temperature;
        this.humidity = humidity;
        this.gravity = gravity;
        this.airPressure = airPressure;
        this.light = light;
        this.distance = distance;
        this.magnetic = magnetic;
        this.gyroscope = gyroscope;
        this.voice = voice;
        this.picture = picture;
        this.fingerprint = fingerprint;
        this.heartRate = heartRate;
        this.bloodOxygen = bloodOxygen;
        this.ultravioletRay = ultravioletRay;
        this.stepNumber = stepNumber;
        this.stepCount = stepCount;
        this.temp1 = temp1;
        this.temp2 = temp2;
        this.temp3 = temp3;
        this.sensorFilePath = sensorFilePath;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAccelerated() {
        return accelerated;
    }

    public void setAccelerated(String accelerated) {
        this.accelerated = accelerated;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getGravity() {
        return gravity;
    }

    public void setGravity(String gravity) {
        this.gravity = gravity;
    }

    public String getAirPressure() {
        return airPressure;
    }

    public void setAirPressure(String airPressure) {
        this.airPressure = airPressure;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMagnetic() {
        return magnetic;
    }

    public void setMagnetic(String magnetic) {
        this.magnetic = magnetic;
    }

    public String getGyroscope() {
        return gyroscope;
    }

    public void setGyroscope(String gyroscope) {
        this.gyroscope = gyroscope;
    }

    public String getVoice() {
        return voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(String bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public String getUltravioletRay() {
        return ultravioletRay;
    }

    public void setUltravioletRay(String ultravioletRay) {
        this.ultravioletRay = ultravioletRay;
    }

    public String getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(String stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getStepCount() {
        return stepCount;
    }

    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
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

    public String getSensorFilePath() {
        return sensorFilePath;
    }

    public void setSensorFilePath(String sensorFilePath) {
        this.sensorFilePath = sensorFilePath;
    }


    @Override
    public String toString() {
        return "Sensor_Message{" +
                "sensor_messageId=" + sensor_messageId +
                ", userId=" + userId +
                ", taskId=" + taskId +
                ", fileName='" + fileName + '\'' +
                ", onlineTime=" + onlineTime +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", accelerated='" + accelerated + '\'' +
                ", direction='" + direction + '\'' +
                ", temperature='" + temperature + '\'' +
                ", humidity='" + humidity + '\'' +
                ", gravity='" + gravity + '\'' +
                ", airPressure='" + airPressure + '\'' +
                ", light='" + light + '\'' +
                ", distance='" + distance + '\'' +
                ", magnetic='" + magnetic + '\'' +
                ", gyroscope='" + gyroscope + '\'' +
                ", voice='" + voice + '\'' +
                ", picture='" + picture + '\'' +
                ", fingerprint='" + fingerprint + '\'' +
                ", heartRate='" + heartRate + '\'' +
                ", bloodOxygen='" + bloodOxygen + '\'' +
                ", ultravioletRay='" + ultravioletRay + '\'' +
                ", stepNumber='" + stepNumber + '\'' +
                ", stepCount='" + stepCount + '\'' +
                ", temp1='" + temp1 + '\'' +
                ", temp2='" + temp2 + '\'' +
                ", temp3='" + temp3 + '\'' +
                ", sensorFilePath='" + sensorFilePath + '\'' +
                '}';
    }
}
