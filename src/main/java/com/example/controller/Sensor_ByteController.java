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

@RestController      //To indicate the module, here is the control module
@RequestMapping("/sensorbyte")
public class Sensor_ByteController {
    @Autowired
    private Familiar_SensorService familiar_sensorService;
    @Autowired
    Sensor_ByteService sensor_byteService;



    //  Steps for users to download sensor data
    //  1--The front end first transmits a Sensor_Byte, which contains userId and taskId, and then uses these to
    //  obtain the sensorFile of the file that the user needs to download
    //  2--Then use another function, pass sensorFile as a parameter, and download
    @RequestMapping(value = "selSensor_Byte", method = RequestMethod.POST)
    public ResponseEntity<List<Sensor_Byte>> selSensor_Byte(/*@RequestBody*/ Sensor_Byte sensor_byte){
        System.out.println("sensor_byte"+sensor_byte);
        List<Sensor_Byte> temp= sensor_byteService.selSensor_Byte(sensor_byte);
        System.out.println("查询后的数据"+ temp);
        System.out.println("单独的fileByte："+ temp.get(0).getFileByte());
        return new ResponseEntity<List<Sensor_Byte>>(sensor_byteService.selSensor_Byte(sensor_byte),HttpStatus.OK);
    }


    ///2021.05.14 Upload the text information of the sensor-binary, two parameters, Sensor_Byte text information +
    // single picture
    ///Currently there is no realization of uploading multiple pictures, but the realization of uploading a single picture
    @RequestMapping(value = "/uploadSensorFileBytes", method = RequestMethod.POST)
    @ResponseBody
    // User class           File type parameter (it can be file, video, picture)
    public ResponseEntity<Sensor_Byte> add_Sensor_File_Bytes(/*@RequestPart("sensor_byte")*/ Sensor_Byte sensor_byte, @RequestPart("file") MultipartFile file) throws IOException {

        System.out.println("欢迎来到传感器数据到二进制的上传：sensor/uploadSensorFileBytes");

        // 20210513 Data judgement to prevent database crash caused by meaningless access
        if(sensor_byte.getSensor_messageId() != null || sensor_byte.getUserId() == null ||
                sensor_byte.getTaskId() == null || file.isEmpty()){//Something is wrong with the data.,400
            return new ResponseEntity<Sensor_Byte>(HttpStatus.BAD_REQUEST);
        }
        System.out.println("测试到了这里了！！！");


//        System.out.println("sfdaeg");
        System.out.println(sensor_byte);

        String fileName = file.getOriginalFilename();
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
        sensor_byte.setOnlineTime(date);

        // 20210514  This section converts file resources directly into data streams
        String fileByte= String.valueOf(file.getBytes());

//        System.out.println("转换成功的二进制文件类型："+ getType(file.getBytes()));
        System.out.println("转换成功的二进制文件初始类型数据："+ file.getBytes());

        System.out.println("转换成功的二进制文件："+ fileByte);

        // 20210514 Stores the converted binary data into a data structure
        sensor_byte.setFileByte(fileByte);


        // 20210515  Here the file is read line by line and the data is stored in the database
//        File fileCopy= (File) file;

//        InputStream inputStream = file.getInputStream();
//        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines().forEach(this::handleLine);
        int MaxId = sensor_byteService.getMaxId().getSensor_messageId();


        try{
            InputStream inputStream = file.getInputStream();
//            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream((File) file), "UTF-8"));//构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));//构造一个BufferedReader类来读取文件

            // The file is read line by line
            int dataMaxNum= 4;
            String s = null;
            while((s = br.readLine())!=null){//Using the readLine method, read one line at a time
                Sensor_Byte sensor_bytetemp= sensor_byte;

                System.out.println("s="+s);

                StringBuilder tempp= new StringBuilder();
                int count = 0;
                for (int i= 0; i< s.length(); i++) {
                    StringBuilder stemp= new StringBuilder();
                    if(count >= dataMaxNum) {//Only four rows are read. If each row contains more data than this, it is a data error ，400
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

                    // Keep adding data
                    tempp.append(new StringBuilder(s.charAt(i)+""));
                    if(count == dataMaxNum- 1 && i == s.length()- 1){
                        count++;
                        System.out.println("------count="+ count);
                        sensor_bytetemp.setTemp4(new String(tempp));
                    }

//                    System.out.print(i +"-"+ s.charAt(i)+"  ");
                }
                //After reading each row of data, the data is stored in the database
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


//        sensor_byteService.addSensor_Byte(sensor_byte);



        return new ResponseEntity<>(HttpStatus.OK);
    }




    ///20200816    Enter the parameter sensorFile and choose to update the latest version
    @RequestMapping("downVersionFromServer/{sensorFile}")
    public ResponseEntity<byte[]> downVersionFromServer(@PathVariable String sensorFile) {
        //@RequestMapping("downVersionFromServer")
        //public ResponseEntity<byte[]> downVersionFromServer() {
//		String suffixName = image.substring(image.lastIndexOf(File.separator));
//		image = suffixName;

        //sensorFile = version_updatingService.getLastOne().getApkName();
        /// 20200828

        System.out.println(sensorFile);

        boolean status1 = sensorFile.contains("/");
        boolean status2 = sensorFile.contains("\\");
        if( status1 || status2  ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //2019.9.15  Data stream conversion
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
