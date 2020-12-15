package com.example.service.serviceInterface;

import com.example.entity.Version_Updating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Version_UpdatingServiceInterface {


    ///2019.10.29  判断是否需要进行更新,前端输入当前的版本，与服务器的最新版本进行比较
    // 版本号不同的话temp为1，进行更新，版本号相同的话temp为0，不需要费更新
    public List<Version_Updating> checkForPresence(Integer versionCode) ;

    ////2019.11.1 修改：一次性返回数据库最后面的一条数据
    public Version_Updating getLastOne();
}
