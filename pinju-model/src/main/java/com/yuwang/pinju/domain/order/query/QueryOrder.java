package com.yuwang.pinju.domain.order.query;

import java.util.Date;

import com.yuwang.pinju.domain.Paginator;

/**
 * Created on 2011-7-26
 * @see
 * <p>Discription: 主订单查询专用</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class QueryOrder extends Paginator{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8147031191915526486L;
	/**
	 * 买家编号
	 */
	private Long buyerId;
	
	/**
	 * 卖家编号
	 */
	private Long sellerId;
	/**
	 * 卖家昵称
	 */
	private String sellerNick;
	
	/**
	 * 买家昵称
	 */
	private String buyerNick;
	/**
	 * 订单状态
	 */
	private Integer[] orderState;
	/**
	 * 买家是否已评价
	 */
	private Integer isBuyerRate;
	/**
	 * 卖家是否已评价
	 */
	private Integer isSellerRate;
	
	/**
	 * 结束时间
	 */
	private Date gmtCreateEnd;
	
	/**
	 * 开始时间
	 */
	private Date gmtCreateStart;
	
	/**
	 * 修改开始时间
	 */
	private Date gmtModifiedEnd;

	/**
	 * 修改结束时间
	 */
	private Date gmtModifiedStart;

	private long startNum;
	
	private long endNum;
	
	public QueryOrder(){
		
	}
	
	
	public QueryOrder(Long buyerId, Long sellerId, String sellerNick,
			String buyerNick, Integer[] orderState, Integer isBuyerRate,
			Integer isSellerRate, Date gmtCreateEnd, Date gmtCreateStart,
			Date gmtModifiedEnd, Date gmtModifiedStart) {
		super();
		this.buyerId = buyerId;
		this.sellerId = sellerId;
		this.sellerNick = sellerNick;
		this.buyerNick = buyerNick;
		this.orderState = orderState;
		this.isBuyerRate = isBuyerRate;
		this.isSellerRate = isSellerRate;
		this.gmtCreateEnd = gmtCreateEnd;
		this.gmtCreateStart = gmtCreateStart;
		this.gmtModifiedEnd = gmtModifiedEnd;
		this.gmtModifiedStart = gmtModifiedStart;
		super.setItemsPerPage(5);
	}


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


	public String getSellerNick() {
		return sellerNick;
	}


	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}


	public String getBuyerNick() {
		return buyerNick;
	}


	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}


	public Integer[] getOrderState() {
		return orderState;
	}


	public void setOrderState(Integer[] orderState) {
		this.orderState = orderState;
	}


	public Integer getIsBuyerRate() {
		return isBuyerRate;
	}


	public void setIsBuyerRate(Integer isBuyerRate) {
		this.isBuyerRate = isBuyerRate;
	}


	public Integer getIsSellerRate() {
		return isSellerRate;
	}


	public void setIsSellerRate(Integer isSellerRate) {
		this.isSellerRate = isSellerRate;
	}


	public Date getGmtCreateEnd() {
		return gmtCreateEnd;
	}


	public void setGmtCreateEnd(Date gmtCreateEnd) {
		this.gmtCreateEnd = gmtCreateEnd;
	}


	public Date getGmtCreateStart() {
		return gmtCreateStart;
	}


	public void setGmtCreateStart(Date gmtCreateStart) {
		this.gmtCreateStart = gmtCreateStart;
	}


	public Date getGmtModifiedEnd() {
		return gmtModifiedEnd;
	}


	public void setGmtModifiedEnd(Date gmtModifiedEnd) {
		this.gmtModifiedEnd = gmtModifiedEnd;
	}


	public Date getGmtModifiedStart() {
		return gmtModifiedStart;
	}


	public void setGmtModifiedStart(Date gmtModifiedStart) {
		this.gmtModifiedStart = gmtModifiedStart;
	}


	public long getStartNum() {
		return startNum;
	}


	public void setStartNum(long startNum) {
		this.startNum = startNum;
	}


	public long getEndNum() {
		return endNum;
	}


	public void setEndNum(long endNum) {
		this.endNum = endNum;
	}

}

