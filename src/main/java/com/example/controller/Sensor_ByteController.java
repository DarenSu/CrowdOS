package com.example.controller;

import com.example.entity.Sensor_Byte;
import com.example.service.Familiar_SensorService;
import com.example.service.Sensor_ByteService;
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

@RestController      //进行模块的注明，此处为控制模块
@RequestMapping("/sensorbyte")
public class Sensor_ByteController {
    @Autowired
    private Familiar_SensorService familiar_sensorService;
    @Autowired
    Sensor_ByteService sensor_byteService;



    //  用户下载传感数据的步骤
    //  1--前端先传一个Sensor_Byte，里面有userId和taskId，然后靠这些取得该用户需要下载的文件的sensorFile
    //  2--然后再用另一个函数，以sensorFile为参数传递，进行下载
    @RequestMapping(value = "selSensor_Byte", method = RequestMethod.POST)
    public ResponseEntity<List<Sensor_Byte>> selSensor_Byte(/*@RequestBody*/ Sensor_Byte sensor_byte){
        System.out.println("sensor_byte"+sensor_byte);
        List<Sensor_Byte> temp= sensor_byteService.selSensor_Byte(sensor_byte);
        System.out.println("查询后的数据"+ temp);
        System.out.println("单独的fileByte："+ temp.get(0).getFileByte());
        return new ResponseEntity<List<Sensor_Byte>>(sensor_byteService.selSensor_Byte(sensor_byte),HttpStatus.OK);
    }


    ///2021.05.14 上传传感器的文字信息--二进制，两个参数，Sensor_Byte文字信息+单张图片
    ///目前没有实现多张图片上传，实现的是单图片的上传
    @RequestMapping(value = "/uploadSensorFileBytes", method = RequestMethod.POST)
    @ResponseBody
    // User类           文件类型的参数（可以是文件、视频、图片均可）
    public ResponseEntity<Sensor_Byte> add_Sensor_File_Bytes(/*@RequestPart("sensor_byte")*/ Sensor_Byte sensor_byte, @RequestPart("file") MultipartFile file) throws IOException {

        System.out.println("欢迎来到传感器数据到二进制的上传：sensor/uploadSensorFileBytes");

        // 20210513 数据判断，防止无意义的访问导致数据库崩溃
        if(sensor_byte.getSensor_messageId() != null || sensor_byte.getUserId() == null ||
                sensor_byte.getTaskId() == null || file.isEmpty()){//数据有问题，返回400
            return new ResponseEntity<Sensor_Byte>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("测试到了这里了！！！");
        //查看文件是否有重复
        // 获取文件名

//        System.out.println("sfdaeg");
        System.out.println(sensor_byte);

        String fileName = file.getOriginalFilename();
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
        sensor_byte.setOnlineTime(date);

        // 20210514  这部分可以将文件资源直接转换成数据流
        String fileByte= String.valueOf(file.getBytes());

//        System.out.println("转换成功的二进制文件类型："+ getType(file.getBytes()));
        System.out.println("转换成功的二进制文件初始类型数据："+ file.getBytes());

        System.out.println("转换成功的二进制文件："+ fileByte);

        // 20210514 将转换成二进制的数据存储到数据结构中
        sensor_byte.setFileByte(fileByte);
        // 20210514 存储到数据库中   还没有写，需要写

        // 20210515  此处进行间将文件逐行进行读取，并将其中的数据存入数据库中
//        File fileCopy= (File) file;

//        InputStream inputStream = file.getInputStream();
//        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().forEach(this::handleLine);
        int MaxId = sensor_byteService.getMaxId().getSensor_messageId();


        try{
            InputStream inputStream = file.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream((File) file), "UTF-8"));//构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));//构造一个BufferedReader类来读取文件

            // 文件进行逐行读取的操作
            int dataMaxNum= 4;
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                Sensor_Byte sensor_bytetemp= sensor_byte;

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
                        if (count == 1) sensor_bytetemp.setTemp1(new String(tempp));
                        if (count == 2) sensor_bytetemp.setTemp2(new String(tempp));
                        if (count == 3) sensor_bytetemp.setTemp3(new String(tempp));
//                        if (count == 4) sensor_bytetemp.setTemp4(new String(tempp));
                        tempp= stemp;
                        continue;
                    }

                    // 不断添加数据
                    tempp.append(new StringBuilder(s.charAt(i)+""));
                    if(count == dataMaxNum- 1 && i == s.length()- 1){
                        count++;
                        System.out.println("------count="+ count);
                        sensor_bytetemp.setTemp4(new String(tempp));
                    }

//                    System.out.print(i +"-"+ s.charAt(i)+"  ");
                }
                //读完每行数据后要将此数据存入数据库中
                MaxId++;
                sensor_bytetemp.setSensor_messageId(MaxId);

                sensor_byteService.addSensor_Byte(sensor_bytetemp);
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
