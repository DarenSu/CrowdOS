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

@RestController      //进行模块的注明，此处为控制模块
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


    ///2019.10.29  判断是否需要进行更新,前端输入当前的版本，与服务器的最新版本进行比较
    // 版本号不同的话temp为1，进行更新，版本号相同的话temp为0，不需要费更新
    @RequestMapping("checkVersion/{versionCode}")
    //public ResponseEntity<List<Version_Updating>> checkVersion(@PathVariable  Integer versionCode) {
    public ResponseEntity<String> checkVersion(@PathVariable Integer versionCode) {
        //return userService.SelInfo(userName).toString();
        System.out.println(versionCode);
        //System.out.println(userService.EnterUser(user));
        ///String类型的image获得数据库中最新的版本的版本名字，及apkName
        Version_Updating version_updating = version_updatingService.getLastOne();
        String image = version_updatingService.getLastOne().getApkName();
        System.out.println("当前最新的APK名字：");
        System.out.println(image);
        if (versionCode == version_updating.getVersionCode()) {
            System.out.println("你的版本就是最新版本，不需要更新！！！");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);///给前端返回找不到，代表着以为最新版本，不需要更新
        }
        if (versionCode > version_updating.getVersionCode()) {
            System.out.println("你的版本是错的版本号，请重新发送！！！");
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);///发送的版本号大于服务器中存储的版本号，错误
        }



        if (version_updatingService.checkForPresence(versionCode) != null) {    ///不是最新版本，需要进行更新
            System.out.println(versionCode);
            //return new ResponseEntity<List<Version_Updating>>(
            //version_updatingService.checkForPresence(versionCode),HttpStatus.OK); ///给前端返回OK，代表着前端可以进行更新才做，
            // 也就是引用函数downVersionFromServer
            String tempName = version_updatingService.getLastOne().getApkName();
            return new ResponseEntity<>(tempName, HttpStatus.OK);
        } else {  ///是最新版本，不需要更新
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);///给前端返回找不到，代表着以为最新版本，不需要更新
        }

    }


    ///######~~~~~^_^~~~~~^_^~~~~~^_^~~~~~^_^~~~~~^_^~~~~~^_^~~~~~^_^#######
    ///2019.11.1  历史性的开创，第一次返回string类型的数据，以后也可以类推到int、float等等单个类型
    ///如果需要更新的话，返回最新的版本的versionName
    @RequestMapping("turnName")
    public ResponseEntity<String> turnName() {
        String tempName = version_updatingService.getLastOne().getApkName();
        System.out.println(tempName);
        return new ResponseEntity<>(tempName, HttpStatus.OK);
    }

    ///2019.10.28  输入参数image,选择更新最新版本
    @RequestMapping("downVersionFromServer/{image}")
    public ResponseEntity<byte[]> downVersionFromServer(@PathVariable String image) {
        //@RequestMapping("downVersionFromServer")
        //public ResponseEntity<byte[]> downVersionFromServer() {
//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;
        //2019.9.17   若是传输的参数中带有自字符  \或者/   的时候终止返回

        ////2019.11.1 一次性返回数据库最后面的一条数据
        image = version_updatingService.getLastOne().getApkName();
        System.out.println(image);

        boolean status1 = image.contains("/");
        boolean status2 = image.contains("\\");
        if (status1 || status2) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //2019.9.15   数据流转换
        InputStream in;
        ResponseEntity<byte[]> response = null;
        String filePath = "E" + File.separator + "springboot-upload" + File.separator + "version" + File.separator;
//		System.out.println(image);
        File file = new File(filePath + image);
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
