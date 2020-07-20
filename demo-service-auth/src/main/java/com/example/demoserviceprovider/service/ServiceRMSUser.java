package com.example.demoserviceprovider.service;

import com.example.demoserviceprovider.dao.RMSUserDao;
import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.base.util.token.RedisTokenMan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceRMSUser implements IServiceRMSUser {

    @Autowired
    private RMSUserDao rmsUserDao;
    @Autowired
    private RedisTokenMan redisTokenMan;

    @Override
    public RMSUser getRMSUserByUserCode(String userCode) {
        return rmsUserDao.getRMSUserByUserCode(userCode);
    }

    @Override
    public void logout(String token) {
        redisTokenMan.logout(token);
    }

    @Override
    public List<RMSUser> getAllRMSUser() {
        return rmsUserDao.getAllRMSUser();
    }

    @Override
    public String getToken(RMSUser userInfo) {
        return redisTokenMan.getToken(userInfo);
    }

    @Override
    public RMSUser getUserByToken(String token) {
        return redisTokenMan.getUserInfoByToken(token);
    }
}
