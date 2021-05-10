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
    ////2019.7.2 修改：从单个的结果展示修改为多个结果展示

    //User SelInfo(String userName);
    List<User> SelInfo(String userName);

    //List<User> getName(String Name);


    ////2019.7.5 用户登录
    User Enter(User user);
    ////2019.7.5 防止用户重复名称注册
    User check(User user);
    ////2019.9.16 修改：一次性返回数据库用户信息最后面的十条数据
    List<User> getUserRank() ;

    ///2019.9.25 防止用户重复注册
    User checkLogin(User user);

    void add(User user);
    

    
    
    User checkUser(User user);
    
    //void del(int id);
    
    //void del(Integer userId);
    
    
    //void add_BeanTask(Bean_Task bean);
}
