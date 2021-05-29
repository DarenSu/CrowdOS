package com.example.service;

import com.example.entity.Version_Updating;
import com.example.mapper.UserMapper;
import com.example.mapper.User_TaskMapper;
import com.example.mapper.Version_UpdatingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * @Author:DarenSu
 * @Date: 2019/6/13
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



	///2019.10.29  Determine whether an update is needed, enter the current version on the front end, and compare it
	// with the latest version of the server
	// If the version number is different, temp is 1, and update is performed. If the version number is the same,
	//  temp is 0, and no update is required.
	public List<Version_Updating> checkForPresence(Integer versionCode) {
		return version_updatingMapper.checkForPresence(versionCode);
	}

	//2019.11.1 Modification: Return the last piece of data in the database at one time
	public Version_Updating getLastOne() {
		return version_updatingMapper.getLastOne();
	}
}
