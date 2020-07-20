package com.example.demoserviceprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.example.demoserviceprovider.mapper")
public class DemoServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoServiceAuthApplication.class, args);
    }

}
