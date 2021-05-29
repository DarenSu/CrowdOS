package com.example.service.serviceImpl;

import com.example.entity.Familiar_Sensor;
import com.example.entity.User_Task;
import com.example.mapper.Familiar_SensorMapper;
import com.example.mapper.User_TaskMapper;
import com.example.service.serviceInterface.Familiar_SensorServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Familiar_SensorServiceImpl implements Familiar_SensorServiceInterface {
    @Autowired
    Familiar_SensorMapper familiar_sensorMapper;
    @Autowired
    User_TaskMapper user_taskMapper;


    //20200824
    public void addFamiliar_Sensor(Familiar_Sensor familiar_sensor) {
        familiar_sensorMapper.addfile(familiar_sensor);
    }

    //2017.7.13
    public void addUser_Task(User_Task user_task) {
        user_taskMapper.add(user_task);
    }

    public List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor) {
        return familiar_sensorMapper.selFamiliar_Sensor(familiar_sensor);
    }
}
