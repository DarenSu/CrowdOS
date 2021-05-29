package com.example.service.serviceInterface;

import com.example.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskServiceInterface {




    ///Bean_Task          2016.6.18
    public void add_Task(Task task);

    ///2019-11-6
    public Task Sel_Task(Integer taskId);


    public void del_Task(Integer taskId);


    ////2019.7.2
    public List<Task> SelInfo(String taskName ) ;

    ////2019.7.6
    public List<Task> getTen();

    ////2019.8.12
    public List<Task> getNewTen(Integer mintaskId);
    ///2019.9.11
    public List<Task> SelTaskFromKind(Integer taskKind);


    ////2019.7.18
    public List<Task> SelUserId(Integer userId );



    //	public Task check_Task(Integer taskId) {
//		return taskMapper.check_Task(taskId);
//	}
    ///1&0
    public Task check_Task(Task task);
}
