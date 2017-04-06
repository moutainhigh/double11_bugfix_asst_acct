package com.yuwang.pinju.domain.trade;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * @Project: pinju-model
 * @Discription: [即时到账订单DO]
 * @author 凌建涛 lingjiantao@zba.com
 * @date 2011-8-11 下午05:37:10
 * @update 2011-8-11 下午05:37:10
 * @version V1.0
 */
public class DirectOrderDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3870942976161578425L;

	/**
	 * 即时到账订单编号业务订单ID
	 */
	private Long id;
	/**
	 * 支付订单ID
	 */
	private Long payOrderId;
	
	/**
	 * 买家ID(目前为购买服务的卖家ID)
	 */
	private Long buyerId;
	/**
	 * 买家昵称(目前为购买服务的卖家昵称)
	 */
	private String buyerNick;
	/**
	 * 卖家ID(目前为品聚账户ID)
	 */
	private Long sellerId;
	/**
	 * 卖家昵称(目前为品聚官方账号昵称)
	 */
	private String sellerNick;

	/**
	 * 订单状态
	 */
	private Integer orderState;
	/**
	 * 外部订单ID(盛付通订单号)
	 */
	private String outPayId;
	/**
	 * 订单金额(单位:分)
	 */
	private Long orderAmount;
	/**
	 * 业务类型。1000-保证金业务
	 */
	private Integer bizType;
	/**
	 * 商品ID(保证金业务为固定ID)
	 */
	private Long itemId;
	/**
	 * 商品表体
	 */
	private String itemTitle;
	/**
	 * 商品价格(单位:分)
	 */
	private Long itemPrice;
	/**
	 * 购买数量
	 */
	private Integer buyAmount;
	/**
	 * 下单IP
	 */
	private Long buyIp;
	/**
	 * 交易结束时间
	 */
	private Date endTime;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return id;
	}

	public Long getPayOrderId(){
		return payOrderId;
	}

	public void setPayOrderId(Long payOrderId){
		this.payOrderId = payOrderId;
	}

	public Long getBuyerId(){
		return buyerId;
	}

	public void setBuyerId(Long buyerId){
		this.buyerId = buyerId;
	}

	public String getBuyerNick(){
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick){
		this.buyerNick = buyerNick;
	}

	public Long getSellerId(){
		return sellerId;
	}

	public void setSellerId(Long sellerId){
		this.sellerId = sellerId;
	}

	public Integer getOrderState(){
		return orderState;
	}

	public void setOrderState(Integer orderState){
		this.orderState = orderState;
	}
	

	public Long getOrderAmount(){
		return orderAmount;
	}

	public void setOrderAmount(Long orderAmount){
		this.orderAmount = orderAmount;
	}

	public Integer getBizType(){
		return bizType;
	}

	public void setBizType(Integer bizType){
		this.bizType = bizType;
	}

	public Long getItemId(){
		return itemId;
	}

	public void setItemId(Long itemId){
		this.itemId = itemId;
	}

	public String getItemTitle(){
		return itemTitle;
	}

	public void setItemTitle(String itemTitle){
		this.itemTitle = itemTitle;
	}

	public Long getItemPrice(){
		return itemPrice;
	}

	public void setItemPrice(Long itemPrice){
		this.itemPrice = itemPrice;
	}

	public Integer getBuyAmount(){
		return buyAmount;
	}

	public void setBuyAmount(Integer buyAmount){
		this.buyAmount = buyAmount;
	}

	public Long getBuyIp(){
		return buyIp;
	}

	public void setBuyIp(Long buyIp){
		this.buyIp = buyIp;
	}

	public Date getEndTime(){
		return endTime;
	}

	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}

	public Date getGmtCreate(){
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate){
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified(){
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified){
		this.gmtModified = gmtModified;
	}

	public void setOutPayId(String outPayId) {
		this.outPayId = outPayId;
	}

	public String getOutPayId() {
		return outPayId;
	}
	/**
	 * 
	 * Created on 2011-8-15
	 * <p>
	 * Discription: 生成即时到帐订单对象
	 * </p>
	 * 
	 * @param directPayParamDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static DirectOrderDO createDirectOrderDO(DirectPayParamDO directPayParamDO,Long orderId,Long payOrderId) {
		DirectOrderDO directOrderDO = new DirectOrderDO();
		directOrderDO.setBizType(directPayParamDO.getBizType());
		directOrderDO.setBuyAmount(directPayParamDO.getBuyAmount());
		directOrderDO.setBuyerId(directPayParamDO.getBuyerId());
		directOrderDO.setBuyerNick(directPayParamDO.getBuyerNick());
		directOrderDO.setBuyIp(directPayParamDO.getBuyerIp());
		directOrderDO.setGmtCreate(directPayParamDO.getCreateTime());
		directOrderDO.setItemId(directPayParamDO.getItemId());
		directOrderDO.setItemPrice(directPayParamDO.getItemPrice());
		directOrderDO.setItemTitle(directPayParamDO.getItemTitle());
		directOrderDO.setOrderAmount(directPayParamDO.getOrderAmount());
		directOrderDO.setSellerId(directPayParamDO.getSellerId());
		directOrderDO.setSellerNick(directPayParamDO.getSellerNick());
		directOrderDO.setId(orderId);
		directOrderDO.setPayOrderId(payOrderId);
		return directOrderDO;
	}
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 封装回调更新DirectOrderDO</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static DirectOrderDO upRevDirectOrderDO(DirectPayReceiveParamDO directPayReceiveParamDO,Integer orderState){
		DirectOrderDO directOrderDO = new DirectOrderDO();
		directOrderDO.setEndTime(directPayReceiveParamDO.getEndTime());
		directOrderDO.setOrderState(orderState);
		directOrderDO.setOutPayId(directPayReceiveParamDO.getSerialno());
		directOrderDO.setPayOrderId(directPayReceiveParamDO.getOrderNo());
		directOrderDO.setBizType(directPayReceiveParamDO.getProductNo());
		return directOrderDO;
	}
	
	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
}
