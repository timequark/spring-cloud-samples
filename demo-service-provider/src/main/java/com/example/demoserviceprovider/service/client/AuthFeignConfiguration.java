package com.example.demoserviceprovider.service.client;

import com.example.demoserviceprovider.base.Constants;
import com.example.demoserviceprovider.base.util.CookieUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AuthFeignConfiguration {

    @Bean
    public RequestInterceptor headerInterceptor() {
        return (requestTemplate) -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            /*String token = request.getHeader(Constants.ACCESS_TOKEN);*/
            String token = CookieUtil.getCookieValue(request, Constants.ACCESS_TOKEN);

            log.warn(String.format("%s: %s", Constants.ACCESS_TOKEN, "" + token));

            if (!StringUtils.isEmpty(token)) {
                requestTemplate.header(Constants.ACCESS_TOKEN, token);
            }
        };
    }

    @Bean
    public Retryer feignRetryer(){
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 5);
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "password");
    }
}
