package com.example.demo.pay.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

/**
 * @author Smart
 * @title: WxPayConfig
 * @projectName demo
 * @description: TODO
 * @date 2020/1/1511:21
 */
@Service
public class WxPayConfig implements WXPayConfig {
    private byte[] certData;

    public WxPayConfig() throws Exception {
        //InputStream certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("cert/wxpay/apiclient_cert.p12");
        InputStream certStream = getClass().getClassLoader().getResourceAsStream("1540638861_20190628_cert.zip");
        this.certData = IOUtils.toByteArray(certStream);
        certStream.close();
    }

    /**
     * 设置我们自己的appid
     * 商户号
     * 秘钥
     */

    @Override
    public String getAppID() {
        return "fasdfasd";
    }

    @Override
    public String getMchID() {
        return "154afdsagdsaf063dsaf8861";
    }

    @Override
    public String getKey() {
        return "sfdsadsfadsfadsfdsafdsafdsafdsaf";
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
    public String getNotifyUrl(){
        return "http://localhost:80/ProductPurchase/payCallback";
    }

}
