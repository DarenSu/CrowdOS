package com.example.mapper;

import com.example.entity.Familiar_Sensor;
import com.example.entity.User_Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Familiar_SensorMapper {

    //20200824
//    void addfilebyte(Sensor_Byte sensor_byte);
    // Upload sensor data (json data + file) to sensor data table
    void addfile(Familiar_Sensor familiar_sensor) ;

    //2019.7.13
    void add(User_Task user_task) ;

    List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor);


}
