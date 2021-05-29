package com.example.service;

import com.example.entity.Familiar_Sensor;
import com.example.entity.Sensor_Detail;
import com.example.entity.User_Task;
import com.example.mapper.Familiar_SensorMapper;
import com.example.mapper.Sensor_DetailMapper;
import com.example.mapper.Sensor_MessageMapper;
import com.example.mapper.User_TaskMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Sensor_DetailService {
    @Autowired
    Familiar_SensorMapper familiar_sensorMapper;
    @Autowired
    User_TaskMapper user_taskMapper;
    @Autowired
    Sensor_MessageMapper sensor_messageMapper;
    @Autowired
    Sensor_DetailMapper sensor_detailMapper;
    //   20200514       log record
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Sensor_DetailService.class);

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



    //20200514   传感器数据（json数据+文件）上传至传感器数据表
    public void addSensor_Byte(Sensor_Detail sensor_detail) {

        sensor_detailMapper.addfilebyte(sensor_detail);
    }

    //    <!--20200514 寻找最新的id，也就是最大的自增主键-->
    public Sensor_Detail getMaxId() {
        return sensor_detailMapper.getMaxId();
    }

    //    20200515  根据任务的userId、taskId返回整个传感器数据的List
    public List<Sensor_Detail> selSensor_Byte(Sensor_Detail sensor_detail) {
        return sensor_detailMapper.selSensor_Byte(sensor_detail);
    }

    //  20210517 根据taskId查看该任务的所有感知数据
    public List<Sensor_Detail> selAllMessageFromTaskId(Sensor_Detail sensor_detail) {
        return sensor_detailMapper.selAllMessageFromTaskId(sensor_detail);
    }

    // 20210517 根据taskId和userId查看该执行这在该任务的所有感知数据
    public List<Sensor_Detail> selAllMessageFromUserIdTaskId(Sensor_Detail sensor_detail) {
        return sensor_detailMapper.selAllMessageFromUserIdTaskId(sensor_detail);
    }
}
