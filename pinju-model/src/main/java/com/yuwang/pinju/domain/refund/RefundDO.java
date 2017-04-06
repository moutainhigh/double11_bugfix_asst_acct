package com.yuwang.pinju.domain.refund;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.domain.BaseDO;

/**
 * <p>退款信息</p>
 *
 * @author shihongbo
 * @since   2011-06-24
 * @version 1.0
 */
public class RefundDO extends BaseDO {
	private static final long serialVersionUID = 1L;

	/**
	 * 无退款
	 */
	public static final Integer STATUS_DEFAULT = 10001;

	/**
	 * 买家已经申请退款，等待卖家同意
	 */
	public static final Integer STATUS_WAIT_SELLER_AGREE = 1;

	/**
	 * 卖家已经同意退款，等待买家退货
	 */
	public static final Integer STATUS_WAIT_BUYER_RETURN_GOODS = 2;

	/**
	 * 买家已经退货，等待卖家确认收货
	 */
	public static final Integer STATUS_WAIT_SELLER_CONFIRM_GOODS = 3;

	/**
	 * 退款关闭
	 */
	public static final Integer STATUS_CLOSED = 4;

	/**
	 * 退款成功
	 */
	public static final Integer STATUS_SUCCESS = 5;

	/**
	 * 卖家拒绝退款
	 */
	public static final Integer STATUS_SELLER_REFUSE_BUYER = 6;

	/**
	 * 客服仲裁
	 */
	public static final Integer STATUS_CS_PROCESS = 7;

	/**
	 * 退款失败
	 */
	public static final Integer STATUS_REFUND_FAIL = 8;

	/**
	 * 退款协议达成
	 */
	public static final Integer STATUS_REFUND_PROTOCAL_AGREE = 9;

	/**
	 * 需要退货
	 */
	public static final Integer NEED_SALES_RETURN_YES = 1;

	/**
	 * 不需要退货
	 */
	public static final Integer NEED_SALES_RETURN_NO = 0;

	/**
	 * 该笔退款经历过客服介入状态
	 */
	public static final Integer CUST_PROCESS_YES = 1;

	/**
	 * 该笔退款没有经历过客服介入状态
	 */
	public static final Integer CUST_PROCESS_NO = 0;


	/**
	 * 退款id
	 */
	private Long id;

	/**
	 * 订单id
	 */
	private Long orderId;

	/**
	 * 子订单id
	 */
	private Long orderItemId;

	/**
	 * 卖家id
	 */
	private Long sellerId;

	/**
	 * 卖家昵称
	 */
	private String sellerNick;

	/**
	 * 买家id
	 */
	private Long buyerId;

	/**
	 * 买家昵称
	 */
	private String buyerNick;

	/**
	 * 买家申请时间
	 */
	private Date applyDate;

	/**
	 * 卖家同意/拒绝时间
	 */
	private Date replyDate;

	/**
	 * 交易金额
	 */
	private Long tradeSum;


	/**
	 * 交易金额
	 */
	private String tradeSumByYuan;

	/**
	 * 买家申请金额
	 */
	private Long applySum;

	/**
	 * 买家申请金额
	 */
	private String applySumByYuan;

	/**
	 * 卖家退款金额
	 */
	private Long replySum;

	/**
	 * 卖家退款金额
	 */
	private String replySumByYuan;

	/**
	 * 卖家是否同意退款
	 */
	private Long sellerAgree;

	/**
	 * 买家申请退款原因
		1-未收到商品
		2-卖家发货不及时
		3-与卖家协商一致退款
		7-收到的货物出现质量问题
		8-收到的货物描述不符
		9-7天无理由退货
		10-假一赔三
	 */
	private Integer applyReason;

	/**
	 * 买家申请退款说明
	 */
	private String applyMemo;

	/**
	 * 退货id
	 */
	private Long refundLogisticsId;

	/**
	 * 退款状态
		1-买家已经申请退款，等待卖家同意
		2-卖家已经同意退款，等待买家退货
		3-买家已经退货，等待卖家确认收货
		4-退款关闭
		5-退款成功
		6-卖家拒绝退款
		7-客服仲裁
	 */
	private Integer refundState = STATUS_WAIT_SELLER_AGREE;

	/**
	 * 是否需要退货
		1-需要退货
		0-不需要退货
	 */
	private Integer needSalesReturn;

	/**
	 * 记录创建时间
	 */
	private Date gmtCreate;

	/**
	 * 记录修改时间
	 */
	private Date gmtModified;

	/**
	 * 状态修改时间
	 */
	private Date stateModified;

	/**
	 * 图片1
	 */
	private String pic1;

