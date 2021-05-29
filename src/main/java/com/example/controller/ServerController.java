package com.example.controller;



/**
 * @Author:DarenSu
 * @Date: 2021/04/21
 * @Time: 14:42
 */

import com.example.entity.Server;
import com.example.service.ServerService;
import com.example.util.ComputerMonitorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController  // To indicate the module, here is the control module
@EnableScheduling //To indicate the module
@RequestMapping("/device")
public class ServerController {


    @Autowired
    ServerService serverService;


    @RequestMapping("getDevice")
    private ResponseEntity<Server> GetDevice() throws Exception {

        System.out.println("次函数功能是获取设备的CPU,内存和磁盘数据");
        System.out.println("被访问的函数的名称：getDevice");




        //The CPU usage of the current system
        double cpuUsage = ComputerMonitorUtil.getCpuUsage();
        //The memory usage of the current system
        double memUsage = ComputerMonitorUtil.getMemUsage();
        //The disk usage of the current system
        double diskUsage = ComputerMonitorUtil.getDiskUsage();
        //double transform int    *100
        int cpu_temp = (int)cpuUsage* 100;
        int memory_temp = (int)memUsage* 100;
        int disk_temp = (int)diskUsage* 100;

        Server server = new Server();
//        server.setCpu(String.valueOf(cpu_temp));
//        server.setMemory(String.valueOf(memory_temp));
//        server.setDisk(String.valueOf(disk_temp));

        server.setCpu((cpu_temp));
        server.setMemory((memory_temp));
        server.setDisk((disk_temp));


        //  20200628    Time fetching for the DATE data type
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        System.out.println(date);
        server.setTime(date);


//        // 2021 04 22 test
//        System.out.println("=--------"+ serverService.getServer());

        if( check_serverMessageIsOK(server) == false ){
//        if( false == false ){
//            return new ResponseEntity<>( serverService.getServer() ,HttpStatus.OK);
            return new ResponseEntity<>(serverService.getServer(), HttpStatus.ACCEPTED);
        }

        //Write this data to the database
        serverService.addServer(server);

        return new ResponseEntity<>(server, HttpStatus.OK);
    }

    //Verify that the Server meets the requirements
    public boolean check_serverMessageIsOK(Server server){
        if(server.getDisk()>0 && server.getDisk() < 10000 &&
            server.getCpu()> 0 && server.getCpu()< 10000 &&
            server.getMemory()>0 && server.getMemory() < 10000)
            return true;
        return false;
    }
    //
    @RequestMapping("getAllDevice")
    private ResponseEntity<List<Server>> GetAllDevice() throws Exception {
        if(serverService.getAllServer()!= null){
            return new ResponseEntity<>(serverService.getAllServer(), HttpStatus.OK);
        }else
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
