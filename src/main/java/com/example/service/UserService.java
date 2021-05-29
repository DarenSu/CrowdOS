package com.example.service;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * @Author:DarenSu
 * @Date: 2019/6/13
 * @Time: 14:42
 */

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;

	public User getUser(Integer id)
	{
		return userMapper.getUser(id);
	}
	public User Sel(int id) {
		return userMapper.Sel(id);
	}

	//2019.7.2 Modification: Modified from a single result display to multiple result displays
	public List<User> SelInfo(String userName ) {
		return userMapper.SelInfo(userName);
	}


	//2019.7.5 User login
	public User EnterUser(User user ) {
		return userMapper.Enter(user);
	}
	//2019.7.5 Prevent users from logging in with the same name twice and being able to log in with their real names
	public User check(User user ) {
		return userMapper.check(user);
	}

	//2019.9.16 Modification: Return the last ten data of database user information at one time
	public List<User> getUserRank() {
		return userMapper.getUserRank();
	}

	//2019.9.25 prevent the same user from signing up twice with a single name
	public User checkLogin(User user ) {
		return userMapper.checkLogin(user);
	}


	public void addUser(User user) {
		userMapper.add(user);
	}
	

	///1&0
	public User checkUser(User user) {
		return userMapper.checkUser(user);
	}



}
