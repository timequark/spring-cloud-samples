package com.example.demoserviceprovider.mapper;

import com.example.demoserviceprovider.entity.RMSUser;

import java.util.List;

public interface RMSUserMapper {
    RMSUser getRMSUserByUserCode(String userCode);
    List<RMSUser> getAllRMSUser();
}
