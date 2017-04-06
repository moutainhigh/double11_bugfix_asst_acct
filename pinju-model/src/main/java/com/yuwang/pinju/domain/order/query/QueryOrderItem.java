package com.yuwang.pinju.domain.order.query;

import java.util.Arrays;
import java.util.Date;

import com.yuwang.pinju.domain.Paginator;

/**
 * Created on 2011-7-26
 * @see
 * <p>Discription: 子订单查询专用</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class QueryOrderItem extends Paginator{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8147031191915526486L;
	
	/**
	 * 订单外键
	 */
	private Long orderId;

	/**
	 * 商品编号
	 */
	private Long itemId;
	
	/**
	 * 商品名称
	 */
	private String itemTitle;
	
	/**
	 * 结束时间
	 */
	private Date gmtCreateEnd;
	
	/**
	 * 开始时间
	 */
	private Date gmtCreateStart;
	
	/**
	 * 查询的天数范围
	 */
	private Integer day;
	
	/**
	 * 订单类型
	 */
	private int[] bussinessType;

	/**
	 * 要查询的子订单状态
	 */
	private int[] orderItemState;
	
	/**
	 * 买家编号
	 */
	private Long buyerId;
	
	/**
	 * 卖家编号
	 */
	private Long sellerId;
	/**
	 * 修改开始时间
	 */
	private Date gmtModifiedEnd;

	/**
	 * 修改结束时间
	 */
	private Date gmtModifiedStart;
	
	/**
	 * 所有商品总件数
	 */
	private Long allItem;
	
	public QueryOrderItem(){
		
	}
	
	/**
	 * <p>给商品详情页面显示某种购买类型下商品购买列表<p/>
	 * @param itemId
	 * @param day
	 * @param bussinessType
	 * @param orderItemState
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名] 
	 */
	public QueryOrderItem(long itemId,int day,int[] bussinessType,int[] orderItemState){
		this.itemId = itemId;
		this.day = day;
		this.bussinessType = bussinessType;
		this.orderItemState = orderItemState;
	}
	
	public QueryOrderItem(long orderId,int[] orderItemState){
		this.orderId = orderId;
		this.orderItemState = orderItemState;
	}
	
	/**
	 * <p>显示某商品某段时间内,某个买家参与制定活动的购买数量 </p>
	 * @param buyerId
	 * @param itemId
	 * @param bussinessType
	 * @param orderItemState
	 * @param gmtStart
	 * @param gmtEnd
	 */
	public QueryOrderItem(long buyerId,long itemId,int[] bussinessType,int[] orderItemState,Date gmtCreateStart,Date gmtCreateEnd){
		this.buyerId = buyerId;
		this.itemId = itemId;
		this.bussinessType = bussinessType;
		this.orderItemState = orderItemState;
		this.gmtCreateStart = gmtCreateStart;
		this.gmtCreateEnd = gmtCreateEnd;
	}
	
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public int[] getBussinessType() {
		return bussinessType;
	}

	public void setBussinessType(int[] bussinessType) {
		this.bussinessType = bussinessType;
	}

	public int[] getOrderItemState() {
		return orderItemState;
	}

	public void setOrderItemState(int[] orderItemState) {
		this.orderItemState = orderItemState;
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

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}


	@Override
	public String toString() {
		return "QueryOrderItem [bussinessType="
				+ Arrays.toString(bussinessType) + ", buyerId=" + buyerId
				+ ", day=" + day + ", gmtCreateEnd=" + gmtCreateEnd
				+ ", gmtCreateStart=" + gmtCreateStart + ", gmtModifiedEnd="
				+ gmtModifiedEnd + ", gmtModifiedStart=" + gmtModifiedStart
				+ ", itemId=" + itemId + ", itemTitle=" + itemTitle
				+ ", orderId=" + orderId + ", orderItemState="
				+ Arrays.toString(orderItemState) + ", sellerId=" + sellerId
				+ "]";
	}

	public Long getAllItem() {
		return allItem;
	}

	public void setAllItem(Long allItem) {
		this.allItem = allItem;
	}

}

