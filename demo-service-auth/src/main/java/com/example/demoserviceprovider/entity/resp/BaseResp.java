package com.example.demoserviceprovider.entity.resp;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BaseResp<T> {
    public String code;
    public String msg;
    public T data;
}
