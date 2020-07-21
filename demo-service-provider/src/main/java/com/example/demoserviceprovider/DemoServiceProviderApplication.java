package com.example.demoserviceprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

/**
 * Doc
 *
 * [MyBatis]
 * https://segmentfault.com/a/1190000019819558
 * https://www.jianshu.com/p/541874714907
 *
 * @Mapper 直接写SQL
 * https://www.cnblogs.com/tianshu/p/10165128.html
 *
 *
 *
 *
 *
 * */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableHystrix
@EnableCaching
@MapperScan("com.example.demoserviceprovider.mapper")
public class DemoServiceProviderApplication {

    public static void main(String[] args) {
        SpringContextUtil springContextUtil = new SpringContextUtil();
        ApplicationContext applicationContext = SpringApplication.run(DemoServiceProviderApplication.class, args);
        springContextUtil.setApplicationContext(applicationContext);
    }

}
