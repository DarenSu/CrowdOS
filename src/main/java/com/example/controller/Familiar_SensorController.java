package com.example.controller;

import com.example.entity.Familiar_Sensor;
import com.example.service.Familiar_SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController      //To indicate the module, here is the control module
@RequestMapping("/sensor")
public class Familiar_SensorController {
    @Autowired
    private Familiar_SensorService familiar_sensorService;


    ///2019.7.25 upload the text description and pictures of task, with two parameters - text description of
    // user_task and single picture
    /// multiple pictures upload functionality aren't implemented, only single picture upload functionality is
    // implemented.
    @RequestMapping(value = "/uploadSensorFiles", method = RequestMethod.POST)
    @ResponseBody
    // Class User       file type parameters (text, video and image type are all allowable)
    public ResponseEntity<Familiar_Sensor> add_Sensor_File(@RequestPart("familiar_sensor") Familiar_Sensor familiar_sensor, @RequestPart("file") MultipartFile file) {
        // check whether files are duplicate
        // obtain the file name
        System.out.println("欢迎来到传感器数据上传：sensor/uploadSensorFiles");
        System.out.println("sfdaeg");
        System.out.println(familiar_sensor);

        String fileName = file.getOriginalFilename();
        System.out.println(fileName);

        // 2019.9.17 read the size of uploaded file, the uploaded file size can't exceed maximum size supported by
        // SSM, in Bytes
        // 2019.9.17 maximum size is set to 10 uploaded files at front end
        long fileLength = 0L;
        fileLength = file.getSize()/1024;
        System.out.println(fileLength+"KB"+"    "+"单张照片小于20480KB，可以传输");
        if (fileLength > 20480){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // obtain the suffix of file name
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // the path of uploaded file
        //String filePath = "F:" + File.separator + "springboot-upload" + File.separator + "image" + File.separator ;
        //This is the code before modifying, it accesses the content in C drive
        //String filePath =  "/E/springboot-upload/image/";
        //20200912  20200912  I changed by myself


        String filePath =  "root:/F/springboot-upload/sensorFiles/";
        // solve the Chinese character problem, file path in Chinese, and picture display problem
        // fileName = UUID.randomUUID() + suffixName;
        File pfile = new File(filePath);
        if (!pfile.exists()) {
            //if not exist, path is automatically generated
            pfile.mkdirs();
        }

        File dest = new File(pfile.getAbsoluteFile() + fileName);
        System.out.println("The upload file's absolute path: " + pfile.getAbsoluteFile() + fileName);
        System.out.println("The dest file's absolute path: " + dest.getAbsolutePath());
        //File dest = new File(filePath + fileName);
        //System.out.println("upload file's absolute path is"  + dest.getAbsolutePath());
        // check whether the directory ever exists
        if (!dest.getParentFile().exists()) {
            //if the file path doesn't exist, path is automatically generated
            dest.getParentFile().mkdirs();
        }

        System.out.println("目录已创建aaaaaaaaaaaa");
        try {
            System.out.println("正在上传中1bbbbbbbbbbbbbbbbbb");
            //if(!dest.exists()) {

                file.transferTo(dest);
            //}
            System.out.println("正在上传中2ccccccccccccccccccccccc");
            //upload the file address to database
            familiar_sensor.setSensorFile(filePath + fileName);
            //upload the task content simultaneously, now utask already has the location info of picture
            familiar_sensorService.addFamiliar_Sensor(familiar_sensor);
            //return this result and set the return state as OK
            return new ResponseEntity<>(familiar_sensor, HttpStatus.OK);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // the steps of downloading sensing data
    // 1--an object of familiar_sensor is passed, with userID and taskID, which are used to access the sensorFile of
    // the file user wants to download
    // 2--begin downloading with sensorFile as argument passed to another function
    @RequestMapping(value = "selFamiliar_Sensor", method = RequestMethod.POST)
    public ResponseEntity<List<Familiar_Sensor>> selFamiliar_Sensor(/*@RequestBody*/ Familiar_Sensor familiar_sensor){
        return new ResponseEntity<List<Familiar_Sensor>>(familiar_sensorService.selFamiliar_Sensor(familiar_sensor),HttpStatus.OK);
    }



    // 20200816 input the argument sensorFile, update to the newest version
    @RequestMapping("downVersionFromServer/{sensorFile}")
    public ResponseEntity<byte[]> downVersionFromServer(@PathVariable String sensorFile) {
        //@RequestMapping("downVersionFromServer")
        //public ResponseEntity<byte[]> downVersionFromServer() {
//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;

        //sensorFile = version_updatingService.getLastOne().getApkName();


        // 20200828 steps of downloading file, firstly, search for the piece of record user wants to download,
        // secondly, return this record to the front end, thirdly, the front end pass the record address back.

        System.out.println(sensorFile);

        boolean status1 = sensorFile.contains("/");
        boolean status2 = sensorFile.contains("\\");
        if( status1 || status2  ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //2019.9.15 data flow conversion
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
