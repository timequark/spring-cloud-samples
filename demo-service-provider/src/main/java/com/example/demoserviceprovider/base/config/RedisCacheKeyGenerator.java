package com.example.demoserviceprovider.base.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.cache.interceptor.KeyGenerator;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class RedisCacheKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        String hotelcode = StringUtils.EMPTY;
        Map<String, Object> map = new HashMap<>();
        map.put("target", target.getClass().toGenericString()); //放入target的名字
        map.put("method", method.getName()); //放入method的名字
        Parameter[] parameters = method.getParameters();

        if (params != null && params.length > 0) { //把所有参数放进去
            int i = 0;
            for (Object o : params) {
                map.put("params-" + i, o);
                if (parameters[i].getName().equalsIgnoreCase("hotelcode")) {
                    hotelcode = o.toString();
                }
                i++;
            }
        }

        String str = JSONObject.toJSON(map).toString();
        byte[] hash = null;
        String s = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        s = MD5Encoder.encode(hash); //使用MD5生成位移key
        return String.format("%s_%s", hotelcode, s);
    }
}
