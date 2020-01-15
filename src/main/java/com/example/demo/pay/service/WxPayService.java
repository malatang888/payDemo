package com.example.demo.pay.service;

import com.example.demo.pay.model.WxTrade;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Smart
 * @title: WxPayService
 * @projectName demo
 * @description: TODO
 * @date 2020/1/1511:09
 */
public interface WxPayService {

    /**
     * 异步请求成功做的事情
     *
     * @param notifyData
     * @return
     */
    public String payBack(String notifyData);

    /**
     * 统一创建订单接口
     *
     * @param wxTrade
     * @return
     */
    public Map<String, String> creatOrder(WxTrade wxTrade) throws Exception;

    /**
     * 异步调用接口封装
     *
     * @param request
     * @param response
     * @return
     */
    public String payNotify(HttpServletRequest request, HttpServletResponse response);
}
