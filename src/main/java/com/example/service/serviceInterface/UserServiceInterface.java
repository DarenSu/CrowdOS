package com.example.service.serviceInterface;

import com.example.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServiceInterface {


    User Sel(int id);
    ////2019.7.2
    public List<User> SelInfo(String userName );


    ////2019.7.5
    public User EnterUser(User user );
    ////2019.7.5
    public User check(User user );

    ////2019.9.16
    public List<User> getUserRank();

    ///2019.9.25
    public User checkLogin(User user );


    void addUser(User user);

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
    User checkUser(User user);


    //public void delUser(Integer userId) {
    //userMapper.del(userId);
    //}

//	public void DelUser(int id) {
//		// TODO Auto-generated method stub
//		userMapper.del(id);
//
//	}



    //public void delUser(int id) {
    //	userMapper.del(id);
    //}
}
