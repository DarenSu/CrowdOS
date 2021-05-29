package com.example.service.serviceInterface;

import com.example.entity.User;
import com.example.entity.User_Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface User_TaskServiceInterface {


    //2019.7.11
    public User_Task Sel(int user_taskId) ;

    //2017.7.13
    public void addUser_Task(User_Task user_task);

    public User_Task getStatus (User_Task user_task);

    ///2019.11.30
    public void acceptUser_Task (User_Task user_task);
    ///2019-11-30
    public User_Task SelUser_Task (User_Task user_task);

    //2019.7.16
    public void updateUser_Task (User_Task user_task);

    ////2019.7.18
    public List<User_Task> SelTaskId (Integer taskId);
    public List<User_Task> SelTaskIdForUT (Integer taskId);

    ///2019.9.10
    public User_Task selcetUser_Task (User_Task utask);




    ////2019.7.2
    public List<User> SelInfo(String userName );


    ////2019.7.5
    public User EnterUser(User user );
    ////2019.7.5
    public User check(User user );

    ///2019-11-1
    public List<User_Task> seluserId (int userId);
    ///2019-11-1
    public List<User_Task> seluserIdFinish (int userId);

    ///2019-11-3
    public List<User_Task> seluserIdUnfinish (int userId);

    ///2019-11-1
    public List<User_Task> seluserIdRandom (int userId);


    ///2019-11-3
    public List<Integer> seluserIdForTaskId (int userId);









    public void addUser(User user);

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
