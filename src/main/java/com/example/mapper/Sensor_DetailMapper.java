package com.example.mapper;

import com.example.entity.Familiar_Sensor;
import com.example.entity.Sensor_Detail;
import com.example.entity.User_Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sensor_DetailMapper {

    //20200824
    void addfile(Familiar_Sensor familiar_sensor) ;

    //2019.7.13
    void add(User_Task user_task) ;

    List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor);



    //20200514   传感器数据（json数据+文件）上传至传感器数据表
    void addfilebyte(Sensor_Detail sensor_detail) ;


    //    <!--20200514 寻找最新的id，也就是最大的自增主键-->
    Sensor_Detail getMaxId();
    //    20200515  根据任务的userId、taskId返回整个传感器数据的List
    List<Sensor_Detail> selSensor_Byte(Sensor_Detail sensor_detail);

    //  20210517 根据taskId查看该任务的所有感知数据
    List<Sensor_Detail> selAllMessageFromTaskId(Sensor_Detail sensor_detail);
    // 20210517 根据taskId和userId查看该执行这在该任务的所有感知数据
    List<Sensor_Detail> selAllMessageFromUserIdTaskId(Sensor_Detail sensor_detail);
}
