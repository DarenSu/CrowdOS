package com.example.service.serviceInterface;

import com.example.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskServiceInterface {




    ///Bean_Task数据库中数据的添加和查询         2016.6.18
    public void add_Task(Task task);

    ///2019-11-6 根据taskId查询task的所有内容
    public Task Sel_Task(Integer taskId);


    public void del_Task(Integer taskId);


    ////2019.7.2 修改：从单个的结果展示修改为多个结果展示
    public List<Task> SelInfo(String taskName ) ;

    ////2019.7.6 修改：一次性返回数据库最后面的十条数据
    public List<Task> getTen();

    ////2019.8.12 修改：一次性返回数据库次后面的十条数据
    public List<Task> getNewTen(Integer mintaskId);
    ///2019.9.11 根据任务的taskId返回任务的List
    public List<Task> SelTaskFromKind(Integer taskKind);


    ////2019.7.18  功能：查询某userId所发布的所有的任务  ，输入的参数：userId，返回的结果：List<task>
    public List<Task> SelUserId(Integer userId );



    //	public Task check_Task(Integer taskId) {
//		return taskMapper.check_Task(taskId);
//	}
    ///查询1&0
    public Task check_Task(Task task);
}
