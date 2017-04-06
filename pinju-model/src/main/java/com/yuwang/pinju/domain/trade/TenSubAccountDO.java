package com.yuwang.pinju.domain.trade;

import java.util.Map;

/**
 * @Discription: 分账实体
 * @Project: pinju-model
 * @Package: com.yuwang.pinju.domain.order.pay
 * @Title: SubAccountDO.java
 * @author: [贺泳]
 * @date 2011-9-13 上午09:50:57
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class TenSubAccountDO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 已经成功支付的财付通交易号(订单号)
	 */
	private String transactionId;
	/**
	 * 商户系统内部的定单号，此参数仅在对账时提供。 
	 */
	private String spBillno;
	/**
	 * 总金额，以分为单位
	 */
	private Long totalFee;
	
	/**
	 * 业务参数，特定格式的字符串，格式为：
	 * (账户^金额^角色)[|(账户^金额^角色)]*
	 * key:分账账户
	 * value:金额
	 */
	private Map<String,Long> map;
	

	
	/**
	 * 卖家分账账户
	 */
	private String sellerAccount;
	/**
	 * 卖家分账金额
	 */
	private Long sellerFee;
	
	/**
	 * 平台分账金额
	 */
	private Long paltFormFee;
	
	public TenSubAccountDO(){
		
	}
	
	public static TenSubAccountDO createTenSubAccountDO(String transactionId,String spBillno,Long totalFee,String sellerAccount,Long sellerFee,Long paltFormFee,Map<String,Long> map){
		TenSubAccountDO tenSubAccountDO = new TenSubAccountDO();
		tenSubAccountDO.setMap(map);
		tenSubAccountDO.setPaltFormFee(paltFormFee);
		tenSubAccountDO.setSellerAccount(sellerAccount);
		tenSubAccountDO.setSellerFee(sellerFee);
		tenSubAccountDO.setSpBillno(spBillno);
		tenSubAccountDO.setTotalFee(totalFee);
		tenSubAccountDO.setTransactionId(transactionId);
		return tenSubAccountDO;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getSpBillno() {
		return spBillno;
	}

	public void setSpBillno(String spBillno) {
		this.spBillno = spBillno;
	}

	public Long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}

	public Map<String, Long> getMap() {
		return map;
	}
	public void setMap(Map<String, Long> map) {
		this.map = map;
	}
	public String getSellerAccount() {
		return sellerAccount;
	}
	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}
	public Long getSellerFee() {
		return sellerFee;
	}
	public void setSellerFee(Long sellerFee) {
		this.sellerFee = sellerFee;
	}
	public Long getPaltFormFee() {
		return paltFormFee;
	}
	public void setPaltFormFee(Long paltFormFee) {
		this.paltFormFee = paltFormFee;
	}

}
