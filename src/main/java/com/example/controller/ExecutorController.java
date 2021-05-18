package com.example.controller;


import com.example.service.serviceInterface.AsyncService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import java.util.logging.Logger;

@RestController
@EnableScheduling //时间模块
@RequestMapping("/thread")
public class ExecutorController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ExecutorController.class);

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/ececutor")
    public String submit(){
        logger.info("start submit");
        //调用service层的任务
        asyncService.executeAsync();
        logger.info("end submit");
        return "success";
    }

}
