package com.example.service.serviceInterface;

import com.example.entity.Familiar_Sensor;
import com.example.entity.User_Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Familiar_SensorServiceInterface {




    //20200824   传感器数据（json数据+文件）上传至传感器数据表
    public void addFamiliar_Sensor(Familiar_Sensor familiar_sensor);

    //2017.7.13 任务数据上传至用户-任务表
    public void addUser_Task(User_Task user_task);

    public List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor) ;
}
