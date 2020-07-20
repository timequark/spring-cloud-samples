package com.example.demoserviceprovider.service;

import com.example.demoserviceprovider.base.Constants;
import com.example.demoserviceprovider.base.annotation.Token;
import com.example.demoserviceprovider.base.util.RedisUtil;
import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.base.util.token.RedisTokenMan;
import com.example.demoserviceprovider.entity.req.UserLogin;
import com.example.demoserviceprovider.entity.resp.BaseResp;
import com.example.demoserviceprovider.entity.resp.RespLogin;
import com.example.demoserviceprovider.service.client.AuthFeignClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceRMSUser implements IServiceRMSUser {

    @Autowired
    private RedisTokenMan redisTokenMan;

    @Autowired
    private AuthFeignClient authFeignClient;

    @Override
    public String getToken(UserLogin userLogin) {
        String token = StringUtils.EMPTY;
        BaseResp<RespLogin> resp = authFeignClient.login(userLogin);
        if (resp != null && resp.code.equalsIgnoreCase(Constants.ReturnCode.SUCCESS)) {
            token = resp.data.token;
        }

        return token;
    }

    @Override
    public RMSUser getUserByToken(@Token String token) {
        RMSUser rmsUser = null;
        BaseResp<RMSUser> resp = authFeignClient.getUserInfo(token);
        if (resp != null && resp.code.equalsIgnoreCase(Constants.ReturnCode.SUCCESS)) {
            rmsUser = resp.data;
        }

        return rmsUser;
    }

    @Override
    public List<RMSUser> getUsers(@Token String token) {
        List<RMSUser> rmsUsers = null;
        BaseResp<List<RMSUser>> resp = authFeignClient.users(StringUtils.EMPTY);
        if (resp != null && resp.code.equalsIgnoreCase(Constants.ReturnCode.SUCCESS)) {
            rmsUsers = resp.data;
        }

        return rmsUsers;
    }

    @Override
    public void logout(String token) {
        authFeignClient.logout(token);
    }
}
