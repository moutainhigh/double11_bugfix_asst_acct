package com.yuwang.pinju.domain.report;

import java.util.Date;

import com.yuwang.pinju.domain.Paginator;

public class SellReportDO extends Paginator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 统计日期
	 */
    private Date stateModifyTime;

    /**
     * 商品编号
     */
    private Long itemId;
    
    /**
     * 卖家编号 
     */
    private Long sellerId;

    /**
     * 商品状态 5订单完成 6订单关闭
     */
    private Long orderItemState;

    /**
     * 购买数量汇总
     */
    private Long buyNum;

    /**
     * 实际结算金额汇总
     */
    private Long orderItemPrice;

    /**
     * 类目编号
     */
    private Long categoryId;

    /**
     * 笔数
     */
    private Long orderCount;

	public Date getStateModifyTime() {
		return stateModifyTime;
	}

	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}


	public Long getOrderItemState() {
		return orderItemState;
	}

	public void setOrderItemState(Long orderItemState) {
		this.orderItemState = orderItemState;
	}

	public Long getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Long buyNum) {
		this.buyNum = buyNum;
	}

	public Long getOrderItemPrice() {
		return orderItemPrice;
	}

	public void setOrderItemPrice(Long orderItemPrice) {
		this.orderItemPrice = orderItemPrice;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

}