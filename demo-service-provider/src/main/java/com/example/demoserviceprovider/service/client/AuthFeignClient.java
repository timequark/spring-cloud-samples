package com.example.demoserviceprovider.service.client;

import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.entity.req.UserLogin;
import com.example.demoserviceprovider.entity.resp.BaseResp;
import com.example.demoserviceprovider.entity.resp.RespLogin;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * https://cloud.spring.io/spring-cloud-openfeign/2.1.x/single/spring-cloud-openfeign.html#
 * https://github.com/OpenFeign/feign
 *
 *
 * */
@FeignClient(name = "demo-service-auth", configuration = AuthFeignConfiguration.class, fallbackFactory = AuthFeignClientFallbackFactory.class)
public interface AuthFeignClient {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Headers("Content-Type: application/json")
    BaseResp<RespLogin> login(UserLogin userLogin);

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @Headers("Content-Type: application/json")
    BaseResp<RMSUser> getUserInfo(String token);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @Headers("Content-Type: application/json")
    BaseResp<List<RMSUser>> users(String token);

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @Headers("Content-Type: application/json")
    BaseResp<String> logout(String token);

}
