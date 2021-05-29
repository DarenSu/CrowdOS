package com.example.mapper;

import com.example.entity.User;
import com.example.entity.User_Task;
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
public interface User_TaskMapper {

    //2019.7.11 User-task table query function
    User_Task Sel(int user_taskId);

    //2019.7.13 Upload task data to user-task table
    void add(User_Task user_task);

    //2019.7.16 用户-任务表状态返回
    User_Task getStatus (User_Task user_task);

    //2019.11.30 Accept the task and change the task status to 1
    void acceptUser_Task(User_Task user_task);
    //2019-11-30 For testing, if the data is not available, it needs to be added separately
    User_Task SelUser_Task (User_Task user_task);

    //2019.7.16  The user-task table is updated. Before, only userId and taskId were only used. Now it is updated,
    // that is, fill in the content and image behind.
    void update(User_Task user_task);

    ////2019.7.18 View the completion of the task, that is, see how many people are performing the task, as long as
    // the task is to be counted
    ////Input parameter taskId, return List<user_task> and return List<user>
    ////Query the corresponding user_task class by taskId
    ////Then query the corresponding user class according to the userId in the queried user_task class (mainly the
    // userName in the user)
    List<User_Task> SelTaskId(Integer taskId);
    List<User_Task> SelTaskIdForUT(Integer taskId);
    //2019.9.10  Input parameter utask, return a single picture.
    User_Task selcetUser_Task (User_Task utask);



    ////2019.7.2 修改：从单个的结果展示修改为多个结果展示

    //User SelInfo(String userName);
    List<User> SelInfo(String userName);

    //List<User> getName(String Name);


    ////2019.7.5 用户登录
    User Enter(User user);
    ////2019.7.5 防止用户重复名称注册
    User check(User user);


    //2019-11-1   According to the user ID transmitted by the front end, return all tasks that this user has received
    List<User_Task> seluserId (int userId);
    //2019-11-1   According to the ID of the user transmitted by the front-end, return that the user has received
    // and completed all tasks
    List<User_Task> seluserIdFinish (int userId);
    // 2019-11-3   According to the user ID transmitted by the front-end, return all tasks that the user has received
    // but not completed
    List<User_Task> seluserIdUnfinish (int userId);

    // 2019-11-1   According to the user ID transmitted by the front-end, the unexecuted tasks that the user is most
    // suitable to accept according to the strategy of the recommendation system are returned (the current nature is
    // random return)
    List<User_Task> seluserIdRandom (int userId);


    // 2019-11-3   According to the user ID transmitted by the front end, return the taskId of all tasks that this
    // user has received
    List<Integer>   seluserIdForTaskId (int userId);




    void add(User user);



    
    
    User checkUser(User user);
    
    //void del(int id);
    
    void del(int id);
    
    
    //void add_BeanTask(Bean_Task bean);
}
