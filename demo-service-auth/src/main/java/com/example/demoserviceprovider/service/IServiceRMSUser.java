package com.example.demoserviceprovider.service;

import com.example.demoserviceprovider.entity.RMSUser;

import java.util.List;

public interface IServiceRMSUser {
    RMSUser getRMSUserByUserCode(String userCode);
    void logout(String token);
    List<RMSUser> getAllRMSUser();

    String getToken(RMSUser userInfo);
    RMSUser getUserByToken(String token);
}
