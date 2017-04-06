package com.yuwang.pinju.domain.rights;

import java.util.Date;

import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.domain.BaseDO;


/**  
* @Project: pinju-model
* @Description: 消保维权记录DO
* @author 石兴 shixing@zba.com
* @date 2011-6-28 下午04:16:15
* @update 2011-6-28 下午04:16:15
* @version V1.0  
*/
public class RightsDO extends BaseDO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5590772513254503180L;

	private Long id; //维权记录ID
	
	private Long orderId;  //订单ID
	
	private Long subOrderId;  //子订单ID
	
	private Long sellerId;  //卖家ID
	
	private String sellerNick;  //卖家昵称
	
	private Long buyerId;  //买家ID
	
	private String buyerNick;  //买家昵称
	
	private Date applyDate;  //买家发起申请时间
	
	private Date sellerProcDate;  //卖家处理时间
	
	private Long price;  //交易金额
	
	/**
	 * 真正退还的金额
	 */
	private Long buyerApplyPrice;  //买家申请退款金额
	
	/**
	 * 由于卖家无法修改金额，此字段暂时不使用
	 */
	private Long sellerReturnPrice;  //卖家退款金额
	
	private Integer isReturnGoods;  //是否需要回寄商品
	
	/**
	 * 维权原因 0-7天无理由退货 1-质量问题 2-假一赔三 
	 */
	private Integer voucherType;  //维权类型
	
	/**
	 * 买家维权要求 0-要求退款  1-要求退货退款
	 */
	private Integer buyerRequire;  
	
	private String buyerReason;  //买家维权详细原因
	
	private Long logisticsId;  //退货物流ID
	
	private Integer state;  //维权状态 
	
	private String voucherPic1;  //维权凭证1
	
	private String voucherPic2; //维权凭证2
	
	private String voucherPic3;  //维权凭证3
	
	private Date gmtCreate;  //记录创建时间
	
	private Date gmtModified;  //记录修改时间
	
	/**
	 * 买家银行账户名
	 */
	private String buyerBankAccountName;
	
	/**
	 * 买家银行卡号
	 */
	private String buyerBankAccountNo;
	
	/**
	 * 买家银行账号开户行
	 */
	private String buyerOpenBankName;
	
	/**
	 * 业务来源
	 */
	private int bizFrom = RightsConstant.RIGHTS_BIZ_FROM_RIGHTS;
	/**
	 * 卖家拒绝原因
	 */
	private String sellerRefuseReason;
	/**
	 * 维权状态更新时间
	 */
	private Date stateUpdateTime;
	
	
	public String getSellerRefuseReason() {
		return sellerRefuseReason;
	}

	public void setSellerRefuseReason(String sellerRefuseReason) {
		this.sellerRefuseReason = sellerRefuseReason;
	}

	public Date getStateUpdateTime() {
		return stateUpdateTime;
	}

	public void setStateUpdateTime(Date stateUpdateTime) {
		this.stateUpdateTime = stateUpdateTime;
	}

	public String getBuyerOpenBankName() {
		return buyerOpenBankName;
	}

	public void setBuyerOpenBankName(String buyerOpenBankName) {
		this.buyerOpenBankName = buyerOpenBankName;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(Long subOrderId) {
		this.subOrderId = subOrderId;
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

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getSellerProcDate() {
		return sellerProcDate;
	}

	public void setSellerProcDate(Date sellerProcDate) {
		this.sellerProcDate = sellerProcDate;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getBuyerApplyPrice() {
		return buyerApplyPrice;
	}

	public void setBuyerApplyPrice(Long buyerApplyPrice) {
		this.buyerApplyPrice = buyerApplyPrice;
	}

	public Long getSellerReturnPrice() {
		return sellerReturnPrice;
	}

	public void setSellerReturnPrice(Long sellerReturnPrice) {
		this.sellerReturnPrice = sellerReturnPrice;
	}

	public Integer getIsReturnGoods() {
		return isReturnGoods;
	}

	public void setIsReturnGoods(Integer isReturnGoods) {
		this.isReturnGoods = isReturnGoods;
	}

	public Integer getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(Integer voucherType) {
		this.voucherType = voucherType;
	}



	public String getBuyerReason() {
		return buyerReason;
	}

	public void setBuyerReason(String buyerReason) {
		this.buyerReason = buyerReason;
	}

	public Long getLogisticsId() {
		return logisticsId;
	}

	public void setLogisticsId(Long logisticsId) {
		this.logisticsId = logisticsId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getVoucherPic1() {
		return voucherPic1;
	}

	public void setVoucherPic1(String voucherPic1) {
		this.voucherPic1 = voucherPic1;
	}

	public String getVoucherPic2() {
		return voucherPic2;
	}

	public void setVoucherPic2(String voucherPic2) {
		this.voucherPic2 = voucherPic2;
	}

	public String getVoucherPic3() {
		return voucherPic3;
	}

	public void setVoucherPic3(String voucherPic3) {
		this.voucherPic3 = voucherPic3;
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

	public void setBuyerRequire(Integer buyerRequire) {
		this.buyerRequire = buyerRequire;
	}

	public Integer getBuyerRequire() {
		return buyerRequire;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public String getBuyerBankAccountNo() {
		return buyerBankAccountNo;
	}

	public void setBuyerBankAccountNo(String buyerBankAccountNo) {
		this.buyerBankAccountNo = buyerBankAccountNo;
	}

	public String getBuyerBankAccountName() {
		return buyerBankAccountName;
	}

	public void setBuyerBankAccountName(String buyerBankAccountName) {
		this.buyerBankAccountName = buyerBankAccountName;
	}

	public int getBizFrom() {
		return bizFrom;
	}

	public void setBizFrom(int bizFrom) {
		this.bizFrom = bizFrom;
	}

	
}
