package com.example.serviceconsumer.service.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * https://cloud.spring.io/spring-cloud-openfeign/2.1.x/single/spring-cloud-openfeign.html#
 * https://github.com/OpenFeign/feign
 *
 *
 * */
@FeignClient(name = "demo-service-provider", configuration = HelloFeignConfiguration.class, fallbackFactory = HelloFeignClientFallbackFactory.class)
public interface HelloFeignClient {

    @RequestMapping(value = "/test/{name}", method = RequestMethod.GET)
    @Headers("Content-Type: application/json")
    String hello(@PathVariable("name") String name);

}
