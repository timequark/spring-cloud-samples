package com.example.demoserviceprovider.controller;

import com.example.demoserviceprovider.base.Constants;
import com.example.demoserviceprovider.base.annotation.IgnoreSecurity;
import com.example.demoserviceprovider.base.annotation.Token;
import com.example.demoserviceprovider.base.util.CookieUtil;
import com.example.demoserviceprovider.entity.DimHotels;
import com.example.demoserviceprovider.entity.ETLMarketseg;
import com.example.demoserviceprovider.entity.HierActual;
import com.example.demoserviceprovider.entity.RMSUser;
import com.example.demoserviceprovider.entity.req.ReqClearCache;
import com.example.demoserviceprovider.entity.req.ReqHierActual;
import com.example.demoserviceprovider.entity.req.ReqHotelInfo;
import com.example.demoserviceprovider.entity.req.UserLogin;
import com.example.demoserviceprovider.entity.resp.BaseResp;
import com.example.demoserviceprovider.entity.resp.RespLogin;
import com.example.demoserviceprovider.service.ServiceCommon;
import com.example.demoserviceprovider.service.ServiceHierActual;
import com.example.demoserviceprovider.service.ServiceRMSUser;
import com.example.demoserviceprovider.base.annotation.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api("HelloWorld module")
@RestController
@Slf4j
public class HelloWorldController {
    @Value("${server.port}")
    private String port;

    @Value("${app.fallbackThreshold}")
    private int fallbackThreshold;

    private static int fallback = 0;

    @Autowired
    private ServiceRMSUser serviceRMSUser;
    @Autowired
    private ServiceHierActual serviceHierActual;
    @Autowired
    private ServiceCommon serviceCommon;

    @ApiOperation(value="测试接口", notes="返回hello {name} from port #port")
    @ApiImplicitParam(name = "name", value = "your name", required = true, dataType = "String")
    @IgnoreSecurity
    @GetMapping("/test/{name}")
    public String test(@PathVariable String name) throws Exception{
        fallback++;
        log.info("fallbackThreshold = " + fallbackThreshold);
        log.info("fallback = " + fallback);
        if (fallback > fallbackThreshold)
            throw new Exception("bussiness exception from Service-Provider");

        return "hello " + name + " from port " + port;
    }

    @IgnoreSecurity
    @PostMapping("/login")
    public BaseResp<RespLogin> login(@ApiIgnore HttpServletResponse response, @RequestBody UserLogin userLogin) {
        BaseResp<RespLogin> resp = new BaseResp<>();

        String token = serviceRMSUser.getToken(userLogin);

        if (!StringUtils.isEmpty(token)) {
            CookieUtil.setCookie(response, Constants.ACCESS_TOKEN, token);

            resp.code = Constants.ReturnCode.SUCCESS;
            resp.msg = Constants.ReturnMsg.SUCCESS;
            resp.data = new RespLogin(token);
        } else {
            resp.code = Constants.ReturnCode.FAILURE;
            resp.msg = Constants.ReturnMsg.LOGIN_FAIL;
        }

        return resp;
    }

    @GetMapping("/user")
    public BaseResp<RMSUser> user(@ApiIgnore @Token String token) {
        BaseResp<RMSUser> resp = new BaseResp<>();

        RMSUser rmsUser = serviceRMSUser.getUserByToken(token);

        if (rmsUser != null) {
            resp.code = Constants.ReturnCode.SUCCESS;
            resp.msg = Constants.ReturnMsg.SUCCESS;
            resp.data = rmsUser;
        } else {
            resp.code = Constants.ReturnCode.FAILURE;
            resp.msg = Constants.ReturnMsg.INVALID_TOKEN;
        }

        return resp;
    }

    @GetMapping("/users")
    public BaseResp<List<RMSUser>> users(@ApiIgnore @Token String token) {
        BaseResp<List<RMSUser>> resp = new BaseResp<>();

        List<RMSUser> rmsUsers = serviceRMSUser.getUsers(token);

        resp.code = Constants.ReturnCode.SUCCESS;
        resp.msg = Constants.ReturnMsg.SUCCESS;
        resp.data = rmsUsers;

        return resp;
    }

