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

@RestController      //To indicate the module, here is the control module
@RequestMapping("/sensormessage")
public class Sensor_MessageController {



    //  2021.05.14  log record
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

    ///2019.7.25 Upload the text message and picture of the task, two parameters, user_task text message + single picture
    ///Currently there is no realization of uploading multiple pictures, but the realization of uploading a single picture
    @RequestMapping(value = "/uploadSensorFiles", method = RequestMethod.POST)
    @ResponseBody
    // User class           File type parameters (can be files, videos, pictures)
    public ResponseEntity<Familiar_Sensor> add_Sensor_File(@RequestPart("familiar_sensor") Familiar_Sensor familiar_sensor, @RequestPart("file") MultipartFile file) throws IOException {
        //Check the file for duplicates
        // Get file name
        System.out.println("欢迎来到传感器数据上传：sensor/uploadSensorFiles");
        System.out.println("sfdaeg");
        System.out.println(familiar_sensor);

        String fileName = file.getOriginalFilename();
        System.out.println(fileName);

        // 20210514  This section converts file resources directly into data streams
        String fileByte= String.valueOf(file.getBytes());


        //2019.9.17  Read the size of the uploaded file, cannot exceed the maximum supported by SSM  ,unit:B
        //2019.9.17  The front end can transfer up to ten files
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
        // Solve the Chinese problem,
        // the Chinese path under LIUNX,
        // the picture display problem
        // fileName = UUID.randomUUID() + suffixName;
        File pfile = new File(filePath);
        if (!pfile.exists()) {
            //Generate the path itself if it doesn't exist
            pfile.mkdirs();
        }

        File dest = new File(pfile.getAbsoluteFile() + fileName);
        System.out.println("The upload file's absolute path: " + pfile.getAbsoluteFile() + fileName);
        System.out.println("The dest file's absolute path: " + dest.getAbsolutePath());
        //File dest = new File(filePath + fileName);
        //System.out.println("upload file's absolute path is"  + dest.getAbsolutePath());
        // Checks for the existence of a directory
        if (!dest.getParentFile().exists()) {
            //Generate the path itself if it doesn't exist
            dest.getParentFile().mkdirs();
        }

        System.out.println("目录已创建aaaaaaaaaaaa");
        try {
            System.out.println("正在上传中1bbbbbbbbbbbbbbbbbb");
            //if(!dest.exists()) {

                file.transferTo(dest);
            //}
            System.out.println("正在上传中2ccccccccccccccccccccccc");
            //Upload the image address to the relevant location in the database
            familiar_sensor.setSensorFile(filePath + fileName);
            //At the same time, the specific content of the task is also uploaded, and the position information of the picture is already in the utask at this time
            familiar_sensorService.addFamiliar_Sensor(familiar_sensor);
            //Returns the result and return status of OK
            return new ResponseEntity<>(familiar_sensor, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //  Steps for users to download sensor data
    //  1--The front end first transmits a familiar_sensor, which contains userId and taskId, and then uses these to
    //  obtain the sensorFile of the file that the user needs to download
    //  2--Then use another function, pass sensorFile as a parameter, and download
    @RequestMapping(value = "selFamiliar_Sensor", method = RequestMethod.POST)
    public ResponseEntity<List<Familiar_Sensor>> selFamiliar_Sensor(/*@RequestBody*/ Familiar_Sensor familiar_sensor){
        return new ResponseEntity<List<Familiar_Sensor>>(familiar_sensorService.selFamiliar_Sensor(familiar_sensor),HttpStatus.OK);
    }


    /// 2021.05.14 Upload sensor data and read out the file line by line. Each line of data here has 24 attributes,
    // separated by "/", sensor data data 1 / sensor data 2 /.../collection date
    /// Currently there is no realization of uploading multiple pictures, but the realization of uploading a single picture
    /// Successfully run
    @RequestMapping(value = "/uploadSensorFileMessages", method = RequestMethod.POST)
    @ResponseBody
    // User class           File type parameters (can be files, videos, pictures)
    public ResponseEntity<Sensor_Message> add_Sensor_File_Bytes(@RequestPart("sensor_message") Sensor_Message sensor_message, @RequestPart("file") MultipartFile file) throws IOException {



        System.out.println("欢迎来到传感器数据到逐条上传到表里：sensor/uploadSensorFileBytes");

        // First check whether the data exists in the table, that is, whether the userId and taskId of this data are
        // really related
        // Need to query in the ut table
        User_Task user_task= new User_Task();
        user_task.setUserId(sensor_message.getUserId());
        user_task.setTaskId(sensor_message.getTaskId());
        User_Task user_taskresult = user_taskService.SelUser_Task(user_task);
        if( user_taskresult == null ){//The request was successful, but there is no resource,
            // This meaning that the userId and taskId are not associated
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        // 20210513 Data judgement to prevent database crash caused by meaningless access
        if(sensor_message.getSensor_messageId() != null || sensor_message.getUserId() == null ||
                sensor_message.getTaskId() == null || file.isEmpty()){//Something is wrong with the data. 400
            return new ResponseEntity<Sensor_Message>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("测试到了这里了！！！");


//        System.out.println("sfdaeg");
        System.out.println(sensor_message);

        String fileName = file.getOriginalFilename();
        sensor_message.setFileName(fileName);
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
        sensor_message.setOnlineTime(date);

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
        int MaxId = sensor_messageService.getMaxId().getSensor_messageId();

        System.out.println("MacId="+MaxId);

        try{
            InputStream inputStream = file.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream((File) file), "UTF-8"));//构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));//构造一个BufferedReader类来读取文件

            // he file is read line by line
            int dataMaxNum= 24;
            String s = null;
            while((s = br.readLine())!=null){//Using the readLine method, read one line at a time
                Sensor_Message sensor_bytetemp= sensor_message;

                System.out.println("s="+s);

                StringBuilder tempp= new StringBuilder();
                int count = 0;
                for (int i= 0; i< s.length(); i++) {
                    StringBuilder stemp= new StringBuilder();
                    if(count >= dataMaxNum) {//Only twenty-four  rows are read. Any more than this is a data error  ,400
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

                    // Keep adding data
                    tempp.append(new StringBuilder(s.charAt(i)+""));
                    if(count == dataMaxNum- 1 && i == s.length()- 1){
                        count++;
                        System.out.println("------count="+ count);
                        sensor_bytetemp.setSensorFilePath(new String(tempp));
                    }

//                    System.out.print(i +"-"+ s.charAt(i)+"  ");
                }
                //After reading each row of data, the data is stored in the database
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

        // 20210514 At the same time, the specific content of the task is also uploaded,
        // and the position information of the picture is already in the utask at this time
//        sensor_byteService.addSensor_Byte(sensor_byte);

        // 20210514  Do you need to check whether this data exists ？？？？？？？？

        return new ResponseEntity<>(HttpStatus.OK);
    }




    // 20210517 View all perceptual data of the task according to taskId
    @RequestMapping(value = "/getAllMessageFromTaskId/{taskId}")
    @ResponseBody
    public ResponseEntity<List<Sensor_Message>> GetAllMessageFromTaskId(@PathVariable Integer taskId){
        Sensor_Message sensor_message= new Sensor_Message();
        sensor_message.setTaskId(taskId);
        List<Sensor_Message> result= sensor_messageService.selAllMessageFromTaskId(sensor_message);
        if (result == null || result.size() == 0){// 404
            System.out.println("没有该数据");
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    // 20210517 According to the taskId and userId to view all the perceptual data of the execution of the task
    @RequestMapping(value = "/getAllMessageFromUserIdTaskId")
    @ResponseBody
    public ResponseEntity<List<Sensor_Message>> GetAllMessageFromTaskId(@RequestBody Sensor_Message sensor_message){
//        Sensor_Message sensor_message= new Sensor_Message();
//        sensor_message.setTaskId(taskId);
        List<Sensor_Message> result= sensor_messageService.selAllMessageFromUserIdTaskId(sensor_message);
        if (result == null){// 404
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }


    ///20200816    Enter the parameter sensorFile and choose to update the latest version
    @RequestMapping("downVersionFromServer/{sensorFile}")
    public ResponseEntity<byte[]> downVersionFromServer(@PathVariable String sensorFile) {
        //@RequestMapping("downVersionFromServer")
        //public ResponseEntity<byte[]> downVersionFromServer() {
//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;

        //sensorFile = version_updatingService.getLastOne().getApkName();
        /// 20200828  Download the file step, first to find the user needs to download the file of the data, and then return to the front segment, the front end will pass the address of this data

        System.out.println(sensorFile);

        boolean status1 = sensorFile.contains("/");
        boolean status2 = sensorFile.contains("\\");
        if( status1 || status2  ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //2019.9.15   Data stream conversion
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
