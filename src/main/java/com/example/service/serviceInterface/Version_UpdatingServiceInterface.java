package com.example.service.serviceInterface;

import com.example.entity.Version_Updating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Version_UpdatingServiceInterface {


    ///2019.10.29
    public List<Version_Updating> checkForPresence(Integer versionCode) ;

    ////2019.11.1
    public Version_Updating getLastOne();
}
