package com.example.controller;

import com.example.entity.Version_Updating;
import com.example.service.TaskService;
import com.example.service.UserService;
import com.example.service.User_TaskService;
import com.example.service.Version_UpdatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;


/**
 * @Author:0xOO
 * @Date: 2018/9/26 0026
 * @Time: 14:42
 */

/**
 * @Author:DarenSu
 * @Date: 2019/6/13 修改
 * @Time: 14:42
 */

@RestController      //To indicate the module, here is the control module
@RequestMapping("/version_updating")
public class Version_UpdatingController {

    @Autowired
    private User_TaskService user_taskService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    @Autowired
    private Version_UpdatingService version_updatingService;

    private Object setuser1;


    ///2019.10.29  Determine whether an update is needed, enter the current version on the front end, and compare it
    // with the latest version of the server
    // If the version number is different, temp is 1, and update is performed. If the version number is the same,
    // temp is 0, and no update is required.
    @RequestMapping("checkVersion/{versionCode}")
    //public ResponseEntity<List<Version_Updating>> checkVersion(@PathVariable  Integer versionCode) {
    public ResponseEntity<String> checkVersion(@PathVariable Integer versionCode) {
        //return userService.SelInfo(userName).toString();
        System.out.println(versionCode);
        //System.out.println(userService.EnterUser(user));

        Version_Updating version_updating = version_updatingService.getLastOne();
        String image = version_updatingService.getLastOne().getApkName();
        System.out.println("当前最新的APK名字：");
        System.out.println(image);
        if (versionCode == version_updating.getVersionCode()) {
            System.out.println("你的版本就是最新版本，不需要更新！！！");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (versionCode > version_updating.getVersionCode()) {
            System.out.println("你的版本是错的版本号，请重新发送！！！");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }



        if (version_updatingService.checkForPresence(versionCode) != null) {
            System.out.println(versionCode);
            //return new ResponseEntity<List<Version_Updating>>(
            //version_updatingService.checkForPresence(versionCode),HttpStatus.OK);
            String tempName = version_updatingService.getLastOne().getApkName();
            return new ResponseEntity<>(tempName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    ///######~~~~~^_^~~~~~^_^~~~~~^_^~~~~~^_^~~~~~^_^~~~~~^_^~~~~~^_^#######
    ///2019.11.1  Return string type data for the first time, and in the future can also be
    // analogized to single types such as int, float, etc.
    ///If you need to update, return the versionName of the latest version
    @RequestMapping("turnName")
    public ResponseEntity<String> turnName() {
        String tempName = version_updatingService.getLastOne().getApkName();
        System.out.println(tempName);
        return new ResponseEntity<>(tempName, HttpStatus.OK);
    }

    ///2019.10.28  Enter the parameter image and choose to update the latest version
    @RequestMapping("downVersionFromServer/{image}")
    public ResponseEntity<byte[]> downVersionFromServer(@PathVariable String image) {
        //@RequestMapping("downVersionFromServer")
        //public ResponseEntity<byte[]> downVersionFromServer() {
//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;

        image = version_updatingService.getLastOne().getApkName();
        System.out.println(image);

        boolean status1 = image.contains("/");
        boolean status2 = image.contains("\\");
        if (status1 || status2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //2019.9.15
        InputStream in;
        ResponseEntity<byte[]> response = null;
        String filePath = "E" + File.separator + "springboot-upload" + File.separator + "version" + File.separator;
//		System.out.println(image);
        File file = new File(filePath + image);
        if (!file.getParentFile().exists()) {
            // No path found and return empty
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            in = new FileInputStream(file);
            byte[] b = new byte[in.available()];
            in.read(b);
            HttpHeaders headers = new HttpHeaders();
            String filename = new String(image.getBytes("gbk"), "iso8859-1");
            headers.add("Content-Disposition", "attachment;filename=" + filename);
            response = new ResponseEntity<byte[]>(b, headers, HttpStatus.OK);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return response;
    }

}
