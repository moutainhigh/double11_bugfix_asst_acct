package com.yuwang.pinju.domain.trade;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;
import com.yuwang.pinju.domain.order.OrderItemDO;

/**
 * @Project: pinju-model
 * @Description: 支付参数DO
 * @author 石兴 shixing@zba.com
 * @date 2011-8-12 下午03:30:36
 * @update 2011-8-12 下午03:30:36
 * @version V1.0
 */
public class DirectPayParamDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1871117035653090450L;

	/**
	 * 买家ID
	 */
	private Long buyerId;

	/**
	 * 卖家ID
	 */
	private Long sellerId;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

	/**
	 * 卖家昵称
	 */
	private String sellerNick;

	/**
	 * 商品ID
	 */
	private Long itemId;

	/**
	 * 商品标题
	 */
	private String itemTitle;

	/**
	 * 商品价格
	 */
	private Long itemPrice;

	/**
	 * 购买数量
	 */
	private Integer buyAmount;

	/**
	 * 业务类型
	 */
	private Integer bizType;

	/**
	 * 换算后的买家IP
	 */
	private Long buyerIp;
	/**
	 * 生成时间
	 */
	private Date createTime = new Date();
	
	/**
	 * 订单金额
	 */
	private Long orderPrice;
	
	/**
	 * 订单id
	 */
	private Long orderId;
	
	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public Long getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Long itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Integer getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Integer buyAmount) {
		this.buyAmount = buyAmount;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Long getBuyerIp() {
		return buyerIp;
	}

	public void setBuyerIp(Long buyerIp) {
		this.buyerIp = buyerIp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Long orderPrice) {
		this.orderPrice = orderPrice;
	}

	/**
	 * 
	 * Created on 2011-8-15
	 * <p>
	 * Discription: 获取订单金额
	 * </p>
	 * 
	 * @param itemPrice
	 * @param buyAmount
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long getOrderAmount() {
		long amount = 0;
		switch (getBizType()) {
		case OrderItemDO.ORDER_ITEM_TYPE_3:
			amount = this.getOrderPrice();
			break;
		case OrderItemDO.ORDER_ITEM_TYPE_4:
			amount = this.getOrderPrice();
			break;	
		case OrderItemDO.ORDER_ITEM_TYPE_2:
			amount = this.getOrderPrice();
			break;
		default:
			amount = this.itemPrice * this.buyAmount;
			break;
		}
		return amount;
	}
}
