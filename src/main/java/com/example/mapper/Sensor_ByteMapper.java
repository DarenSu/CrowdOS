package com.example.mapper;

import com.example.entity.Sensor_Byte;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sensor_ByteMapper {

    //20200514   Upload sensor data (json data + file) to sensor data table
    void addfilebyte(Sensor_Byte sensor_byte) ;


    //    <!--20200514 Find the latest id, which is the largest auto-incrementing primary key-->
    Sensor_Byte getMaxId() ;
    //    20200515 20200515  Return a List of the entire sensor data according to the userId and taskId of the task
    List<Sensor_Byte> selSensor_Byte(Sensor_Byte sensor_byte);
}
