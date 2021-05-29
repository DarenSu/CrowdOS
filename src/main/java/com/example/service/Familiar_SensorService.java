package com.example.service;

import com.example.entity.Familiar_Sensor;
import com.example.entity.User_Task;
import com.example.mapper.Familiar_SensorMapper;
import com.example.mapper.User_TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Familiar_SensorService {
    @Autowired
    Familiar_SensorMapper familiar_sensorMapper;
    @Autowired
    User_TaskMapper user_taskMapper;


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
}
