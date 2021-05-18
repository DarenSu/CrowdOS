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
    //   20200514       日志记录
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Sensor_MessageService.class);

    //20200824   传感器数据（json数据+文件）上传至传感器数据表
    public void addFamiliar_Sensor(Familiar_Sensor familiar_sensor) {
        familiar_sensorMapper.addfile(familiar_sensor);
    }

    //2017.7.13 任务数据上传至用户-任务表
    public void addUser_Task(User_Task user_task) {
        user_taskMapper.add(user_task);
    }

    public List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor) {
        return familiar_sensorMapper.selFamiliar_Sensor(familiar_sensor);
    }



    //20200514   传感器数据（json数据+文件）上传至传感器数据表
    public void addSensor_Byte(Sensor_Message sensor_message) {

        sensor_messageMapper.addfilebyte(sensor_message);
    }

    //    <!--20200514 寻找最新的id，也就是最大的自增主键-->
    public Sensor_Message getMaxId() {
        return sensor_messageMapper.getMaxId();
    }

    //    20200515  根据任务的userId、taskId返回整个传感器数据的List
    public List<Sensor_Message> selSensor_Byte(Sensor_Message sensor_message) {
        return sensor_messageMapper.selSensor_Byte(sensor_message);
    }

    //  20210517 根据taskId查看该任务的所有感知数据
    public List<Sensor_Message> selAllMessageFromTaskId(Sensor_Message sensor_message) {
        return sensor_messageMapper.selAllMessageFromTaskId(sensor_message);
    }

    // 20210517 根据taskId和userId查看该执行这在该任务的所有感知数据
    public List<Sensor_Message> selAllMessageFromUserIdTaskId(Sensor_Message sensor_message) {
        return sensor_messageMapper.selAllMessageFromUserIdTaskId(sensor_message);
    }
}