	/**
	 * 图片2
	 */
	private String pic2;

	/**
	 * 图片3
	 */
	private String pic3;

	/**
	 * 该笔退款是否经历过客服介入状态
        1-经历过客服介入状态
		0-没有经历过客服介入状态
	 */
	private Integer isCustProcessed;

	public Integer getIsCustProcessed() {
		return isCustProcessed;
	}

	public void setIsCustProcessed(Integer isCustProcessed) {
		this.isCustProcessed = isCustProcessed;
	}

	public Long getId(){
		return id;
	}

	public Long getOrderId(){
		return orderId;
	}

	public Long getOrderItemId(){
		return orderItemId;
	}

	public Long getSellerId(){
		return sellerId;
	}

	public String getSellerNick(){
		return sellerNick;
	}

	public Long getBuyerId(){
		return buyerId;
	}

	public String getBuyerNick(){
		return buyerNick;
	}

	public Date getApplyDate(){
		return applyDate;
	}

	public Date getReplyDate(){
		return replyDate;
	}

	public Long getTradeSum(){
		return tradeSum;
	}

	public String getTradeSumByYuan() {
		return tradeSumByYuan;
	}

	/**
	 * 支付给卖家的金额
	 */
	 public String getPaySumByYuan() {
		if(this.tradeSumByYuan == null)
			return "0";

		if(this.applySum == null)
			return "0";

		Long paysum =  tradeSum - applySum;
		if(paysum.compareTo(0L) < 0)
			return "0";

		return MoneyUtil.getCentToDollar(paysum);
	 }

	 public void setTradeSumByYuan(String tradeSumByYuan) {
		 this.tradeSumByYuan = tradeSumByYuan;
	 }

	 public String getApplySumByYuan() {
		 return applySumByYuan;
	 }

	 public void setApplySumByYuan(String applySumByYuan) {
		 this.applySumByYuan = applySumByYuan;
	 }

	 public String getReplySumByYuan() {
		 return replySumByYuan;
	 }

	 public void setReplySumByYuan(String replySumByYuan) {
		 this.replySumByYuan = replySumByYuan;
	 }

	 public Long getApplySum(){
		 return applySum;
	 }

	 public Long getReplySum(){
		 return replySum;
	 }

	 public Long getSellerAgree(){
		 return sellerAgree;
	 }

	 public Integer getApplyReason(){
		 return applyReason;
	 }

	 public String getApplyMemo(){
		 return applyMemo;
	 }

	 public Long getRefundLogisticsId(){
		 return refundLogisticsId;
	 }

	 public Integer getRefundState(){
		 return refundState;
	 }

	 public Integer getNeedSalesReturn(){
		 return needSalesReturn;
	 }

	 public String getPic1(){
		 return pic1;
	 }

	 public String getPic2(){
		 return pic2;
	 }

	 public String getPic3(){
		 return pic3;
	 }

	 public void setId(Long id){
		 this.id = id;
	 }

	 public void setOrderId(Long orderId){
		 this.orderId = orderId;
	 }

	 public void setOrderItemId(Long orderItemId){
		 this.orderItemId = orderItemId;
	 }

	 public void setSellerId(Long sellerId){
		 this.sellerId = sellerId;
	 }

	 public void setSellerNick(String sellerNick){
		 this.sellerNick = sellerNick;
	 }

	 public void setBuyerId(Long buyerId){
		 this.buyerId = buyerId;
	 }

	 public void setBuyerNick(String buyerNick){
		 this.buyerNick = buyerNick;
	 }

	 public void setApplyDate(Date applyDate){
		 this.applyDate = applyDate;
	 }

	 public void setReplyDate(Date replyDate){
		 this.replyDate = replyDate;
	 }

	 public void setTradeSum(Long tradeSum){
		 this.tradeSum = tradeSum;

		 if(this.tradeSum != null)
			 this.tradeSumByYuan = MoneyUtil.getCentToDollar(this.tradeSum);
	 }

	 public void setApplySum(Long applySum){
		 this.applySum = applySum;

		 if(this.applySum != null)
			 this.applySumByYuan = MoneyUtil.getCentToDollar(this.applySum);
	 }

	 public void setReplySum(Long replySum){
		 this.replySum = replySum;

		 if(this.replySum != null)
			 this.replySumByYuan = MoneyUtil.getCentToDollar(this.replySum);

	 }

	 public void setSellerAgree(Long sellerAgree){
		 this.sellerAgree = sellerAgree;
	 }

	 public void setApplyReason(Integer applyReason){
		 this.applyReason = applyReason;
	 }

