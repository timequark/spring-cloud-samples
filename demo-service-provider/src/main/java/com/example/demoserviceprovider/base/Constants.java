package com.example.demoserviceprovider.base;

public class Constants {
    public static String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static String USER_INFO = "userinfo";

    public static int REDIS_DB0 = 0;
    public static int REDIS_DB1 = 1;

    public static class ReturnCode {
        public static final String SUCCESS = "0";
        public static final String FAILURE = "10001";
    }

    public static class ReturnMsg {
        public static final String SUCCESS = "success";
        public static final String FAILURE = "failure";
        public static final String INVALID_TOKEN = "Invalid " + Constants.ACCESS_TOKEN;
        public static final String LOGIN_FAIL = "Login fail. Check your ID, PASSWORD and VERIFY CODE";
    }

}
