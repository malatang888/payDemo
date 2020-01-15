package com.example.demo.pay.dao;

import com.example.demo.base.FbBaseDao;
import com.example.demo.pay.model.WxTrade;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxTradeDao extends FbBaseDao<WxTrade> {

    /**
     * 生成订单，未支付订单
     * @param entity
     * @return
     */
    int insert(WxTrade entity);

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