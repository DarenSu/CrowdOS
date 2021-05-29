package com.example.service.serviceImpl;

import com.example.entity.User;
import com.example.entity.User_Task;
import com.example.mapper.UserMapper;
import com.example.mapper.User_TaskMapper;
import com.example.service.serviceInterface.User_TaskServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class User_TaskServiceImpl implements User_TaskServiceInterface {
    @Autowired
    User_TaskMapper user_taskMapper;
    @Autowired
    UserMapper userMapper;


    //2019.7.11
    public User_Task Sel(int user_taskId) { return user_taskMapper.Sel(user_taskId); }

    //2017.7.13
    public void addUser_Task(User_Task user_task) {
        user_taskMapper.add(user_task);
    }

    public User_Task getStatus (User_Task user_task) {
        return user_taskMapper.getStatus(user_task);
    }

    ///2019.11.30
    public void acceptUser_Task (User_Task user_task){
        user_taskMapper.acceptUser_Task(user_task);
    }
    ///2019-11-30
    public User_Task SelUser_Task (User_Task user_task) {
        return user_taskMapper.SelUser_Task(user_task);
    }

    //2019.7.16
    public void updateUser_Task (User_Task user_task) {
        user_taskMapper.update(user_task);
    }

    ////2019.7.18
    public List<User_Task> SelTaskId (Integer taskId){
        return user_taskMapper.SelTaskId(taskId);
    }
    public List<User_Task> SelTaskIdForUT (Integer taskId){
        return user_taskMapper.SelTaskIdForUT(taskId);
    }

    ///2019.9.10
    public User_Task selcetUser_Task (User_Task utask){
        return user_taskMapper.selcetUser_Task(utask);
    }




    ////2019.7.2
    public List<User> SelInfo(String userName ) {
        return userMapper.SelInfo(userName);
    }


    ////2019.7.5
    public User EnterUser(User user ) {
        return userMapper.Enter(user);
    }
    ////2019.7.5
    public User check(User user ) {
        return userMapper.check(user);
    }

    ///2019-11-1
    public List<User_Task> seluserId (int userId){
        return user_taskMapper.seluserId(userId);
    }
    ///2019-11-1
    public List<User_Task> seluserIdFinish (int userId){
        return user_taskMapper.seluserIdFinish(userId);
    }

    ///2019-11-3
    public List<User_Task> seluserIdUnfinish (int userId){
        return user_taskMapper.seluserIdUnfinish(userId);
    }

    ///2019-11-1
    public List<User_Task> seluserIdRandom (int userId){
        return user_taskMapper.seluserIdRandom(userId);
    }


    ///2019-11-3
    public List<Integer> seluserIdForTaskId (int userId){
        return user_taskMapper.seluserIdForTaskId(userId);
    }









    public void addUser(User user) {
        userMapper.add(user);
    }

///          2016.6.18
//	public void addBean_Task(Bean_Task bean) {
//		userMapper.add_BeanTask(bean);
//	}
//
//
//	public Bean_Task Sel_BeanTask(String id) {
//		return userMapper.Sel_BeanTask(id);
//	}
//





    ///1&0
    public User checkUser(User user) {
        return userMapper.checkUser(user);
    }


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
