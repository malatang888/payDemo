package com.example.demo.pay.service;

import com.example.demo.base.FbBaseService;
import com.example.demo.pay.model.WxTrade;

/**
 * 订单
 */
public interface WxTradeService extends FbBaseService<WxTrade> {
    /**
     * 生成订单，未支付订单
     * @param entity
     * @return
     */
    WxTrade insert(WxTrade entity);

    /**
     * 根据订单编号查询订单信息
     * @param tradeNo
     * @return
     */
    WxTrade selectByTradeNo(String tradeNo);

    /**
     * 更新订单信息，将支付状态更改成已支付状态。
     * @param tradeNo
     * @return
     */
    int updateWxTradeStatus(String tradeNo);

}