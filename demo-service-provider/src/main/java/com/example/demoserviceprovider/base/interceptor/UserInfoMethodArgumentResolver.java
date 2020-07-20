package com.example.demoserviceprovider.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.demoserviceprovider.base.Constants;
import com.example.demoserviceprovider.base.annotation.Token;
import com.example.demoserviceprovider.base.annotation.UserInfo;
import com.example.demoserviceprovider.base.util.CookieUtil;
import com.example.demoserviceprovider.entity.RMSUser;
import org.apache.commons.lang.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class UserInfoMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean support = false;

        if (parameter.getParameterType().isAssignableFrom(RMSUser.class)
                && parameter.hasParameterAnnotation(UserInfo.class))
            support = true;
        else if (parameter.getParameterType().isAssignableFrom(String.class)
                && parameter.hasParameterAnnotation(Token.class))
            support = true;

        return support;
    }
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.hasParameterAnnotation(Token.class)) {
            HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
            String token = CookieUtil.getCookieValue(request, Constants.ACCESS_TOKEN);
            if (!StringUtils.isEmpty(token)) {
                return token;
            }

            throw new MissingServletRequestPartException(Constants.ACCESS_TOKEN);
        }
        else if (parameter.hasParameterAnnotation(UserInfo.class)) {
            RMSUser userInfo = (RMSUser) webRequest.getAttribute(Constants.USER_INFO, RequestAttributes.SCOPE_REQUEST);
            if (userInfo != null) {
                log.info(String.format("get attribute '%s' as %s", Constants.USER_INFO, JSON.toJSONString(userInfo)));
                return userInfo;
            }

            throw new MissingServletRequestPartException(Constants.USER_INFO);
        }

        return null;
    }
}
