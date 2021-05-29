package com.example.service.serviceImpl;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.serviceInterface.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserServiceInterface {
    @Autowired
    UserMapper userMapper;

    public User Sel(int id) {
        return userMapper.Sel(id);
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

    ////2019.9.16
    public List<User> getUserRank() {
        return userMapper.getUserRank();
    }

    ///2019.9.25
    public User checkLogin(User user ) {
        return userMapper.checkLogin(user);
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
