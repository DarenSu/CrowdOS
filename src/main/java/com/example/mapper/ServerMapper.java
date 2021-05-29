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



    //2021 04 21 Store device information in the database
    void addServer(Server service);

    //2021 04 21  Get the latest piece of data
    Server getServer();
    List<Server> getAllServer();


}