    @PostMapping("/user/update")
    @ApiImplicitParam(name = "name", value = "user's new name")
    public BaseResp<String> updateUserName(@ApiIgnore @UserInfo RMSUser userInfo, String name) {
        BaseResp<String> resp = new BaseResp<>();

        resp.code = Constants.ReturnCode.SUCCESS;
        resp.msg = Constants.ReturnMsg.SUCCESS;
        resp.data = String.format("'%s's new name is '%s'", userInfo.getUserCode(), name);

        return resp;
    }

    @GetMapping("/logout")
    public BaseResp<String> logout(@ApiIgnore HttpServletRequest request, @ApiIgnore HttpServletResponse response, @ApiIgnore @Token String token) {
        serviceRMSUser.logout(token);

        CookieUtil.removeCookie(request, response, Constants.ACCESS_TOKEN);

        BaseResp<String> resp = new BaseResp<>();
        resp.code = Constants.ReturnCode.SUCCESS;
        resp.msg = Constants.ReturnMsg.SUCCESS;
        resp.data = "";

        return resp;
    }





    @ApiOperation(value="获取Actual明细")
    @PostMapping("/hieractual/detail")
    public BaseResp<List<HierActual>> getHierActualDetail(@RequestBody ReqHierActual req) {
        BaseResp<List<HierActual>> resp = new BaseResp<>();

        DimHotels dimHotels = serviceCommon.getDimHotels(req.hotelcode);
        List<HierActual> actuals = serviceHierActual.getHierActualDetail(req.hotelcode, dimHotels.getHotelKey(), req.fromBiz, req.toBiz);
        if (actuals != null) {
            resp.code = Constants.ReturnCode.SUCCESS;
            resp.msg = Constants.ReturnMsg.SUCCESS;
            resp.data = actuals;
        } else {
            resp.code = Constants.ReturnCode.FAILURE;
            resp.msg = Constants.ReturnMsg.FAILURE;
        }

        return resp;
    }

    @ApiOperation(value="获取Actual汇总")
    @PostMapping("/hieractual/summary")
    public BaseResp<List<HierActual>> getHierActualSummary(@RequestBody ReqHierActual req) {
        BaseResp<List<HierActual>> resp = new BaseResp<>();

        DimHotels dimHotels = serviceCommon.getDimHotels(req.hotelcode);
        List<HierActual> actuals = serviceHierActual.getHierActualSummaryByDay(req.hotelcode, dimHotels.getHotelKey(), req.fromBiz, req.toBiz);
        if (actuals != null) {
            resp.code = Constants.ReturnCode.SUCCESS;
            resp.msg = Constants.ReturnMsg.SUCCESS;
            resp.data = actuals;
        } else {
            resp.code = Constants.ReturnCode.FAILURE;
            resp.msg = Constants.ReturnMsg.FAILURE;
        }

        return resp;
    }

    @PostMapping("/hotelinfo")
    public BaseResp<DimHotels> getHotelInfo(@ApiIgnore @Token String token, @RequestBody ReqHotelInfo req) {
        BaseResp<DimHotels> resp = new BaseResp<>();

        DimHotels dimHotels = serviceCommon.getDimHotels(req.hotelcode);

        List<ETLMarketseg> mktsegs = serviceCommon.getETLMarketseg(req.hotelcode, dimHotels.getHotelKey());

        if (dimHotels != null) {
            resp.code = Constants.ReturnCode.SUCCESS;
            resp.msg = Constants.ReturnMsg.SUCCESS;
            resp.data = dimHotels;
        } else {
            resp.code = Constants.ReturnCode.FAILURE;
            resp.msg = Constants.ReturnMsg.FAILURE;
        }

        return resp;
    }

    @PostMapping("/clearcache")
    public BaseResp<String> clearCache(@ApiIgnore @Token String token, @RequestBody ReqClearCache req) {
        BaseResp<String> resp = new BaseResp<>();

        // TODO: Check permission of user

        serviceCommon.clearCache(req.hotelcode);

        resp.code = Constants.ReturnCode.SUCCESS;
        resp.msg = Constants.ReturnMsg.SUCCESS;
        resp.data = "ok";

        return resp;
    }
}
