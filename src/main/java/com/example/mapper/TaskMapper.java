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

    ///Task数据库中数据的添加和查询         2016.6.18
    void add_Task(Task task);
    
    ///有问题 id再给的文件中为String
    Task Sel_Task(Integer taskId);

    ////2019.7.5 修改：从单个的结果展示修改为多个结果展示
    List<Task> SelInfo(String taskName);

    ////2019.7.6 修改：一次性返回数据库最后面的十条数据
    List<Task> getTen() ;
    ////2019.8.12 修改：一次性返回数据库某taskID前面的十条数据
    List<Task> getNewTen(Integer mintaskId);
    ///2019.9.11 根据任务的taskId返回任务的List
    List<Task> SelTaskFromKind(Integer taskKind);


    ////2019.7.18  功能：查询某userId所发布的所有的任务  ，输入的参数：userId，返回的结果：List<task>
    List<Task> SelUserId(Integer userId);

    //User checkUser(User user);
    
    //void del(int id);

    void del_Task(Integer taskId);

    Task checkTask(Task task);

    //void add_Task(Task task);
}
