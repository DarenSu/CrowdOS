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
public class HumanService {
	@Autowired
	UserMapper userMapper;
	/*
	Functions to be added
	 */


	public User Sel1(int id) {
		return userMapper.Sel(id);
	}
	public User Sel2(int id) {
		return userMapper.Sel(id);
	}
	public User Sel3(int id) {
		return userMapper.Sel(id);
	}





	public User Sel(int id) {
		return userMapper.Sel(id);
	}

	////2019.7.2 Modification: Modified from a single result display to multiple result displays
	public List<User> SelInfo(String userName ) {
		return userMapper.SelInfo(userName);
	}



}
