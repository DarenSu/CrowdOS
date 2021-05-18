package com.example.service.serviceInterface;

import org.springframework.stereotype.Service;

@Service
public interface AsyncService {
    /**
     * 执行异步任务
     */

    void executeAsync();
}
