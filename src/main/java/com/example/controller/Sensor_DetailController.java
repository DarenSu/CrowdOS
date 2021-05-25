package com.example.controller;


import com.example.entity.Sensor_Detail;
import com.example.service.*;
import com.example.service.serviceInterface.AsyncService;
import com.example.util.ExecutorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import com.***.common.model.Result;
//import com.***.common.upload.file.ChunkInfoModel;
//import com.***.common.upload.file.UploadFileCallback;
//import com.***.common.upload.file.UploadFileConfig;

/*
传感器文件上传
 - 采用的模式是每个传感上传一个文件夹，文件夹一行代表着一条记录，里面有两个属性：时间/数值
 - 并且使用的模式是前端定时向后台传送文件，并非是任务的要求才传送该数据的，也就是这个和FS里面的传感器文件传输是不一样的
 - 并且使用的参数有两个，分别是Sensor_Detail和file，其中Sensor_Detail里面只有userId非空即可
 - 经测试发现，一小时之内，每个传感器产生数据的量大概在800条左右
 - 经测试发现，一小时之内，每个传感器产生的数据大小在35 - 65kb
 */


@RestController      //进行模块的注明，此处为控制模块
@RequestMapping("/sensordetail")
public class Sensor_DetailController {

    //  2021.05.14          日志记录
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Sensor_DetailController.class);
    // 公共文件存储目录
    @Autowired
    private ExecutorConfig executorConfig;
    // 文件上传线程池
    @Autowired
    private AsyncService asyncService;

    @Autowired
    private Familiar_SensorService familiar_sensorService;
    @Autowired
    Sensor_ByteService sensor_byteService;
    @Autowired
    Sensor_MessageService sensor_messageService;
    @Autowired
    User_TaskService user_taskService;
    @Autowired
    Sensor_DetailService sensor_detailService;



    /// 2021.05.14 上传传感器数据，并将其文件逐行读出来，这里的每行数据有两个属性，用“/”隔开，采集日期/感知数据1-感知数据2
    /// 目前没有实现多张图片上传，实现的是单图片的上传
    /// 已成功运行
    @RequestMapping(value = "/uploadSensorFileMessageDetail", method = RequestMethod.POST)
    @ResponseBody
    // User类           文件类型的参数（可以是文件、视频、图片均可）
    public ResponseEntity<Sensor_Detail> add_Sensor_File_Bytes(@RequestPart("sensor_detail") Sensor_Detail sensor_detail, @RequestPart("file") MultipartFile file) throws IOException {

        System.out.println("欢迎来到传感器数据到逐条上传到表里：sensordetial/uploadSensorFileMessageDetail");

        // 首先检验该条数据是否在存在在表中，也就是这条数据的userId和taskId是否真的有关联
        // 需要在ut表中进行查询
        // 不需要计算了，因为这个函数的功能实现的是传感器数据的定时上传，和任务是没有关系的
//        User_Task user_task= new User_Task();
//        user_task.setUserId(sensor_detail.getUserId());
//        user_task.setTaskId(sensor_detail.getTaskId());
//        User_Task user_taskresult = user_taskService.SelUser_Task(user_task);
//        if( user_taskresult == null ){//请求成功了，但是没有资源，也就是这个userId和taskId没有关联  204
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        //  设定这类和人物没有关系的传感器感知数据的任务的id为-1
        sensor_detail.setTaskId(-1);
        // 20210513 数据判断，防止无意义的访问导致数据库崩溃
        if(sensor_detail.getSensor_detailId() != null || sensor_detail.getUserId() == null ||
                sensor_detail.getTaskId() == null || file.isEmpty()){//数据有问题，返回400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("测试到了这里了！！！");
        //查看文件是否有重复
        // 获取文件名

//        System.out.println("sfdaeg");
        System.out.println(sensor_detail);

        String fileName = file.getOriginalFilename();
        sensor_detail.setFileName(fileName);
        System.out.println(fileName);

        // 20210514  读取上传的文件的大小，不能超过SSM所支持的最大值,单位为B
        // 20210514  前端那边设置最多可以传输十个文件
        long fileLength = 0L;
        fileLength = file.getSize()/1024;
        System.out.println(fileLength+"KB"+"    "+"单张照片小于20480KB，可以传输");
        if (fileLength > 20480){// 数据格式不对，即数据有问题，返回400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 20210514    Date数据类型的时间获取
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(date);
        sensor_detail.setOnlineTime(date);

        // 20210514  这部分可以将文件资源直接转换成数据流
        String fileByte= String.valueOf(file.getBytes());

//        System.out.println("转换成功的二进制文件类型："+ getType(file.getBytes()));
        System.out.println("转换成功的二进制文件初始类型数据："+ file.getBytes());

        System.out.println("转换成功的二进制文件："+ fileByte);

        // 20210514 将转换成二进制的数据存储到数据结构中
//        sensor_byte.setFileByte(fileByte);
        // 20210514 存储到数据库中   还没有写，需要写

        // 20210515  此处进行间将文件逐行进行读取，并将其中的数据存入数据库中
//        File fileCopy= (File) file;

//        InputStream inputStream = file.getInputStream();
//        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().forEach(this::handleLine);
        // 获取当前表中最大的主键id，一边后面递增使用
        int MaxId = sensor_detailService.getMaxId().getSensor_detailId();

        System.out.println("MaxId="+MaxId);

        try{
            InputStream inputStream = file.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream((File) file), "UTF-8"));//构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));//构造一个BufferedReader类来读取文件

            // 文件进行逐行读取的操作
            int dataMaxNum= 2;
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                Sensor_Detail sensor_detail1temp= sensor_detail;

                System.out.println("s="+s);

                StringBuilder tempp= new StringBuilder();
                int count = 0;
                for (int i= 0; i< s.length(); i++) {
                    StringBuilder stemp= new StringBuilder();
                    if(count >= dataMaxNum) {//只读取四个数据,每行的数据超过了这个量就是数据错误，返回400
                        System.out.println("-1");
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                    if(s.charAt(i) == '/' ){
                        count++;
                        System.out.println("count="+count);
                        if (count == 1) sensor_detail1temp.setAcquisitionTime(new String(tempp));
//                        if (count == 2) sensor_bytetemp.setLatitude(new String(tempp));
//                        if (count == 3) sensor_bytetemp.setAccelerated(new String(tempp));
//                        if (count == 4) sensor_bytetemp.setDirection(new String(tempp));
//                        if (count == 5) sensor_bytetemp.setTemperature(new String(tempp));
//
//                        if (count == 6) sensor_bytetemp.setHumidity(new String(tempp));
//                        if (count == 7) sensor_bytetemp.setGravity(new String(tempp));
//                        if (count == 8) sensor_bytetemp.setAirPressure(new String(tempp));
//                        if (count == 9) sensor_bytetemp.setLight(new String(tempp));
//                        if (count == 10) sensor_bytetemp.setDistance(new String(tempp));
//
//
//                        if (count == 12) sensor_bytetemp.setMagnetic(new String(tempp));
//                        if (count == 13) sensor_bytetemp.setGyroscope(new String(tempp));
//                        if (count == 14) sensor_bytetemp.setVoice(new String(tempp));
//                        if (count == 15) sensor_bytetemp.setPicture(new String(tempp));
//                        if (count == 16) sensor_bytetemp.setFingerprint(new String(tempp));
//
//                        if (count == 17) sensor_bytetemp.setHeartRate(new String(tempp));
//                        if (count == 18) sensor_bytetemp.setBloodOxygen(new String(tempp));
//                        if (count == 19) sensor_bytetemp.setUltravioletRay(new String(tempp));
//                        if (count == 20) sensor_bytetemp.setStepNumber(new String(tempp));
//                        if (count == 21) sensor_bytetemp.setStepCount(new String(tempp));
//
//                        if (count == 22) sensor_bytetemp.setTemp1(new String(tempp));
//                        if (count == 23) sensor_bytetemp.setTemp2(new String(tempp));
//                        if (count == 24) sensor_bytetemp.setTemp3(new String(tempp));
//                        if (count == 25) sensor_bytetemp.setSensorFilePath(new String(tempp));
                        tempp= stemp;
                        continue;
                    }

                    // 不断添加数据
                    tempp.append(new StringBuilder(s.charAt(i)+""));
                    if(count == dataMaxNum- 1 && i == s.length()- 1){
                        count++;
                        System.out.println("------count="+ count);
                        sensor_detail1temp.setSensorValue(new String(tempp));
                    }

//                    System.out.print(i +"-"+ s.charAt(i)+"  ");
                }
                //读完每行数据后要将此数据存入数据库中
                MaxId++;
                sensor_detail1temp.setSensor_detailId(MaxId);

                sensor_detailService.addSensor_Byte(sensor_detail1temp);
//                System.out.println();
//                result.append( System.lineSeparator() + s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
//            log.error(e.getMessage(), e);
        }

        // 20210514 同时将任务的具体内容也上传，此时的utask里面已经有了图片的位置信息
//        sensor_byteService.addSensor_Byte(sensor_byte);

        // 20210514  是否需要查看下该条数据是否存在？？？？？？？？

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 20210517 根据taskId查看该任务的所有感知数据
    @RequestMapping(value = "/getAllMessageFromTaskId/{taskId}")
    @ResponseBody
    public ResponseEntity<List<Sensor_Detail>> GetAllMessageFromTaskId(@PathVariable Integer taskId){
        Sensor_Detail sensor_detail= new Sensor_Detail();
        sensor_detail.setTaskId(taskId);
        List<Sensor_Detail> result= sensor_detailService.selAllMessageFromTaskId(sensor_detail);
        if (result == null || result.size() == 0){// 无资源没有找到，返回404
            System.out.println("没有该数据");
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    // 20210517 根据taskId和userId查看该执行这在该任务的所有感知数据
    @RequestMapping(value = "getAllMessageFromUserIdTaskId")
    @ResponseBody
    public ResponseEntity<List<Sensor_Detail>> GetAllMessageFromTaskId(@RequestBody Sensor_Detail sensor_detail){
//        Sensor_Message sensor_message= new Sensor_Message();
//        sensor_message.setTaskId(taskId);
        List<Sensor_Detail> result= sensor_detailService.selAllMessageFromUserIdTaskId(sensor_detail);
        System.out.println("返回结果result="+result);
        if (result == null){// 无资源没有找到，返回404
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
