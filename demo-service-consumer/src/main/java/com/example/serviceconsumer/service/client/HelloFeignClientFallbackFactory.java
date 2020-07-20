package com.example.serviceconsumer.service.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloFeignClientFallbackFactory implements FallbackFactory<HelloFeignClient> {
    @Override
    public HelloFeignClient create(Throwable cause) {
        return new HelloFeignClient() {
            @Override
            public String hello(String name) {
                return "fallback; reason was: " + cause.getMessage();
            }
        };
    }
}
