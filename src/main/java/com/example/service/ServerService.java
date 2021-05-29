package com.example.service;

import com.example.entity.Server;
import com.example.mapper.ServerMapper;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:23
 */

/**
 * @Author:DarenSu
 * @Date: 2019/6/13 修改
 * @Time: 14:42
 */

@Service
public class ServerService {
	@Autowired
    UserMapper userMapper;
	@Autowired
    ServerMapper serverMapper;



	//2021 04 21 Store device information in the database
	public  void addServer(Server server){serverMapper.addServer(server); }

	////2021 04 21  Get the latest piece of data
	public Server getServer(){ return serverMapper.getServer(); }
	public List<Server> getAllServer(){ return serverMapper.getAllServer(); }
	
	


}
