package com.example.demo.pay.model;

import java.math.BigDecimal;

public class WxpayParam {


    /** 微信支付的金额是String类型 并且是以分为单位
     * 下面举个例子单位是元是怎么转为分的
     * */
    BigDecimal totalPrice  = new BigDecimal(1); //此时的单位是元

    private String body = "产品信息";
    private String totalFee = totalPrice.multiply(new BigDecimal(100)).toBigInteger().toString();
    /** 随机数字字符串*/
    private String outTradeNo = "4784984230432842944002";

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}