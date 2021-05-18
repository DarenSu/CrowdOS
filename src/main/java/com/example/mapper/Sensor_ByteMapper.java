package com.example.mapper;

import com.example.entity.Sensor_Byte;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sensor_ByteMapper {

    //20200514   传感器数据（json数据+文件）上传至传感器数据表
    void addfilebyte(Sensor_Byte sensor_byte) ;


    //    <!--20200514 寻找最新的id，也就是最大的自增主键-->
    Sensor_Byte getMaxId() ;
    //    20200515  根据任务的userId、taskId返回整个传感器数据的List
    List<Sensor_Byte> selSensor_Byte(Sensor_Byte sensor_byte);
}
