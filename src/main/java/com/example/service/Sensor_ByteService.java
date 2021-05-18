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


    //20200514   传感器数据（json数据+文件）上传至传感器数据表
    public void addSensor_Byte(Sensor_Byte sensor_byte) {
        sensor_byteMapper.addfilebyte(sensor_byte);
    }

//    <!--20200514 寻找最新的id，也就是最大的自增主键-->
    public Sensor_Byte getMaxId() {
        return sensor_byteMapper.getMaxId();
    }

//    20200515  根据任务的userId、taskId返回整个传感器数据的List
    public List<Sensor_Byte> selSensor_Byte(Sensor_Byte sensor_byte) {
        return sensor_byteMapper.selSensor_Byte(sensor_byte);
    }
}
