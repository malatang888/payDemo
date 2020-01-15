package com.example.demo.pay.controller;

import com.alipay.api.AlipayApiException;
import com.example.demo.pay.model.WxTrade;
import com.example.demo.pay.service.AliPayService;
import com.example.demo.pay.service.WxPayService;
import com.example.demo.pay.service.WxTradeService;
import com.example.demo.utils.HttpCode;
import com.example.demo.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Smart
 * @title: AliPayController
 * @projectName fbstock-core
 * @description: TODO
 * @date 2020/1/613:58
 */
@RestController
@RequestMapping("/aliPay")
public class AliPayController {

    @Autowired
    private AliPayService aliPayService;
    @Autowired
    private WxTradeService wxTradeService;
    @Autowired
    private WxPayService wxPayService;


    /**
     * @param userId 充值人
     * @throws AlipayApiException ModelAndView
     */
    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResponse alipay(@RequestParam("userId") String userId, @RequestParam("memberId") String memberId, @RequestParam("type") String type) throws AlipayApiException {
        try {
            WxTrade wxTrade = new WxTrade();
            wxTrade.setUserId(userId);
            wxTrade.setMemberId(memberId);
            wxTrade.setType(type);
            //String creatOrder = aliPayService.creatOrder(wxTrade);//支付宝返回接口
            //0表示微信，1表示支付宝，2表示银行卡，3表示苹果
            if ("0".equals(type)) {//微信
                return new RestResponse(wxPayService.creatOrder(wxTrade), HttpCode.OK, "", "数据请求成功!");
            } else if ("1".equals(type)) {//支付宝
                return new RestResponse(aliPayService.creatOrder(wxTrade), HttpCode.OK, "", "数据请求成功!");
            } else {//银行卡
                return new RestResponse("", HttpCode.OK, "", "数据请求成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new RestResponse("", HttpCode.ERROR, "", "数据请求失败!");
        }
    }

    /**
     * 支付宝支付成功后.回调该接口
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/ali_notify_url", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return aliPayService.payNotify(request, response);
    }

    /**
     * 微信支付成功后回调页面，异步支付成功页面
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/wx_notify_url")
    public String payCallback(HttpServletRequest request, HttpServletResponse response) {
        return wxPayService.payNotify(request, response);
    }

    /**
     * 支付成功后.通知页面
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @date 2017年11月2日
     */
    @RequestMapping(value = "/return_url", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResponse returnUrl(@RequestParam("outTradeNo") String outTradeNo, @RequestParam("userId") String
            userId, HttpServletRequest request) throws UnsupportedEncodingException {
        System.err.println("。。。。。。 同步通知 。。。。。。");
        System.err.println("。。。。。。 同步通知 。。。。。。");
        System.err.println("。。。。。。 同步通知 。。。。。。");
        try {
            WxTrade wxTrade = wxTradeService.selectByTradeNo(outTradeNo);
            // 返回值Map
            if (wxTrade != null) {
                return new RestResponse(outTradeNo, HttpCode.OK, "", "订单支付成功!");
            } else {
                return new RestResponse(outTradeNo, HttpCode.ERROR, "", "无此订单编号!");
            }
        } catch (Exception e) {
            return new RestResponse(outTradeNo, HttpCode.ERROR, "", "订单支付失败!");
        }
    }
}
