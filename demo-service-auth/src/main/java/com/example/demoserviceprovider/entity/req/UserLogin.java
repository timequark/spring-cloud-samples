package com.example.demoserviceprovider.entity.req;

import lombok.Builder;
import lombok.Data;

@Data
public class UserLogin {
    public String usercode;
    public String password;
    public String verification;
}
