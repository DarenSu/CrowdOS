package com.example.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
//import java.util.logging.Logger;


@Configuration
@EnableAsync
public class ExecutorConfig {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ExecutorConfig.class);
    @Bean(name = "asyncServiceExecutor")
    public Executor asyncServiceExecutor() {
        logger.info("start asyncServiceExecutor");
        //ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();

        executor.setCorePoolSize(5);

        executor.setMaxPoolSize(5);

        executor.setQueueCapacity(99999);

        executor.setThreadNamePrefix("async-service-");


        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());


        executor.initialize();
        return executor;

    }

}
