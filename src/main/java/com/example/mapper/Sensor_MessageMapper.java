package com.example.mapper;

import com.example.entity.Familiar_Sensor;
import com.example.entity.Sensor_Message;
import com.example.entity.User_Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sensor_MessageMapper {

    //20200824   传感器数据（json数据+文件）上传至传感器数据表
    void addfile(Familiar_Sensor familiar_sensor) ;

    //2019.7.13 用户-任务表数据上传，添加功能
    void add(User_Task user_task) ;

    List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor);



    //20200514   传感器数据（json数据+文件）上传至传感器数据表
    void addfilebyte(Sensor_Message sensor_message) ;


    //    <!--20200514 寻找最新的id，也就是最大的自增主键-->
    Sensor_Message getMaxId();
    //    20200515  根据任务的userId、taskId返回整个传感器数据的List
    List<Sensor_Message> selSensor_Byte(Sensor_Message sensor_message);

    //  20210517 根据taskId查看该任务的所有感知数据
    List<Sensor_Message> selAllMessageFromTaskId(Sensor_Message sensor_message);
    // 20210517 根据taskId和userId查看该执行这在该任务的所有感知数据
    List<Sensor_Message> selAllMessageFromUserIdTaskId(Sensor_Message sensor_message);
}
