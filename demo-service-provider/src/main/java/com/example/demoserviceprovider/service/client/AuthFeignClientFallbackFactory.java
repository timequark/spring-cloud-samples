package com.example.demoserviceprovider.service.client;

import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.entity.req.UserLogin;
import com.example.demoserviceprovider.entity.resp.BaseResp;
import com.example.demoserviceprovider.entity.resp.RespLogin;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthFeignClientFallbackFactory implements FallbackFactory<AuthFeignClient> {
    @Override
    public AuthFeignClient create(Throwable cause) {
        return new AuthFeignClient() {
            @Override
            public BaseResp<RespLogin> login(UserLogin userLogin) {
                return null;
            }

            @Override
            public BaseResp<RMSUser> getUserInfo(String token) {
                return null;
            }

            @Override
            public BaseResp<List<RMSUser>> users(String token) {
                return null;
            }

            @Override
            public BaseResp<String> logout(String token) {
                return null;
            }
        };
    }
}
