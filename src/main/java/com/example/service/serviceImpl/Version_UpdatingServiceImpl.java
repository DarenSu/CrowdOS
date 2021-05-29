package com.example.service.serviceImpl;

import com.example.entity.Version_Updating;
import com.example.mapper.UserMapper;
import com.example.mapper.User_TaskMapper;
import com.example.mapper.Version_UpdatingMapper;
import com.example.service.serviceInterface.Version_UpdatingServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class Version_UpdatingServiceImpl implements Version_UpdatingServiceInterface {
    @Autowired
    User_TaskMapper user_taskMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    Version_UpdatingMapper version_updatingMapper;



    ///2019.10.29
    public List<Version_Updating> checkForPresence(Integer versionCode) {
        return version_updatingMapper.checkForPresence(versionCode);
    }

    ////2019.11.1
    public Version_Updating getLastOne() {
        return version_updatingMapper.getLastOne();
    }

}
