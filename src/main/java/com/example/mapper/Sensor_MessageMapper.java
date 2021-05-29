package com.example.mapper;

import com.example.entity.Familiar_Sensor;
import com.example.entity.Sensor_Message;
import com.example.entity.User_Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sensor_MessageMapper {

    //20200824   传感器数据（json数据+文件）上传至传感器数据表
    void addfile(Familiar_Sensor familiar_sensor) ;

    //2019.7.13 用户-任务表数据上传，添加功能
    void add(User_Task user_task) ;

    List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor);



    //20200514   Upload sensor data (json data + file) to sensor data table
    void addfilebyte(Sensor_Message sensor_message) ;


    //    20200514 Find the latest id, which is the largest auto-incrementing primary key
    Sensor_Message getMaxId();
    //    20200515  Return a List of the entire sensor data according to the userId and taskId of the task
    List<Sensor_Message> selSensor_Byte(Sensor_Message sensor_message);

    //  20210517 View all sensing data of the task according to taskId
    List<Sensor_Message> selAllMessageFromTaskId(Sensor_Message sensor_message);
    // 20210517 According to the taskId and userId to view all the sensing data of the execution of the task
    List<Sensor_Message> selAllMessageFromUserIdTaskId(Sensor_Message sensor_message);
}
