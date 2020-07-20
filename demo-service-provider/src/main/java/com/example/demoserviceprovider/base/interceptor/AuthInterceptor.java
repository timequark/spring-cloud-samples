package com.example.demoserviceprovider.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.demoserviceprovider.base.Constants;
import com.example.demoserviceprovider.base.annotation.IgnoreSecurity;
import com.example.demoserviceprovider.base.util.CookieUtil;
import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.entity.resp.BaseResp;
import com.example.demoserviceprovider.service.ServiceRMSUser;
import com.example.demoserviceprovider.base.util.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private ServiceRMSUser serviceRMSUser;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String requestPath = request.getRequestURI();
        log.debug("Request IP: " + NetUtil.getIpAddress(request));
        log.debug("Method: " + method.getName() + ", IgnoreSecurity: " + method.isAnnotationPresent(IgnoreSecurity.class));
        log.debug("requestPath: " + requestPath);

        if (requestPath.contains("/v2/api-docs") || requestPath.contains("/swagger") || requestPath.contains("/configuration/ui")) {
            return true;
        }
        if (requestPath.contains("/error")) {
            return true;
        }
        if (method.isAnnotationPresent(IgnoreSecurity.class)) {
            return true;
        }
        /*String token = request.getHeader(Constants.ACCESS_TOKEN);*/
        String token = CookieUtil.getCookieValue(request, Constants.ACCESS_TOKEN);
        log.debug("token: " + token);
        if (StringUtils.isEmpty(token)) {
            outputInvalidToken(response);
            return false;
        }

        RMSUser userInfo = serviceRMSUser.getUserByToken(token);
        if (userInfo == null) {
            outputInvalidToken(response);
            return false;
        }
        request.setAttribute(Constants.USER_INFO, userInfo);

        log.info(String.format("set attribute '%s' to %s", Constants.USER_INFO, JSON.toJSONString(userInfo)));
        return true;
    }

    private void outputInvalidToken(HttpServletResponse response) throws Exception {
        try {
            BaseResp<String> resp = new BaseResp<>();
            resp.code = Constants.ReturnCode.FAILURE;
            resp.msg = Constants.ReturnMsg.FAILURE;
            resp.data = Constants.ReturnMsg.INVALID_TOKEN;

            response.setHeader("Content-type", "application/json;charset=UTF-8");
            Writer writer = response.getWriter();
            writer.write(JSON.toJSONString(resp));
        } catch (Exception e) {
            throw e;
        }

    }
}
