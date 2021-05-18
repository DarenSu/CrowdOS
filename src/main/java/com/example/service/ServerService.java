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



	//2021 04 21 将设备信息存储到数据库中
	public  void addServer(Server server){serverMapper.addServer(server); }

	//2021 04 21  获取最新的一条数据信息
	public Server getServer(){ return serverMapper.getServer(); }
	public List<Server> getAllServer(){ return serverMapper.getAllServer(); }
	
	


}
