package com.example.controller;


import com.example.entity.Familiar_Sensor;
import com.example.entity.Sensor_Message;
import com.example.entity.User_Task;
import com.example.service.Familiar_SensorService;
import com.example.service.Sensor_ByteService;
import com.example.service.Sensor_MessageService;
import com.example.service.User_TaskService;
import com.example.service.serviceInterface.AsyncService;
import com.example.util.ExecutorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import com.***.common.model.Result;
//import com.***.common.upload.file.ChunkInfoModel;
//import com.***.common.upload.file.UploadFileCallback;
//import com.***.common.upload.file.UploadFileConfig;

@RestController      //进行模块的注明，此处为控制模块
@RequestMapping("/sensormessage")
public class Sensor_MessageController {

    //  2021.05.14          日志记录
    private static final Logger logger = (Logger) LoggerFactory.getLogger(Sensor_MessageController.class);
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

    ///2019.7.25 上传任务的文字信息和图片，两个参数，user_task文字信息+单张图片
    ///目前没有实现多张图片上传，实现的是单图片的上传
    @RequestMapping(value = "/uploadSensorFiles", method = RequestMethod.POST)
    @ResponseBody
    // User类           文件类型的参数（可以是文件、视频、图片均可）
    public ResponseEntity<Familiar_Sensor> add_Sensor_File(@RequestPart("familiar_sensor") Familiar_Sensor familiar_sensor, @RequestPart("file") MultipartFile file) throws IOException {
        //查看文件是否有重复
        // 获取文件名
        System.out.println("欢迎来到传感器数据上传：sensor/uploadSensorFiles");
        System.out.println("sfdaeg");
        System.out.println(familiar_sensor);

        String fileName = file.getOriginalFilename();
        System.out.println(fileName);

        // 20210514  这部分可以将文件资源直接转换成数据流
        String fileByte= String.valueOf(file.getBytes());
        // 将这些数据存储到数据库中


        //2019.9.17  读取上传的文件的大小，不能超过SSM所支持的最大值,单位为B
        //2019.9.17  前端那边设置最多可以传输十个文件
        long fileLength = 0L;
        fileLength = file.getSize()/1024;
        System.out.println(fileLength+"KB"+"    "+"单张照片小于20480KB，可以传输");
        if (fileLength > 20480){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 文件上传后的路径
        //String filePath = "F:" + File.separator + "springboot-upload" + File.separator + "image" + File.separator ;
        //之前的代码形式，不过这样子访问的是C盘里面的内容
        //String filePath =  "/E/springboot-upload/image/";
        //20200912  自己改的


        String filePath =  "root:/F/springboot-upload/sensorFiles/";
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File pfile = new File(filePath);
        if (!pfile.exists()) {
            //如果不存在的话则自己生成路径
            pfile.mkdirs();
        }

        File dest = new File(pfile.getAbsoluteFile() + fileName);
        System.out.println("The upload file's absolute path: " + pfile.getAbsoluteFile() + fileName);
        System.out.println("The dest file's absolute path: " + dest.getAbsolutePath());
        //File dest = new File(filePath + fileName);
        //System.out.println("upload file's absolute path is"  + dest.getAbsolutePath());
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //如果不存在的话则自己生成路径
            dest.getParentFile().mkdirs();
        }

        System.out.println("目录已创建aaaaaaaaaaaa");
        try {
            System.out.println("正在上传中1bbbbbbbbbbbbbbbbbb");
            //if(!dest.exists()) {

                file.transferTo(dest);
            //}
            System.out.println("正在上传中2ccccccccccccccccccccccc");
            //上传图片的地址到数据库的相关位置
            familiar_sensor.setSensorFile(filePath + fileName);
            //同时将任务的具体内容也上传，此时的utask里面已经有了图片的位置信息
            familiar_sensorService.addFamiliar_Sensor(familiar_sensor);
            //将这个结果返回，并且返回状态为OK
            return new ResponseEntity<>(familiar_sensor, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //  用户下载传感数据的步骤
    //  1--前端先传一个familiar_sensor，里面有userId和taskId，然后靠这些取得该用户需要下载的文件的sensorFile
    //  2--然后再用另一个函数，以sensorFile为参数传递，进行下载
    @RequestMapping(value = "selFamiliar_Sensor", method = RequestMethod.POST)
    public ResponseEntity<List<Familiar_Sensor>> selFamiliar_Sensor(/*@RequestBody*/ Familiar_Sensor familiar_sensor){
        return new ResponseEntity<List<Familiar_Sensor>>(familiar_sensorService.selFamiliar_Sensor(familiar_sensor),HttpStatus.OK);
    }


    /// 2021.05.14 上传传感器数据，并将其文件逐行读出来，这里的每行数据有24个属性，用“/”隔开，传感器数据数据1/传感器数据2/.../采集日期
    /// 目前没有实现多张图片上传，实现的是单图片的上传
    /// 已成功运行
    @RequestMapping(value = "/uploadSensorFileMessages", method = RequestMethod.POST)
    @ResponseBody
    // User类           文件类型的参数（可以是文件、视频、图片均可）
    public ResponseEntity<Sensor_Message> add_Sensor_File_Bytes(@RequestPart("sensor_message") Sensor_Message sensor_message, @RequestPart("file") MultipartFile file) throws IOException {



        System.out.println("欢迎来到传感器数据到逐条上传到表里：sensor/uploadSensorFileBytes");

        // 首先检验该条数据是否在存在在表中，也就是这条数据的userId和taskId是否真的有关联
        // 需要在ut表中进行查询
        User_Task user_task= new User_Task();
        user_task.setUserId(sensor_message.getUserId());
        user_task.setTaskId(sensor_message.getTaskId());
        User_Task user_taskresult = user_taskService.SelUser_Task(user_task);
        if( user_taskresult == null ){//请求成功了，但是没有资源，也就是这个userId和taskId没有关联
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        // 20210513 数据判断，防止无意义的访问导致数据库崩溃
        if(sensor_message.getSensor_messageId() != null || sensor_message.getUserId() == null ||
                sensor_message.getTaskId() == null || file.isEmpty()){//数据有问题，返回400
            return new ResponseEntity<Sensor_Message>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("测试到了这里了！！！");
        //查看文件是否有重复
        // 获取文件名

//        System.out.println("sfdaeg");
        System.out.println(sensor_message);

        String fileName = file.getOriginalFilename();
        sensor_message.setFileName(fileName);
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
        sensor_message.setOnlineTime(date);

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
        int MaxId = sensor_messageService.getMaxId().getSensor_messageId();

        System.out.println("MacId="+MaxId);

        try{
            InputStream inputStream = file.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream((File) file), "UTF-8"));//构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));//构造一个BufferedReader类来读取文件

            // 文件进行逐行读取的操作
            int dataMaxNum= 24;
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                Sensor_Message sensor_bytetemp= sensor_message;

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
                        if (count == 1) sensor_bytetemp.setLongitude(new String(tempp));
                        if (count == 2) sensor_bytetemp.setLatitude(new String(tempp));
                        if (count == 3) sensor_bytetemp.setAccelerated(new String(tempp));
                        if (count == 4) sensor_bytetemp.setDirection(new String(tempp));
                        if (count == 5) sensor_bytetemp.setTemperature(new String(tempp));

                        if (count == 6) sensor_bytetemp.setHumidity(new String(tempp));
                        if (count == 7) sensor_bytetemp.setGravity(new String(tempp));
                        if (count == 8) sensor_bytetemp.setAirPressure(new String(tempp));
                        if (count == 9) sensor_bytetemp.setLight(new String(tempp));
                        if (count == 10) sensor_bytetemp.setDistance(new String(tempp));


                        if (count == 12) sensor_bytetemp.setMagnetic(new String(tempp));
                        if (count == 13) sensor_bytetemp.setGyroscope(new String(tempp));
                        if (count == 14) sensor_bytetemp.setVoice(new String(tempp));
                        if (count == 15) sensor_bytetemp.setPicture(new String(tempp));
                        if (count == 16) sensor_bytetemp.setFingerprint(new String(tempp));

                        if (count == 17) sensor_bytetemp.setHeartRate(new String(tempp));
                        if (count == 18) sensor_bytetemp.setBloodOxygen(new String(tempp));
                        if (count == 19) sensor_bytetemp.setUltravioletRay(new String(tempp));
                        if (count == 20) sensor_bytetemp.setStepNumber(new String(tempp));
                        if (count == 21) sensor_bytetemp.setStepCount(new String(tempp));

                        if (count == 22) sensor_bytetemp.setTemp1(new String(tempp));
                        if (count == 23) sensor_bytetemp.setTemp2(new String(tempp));
                        if (count == 24) sensor_bytetemp.setTemp3(new String(tempp));
//                        if (count == 25) sensor_bytetemp.setSensorFilePath(new String(tempp));
                        tempp= stemp;
                        continue;
                    }

                    // 不断添加数据
                    tempp.append(new StringBuilder(s.charAt(i)+""));
                    if(count == dataMaxNum- 1 && i == s.length()- 1){
                        count++;
                        System.out.println("------count="+ count);
                        sensor_bytetemp.setSensorFilePath(new String(tempp));
                    }

//                    System.out.print(i +"-"+ s.charAt(i)+"  ");
                }
                //读完每行数据后要将此数据存入数据库中
                MaxId++;
                sensor_bytetemp.setSensor_messageId(MaxId);

                sensor_messageService.addSensor_Byte(sensor_bytetemp);
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
    public ResponseEntity<List<Sensor_Message>> GetAllMessageFromTaskId(@PathVariable Integer taskId){
        Sensor_Message sensor_message= new Sensor_Message();
        sensor_message.setTaskId(taskId);
        List<Sensor_Message> result= sensor_messageService.selAllMessageFromTaskId(sensor_message);
        if (result == null || result.size() == 0){// 无资源没有找到，返回404
            System.out.println("没有该数据");
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    // 20210517 根据taskId和userId查看该执行这在该任务的所有感知数据
    @RequestMapping(value = "/getAllMessageFromUserIdTaskId")
    @ResponseBody
    public ResponseEntity<List<Sensor_Message>> GetAllMessageFromTaskId(@RequestBody Sensor_Message sensor_message){
//        Sensor_Message sensor_message= new Sensor_Message();
//        sensor_message.setTaskId(taskId);
        List<Sensor_Message> result= sensor_messageService.selAllMessageFromUserIdTaskId(sensor_message);
        if (result == null){// 无资源没有找到，返回404
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    ///20200816    输入参数sensorFile,选择更新最新版本
    @RequestMapping("downVersionFromServer/{sensorFile}")
    public ResponseEntity<byte[]> downVersionFromServer(@PathVariable String sensorFile) {
        //@RequestMapping("downVersionFromServer")
        //public ResponseEntity<byte[]> downVersionFromServer() {
//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;

        //sensorFile = version_updatingService.getLastOne().getApkName();
        /// 20200828  下载文件的步骤，首先寻找到该用户需要下载的文件的那条数据，然后返回前段，前端再将这条数据的地址传过来

        System.out.println(sensorFile);

        boolean status1 = sensorFile.contains("/");
        boolean status2 = sensorFile.contains("\\");
        if( status1 || status2  ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //2019.9.15   数据流转换
        InputStream in;
        ResponseEntity<byte[]> response=null ;
        String filePath = "E" + File.separator + "springboot-upload" + File.separator + "version" + File.separator ;
//		System.out.println(image);
        File file = new File(filePath + sensorFile);
        try {
            in = new FileInputStream(file);
            byte[] b=new byte[in.available()];
            in.read(b);
            HttpHeaders headers = new HttpHeaders();
            String filename = new String(sensorFile.getBytes("gbk"),"iso8859-1");
            headers.add("Content-Disposition", "attachment;filename="+filename);
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
