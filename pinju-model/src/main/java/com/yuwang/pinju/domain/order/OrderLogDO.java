package com.yuwang.pinju.domain.order;

import java.util.Date;

/**
 * 订单日志
 * 
 * @author 杜成
 * @version 1.0
 * @since 2011-06-01
 */
public class OrderLogDO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -593730569551473853L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	// 主键
	private Long orderLogId;
	// 订单编号
	private Long orderId;
	// 子订单编号
	private Long orderItemId;
	// 操作IP
	private String operateIp;
	// 买家用户编号
	private Long buyerId;
	// 卖家用户编号
	private Long sellerId;
	// 状态最后一次成功修改时间
	private Date stateModifyTime;
	// 卖家更新价格 -为优惠 +为增加
	private Long sellerMarginPrice;
	// 子订单状态
	private Integer orderItemState;
	// 操作描述
	private String operateDesc;
	// 操作员编号
	private String opeartorId;
	// 操作类型。待定
	private String operateType;
	// 操作状态
	private Integer operateState;
	// 记录生成时间，数据维护
	private Date gmtCreate;

	public OrderLogDO(){}
	
	public OrderLogDO(long buyerId,String opeartorId,String operateDesc
			,String operateIp,Integer operateState
			,String operateType,long orderId,Integer orderItemState
			,long sellerId,long sellerMarginPrice,long orderItemId){
		this.buyerId = buyerId;
		this.opeartorId = opeartorId;
		this.operateDesc = operateDesc;
		this.operateIp = operateIp;
		this.operateState = operateState;
		this.operateType = operateType;
		this.orderId = orderId;
		this.orderItemState = orderItemState;
		this.sellerId = sellerId;
		this.sellerMarginPrice = sellerMarginPrice;
		this.orderItemId = orderItemId;
		
	}
	
	/**
	 * Created on 2011-9-20
	 * <p>Discription: 取消订单  -- 插入日志</p>
	 * @param buyerId
	 * @param opeartorId
	 * @param operateDesc
	 * @param operateIp
	 * @param operateType
	 * @param orderId
	 * @param sellerId
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static OrderLogDO newOrderLog(long buyerId,String opeartorId,String operateDesc
			,String operateIp
			,String operateType,long orderId
			,long sellerId,long orderItemId,int orderState){
		OrderLogDO orderLogDO = new OrderLogDO();
		orderLogDO.setBuyerId(buyerId);
		orderLogDO.setOpeartorId(opeartorId);
		orderLogDO.setOperateDesc(operateDesc);
		orderLogDO.setOperateIp(operateIp);
		orderLogDO.setOperateState(0);
		orderLogDO.setOperateType(operateType);
		orderLogDO.setOrderId(orderId);
		orderLogDO.setSellerId(sellerId);
		orderLogDO.setOrderItemId(orderItemId);
		orderLogDO.setOrderItemState(orderState);
		return orderLogDO;
	}
	
	
	
	/**
	 * 
	 * Created on 2011-9-19
	 * <p>Discription: 订单生成插入日志</p>
	 * @param buyerId
	 * @param opeartorId
	 * @param operateDesc
	 * @param operateIp
	 * @param operateState
	 * @param operateType
	 * @param orderId
	 * @param sellerId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static OrderLogDO createOrderLogDO(long buyerId,String opeartorId,String operateDesc
			,String operateIp
			,String operateType,long orderId
			,long sellerId,long orderItemId){
		OrderLogDO orderLogDO = new OrderLogDO();
		orderLogDO.setBuyerId(buyerId);
		orderLogDO.setOpeartorId(opeartorId);
		orderLogDO.setOperateDesc(operateDesc);
		orderLogDO.setOperateIp(operateIp);
		orderLogDO.setOperateState(0);
		orderLogDO.setOperateType(operateType);
		orderLogDO.setOrderId(orderId);
		orderLogDO.setSellerId(sellerId);
		orderLogDO.setOrderItemId(orderItemId);
		orderLogDO.setOrderItemState(OrderDO.ORDER_STATE_1);
		return orderLogDO;
	}
	
	public Long getOrderLogId() {
		return orderLogId;
	}
	public void setOrderLogId(Long orderLogId) {
		this.orderLogId = orderLogId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	public String getOperateIp() {
		return operateIp;
	}
	public void setOperateIp(String operateIp) {
		this.operateIp = operateIp;
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
	public Date getStateModifyTime() {
		return stateModifyTime;
	}
	public void setStateModifyTime(Date stateModifyTime) {
		this.stateModifyTime = stateModifyTime;
	}
	public Long getSellerMarginPrice() {
		return sellerMarginPrice;
	}
	public void setSellerMarginPrice(Long sellerMarginPrice) {
		this.sellerMarginPrice = sellerMarginPrice;
	}
	public Integer getOrderItemState() {
		return orderItemState;
	}
	public void setOrderItemState(Integer orderItemState) {
		this.orderItemState = orderItemState;
	}
	public String getOperateDesc() {
		return operateDesc;
	}
	public void setOperateDesc(String operateDesc) {
		this.operateDesc = operateDesc;
	}
	public String getOpeartorId() {
		return opeartorId;
	}
	public void setOpeartorId(String opeartorId) {
		this.opeartorId = opeartorId;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Integer getOperateState() {
		return operateState;
	}
	public void setOperateState(Integer operateState) {
		this.operateState = operateState;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
