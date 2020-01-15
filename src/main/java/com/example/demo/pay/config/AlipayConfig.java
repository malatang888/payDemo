package com.example.demo.pay.config;

/**
 * @author Smart
 * @title: AlipayConfig
 * @projectName fbstock-core
 * @description: TODO
 * @date 2020/1/613:52
 */

public class AlipayConfig {

    // 1.商户appid
    public static String APPID = "";


    // 2.私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "";

    // 3.支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "";

    // 4.服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "";

    // 5.页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "";

    // 6.请求网关地址
    //public static String URL = "https://openapi.alipay.com/gateway.do";
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 7.编码
    public static String CHARSET = "UTF-8";

    // 8.返回格式
    public static String FORMAT = "json";

    // 9.加密类型
    public static String SIGNTYPE = "RSA2";
    //10、商家id
    /**
     * 商家id
     */
    public static String SELLERID = "";

}

