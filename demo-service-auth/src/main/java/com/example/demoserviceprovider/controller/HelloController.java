package com.example.demoserviceprovider.controller;

import com.example.demoserviceprovider.base.Constants;
import com.example.demoserviceprovider.base.annotation.IgnoreSecurity;
import com.example.demoserviceprovider.base.annotation.Token;
import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.entity.req.UserLogin;
import com.example.demoserviceprovider.entity.resp.BaseResp;
import com.example.demoserviceprovider.entity.resp.RespLogin;
import com.example.demoserviceprovider.service.ServiceRMSUser;
import com.example.demoserviceprovider.base.annotation.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api("Authorization module")
@RestController
@Slf4j
public class HelloController {
    /*@Value("${server.port}")
    private String port;

    @Value("${app.fallbackThreshold}")
    private int fallbackThreshold;

    private static int fallback = 0;*/

    @Autowired
    private ServiceRMSUser serviceRMSUser;

    /*@ApiOperation(value="测试接口", notes="返回hello {name} from port #port")
    @ApiImplicitParam(name = "name", value = "your name", required = true, dataType = "String")
    @GetMapping("/test/{name}")
    public String test(@PathVariable String name) throws Exception{
        fallback++;
        log.info("fallbackThreshold = " + fallbackThreshold);
        log.info("fallback = " + fallback);
        if (fallback > fallbackThreshold)
            throw new Exception("bussiness exception from Service-Provider");

        return "hello " + name + " from port " + port;
    }*/

    @IgnoreSecurity
    @PostMapping("/login")
    public BaseResp<RespLogin> login(@RequestBody UserLogin userLogin) {
        BaseResp<RespLogin> resp = new BaseResp<>();
        if (/*userLogin.usercode.equals("test") && userLogin.password.equals("12345") && */userLogin.verification.equals("111111")) {
            resp.code = Constants.ReturnCode.SUCCESS;
            resp.msg = Constants.ReturnMsg.SUCCESS;

            RMSUser userInfo = serviceRMSUser.getRMSUserByUserCode(userLogin.usercode);
            RespLogin data = new RespLogin(serviceRMSUser.getToken(userInfo));
            resp.data = data;

            return resp;
        }

        resp.code = Constants.ReturnCode.FAILURE;
        resp.msg = Constants.ReturnMsg.FAILURE;
        return resp;
    }

    @PostMapping("/user")
    public BaseResp<RMSUser> getUserInfo(@ApiIgnore @Token String token) {
        RMSUser userInfo = serviceRMSUser.getUserByToken(token);

        BaseResp<RMSUser> resp = new BaseResp<>();
        resp.code = Constants.ReturnCode.SUCCESS;
        resp.msg = Constants.ReturnMsg.SUCCESS;
        resp.data = userInfo;

        return resp;
    }

    @PostMapping("/users")
    public BaseResp<List<RMSUser>> users(@ApiIgnore @Token String token) {
        // TODO: check user role

        BaseResp<List<RMSUser>> resp = new BaseResp<List<RMSUser>>();
        resp.code = Constants.ReturnCode.SUCCESS;
        resp.msg = Constants.ReturnMsg.SUCCESS;
        resp.data = serviceRMSUser.getAllRMSUser();

        return resp;
    }

    @PostMapping("/logout")
    public BaseResp<String> logout(@ApiIgnore @Token String token) {
        serviceRMSUser.logout(token);

        BaseResp<String> resp = new BaseResp<>();
        resp.code = Constants.ReturnCode.SUCCESS;
        resp.msg = Constants.ReturnMsg.SUCCESS;
        resp.data = "";

        return resp;
    }
}
