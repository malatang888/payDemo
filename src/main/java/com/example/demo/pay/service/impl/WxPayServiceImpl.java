package com.example.demo.pay.service.impl;

import com.example.demo.pay.model.WxTrade;
import com.example.demo.pay.model.WxpayParam;
import com.example.demo.pay.service.WxPayService;
import com.example.demo.pay.service.WxTradeService;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Smart
 * @title: WxPayServiceImpl
 * @projectName demo
 * @description: TODO
 * @date 2020/1/1511:11
 */
@Service
public class WxPayServiceImpl implements WxPayService {
    @Autowired
    private WxTradeService wxTradeService;
    @Autowired
    private WxPayConfig wxPayConfig;


    @Override
    public String payBack(String notifyData) {
        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            WXPay wxpay = new WXPay(wxPayConfig);
            notifyMap = WXPayUtil.xmlToMap(notifyData);         // 转换成map
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {
                // 签名正确
                // 进行处理。
                // 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
                String return_code = notifyMap.get("return_code");//状态
                String out_trade_no = notifyMap.get("out_trade_no");//订单号
                if (out_trade_no == null) {
                    xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }
                wxTradeService.updateWxTradeStatus(out_trade_no);
                WxTrade wxTrade = wxTradeService.selectByTradeNo(out_trade_no);
                if (wxTrade!=null) {//微信支付
                    //this.updateSometings(wxTrade.getUserId(), wxTrade.getMemberId(), "1");
                    //业务处理
                    xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[SUCCESS]]></return_msg>" + "</xml> ";
                    return xmlBack;
                }
            } else {
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            e.printStackTrace();
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }

    @Override
    public Map<String, String> creatOrder(WxTrade wxTrade) throws Exception {
        WxpayParam wxpayParam = new WxpayParam();
        wxTradeService.insert(wxTrade);
        BigDecimal totalPrice = new BigDecimal(wxTrade.getTotalFee()); //此时的单位是元
        String Fee = totalPrice.multiply(new BigDecimal(100)).toBigInteger().toString();//分
        wxpayParam.setTotalFee(Fee);
        WxPayConfig ourWxPayConfig = new WxPayConfig();
        WXPay wxPay = new WXPay(ourWxPayConfig);

        //根据微信支付api来设置
        Map<String, String> data = new HashMap<>();
        data.put("appid", ourWxPayConfig.getAppID());
        data.put("mch_id", ourWxPayConfig.getMchID());         //商户号
        data.put("trade_type", "APP");                         //支付场景 APP 微信app支付 JSAPI 公众号支付  NATIVE 扫码支付
        data.put("notify_url", ourWxPayConfig.getNotifyUrl());                     //回调地址
        data.put("spbill_create_ip", "127.0.0.1");             //终端ip
        data.put("total_fee", wxpayParam.getTotalFee());       //订单总金额wxpayParam.getTotalFee()
        data.put("fee_type", "CNY");                           //默认人民币
        data.put("out_trade_no", wxTrade.getTradeNo());   //交易号wxpayParam.getOutTradeNo()
        data.put("body", wxpayParam.getBody());
        data.put("nonce_str", "bfrhncjkfdkfd");   // 随机字符串小于32位
        String s = WXPayUtil.generateSignature(data, ourWxPayConfig.getKey());  //签名
        data.put("sign", s);
        /** wxPay.unifiedOrder 这个方法中调用微信统一下单接口 */
        Map<String, String> respData = wxPay.unifiedOrder(data);
        if (respData.get("return_code").equals("SUCCESS")) {
            try {
                //返回给APP端的参数，APP端再调起支付接口
                Map<String, String> repData = new HashMap<>();
                repData.put("appid", ourWxPayConfig.getAppID());
                repData.put("mch_id", ourWxPayConfig.getMchID());
                repData.put("prepayid", respData.get("prepay_id"));
                repData.put("package", "WXPay");
                repData.put("noncestr", respData.get("nonce_str"));
                repData.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                String sign = WXPayUtil.generateSignature(repData, ourWxPayConfig.getKey()); //签名
                respData.put("sign", sign);
                respData.put("timestamp", repData.get("timestamp"));
                respData.put("package", "WXPay");
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(respData.get("err_code_des"));
            }
            return respData;
        }
        throw new Exception(respData.get("err_code_des"));
    }

    @Override
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        String resXml = "";
        try {
            InputStream is = request.getInputStream();
            //将InputStream转换成String
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                is.close();
            }
            resXml = sb.toString();
            return this.payBack(resXml);
        } catch (Exception e) {
            e.printStackTrace();
            return "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
    }
}
