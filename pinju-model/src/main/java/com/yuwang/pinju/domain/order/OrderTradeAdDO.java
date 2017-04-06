package com.yuwang.pinju.domain.order;

import java.util.Date;

/**
 * @Discription: 订单广告订单表
 * @Project: pinju-model
 * @Package: com.yuwang.pinju.domain.order
 * @Title: OrderTradeAdDO.java
 * @author: [贺泳]
 * @date 2011-9-7 下午06:24:02
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderTradeAdDO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 业务编号
     */
    private Long adId;

    /**
     * 子订单编号
     */
    private Long orderItemId;

    /**
     * 广告代码
     */
    private String adCode;

    /**
     * 商品ID
     */
    private Long itemId;

    /**
     * 记录生成时间，数据维护
     */
    private Date gmtCreate;

    /**
     * 记录修改时间，数据维护
     */
    private Date gmtModified;


    public Long getId(){
        return id;
    }

    public Long getAdId(){
        return adId;
    }

    public Long getOrderItemId(){
        return orderItemId;
    }

    public String getAdCode(){
        return adCode;
    }

    public Long getItemId(){
        return itemId;
    }

    public Date getGmtCreate(){
        return gmtCreate;
    }

    public Date getGmtModified(){
        return gmtModified;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setAdId(Long adId){
        this.adId = adId;
    }

    public void setOrderItemId(Long orderItemId){
        this.orderItemId = orderItemId;
    }

    public void setAdCode(String adCode){
        this.adCode = adCode;
    }

    public void setItemId(Long itemId){
        this.itemId = itemId;
    }

    public void setGmtCreate(Date gmtCreate){
        this.gmtCreate = gmtCreate;
    }

    public void setGmtModified(Date gmtModified){
        this.gmtModified = gmtModified;
    }

}

