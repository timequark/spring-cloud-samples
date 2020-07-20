package com.example.demoserviceprovider.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
