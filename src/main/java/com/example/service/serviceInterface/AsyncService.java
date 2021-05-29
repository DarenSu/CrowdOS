package com.example.service.serviceInterface;

import org.springframework.stereotype.Service;

@Service
public interface AsyncService {
    /**
     * Performing asynchronous tasks
     */

    void executeAsync();
}
