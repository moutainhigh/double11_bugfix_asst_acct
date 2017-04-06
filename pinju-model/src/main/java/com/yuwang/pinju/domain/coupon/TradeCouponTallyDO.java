package com.yuwang.pinju.domain.coupon;

import java.util.Date;

public class TradeCouponTallyDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;


    private Long id;

    /**
     * 批次表id
     */
    private Long couponBatchId;

    /**
     * 买家id
     */
    private Long buyerId;

    /**
     * 买家name
     */
    private String buyerNick;

    /**
     * 领取的优惠券张数
     */
    private Integer couponTally;

    /**
     * 领取优惠券数量 更新时间
     */
    private Date tallyModifyDate;

    /**
     * 生成时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;


    public Long getId(){
        return id;
    }

    public Long getCouponBatchId(){
        return couponBatchId;
    }

    public Long getBuyerId(){
        return buyerId;
    }

    public String getBuyerNick(){
        return buyerNick;
    }

    public Integer getCouponTally(){
        return couponTally;
    }

    public Date getTallyModifyDate(){
        return tallyModifyDate;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModify(){
        return gmtModify;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setCouponBatchId(Long couponBatchId){
        this.couponBatchId = couponBatchId;
    }

    public void setBuyerId(Long buyerId){
        this.buyerId = buyerId;
    }

    public void setBuyerNick(String buyerNick){
        this.buyerNick = buyerNick;
    }

    public void setCouponTally(Integer couponTally){
        this.couponTally = couponTally;
    }

    public void setTallyModifyDate(Date tallyModifyDate){
        this.tallyModifyDate = tallyModifyDate;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModify(Date gmtModify){
        this.gmtModify = gmtModify;
    }

}

