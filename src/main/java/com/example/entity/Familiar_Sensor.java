package com.example.entity;

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

public class Familiar_Sensor {
    private Integer familiar_sensorId;          //主键
    private Integer userId;                    //用户ID
    private Integer taskId;                    //任务ID

    private Float longitude;  //经度
    private Float latitude;  //维度
    private Float speed; //速度

    private String sensorType;  //类型

    private String sensorFile;   //位置存储

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
