package com.example.p2pinvest.common;

/**
 * Created by shkstart on 2016/12/2 0002.
 * 配置网络请求相关的地址
 */
public class AppNetConfig {

    public static final String IPADDRESS = "192.168.0.3";
    //public static final String IPADDRESS = "localhost";

    public static final String BASE_URL = "http://" + IPADDRESS + ":8080/WebRoot/";

    public static final String PRODUCT = BASE_URL + "file/product.json";//访问“全部理财”产品

    public static final String LOGIN = BASE_URL + "P2PInvest/src";//登录

    public static final String INDEX = BASE_URL + "file/index.json";//访问“homeFragment”

    public static final String USERREGISTER = BASE_URL + "UserRegister";//访问“homeFragment”

    public static final String FEEDBACK = BASE_URL + "FeedBack";//注册

    public static final String UPDATE = BASE_URL + "update.json";//更新应用


}
