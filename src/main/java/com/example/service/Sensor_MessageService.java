package com.example.service;

import com.example.entity.Familiar_Sensor;
import com.example.entity.Sensor_Message;
import com.example.entity.User_Task;
import com.example.mapper.Familiar_SensorMapper;
import com.example.mapper.Sensor_MessageMapper;
import com.example.mapper.User_TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Sensor_MessageService {
    @Autowired
    Familiar_SensorMapper familiar_sensorMapper;
    @Autowired
    User_TaskMapper user_taskMapper;
    @Autowired
    Sensor_MessageMapper sensor_messageMapper;
    //   20200514       log record
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Sensor_MessageService.class);

    //20200824   Upload sensor data (json data + file) to sensor data table
    public void addFamiliar_Sensor(Familiar_Sensor familiar_sensor) {
        familiar_sensorMapper.addfile(familiar_sensor);
    }

    //2019.7.13 Upload task data to user-task table
    public void addUser_Task(User_Task user_task) {
        user_taskMapper.add(user_task);
    }

    public List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor) {
        return familiar_sensorMapper.selFamiliar_Sensor(familiar_sensor);
    }



    //20200514   Upload sensor data (json data + file) to sensor data table
    public void addSensor_Byte(Sensor_Message sensor_message) {

        sensor_messageMapper.addfilebyte(sensor_message);
    }

    //    20200514 Find the latest id, which is the largest auto-incrementing primary key
    public Sensor_Message getMaxId() {
        return sensor_messageMapper.getMaxId();
    }

    //    20200515  Return a List of the entire sensor data according to the userId and taskId of the task
    public List<Sensor_Message> selSensor_Byte(Sensor_Message sensor_message) {
        return sensor_messageMapper.selSensor_Byte(sensor_message);
    }

    //  20210517 View all sensing data of the task according to taskId
    public List<Sensor_Message> selAllMessageFromTaskId(Sensor_Message sensor_message) {
        return sensor_messageMapper.selAllMessageFromTaskId(sensor_message);
    }

    // 20210517 According to the taskId and userId to view all the sensing data of the execution of the task
    public List<Sensor_Message> selAllMessageFromUserIdTaskId(Sensor_Message sensor_message) {
        return sensor_messageMapper.selAllMessageFromUserIdTaskId(sensor_message);
    }
}