	 public void setApplyMemo(String applyMemo){
		 if(StringUtils.isNotBlank(applyMemo)){
			 applyMemo = applyMemo.trim();
			 applyMemo = applyMemo.replace("<", "&lt;");
			 applyMemo = applyMemo.replace(">", "&gt;");
		 }
		 this.applyMemo = applyMemo;
	 }

	 public void setRefundLogisticsId(Long refundLogisticsId){
		 this.refundLogisticsId = refundLogisticsId;
	 }

	 public void setRefundState(Integer refundState){
		 this.refundState = refundState;
	 }

	 public void setNeedSalesReturn(Integer needSalesReturn){
		 this.needSalesReturn = needSalesReturn;
	 }

	 public void setPic1(String pic1){
		 this.pic1 = pic1;
	 }

	 public void setPic2(String pic2){
		 this.pic2 = pic2;
	 }

	 public void setPic3(String pic3){
		 this.pic3 = pic3;
	 }

	 public Date getGmtCreate(){
		 return gmtCreate;
	 }

	 public Date getGmtModified(){
		 return gmtModified;
	 }

	 public Date getStateModified(){
		 return stateModified;
	 }

	 public void setGmtCreate(Date gmtCreate){
		 this.gmtCreate = gmtCreate;
	 }

	 public void setGmtModified(Date gmtModified){
		 this.gmtModified = gmtModified;
	 }

	 public void setStateModified(Date stateModified){
		 this.stateModified = stateModified;
	 }

	 private static Map<Integer, String> refundStateMap = new HashMap<Integer, String>();

	 static{
		 refundStateMap.put(STATUS_WAIT_SELLER_AGREE, "等待卖家处理");
		 refundStateMap.put(STATUS_WAIT_BUYER_RETURN_GOODS, "等待买家退还商品");
		 refundStateMap.put(STATUS_WAIT_SELLER_CONFIRM_GOODS, "等待卖家确认收货");
		 refundStateMap.put(STATUS_CLOSED, "退款关闭");
		 refundStateMap.put(STATUS_SUCCESS, "退款成功");
		 refundStateMap.put(STATUS_SELLER_REFUSE_BUYER, "卖家拒绝退款");
		 refundStateMap.put(STATUS_CS_PROCESS, "客服仲裁");
		 refundStateMap.put(STATUS_REFUND_FAIL, "退款失败");
		 refundStateMap.put(STATUS_REFUND_PROTOCAL_AGREE, "退款协议达成");

	 }

	 /**
	  * <p>取得退款状态显示文字信息</p>
	  * 
	  * @return 退款状态显示文字信息
	  *
	  * @author shihongbo
	  */
	 public String getRefundStateDisplay(){
		 return refundStateMap.get(refundState);
	 }

	 private static Map<Integer, String> applyReasonMap = new HashMap<Integer, String>();

	 static{
		 applyReasonMap.put(1, "未收到商品");
		 applyReasonMap.put(2, "卖家发货不及时");
		 applyReasonMap.put(3, "与卖家协商一致退款");

		 applyReasonMap.put(7, "收到的货物出现质量问题");
		 applyReasonMap.put(8, "收到的货物描述不符");
		 applyReasonMap.put(9, "7天无理由退货");
		 applyReasonMap.put(10, "假一赔三");
	 }

	 /**
	  * <p>取得退款状态显示文字信息</p>
	  * 
	  * @return 退款状态显示文字信息
	  *
	  * @author shihongbo
	  */
	 public String getApplyReasonDisplay(){
		 return applyReasonMap.get(applyReason);
	 }


	 public static Integer getStatusWaitSellerAgree() {
		 return STATUS_WAIT_SELLER_AGREE;
	 }

	 public static Integer getStatusWaitBuyerReturnGoods() {
		 return STATUS_WAIT_BUYER_RETURN_GOODS;
	 }

	 public static Integer getStatusWaitSellerConfirmGoods() {
		 return STATUS_WAIT_SELLER_CONFIRM_GOODS;
	 }

	 public static Integer getStatusClosed() {
		 return STATUS_CLOSED;
	 }

	 public static Integer getStatusSuccess() {
		 return STATUS_SUCCESS;
	 }

	 public static Integer getStatusSellerRefuseBuyer() {
		 return STATUS_SELLER_REFUSE_BUYER;
	 }

	 public static Integer getStatusCsProcess() {
		 return STATUS_CS_PROCESS;
	 }

	 public static Map<Integer, String> getRefundStateMap() {
		 return refundStateMap;
	 }

	 public static Map<Integer, String> getApplyReasonMap() {
		 return applyReasonMap;
	 }

}