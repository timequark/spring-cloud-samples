package com.example.demoserviceprovider.service;

import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.entity.req.UserLogin;

import java.util.List;

public interface IServiceRMSUser {
    void logout(String token);
    List<RMSUser> getUsers(String token);
    String getToken(UserLogin userLogin);
    RMSUser getUserByToken(String token);
}
