package com.yuwang.pinju.domain.rights;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;




public class FinanceWorkOrderDO extends BaseDO{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 编号(即工单号)
	 */
	private Long id;
	
	/**
	 * 记账凭证(一般为银行处理单号)
	 */
	private String evidenceVoucherCode;
	
	/**
	 * 卖家昵称
	 */
	private String sellerNick;
	
	/**
	 * 卖家Id
	 */
	private Long sellerId;
	
	/**
	 * 卖家店铺名称
	 */
	private String shopName;
	
	/**
	 * 卖家店铺Id
	 */
	private Long shopId;
	
	/**
	 * 扣除卖家金额
	 */
	private Long deMargin;
	
	/**
	 * 特性值(key1:value1;key2:value2)
	 */
	private String features;
	
	/**
	 * 买家昵称
	 */
	private String buyerNick;
	
	/**
	 * 卖家Id
	 */
	private Long buyerId;
	
	/**
	 * 买家银行账户名
	 */
	private String buyerBankAccount;
	
	/**
	 * 买家银行开户地址
	 */
	private String buyerBankOpen;
	
	/**
	 * 买家银行账号
	 */
	private String buyerBankCode;
	
	/**
	 * 订单Id
	 */
	private Long orderId;
	
	/**
	 * 业务编号(维权ID||退款ID)
	 */
	private Long bizId;
	
	/**
	 * 业务类型(100:维权101退款)
	 */
	private Integer bizType;
	
	/**
	 * 操作类型：
	 */
	private Integer operType;
	
	/**
	 * 买家维权原因
	 */
	private String rightsReason;
	
	/**
	 * crm后台操作人（财务人员账户昵称）
	 */
	private String operPerson;
	
	/**
	 * crm后台操作人（财务人员账户Id）
	 */
	private Long operId;
	
	/**
	 * 状态： 100未处理，200已处理
	 */
	private Integer status;
	
	/**
	 * 上一次提醒时间(卖家保证金不足时)
	 */
	private Date warnTime;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 记录创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 记录修改时间
	 */
	private Date gmtModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public Long getDeMargin() {
		return deMargin;
	}

	public void setDeMargin(Long deMargin) {
		this.deMargin = deMargin;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerBankAccount() {
		return buyerBankAccount;
	}

	public void setBuyerBankAccount(String buyerBankAccount) {
		this.buyerBankAccount = buyerBankAccount;
	}

	public String getBuyerBankOpen() {
		return buyerBankOpen;
	}

	public void setBuyerBankOpen(String buyerBankOpen) {
		this.buyerBankOpen = buyerBankOpen;
	}

	public String getBuyerBankCode() {
		return buyerBankCode;
	}

	public void setBuyerBankCode(String buyerBankCode) {
		this.buyerBankCode = buyerBankCode;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public String getRightsReason() {
		return rightsReason;
	}

	public void setRightsReason(String rightsReason) {
		this.rightsReason = rightsReason;
	}

	public String getOperPerson() {
		return operPerson;
	}

	public void setOperPerson(String operPerson) {
		this.operPerson = operPerson;
	}

	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

	public void setBizId(Long bizId){
		this.bizId = bizId;
	}

	public Long getBizId(){
		return bizId;
	}

	public void setEvidenceVoucherCode(String evidenceVoucherCode){
		this.evidenceVoucherCode = evidenceVoucherCode;
	}

	public String getEvidenceVoucherCode(){
		return evidenceVoucherCode;
	}

	public void setFeatures(String features){
		this.features = features;
	}

	public String getFeatures(){
		return features;
	}

	public void setBizType(Integer bizType){
		this.bizType = bizType;
	}

	public Integer getBizType(){
		return bizType;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return remark;
	}
	
	
	
}
