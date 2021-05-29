package com.example.mapper;

import com.example.entity.Version_Updating;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 15:20
 */

/**
 * @Author:DarenSu
 * @Date: 2019/6/13 修改
 * @Time: 14:42
 */

@Repository
public interface Version_UpdatingMapper {

    ///2019.10.29  Determine whether an update is needed, enter the current version on the front end, and compare it
    // with the latest version of the server
    // If the version number is different, temp is 1, and update is performed. If the version number is the same,
    //  temp is 0, and no update is required.
    List<Version_Updating> checkForPresence(Integer versionCode);

    //2019.11.1 Modification: Return the last piece of data in the database at one time
    Version_Updating getLastOne() ;
}
