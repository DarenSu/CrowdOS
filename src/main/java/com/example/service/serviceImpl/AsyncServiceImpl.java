package com.example.service.serviceImpl;


import com.example.service.serviceInterface.AsyncService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//import java.util.logging.Logger;

@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync() {
        logger.info("start executeAsync");
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("end executeAsync");
    }
}
