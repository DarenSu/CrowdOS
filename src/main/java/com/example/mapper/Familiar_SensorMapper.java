package com.example.mapper;

import com.example.entity.Familiar_Sensor;
import com.example.entity.User_Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Familiar_SensorMapper {

    //20200824   传感器数据（json数据+文件）上传至传感器数据表
    void addfile(Familiar_Sensor familiar_sensor) ;

    //2019.7.13 用户-任务表数据上传，添加功能
    void add(User_Task user_task) ;

    List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor);
}
