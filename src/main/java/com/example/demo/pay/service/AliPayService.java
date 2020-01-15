package com.example.demo.pay.service;

import com.alipay.api.AlipayApiException;
import com.example.demo.pay.model.WxTrade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Smart
 * @title: aliPayService
 * @projectName fbstock-core
 * @description: TODO
 * @date 2020/1/613:56
 */
public interface AliPayService {

    /**
     * 异步请求成功做的事情
     * @param notifyData
     * @return
     */
    public int payBack(String notifyData);

    /**
     * 统一创建订单接口
     * @param wxTrade
     * @return
     */
    public String creatOrder(WxTrade wxTrade) throws AlipayApiException;

    /**
     * 异步调用接口封装
     * @param request
     * @param response
     * @return
     */
    public String payNotify(HttpServletRequest request, HttpServletResponse response);
}
