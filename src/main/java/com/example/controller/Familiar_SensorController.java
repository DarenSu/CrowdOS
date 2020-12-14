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

@RestController      //进行模块的注明，此处为控制模块
@RequestMapping("/sensor")
public class Familiar_SensorController {
    @Autowired
    private Familiar_SensorService familiar_sensorService;


    ///2019.7.25 上传任务的文字信息和图片，两个参数，user_task文字信息+单张图片
    ///目前没有实现多张图片上传，实现的是单图片的上传
    @RequestMapping(value = "/uploadSensorFiles", method = RequestMethod.POST)
    @ResponseBody
    // User类           文件类型的参数（可以是文件、视频、图片均可）
    public ResponseEntity<Familiar_Sensor> add_Sensor_File(@RequestPart("familiar_sensor") Familiar_Sensor familiar_sensor, @RequestPart("file") MultipartFile file) {
        //查看文件是否有重复
        // 获取文件名
        System.out.println("欢迎来到传感器数据上传：sensor/uploadSensorFiles");
        System.out.println("sfdaeg");
        System.out.println(familiar_sensor);

        String fileName = file.getOriginalFilename();
        System.out.println(fileName);

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
