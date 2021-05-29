package com.example.mapper;

import com.example.entity.Task;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:20
 */

/**
 * @Author:DarenSu
 * @Date: 2019/6/13 修改
 * @Time: 14:42
 */


@Repository
public interface TaskMapper {


    static void del(Integer taskId) {
    }

    ///Adding and querying data in Bean_Task database         2016.6.18
    void add_Task(Task task);

    ///2019-11-6 Query all contents of task according to taskId
    Task Sel_Task(Integer taskId);

    ////2019.7.2 Modification: Modified from a single result display to multiple result displays
    List<Task> SelInfo(String taskName);

    //2019.7.6 Modification: Return the last ten data of the database at one time
    List<Task> getTen() ;
    //2021.1.11 Modification: Return all data in the database at once
    List<Task> getAll() ;
    //2019.8.12 Modification: Return the last ten data of the database at one time
    List<Task> getNewTen(Integer mintaskId);
    //2019.9.11 Return a list of tasks according to the taskId of the task
    List<Task> SelTaskFromKind(Integer taskKind);


    //2019.7.18  Function: Query all tasks published by a userId, input parameter: userId, return result: List<task>
    List<Task> SelUserId(Integer userId);

    //User checkUser(User user);
    
    //void del(int id);

    void del_Task(Integer taskId);

    Task checkTask(Task task);
    //20210516   The total number of people remaining in the task minus 1
    void updateTaskTotalNum(Task task);
    //20210516  Find the entire information of the task according to taskId
    Task SelTaskFromTaskId(Integer taskId);

    //void add_Task(Task task);
}
