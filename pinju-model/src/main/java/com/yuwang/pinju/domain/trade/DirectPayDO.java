package com.yuwang.pinju.domain.trade;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;

/**
 * @Project: pinju-model
 * @Discription: [即时到账支付DO]
 * @author 凌建涛 lingjiantao@zba.com
 * @date 2011-8-11 下午05:53:53
 * @update 2011-8-11 下午05:53:53
 * @version V1.0
 */
public class DirectPayDO extends BaseDO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 即时到账支付编号
	 */
	private Long id;
	
	/**
	 * 业务订单ID
	 */
	private Long orderId;
	
	/**
	 * 支付订单ID
	 */
	private Long payOrderId;
	/**
	 * 外部订单ID(第三方支付订单号)
	 */
	private String outPayId;
	/**
	 * 支付总金额 单位为分 = 商品数量 × 商品单价
	 */
	private Long totalFee;
	/**
	 * 实付款金额(单位:分)
	 */
	private Long realPayAmount;
	/**
	 * 卖家ID(目前为品聚账户ID)
	 */
	private Long sellerId;
	/**
	 * 卖家收款账号
	 */
	private String acceptAccount;
	/**
	 * 买家ID(目前为购买服务的卖家ID)
	 */
	private Long buyerId;
	/**
	 * 买家支付账号
	 */
	private String payAccount;
	/**
	 * 佣金费用
	 */
	private Long commisionFee;
	/**
	 * 折扣金额(单位:分)
	 */
	private Long discountFee;
	
	/**
	 * 业务类型。1000-保证金业务
	 */
	private Integer bizType;
	
	/**
	 * 支付状态
	 */
	private Integer payState;
	/**
	 * 付款时间
	 */
	private Date payTime;
	/**
	 * 支付订单创建时间
	 */
	private Date createTime;
	/**
	 * 交易结束时间
	 */
	private Date endTime;
	/**
	 * 交易被谁关闭
	 */
	private String closer;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModified;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	public Long getPayOrderId(){
		return payOrderId;
	}

	public void setPayOrderId(Long payOrderId){
		this.payOrderId = payOrderId;
	}

	public Long getTotalFee(){
		return totalFee;
	}

	public void setTotalFee(Long totalFee){
		this.totalFee = totalFee;
	}

	public Long getSellerId(){
		return sellerId;
	}

	public void setSellerId(Long sellerId){
		this.sellerId = sellerId;
	}

	public String getAcceptAccount(){
		return acceptAccount;
	}

	public void setAcceptAccount(String acceptAccount){
		this.acceptAccount = acceptAccount;
	}

	public Long getBuyerId(){
		return buyerId;
	}

	public void setBuyerId(Long buyerId){
		this.buyerId = buyerId;
	}

	public String getPayAccount(){
		return payAccount;
	}

	public void setPayAccount(String payAccount){
		this.payAccount = payAccount;
	}

	public Long getCommisionFee(){
		return commisionFee;
	}

	public void setCommisionFee(Long commisionFee){
		this.commisionFee = commisionFee;
	}

	public Long getDiscountFee(){
		return discountFee;
	}

	public void setDiscountFee(Long discountFee){
		this.discountFee = discountFee;
	}

	public Integer getPayState(){
		return payState;
	}

	public void setPayState(Integer payState){
		this.payState = payState;
	}

	public Date getPayTime(){
		return payTime;
	}

	public void setPayTime(Date payTime){
		this.payTime = payTime;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getEndTime(){
		return endTime;
	}

	public void setEndTime(Date endTime){
		this.endTime = endTime;
	}

	public String getCloser(){
		return closer;
	}

	public void setCloser(String closer){
		this.closer = closer;
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

	public void setRealPayAmount(Long realPayAmount){
		this.realPayAmount = realPayAmount;
	}

	public Long getRealPayAmount(){
		return realPayAmount;
	}

	public void setOutPayId(String outPayId) {
		this.outPayId = outPayId;
	}

	public String getOutPayId() {
		return outPayId;
	}
	
	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 
	 * Created on 2011-8-15
	 * <p>
	 * Discription: 封装即时到帐支付表
	 * </p>
	 * 
	 * @param directPayParamDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static DirectPayDO createDirectPayDO(DirectPayParamDO directPayParamDO,Long payOrderId,Long orderId) {
		DirectPayDO directPayDO = new DirectPayDO();
		directPayDO.setBuyerId(directPayParamDO.getBuyerId());
		directPayDO.setCreateTime(directPayParamDO.getCreateTime());
		directPayDO.setSellerId(directPayParamDO.getSellerId());
		directPayDO.setTotalFee(directPayParamDO.getOrderAmount());
		directPayDO.setPayOrderId(payOrderId);
		directPayDO.setId(payOrderId);
		directPayDO.setOrderId(orderId);
		directPayDO.setBizType(directPayParamDO.getBizType());
		return directPayDO;
	}
	
	/**
	 * 
	 * Created on 2011-9-7
	 * <p>
	 * Discription: 封装即时到帐支付表 担保订单走即时到帐专用
	 * </p>
	 * 
	 * @param directPayParamDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static DirectPayDO createVouchDirectPayDO(DirectPayParamDO directPayParamDO,Long payOrderId) {
		DirectPayDO directPayDO = new DirectPayDO();
		directPayDO.setBuyerId(directPayParamDO.getBuyerId());
		directPayDO.setCreateTime(directPayParamDO.getCreateTime());
		directPayDO.setSellerId(directPayParamDO.getSellerId());
		directPayDO.setTotalFee(directPayParamDO.getOrderAmount());
		directPayDO.setPayOrderId(payOrderId);
		directPayDO.setId(payOrderId);
		directPayDO.setOrderId(directPayParamDO.getOrderId());
		directPayDO.setBizType(directPayParamDO.getBizType());
		return directPayDO;
	}
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 封装回调更新DirectPayDO</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public static DirectPayDO upRevDirectPayDO(DirectPayReceiveParamDO directPayReceiveParamDO,Integer payState){
		DirectPayDO directPayDO = new DirectPayDO();
		directPayDO.setPayOrderId(directPayReceiveParamDO.getOrderNo());
		directPayDO.setOutPayId(directPayReceiveParamDO.getSerialno());
		directPayDO.setRealPayAmount(directPayReceiveParamDO.getPayAmount());
		directPayDO.setPayState(payState);
		directPayDO.setPayTime(directPayReceiveParamDO.getPayTime());
		directPayDO.setEndTime(directPayReceiveParamDO.getEndTime());
		directPayDO.setBizType(directPayReceiveParamDO.getProductNo());
		return directPayDO;
	}
}
