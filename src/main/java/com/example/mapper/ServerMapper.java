package com.example.mapper;

import com.example.entity.Server;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author:DarenSu
 * @Date: 2021/04/21
 * @Time: 14:42
 */

@Repository
public interface ServerMapper {



    //2021 04 21 将设备信息存储到数据库中
    void addServer(Server service);

    //2021 04 21  获取最新的一条数据信息
    Server getServer();
    List<Server> getAllServer();


}
