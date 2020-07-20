package com.example.demoserviceprovider.base.util.token;

import com.example.demoserviceprovider.entity.RMSUser;

public interface ITokenMan {

    String getToken(RMSUser userInfo);
    RMSUser getUserInfoByToken(String token);
    void logout(String token);
}
