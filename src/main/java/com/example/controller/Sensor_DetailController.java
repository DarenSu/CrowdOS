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
Sensor File Upload
 - The mode used is that each sensor uploads a folder, and the folder row represents a record, which has two attributes: time/value
 */


@RestController      //To indicate the module, here is the control module
@RequestMapping("/sensordetail")
public class Sensor_DetailController {

    //  2021.05.14   log record
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Sensor_DetailController.class);
    // Public file storage directory
    @Autowired
    private ExecutorConfig executorConfig;
    // File upload thread pool
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



    /// 2021.05.14 Upload sensor data and read out the file line by line. Each line of data here has two attributes,
    // separated by "/", date of collection/perception data 1-perception data 2
    /// Currently there is no realization of uploading multiple pictures, but the realization of uploading a single picture
    /// Successfully run
    @RequestMapping(value = "/uploadSensorFileMessageDetail", method = RequestMethod.POST)
    @ResponseBody
    // Sensor_Detail           File type parameters (can be files, videos, pictures)
    public ResponseEntity<Sensor_Detail> add_Sensor_File_Bytes(@RequestPart("sensor_detail") Sensor_Detail sensor_detail, @RequestPart("file") MultipartFile file) throws IOException {

        System.out.println("欢迎来到传感器数据到逐条上传到表里：sensordetial/uploadSensorFileMessageDetail");

        // First check whether the data exists in the table, that is, whether the userId and taskId of this data are
        // really related
        // Need to query in the ut table
//        User_Task user_task= new User_Task();
//        user_task.setUserId(sensor_detail.getUserId());
//        user_task.setTaskId(sensor_detail.getTaskId());
//        User_Task user_taskresult = user_taskService.SelUser_Task(user_task);
//        if( user_taskresult == null ){//请求成功了，但是没有资源，也就是这个userId和taskId没有关联  204
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }

        //  Set the task ID of this type of sensor sensing data that is not related to the person to -1
        sensor_detail.setTaskId(-1);
        // 20210513 Data judgement to prevent database crash caused by meaningless access
        if(sensor_detail.getSensor_detailId() != null || sensor_detail.getUserId() == null ||
                sensor_detail.getTaskId() == null || file.isEmpty()){//数据有问题，返回400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("测试到了这里了！！！");


//        System.out.println("sfdaeg");
        System.out.println(sensor_detail);

        String fileName = file.getOriginalFilename();
        sensor_detail.setFileName(fileName);
        System.out.println(fileName);

        // 20210514  Read the size of the uploaded file, cannot exceed the maximum supported by SSM  ,unit:B
        long fileLength = 0L;
        fileLength = file.getSize()/1024;
        System.out.println(fileLength+"KB"+"    "+"单张照片小于20480KB，可以传输");
        if (fileLength > 20480){// The data format is incorrect, that is, the data has a problem，400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // 20210514    Time fetching for the DATE data type
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(date);
        sensor_detail.setOnlineTime(date);

        // 20210514  This section converts file resources directly into data streams
        String fileByte= String.valueOf(file.getBytes());

//        System.out.println("转换成功的二进制文件类型："+ getType(file.getBytes()));
        System.out.println("转换成功的二进制文件初始类型数据："+ file.getBytes());

        System.out.println("转换成功的二进制文件："+ fileByte);

        // 20210514 Stores the converted binary data into a data structure
//        sensor_byte.setFileByte(fileByte);

        // 20210515  Here the file is read line by line and the data is stored in the database
//        File fileCopy= (File) file;

//        InputStream inputStream = file.getInputStream();
//        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().forEach(this::handleLine);
        // Gets the largest primary key ID in the current table
        int MaxId = sensor_detailService.getMaxId().getSensor_detailId();

        System.out.println("MaxId="+MaxId);

        try{
            InputStream inputStream = file.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream((File) file), "UTF-8"));//构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));//构造一个BufferedReader类来读取文件

            // The file is read line by line
            int dataMaxNum= 2;
            String s = null;
            while((s = br.readLine())!=null){//Using the readLine method, read one line at a time
                Sensor_Detail sensor_detail1temp= sensor_detail;

                System.out.println("s="+s);

                StringBuilder tempp= new StringBuilder();
                int count = 0;
                for (int i= 0; i< s.length(); i++) {
                    StringBuilder stemp= new StringBuilder();
                    if(count >= dataMaxNum) {//Only four rows are read. Any more than this is a data error  ,400
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

                    // Keep adding data
                    tempp.append(new StringBuilder(s.charAt(i)+""));
                    if(count == dataMaxNum- 1 && i == s.length()- 1){
                        count++;
                        System.out.println("------count="+ count);
                        sensor_detail1temp.setSensorValue(new String(tempp));
                    }

//                    System.out.print(i +"-"+ s.charAt(i)+"  ");
                }
                //After reading each row of data, the data is stored in the database
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


//        sensor_byteService.addSensor_Byte(sensor_byte);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 20210517 View all perceptual data of the task according to taskId
    @RequestMapping(value = "/getAllMessageFromTaskId/{taskId}")
    @ResponseBody
    public ResponseEntity<List<Sensor_Detail>> GetAllMessageFromTaskId(@PathVariable Integer taskId){
        Sensor_Detail sensor_detail= new Sensor_Detail();
        sensor_detail.setTaskId(taskId);
        List<Sensor_Detail> result= sensor_detailService.selAllMessageFromTaskId(sensor_detail);
        if (result == null || result.size() == 0){// 404
            System.out.println("没有该数据");
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    // 20210517 According to the taskId and userId to view all the perceptual data of the execution of the task
    @RequestMapping(value = "getAllMessageFromUserIdTaskId")
    @ResponseBody
    public ResponseEntity<List<Sensor_Detail>> GetAllMessageFromTaskId(@RequestBody Sensor_Detail sensor_detail){
//        Sensor_Message sensor_message= new Sensor_Message();
//        sensor_message.setTaskId(taskId);
        List<Sensor_Detail> result= sensor_detailService.selAllMessageFromUserIdTaskId(sensor_detail);
        System.out.println("返回结果result="+result);
        if (result == null){// 404
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

}
