package com.example.serviceconsumer.service;

import com.example.serviceconsumer.service.client.HelloFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceHello {
    @Autowired
    HelloFeignClient helloFeignClient;

    public String hello(String name) {
        return helloFeignClient.hello(name);
    }
}
