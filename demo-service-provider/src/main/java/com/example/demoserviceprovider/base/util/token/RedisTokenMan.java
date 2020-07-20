package com.example.demoserviceprovider.base.util.token;

import com.alibaba.fastjson.JSON;
import com.example.demoserviceprovider.base.Constants;
import com.example.demoserviceprovider.base.util.RedisUtil;
import com.example.demoserviceprovider.entity.RMSUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@Slf4j
public class RedisTokenMan implements ITokenMan {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${spring.redis.token-exp}")
    private int tokenExpiredSeconds;

    @Override
    public String getToken(RMSUser userInfo) {
        String token = "tk_" + UUID.randomUUID().toString().replace("-", "");
        redisUtil.setex(token, JSON.toJSONString(userInfo), tokenExpiredSeconds, Constants.REDIS_DB0);
        log.debug("TOKEN: " + token);
        return token;
    }

    @Override
    public RMSUser getUserInfoByToken(String token) {
        if (!StringUtils.isEmpty(token) && redisUtil.exists(token)) {
            return JSON.parseObject(redisUtil.get(token, Constants.REDIS_DB0), RMSUser.class);
        }
        return null;
    }

    @Override
    public void logout(String token) {
        if (!StringUtils.isEmpty(token)) {
            redisUtil.del(token);
            log.debug("logout TOKEN: " + token);
        }
    }
}
