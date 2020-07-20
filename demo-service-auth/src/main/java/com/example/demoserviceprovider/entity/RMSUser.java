package com.example.demoserviceprovider.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RMSUser {
    private String sysId;
    private String userCode;
    private String userName;
    private String password;
    private String gender;
    private String email;
    private String creater;
    private String custSysId;
    private String accountSysId;
    private String roleSysId;
}
