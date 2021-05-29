package com.example.controller;


import com.example.service.serviceInterface.AsyncService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import java.util.logging.Logger;

@RestController //To indicate the module, here is the control module
@EnableScheduling //Time module
@RequestMapping("/thread")
public class ExecutorController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ExecutorController.class);

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/ececutor")
    public String submit(){
        logger.info("start submit");
        //Tasks that invoke the Service layer
        asyncService.executeAsync();
        logger.info("end submit");
        return "success";
    }

}
