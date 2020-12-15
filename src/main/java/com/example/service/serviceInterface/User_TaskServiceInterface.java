package com.example.service.serviceInterface;

import com.example.entity.User;
import com.example.entity.User_Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface User_TaskServiceInterface {


    //2019.7.11 用户-任务表查询功能
    public User_Task Sel(int user_taskId) ;

    //2017.7.13 任务数据上传至用户-任务表
    public void addUser_Task(User_Task user_task);

    public User_Task getStatus (User_Task user_task);

    ///2019.11.30 接受任务，将任务状态变为1
    public void acceptUser_Task (User_Task user_task);
    ///2019-11-30 进行检测，若是该数据没有的话，则需要另行添加
    public User_Task SelUser_Task (User_Task user_task);

    //2019.7.16  用户-任务表更新，之前只有userId和taskId,现在是更新，也就是填充后面的content和image
    public void updateUser_Task (User_Task user_task);

    ////2019.7.18 查看task的完成情况，即看有多少人正在执行该任务，只要是接受任务的都要统计
    ////输入参数taskId，返回List<user_task>和返回List<user>
    ////通过taskId查询出对应的多有的user_task类
    ////然后根据查询到的user_task类里的userId查询出其对应的user类（主要是要user里的userName）
    public List<User_Task> SelTaskId (Integer taskId);
    public List<User_Task> SelTaskIdForUT (Integer taskId);

    ///2019.9.10  输入参数utask,返回单个图片，
    public User_Task selcetUser_Task (User_Task utask);




    ////2019.7.2 修改：从单个的结果展示修改为多个结果展示
    public List<User> SelInfo(String userName );


    ////2019.7.5 用户登录
    public User EnterUser(User user );
    ////2019.7.5 防止用户重复名称注册
    public User check(User user );

    ///2019-11-1   根据前端传输的用户的ID，返回此用户已经接收的所有任务
    public List<User_Task> seluserId (int userId);
    ///2019-11-1   根据前端传输的用户的ID，返回此用户已经接收并已经完成所有任务
    public List<User_Task> seluserIdFinish (int userId);

    ///2019-11-3   根据前端传输的用户的ID，返回此用户已经接收的但是未完成完成的所有任务
    public List<User_Task> seluserIdUnfinish (int userId);

    ///2019-11-1   根据前端传输的用户的ID，根据推荐系统的策略返回此用户最合适接受的未执行的任务（目前的本质是随机返回）
    public List<User_Task> seluserIdRandom (int userId);


    ///2019-11-3   根据前端传输的用户的ID，返回此用户已经接收的所有任务的taskId
    public List<Integer> seluserIdForTaskId (int userId);









    public void addUser(User user);

///Bean_Task数据库中数据的添加和查询         2016.6.18
//	public void addBean_Task(Bean_Task bean) {
//		userMapper.add_BeanTask(bean);
//	}
//
//
//	public Bean_Task Sel_BeanTask(String id) {
//		return userMapper.Sel_BeanTask(id);
//	}
//





    ///查询1&0
    public User checkUser(User user);


//	public void delUser(int id) {
//		// TODO Auto-generated method stub
//		//userMapper.del(id);
//
//	}
//
//	public void DelUser(int id) {
//		// TODO Auto-generated method stub
//		userMapper.del(id);
//
//	}



    //public void delUser(int id) {
    //	userMapper.del(id);
    //}
}
