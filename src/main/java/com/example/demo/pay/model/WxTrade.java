package com.example.demo.pay.model;

/**
 * 订单类
 */
public class WxTrade {
    /**
     * 订单编号，唯一
     */
    private String tradeNo;//订单编号，唯一
    /**
     * 用户id
     */
    private String userId;
    /**
     * 商品id
     */
    private String memberId;
    /**
     * 支付状态，
     */
    private String status;//0是未支付，1是支付成功，2是支付失败
    /**
     * 支付钱
     */
    private String totalFee;//单位元
    /**
     * 创建订单时间
     */
    private String createDate;
    /**
     * 更新订单时间
     */
    private String updateDate;
    /**
     * 支付渠道
     */
    private String type;//0表示微信，1表示支付宝，2表示银行卡，3表示苹果

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
