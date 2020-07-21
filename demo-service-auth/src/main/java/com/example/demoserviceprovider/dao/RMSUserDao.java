package com.example.demoserviceprovider.dao;

import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.mapper.RMSUserMapper;
import com.example.demoserviceprovider.mapper.RMSUserMapperV2;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RMSUserDao {
    @Resource
    private RMSUserMapper rmsUserMapper;

    public RMSUser getRMSUserByUserCode(String userCode) {
        return rmsUserMapper.getRMSUserByUserCode(userCode);
    }

    public List<RMSUser> getAllRMSUser() {
        return rmsUserMapper.getAllRMSUser();
    }
}
