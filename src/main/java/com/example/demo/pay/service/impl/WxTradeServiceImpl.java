package com.example.demo.pay.service.impl;

import com.example.demo.base.FbBaseServiceImpl;
import com.example.demo.pay.dao.WxTradeDao;
import com.example.demo.pay.model.WxTrade;
import com.example.demo.pay.service.WxTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class WxTradeServiceImpl extends FbBaseServiceImpl<WxTrade> implements WxTradeService {
    @Autowired
    private WxTradeDao baseMapper;

    @Autowired
    @Override
    public void setBaseMapper() {
        super.setBaseMapper(baseMapper);
    }

    @Override
    public WxTrade selectByTradeNo(String tradeNo) {
        return baseMapper.selectByTradeNo(tradeNo);
    }

    @Override
    public WxTrade insert(WxTrade wxTrade) {
        //0表示微信，1表示支付宝，2表示银行卡，3表示苹果
        //根据商品id查询价格以及其他参数
        //RechargePoint rechargePoint = wxTicketService.findRechargePointById(memberId);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfNo = new SimpleDateFormat("yyyyMMddHHmmss");
        wxTrade.setTradeNo(sdfNo.format(date));
        /*wxTrade.setMemberId(memberId);
        wxTrade.setUserId(userId);*/
        wxTrade.setTotalFee("6");//从商品表中得到的数据
        wxTrade.setCreateDate(sdf.format(date));
        wxTrade.setStatus("0");//未支付
        //wxTrade.setType("0");//支付宝支付
        baseMapper.insert(wxTrade);
        return wxTrade;
    }

    @Override
    public int updateWxTradeStatus(String tradeNo) {
        return baseMapper.updateWxTradeStatus(tradeNo);
    }
}