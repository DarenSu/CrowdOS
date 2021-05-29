package com.example.mapper;

import com.example.entity.User;
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
public interface UserMapper {

    User Sel(int id);


    User getUser(int id);


    //User SelInfo(String userName);
    //2019.7.2 Modification: Modified from a single result display to multiple result displays
    List<User> SelInfo(String userName);

    //List<User> getName(String Name);


    //2019.7.5 User login
    User Enter(User user);
    //2019.7.5 Prevent users from logging in with the same name twice and being able to log in with their real names
    User check(User user);
    //2019.9.16 Modification: Return the last ten data of database user information at one time
    List<User> getUserRank() ;

    //2019.9.25 prevent the same user from signing up twice with a single name
    User checkLogin(User user);

    void add(User user);
    

    
    
    User checkUser(User user);
    
    //void del(int id);
    
    //void del(Integer userId);
    
    
    //void add_BeanTask(Bean_Task bean);
}
