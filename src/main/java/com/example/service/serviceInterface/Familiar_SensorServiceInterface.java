package com.example.service.serviceInterface;

import com.example.entity.Familiar_Sensor;
import com.example.entity.User_Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Familiar_SensorServiceInterface {




    //20200824
    public void addFamiliar_Sensor(Familiar_Sensor familiar_sensor);

    //2017.7.13
    public void addUser_Task(User_Task user_task);

    public List<Familiar_Sensor> selFamiliar_Sensor(Familiar_Sensor familiar_sensor) ;
}
