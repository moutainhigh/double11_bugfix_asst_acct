package com.yuwang.pinju.domain.rights;

import java.util.Date;

import com.yuwang.pinju.domain.BaseDO;


/**  
* @Project: pinju-model
* @Description: 消保维权-退货记录DO
* @author 石兴 shixing@zba.com
* @date 2011-6-28 下午04:17:01
* @update 2011-6-28 下午04:17:01
* @version V1.0  
*/
public class RightsLogisticsDO extends BaseDO{
	
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	/**
	 * 平邮
	 */
	public final static int PING_YOU_POST= 1;
	/**
	 * 快递
	 */
	public final static int KUAI_DI_YOU_POST= 2;
	/**
	 * EMS
	 */
	public final static int EMS_POST= 3;
	
	private Long id; //买家退货ID
	
	private Long logisticsId; //物流ID
	
	private String logisticsName; //物流名称
	/**运单号码**/
	private String billNo; 
	
	private Long buyerId;  //买家ID
	
	private String comments; //买家备注说明
	
	private Date buySendDate;  //买家发货时间
	
	private Long sellerId;  //卖家ID
	
	private Date sellerConfirmDate;  //卖家确认收货时间
	
	private int buyerReturnState; //买家退货状态
	
	private Long voucherId;  //维权记录ID
	
	private Date gmtCreate;  //记录创建时间
	
	private Date gmtModified;  //记录修改时间


	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Date getBuySendDate() {
		return buySendDate;
	}

	public void setBuySendDate(Date buySendDate) {
		this.buySendDate = buySendDate;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Date getSellerConfirmDate() {
		return sellerConfirmDate;
	}

	public void setSellerConfirmDate(Date sellerConfirmDate) {
		this.sellerConfirmDate = sellerConfirmDate;
	}

	public int getBuyerReturnState() {
		return buyerReturnState;
	}

	public void setBuyerReturnState(int buyerReturnState) {
		this.buyerReturnState = buyerReturnState;
	}

	public Long getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(Long voucherId) {
		this.voucherId = voucherId;
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

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setLogisticsId(Long logisticsId) {
		this.logisticsId = logisticsId;
	}

	public Long getLogisticsId() {
		return logisticsId;
	}

	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	public String getBillNo(){
		return billNo;
	}
	
	
}
