package com.example.demo.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.example.demo.pay.config.AlipayConfig;
import com.example.demo.pay.model.WxTrade;
import com.example.demo.pay.service.AliPayService;
import com.example.demo.pay.service.WxTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Smart
 * @title: AliPayServiceImpl
 * @projectName fbstock-core
 * @description: TODO
 * @date 2020/1/613:56
 */
@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    private WxTradeService wxTradeService;

    //    @Autowired
//    private WxTicketService wxTicketService;
    @Override
    @Transactional
    public int payBack(String notifyData) {
        try {
            /*wxTradeService.updateWxTradeStatus(notifyData);
            WxTrade wxTrade = wxTradeService.selectByTradeNo(notifyData);
            if ("0".equals(wxTrade.getType())) {//0是支付宝支付
                wxTicketService.updateSometings(wxTrade.getUserId(),wxTrade.getMemberId(),"0");//做自己业务方面的处理。
            }*/
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public String creatOrder(WxTrade trade) throws AlipayApiException {
        /****** 1.封装你的交易订单开始 *****/                                        //自己用
        //此处封装你的订单数据，订单状态可以设置为等待支付
        WxTrade wxTrade = wxTradeService.insert(trade);
        /****** 1.封装你的交易订单结束 *****/
        Map<String, String> orderMap = new LinkedHashMap<String, String>();            //订单实体
        Map<String, String> bizModel = new LinkedHashMap<String, String>();            //公共实体
        /****** 2.商品参数封装开始 *****/                                            //手机端用
        // 商户订单号，商户网站订单系统中唯一订单号，必填
        orderMap.put("out_trade_no", wxTrade.getTradeNo());
        // 订单名称，必填
        orderMap.put("subject", "购买富巴币");
        // 付款金额，必填
        orderMap.put("total_amount", String.valueOf(wxTrade.getTotalFee()));
        // 商品描述，可空
        orderMap.put("body", "您购买富巴币" + String.valueOf(wxTrade.getTotalFee()) + "元");
        // 超时时间 可空
        orderMap.put("timeout_express", "30m");
        // 销售产品码 必填
        orderMap.put("product_code", "QUICK_WAP_PAY");

        /****** 2.商品参数封装结束 *****/

        /******--------------- 3.公共参数封装 开始 ------------------------*****/        //支付宝用
        //1.商户appid
        bizModel.put("app_id", AlipayConfig.APPID);
        //2.请求网关地址
        bizModel.put("method", AlipayConfig.URL);
        //3.请求格式
        bizModel.put("format", AlipayConfig.FORMAT);
        //4.回调地址
        bizModel.put("return_url", AlipayConfig.return_url);
        //5.私钥
        bizModel.put("private_key", AlipayConfig.RSA_PRIVATE_KEY);
        //6.商家id
        bizModel.put("seller_id", AlipayConfig.SELLERID);
        //7.加密格式
        bizModel.put("sign_type", AlipayConfig.SIGNTYPE + "");

        /******--------------- 3.公共参数封装 结束 ------------------------*****/

        //实例化客户端
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);

        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();

        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setPassbackParams(URLEncoder.encode(orderMap.get("body")));
        //描述信息  添加附加数据
        model.setBody(orderMap.get("body"));                        //商品信息
        model.setSubject(orderMap.get("subject"));                  //商品名称
        model.setOutTradeNo(orderMap.get("out_trade_no"));          //商户订单号(自动生成)
        model.setTimeoutExpress(orderMap.get("timeout_express"));     //交易超时时间
        model.setTotalAmount(orderMap.get("total_amount"));         //支付金额
        model.setProductCode(orderMap.get("product_code"));         //销售产品码
        model.setSellerId(AlipayConfig.SELLERID);                        //商家id
        ali_request.setBizModel(model);
        ali_request.setNotifyUrl(AlipayConfig.notify_url);          //回调地址

        AlipayTradeAppPayResponse response = client.sdkExecute(ali_request);
        String orderStr = response.getBody();
        System.err.println(orderStr);                                //就是orderString 可以直接给客户端请求，无需再做处理。
        return orderStr;
    }

    @Override
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> params = new HashMap<String, String>();
        //1.从支付宝回调的request域中取值
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //2.封装必须参数
        String out_trade_no = request.getParameter("out_trade_no");            // 商户订单号
        String orderType = request.getParameter("body");                    // 订单内容
        String tradeStatus = request.getParameter("trade_status");            //交易状态

        //3.签名验证(对支付宝返回的数据验证，确定是支付宝返回的)
        boolean signVerified = false;
        try {
            //3.1调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        //4.对验签进行处理
        if (signVerified) {    //验签通过
            if (tradeStatus.equals("TRADE_SUCCESS")) {    //只处理支付成功的订单: 修改交易表状态,支付成功
                int s = this.payBack(out_trade_no);//根据订单编号去查询更改其数据订单格式
                if (s > 0) {
                    return "success";
                } else {
                    return "fail";
                }
            } else {
                return "fail";
            }
        } else {  //验签不通过
            System.err.println("验签失败");
            return "fail";
        }
    }
}
