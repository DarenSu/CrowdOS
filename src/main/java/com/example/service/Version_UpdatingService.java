package com.example.service;

import com.example.entity.Version_Updating;
import com.example.mapper.UserMapper;
import com.example.mapper.User_TaskMapper;
import com.example.mapper.Version_UpdatingMapper;
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
public class Version_UpdatingService {
	@Autowired
    User_TaskMapper user_taskMapper;
	@Autowired
    UserMapper userMapper;
	@Autowired
	Version_UpdatingMapper version_updatingMapper;



	///2019.10.29  判断是否需要进行更新,前端输入当前的版本，与服务器的最新版本进行比较
	// 版本号不同的话temp为1，进行更新，版本号相同的话temp为0，不需要费更新
	public List<Version_Updating> checkForPresence(Integer versionCode) {
		return version_updatingMapper.checkForPresence(versionCode);
	}

	////2019.11.1 修改：一次性返回数据库最后面的一条数据
	public Version_Updating getLastOne() {
		return version_updatingMapper.getLastOne();
	}
}
