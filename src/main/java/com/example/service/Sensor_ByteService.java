package com.example.service;

import com.example.entity.Sensor_Byte;
import com.example.mapper.Familiar_SensorMapper;
import com.example.mapper.Sensor_ByteMapper;
import com.example.mapper.User_TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Sensor_ByteService {
    @Autowired
    Familiar_SensorMapper familiar_sensorMapper;
    @Autowired
    User_TaskMapper user_taskMapper;
    @Autowired
    Sensor_ByteMapper sensor_byteMapper;


    //20200514   Upload sensor data (json data + file) to sensor data table
    public void addSensor_Byte(Sensor_Byte sensor_byte) {
        sensor_byteMapper.addfilebyte(sensor_byte);
    }

//    <!--20200514 Find the latest id, which is the largest auto-incrementing primary key-->
    public Sensor_Byte getMaxId() {
        return sensor_byteMapper.getMaxId();
    }

//    20200515  20200515  Return a List of the entire sensor data according to the userId and taskId of the task
    public List<Sensor_Byte> selSensor_Byte(Sensor_Byte sensor_byte) {
        return sensor_byteMapper.selSensor_Byte(sensor_byte);
    }
}
