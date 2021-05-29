package com.example.service.serviceImpl;

import com.example.entity.Task;
import com.example.mapper.TaskMapper;
import com.example.service.serviceInterface.TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskServiceInterface {

    @Autowired
    TaskMapper taskMapper;


    ///Bean_Task
    public void add_Task(Task task) {
        taskMapper.add_Task(task);
    }

    ///2019-11-6
    public Task Sel_Task(Integer taskId) {
        return taskMapper.Sel_Task(taskId);
    }


    public void del_Task(Integer taskId) {
        taskMapper.del_Task(taskId);
    }


    ////2019.7.2
    public List<Task> SelInfo(String taskName ) {
        return taskMapper.SelInfo(taskName);
    }

    ////2019.7.6
    public List<Task> getTen() {
        return taskMapper.getTen();
    }

    ////2019.8.12
    public List<Task> getNewTen(Integer mintaskId) {
        return taskMapper.getNewTen(mintaskId);
    }
    ///2019.9.11
    public List<Task> SelTaskFromKind(Integer taskKind) {
        return taskMapper.SelTaskFromKind(taskKind);
    }


    ////2019.7.18
    public List<Task> SelUserId(Integer userId ) {
        return taskMapper.SelUserId(userId);
    }



    //	public Task check_Task(Integer taskId) {
//		return taskMapper.check_Task(taskId);
//	}
    ///1&0
    public Task check_Task(Task task) {		return taskMapper.checkTask(task);	}
}
