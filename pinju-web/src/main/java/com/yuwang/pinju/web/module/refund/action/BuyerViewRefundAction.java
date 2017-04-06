package com.yuwang.pinju.web.module.refund.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.coupon.ao.OrderCouponAO;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.refund.ao.BuyerRefundTimeoutAO;
import com.yuwang.pinju.core.refund.ao.ItemPropertyAO;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundBaseAO;
import com.yuwang.pinju.core.refund.ao.RefundCheckAO;
import com.yuwang.pinju.core.refund.ao.SellerRefundTimeoutAO;
import com.yuwang.pinju.core.refund.ao.TradeRefundAO;
import com.yuwang.pinju.core.util.DateUtil;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;

/**
 * <p>Description:  买家查款退款详情</p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-8-26
 */
public class BuyerViewRefundAction extends  RefundBaseAction implements BuyerRefund{
	private static final long serialVersionUID = 1592077847882135481L;
	
	private TradeRefundAO tradeRefundAO;
	private RefundApplyAO refundApplyAO;
	private RefundCheckAO refundCheckAO;
	private RefundBaseAO refundBaseAO;
	private LogisticsCorpAO logisticsCorpAO;
	private ItemPropertyAO itemPropertyAO;
	private SellerRefundTimeoutAO sellerRefundTimeoutAO;
	private BuyerRefundTimeoutAO buyerRefundTimeoutAO;
	private OrderCouponAO orderCouponAO;
	
	private OrderDO order;
	private OrderItemDO orderItem;
	private ItemDO itemDO;
	private RefundDO refundDO;
	private OrderLogisticsDO orderLogisticsDO;
	private TradeRefundLogisticsDO tradeRefundLogisticsDO;
	 //物流列表
    private List<LogisticsCorpDO> logisticsCorpDOList;
    
    // 留言集合
    private List<TradeRefundLeavewordDO> levList;
    List<ItemPropertyVO> itemPropertyList;
    
	
    
   
	private Long refundId;
	private Long orderItemId;
	private Date  outDate;
    private String logisticsId;
    private String logisticsNumber;
    private String buyerMemo;
    private String sellerReplyTimeoutUrl;
    private String sellerConfirmGoodsTimeoutUrl;
    private String buyerReplyTimeoutUrl;
    private String couponMoneyByYuan;

	private Map<String,String> priceMap = new HashMap<String,String>();
    

    
    /**
	 * 查看买家申请退款中详情
	 */
	public String execute(){
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE)==0){
			
			//判断卖家是否响应超时
			Boolean isSellerReplyTimeout = sellerRefundTimeoutAO.isSellerReplyTimeout(refundDO);
			
			//已经超时
			if(isSellerReplyTimeout){
				
				Result timeoutResult = sellerRefundTimeoutAO.sellerReplyTimeout(refundId, false);
				
				sellerReplyTimeoutUrl = (String)timeoutResult.getModel("sellerReplyTimeoutUrl");
				
				return "sellerReplyTimeout";	
			}
			
			outDate = DateUtil.skipDateTime(refundDO.getApplyDate(), RefundBaseAO.WAIT_SELL_ARGEE);
			return loadBuyerRefundData();
			
		}else{
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
	/**
	 * 买家查看卖家拒绝退款请求
	 */
	public String buyerViewSellerReject() {
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_SELLER_REFUSE_BUYER) == 0) {
			
			//卖家拒绝，买家超时
			Boolean isBuyerReplyTimeout = buyerRefundTimeoutAO.isBuyerNoReplyTimeout(refundDO);
			if(isBuyerReplyTimeout){
				Result timeoutResult = buyerRefundTimeoutAO.buyerReplyTimeout(refundId, true);
				if(timeoutResult.isSuccess()){
					buyerReplyTimeoutUrl = (String)timeoutResult.getModel("buyerReplyTimeoutUrl");
					if(StringUtils.isNotBlank(timeoutResult.getResultCode())){
						this.errorMessage = ResultCodeMsg.getResultMessage(timeoutResult.getResultCode());
					}
					return BUYER_REPLY_TIMEOUT;
				}
			}
			
			//没有超时
			outDate = DateUtil.skipDateTime(refundDO.getReplyDate(),RefundBaseAO.SELLER_REF_REFUND);
			return loadBuyerRefundData();
		} else {
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
	
	/**
	 * 买家查看退款申请关闭
	 */
	public String buyerViewRefundClosed(){
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_CLOSED) == 0) {
			// 加载退款信息
			return loadBuyerRefundData();
		}else{
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
	
	/**
	 * 买家查看退款申请成功
	 */
	public String buyerViewRefundSuccess(){
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_SUCCESS) == 0) {
			return loadBuyerRefundData();
		}else{
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
	
	/**
	 * 买家查看退款协议达成
	 */
	public String buyerViewRefundProtocalAgree(){
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) == 0) {
			return loadBuyerRefundData();
		}else{
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
	
	/**
	 * 买家查看退款申请失败
	 * success  手工单存在，没有填写银行卡信息
	 * exist 手工单已经填写了银行卡信息
	 * wait  手工单不存在，属于全额退款失败
	 * error 状态错误
	 */
	public String buyerViewRefundFail(){
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_FAIL) == 0) {
			
			// 加载退款信息详情
			String LoadResult= loadBuyerRefundData();
			if(SUCCESS.compareTo(LoadResult) !=0) return LoadResult;
			
			//根据订单id查询RightsWorkOrderDO手工单
			Result result = refundApplyAO.getRightsWorkOrderDOByOrderId(refundDO.getOrderId());
			
			//手工单是否存在
			Boolean refundManualExist = result.isSuccess();
			
			//手工单存在
			if(refundManualExist){
				
				FinanceWorkOrderDO financeWorkOrderDO = (FinanceWorkOrderDO)result.getModel("financeWorkOrderDO");
				
				//没有提供银行卡信息，需要手工填写
				if(financeWorkOrderDO.getBuyerBankAccount() == null){
					return SUCCESS;
					
				//已经提交过银行卡信息
				}else{
					return "exist";
				}
				
			
			//手工单不存在，平台退款失败，等待定时退款
			}else{
				return "wait";
			}
			
			
		//状态错误
		}else{
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
	
	
	/**
	 * 买家查看退款客户介入中
	 */
	public String buyerViewRefundCustProcess(){
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_CS_PROCESS) == 0) {
			return loadBuyerRefundData();
		}else{
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
	
	/**
	 * 买家查看等待卖家确认收货
	 */
	public String buyerViewSellerConfirmGoods() {
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS) == 0) {
			
			//卖家确认收货超时
			Boolean isSellerConfirmGoodsTimeout = sellerRefundTimeoutAO.isSellerConfirmGoodsTimeout(refundDO.getId());
			if(isSellerConfirmGoodsTimeout){
				Result sellerConfirmGoodsTimeoutResult = sellerRefundTimeoutAO.sellerConfirmGoodsTimeout(refundId, false);
				sellerConfirmGoodsTimeoutUrl = (String)sellerConfirmGoodsTimeoutResult.getModel("sellerConfirmGoodsTimeoutUrl");
				return "sellerConfirmGoodsTimeout";
			}
			
			// 没有超时的
			String result= loadBuyerRefundData();
			if(SUCCESS.compareTo(result) !=0) return result;
			
			// 1 是平邮 2,3是快递
			Long logisticsType = tradeRefundLogisticsDO.getLogisticsType() == null ? 1L : tradeRefundLogisticsDO.getLogisticsType();
			if (logisticsType.compareTo(1L) == 0) {
				outDate = DateUtil.skipDateTime(tradeRefundLogisticsDO.getSendDate(),RefundBaseAO.SELLER_ACCETP_SNAIL);
			} else {
				outDate = DateUtil.skipDateTime(tradeRefundLogisticsDO.getSendDate(),RefundBaseAO.SELLER_ACCETP_EMS);
			}
			return SUCCESS;
			
		} else {
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
    /**
     * 卖家同意退款，等待买家退货
     */
	public String buyerViewWaitGoodsReturn(){
		String valid = this.verificationRefund();
		if(SUCCESS.compareTo(valid) !=0) return valid;
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS)==0){
			
			//判断是否超时   卖家同意退款，而买家在5天内不退还商品 超时  -- 关闭该退款(买家)
			Boolean isBuyerReplyTimeout = buyerRefundTimeoutAO.isBuyerNotReturnGoods(refundDO);
			if(isBuyerReplyTimeout){
				Result timeoutResult = buyerRefundTimeoutAO.buyerNotReturnGoods(refundId, true);
				if(timeoutResult.isSuccess()){
					buyerReplyTimeoutUrl = (String)timeoutResult.getModel("buyerReplyTimeoutUrl");
					if(StringUtils.isNotBlank(timeoutResult.getResultCode())){
						this.errorMessage = ResultCodeMsg.getResultMessage(timeoutResult.getResultCode());
					}
					return BUYER_REPLY_TIMEOUT;
				}
			}
			//没有超时
			String result= loadBuyerRefundData();
			if(SUCCESS.compareTo(result) !=0) return result;
			outDate =DateUtil.skipDateTime(refundDO.getStateModified(), RefundBaseAO.BUYER_RETURN_GOODS);
			return SUCCESS;
		}else{
			buyerReturnNewStatePage(refundDO);
			return INPUT;
		}
	}
	
	/**
	 * Method description TODO
	 * @return
	 * @author:[MaYuanChao]
	 * @version $Revision$
	 * @create:2011-11-21
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private String verificationRefund(){
		if (log.isInfoEnabled()) {
			log.info("Entry: BuyerViewRefundAction.verificationRefund()  verification Refund detail infomation");
		}
		try {
			if (refundId != null && refundId != 0) {
				refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
				if (refundDO == null) {
					return INVALID_BUYER_REFUND;
				}
			} else {
				if (orderItemId != null && orderItemId != 0) {
					refundDO = refundApplyAO.loadRefundByOrderItem(orderItemId);
					if (refundDO == null) {
						return INVALID_BUYER_REFUND;
					}
					
					refundId = refundDO.getId();
				}
			}
			if (refundDO == null) {
				return INVALID_BUYER_REFUND;
			}
		} catch (Exception e) {
			log.error("execute BuyerViewRefundAction.verificationRefund() error", e);
			return INVALID_BUYER_REFUND;
		}
		return SUCCESS;
	}
	
	/**
	 * Method description: 加载退款详情信息
	 * @return
	 * @author:[MaYuanChao]
	 * @version $Revision$
	 * @create:2011-11-21
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private String loadBuyerRefundData(){  
		if (log.isInfoEnabled()) {
			log.info("Entry: BuyerViewRefundAction.loadBuyerRefundData()  Loading  Refund detail infomation");
		}
		try {
			if (refundDO == null) {
				return INVALID_BUYER_REFUND;
			}
			orderItemId = refundDO.getOrderItemId();
			refundId = refundDO.getId();
			order = refundApplyAO.getOrderInfo(refundDO.getOrderId());
			orderItem = refundApplyAO.getOrderItemInfo(orderItemId);
			itemDO = refundApplyAO.loadItemByID(orderItem.getItemId());
			orderLogisticsDO = refundApplyAO.queryOrderLogisticsByOrderId(orderItem.getOrderId());
			// 获取商品总价给
			Long orderPriceAmount = order.getPriceAmount() != null ? order.getPriceAmount() : 0L;
			Long postPrice = orderLogisticsDO.getPostPrice() != null ? orderLogisticsDO.getPostPrice() : 0L;
			Long totalPrice = orderPriceAmount - postPrice;
			priceMap.put("totalPrice", MoneyUtil.getCentToDollar(totalPrice));
			// 获取实付价格
			priceMap.put("realPayPrice",refundBaseAO.getRealPayMentamount(refundDO.getOrderId()));
			// 获取优惠价格
			Long sellerMarginPrice = orderItem.getSellerMarginPrice() != null ? orderItem.getSellerMarginPrice() : 0L;
			sellerMarginPrice = (orderItem.getOrderItemPrice() - orderItem.getOriginalPrice())* orderItem.getBuyNum()+ sellerMarginPrice;
			priceMap.put("sellerMarginPrice",MoneyUtil.getCentToDollar(sellerMarginPrice));
			//获取支付给卖家的金额
			long needPaySellerSum = refundCheckAO.getNeedPaySellerSum(orderItem.getOrderId());
			needPaySellerSum = MoneyUtil.getDollarToCent(priceMap.get("realPayPrice")) - needPaySellerSum;
			needPaySellerSum = needPaySellerSum < 0 ? 0 : needPaySellerSum;
			priceMap.put("needPaySellerSum",MoneyUtil.getCentToDollar(needPaySellerSum));
			Long skuid = orderItem.getItemSkuId() == null ? 0L : orderItem.getItemSkuId();
			//如果有skuid, 设置商品sku属性
			if (skuid.compareTo(0L) != 0) {
				SkuDO sku = refundApplyAO.getItemSkuById(skuid);
				itemPropertyList = itemPropertyAO.getItemPropertyBySku(sku);
			}
			//加载物流
			loadRefundLogistics(refundId, refundDO.getRefundState());
			//加载留言
			loadRefundLeavewordList(refundId);
			// 加载物流列表
			loadLogisticsCorpList(refundDO.getRefundState());
			
			//获取优惠券优惠金额
			couponMoneyByYuan = orderCouponAO.getCouponMoneyByYuan(orderItem.getOrderId());
		} catch (Exception e) {
			log.error("execute BuyerViewRefundAction.loadBuyerRefundData() error", e);
			return INVALID_BUYER_REFUND;
		}
		return SUCCESS;
	}
	
	
	/*//加载退款详情信息
	private String loadBuyerRefundData(){
		if (log.isInfoEnabled()) {
			log.info("Entry: BuyerViewRefundAction.loadBuyerRefundData()  load Refund detail infomation");
		}
		try {
			refundId = getLong("id");
			orderItemId = getLong("oid");
			if (refundId != null && refundId != 0) {
				refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
				if (refundDO == null){
					return INVALID_BUYER_REFUND;
				}
				orderItemId = refundDO.getOrderItemId();
				order = refundApplyAO.getOrderInfo(refundDO.getOrderId());
				orderItem = refundApplyAO.getOrderItemInfo(orderItemId);
				itemDO = refundApplyAO.loadItemByID(orderItem.getItemId());
				orderLogisticsDO = refundApplyAO.queryOrderLogisticsByOrderId(orderItem.getOrderId());

			} else {
				if (orderItemId != null && orderItemId != 0) {
					orderItem = refundApplyAO.getOrderItemInfo(orderItemId);
					refundDO = refundApplyAO.loadRefundByOrderItem(orderItemId);
					if (refundDO == null){
						return INVALID_BUYER_REFUND;
					}
					refundId = refundDO.getId();
					order = refundApplyAO.getOrderInfo(orderItem.getOrderId());
					itemDO = refundApplyAO.loadItemByID(orderItem.getItemId());
					orderLogisticsDO = refundApplyAO.queryOrderLogisticsByOrderId(orderItem.getOrderId());
				}
			}
			// 获取商品总价给
			Long orderPriceAmount = order.getPriceAmount() != null ? order.getPriceAmount() : 0L;
			Long postPrice = orderLogisticsDO.getPostPrice() != null ? orderLogisticsDO.getPostPrice() : 0L;
			Long totalPrice = orderPriceAmount - postPrice;
			priceMap.put("totalPrice", MoneyUtil.getCentToDollar(totalPrice));
			
			// 获取实付价格
			priceMap.put("realPayPrice",refundBaseAO.getRealPayMentamount(refundDO.getOrderId()));
			// 获取优惠价格
			Long sellerMarginPrice = orderItem.getSellerMarginPrice() != null ? orderItem.getSellerMarginPrice() : 0L;
			sellerMarginPrice = (orderItem.getOrderItemPrice() - orderItem.getOriginalPrice())* orderItem.getBuyNum()+ sellerMarginPrice;
			priceMap.put("sellerMarginPrice",MoneyUtil.getCentToDollar(sellerMarginPrice));
			
			//获取支付给卖家的金额
			long needPaySellerSum = refundCheckAO.getNeedPaySellerSum(orderItem.getOrderId());
			needPaySellerSum = MoneyUtil.getDollarToCent(priceMap.get("realPayPrice")) - needPaySellerSum;
			needPaySellerSum = needPaySellerSum < 0 ? 0 : needPaySellerSum;
			priceMap.put("needPaySellerSum",MoneyUtil.getCentToDollar(needPaySellerSum));
			Long skuid = orderItem.getItemSkuId() == null ? 0L : orderItem.getItemSkuId();
			//如果有skuid, 设置商品sku属性
			if (skuid.compareTo(0L) != 0) {
				SkuDO sku = refundApplyAO.getItemSkuById(skuid);
				itemPropertyList = itemPropertyAO.getItemPropertyBySku(sku);
			}
			//加载物流
			loadRefundLogistics(refundId, refundDO.getRefundState());
			//加载留言
			loadRefundLeavewordList(refundId);
			// 加载物流列表
			loadLogisticsCorpList(refundDO.getRefundState());
			
			// 验证是否超时
			
			
			
		} catch (Exception e) {
			log.error("execute BuyerViewRefundAction.loadBuyerRefundData() error", e);
			return INVALID_BUYER_REFUND;
		}
		return null;
	}*/
	
	
	/**
	 * <p>Description: 	 加载物流信息</p>
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-26
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void loadRefundLogistics(Long refundId,Integer status){
		if(refundId!=null && status.compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE)>0)
			tradeRefundLogisticsDO = refundApplyAO.getRefundLogistics(refundId);
	}
	/**
	 * <p>Description:  加载物流列表</p>
	 * @param status
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-26
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void loadLogisticsCorpList(Integer status){
		if(status.compareTo(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS)==0)
		logisticsCorpDOList=logisticsCorpAO.getLogisticsCorpByStatus(new LogisticsCorpDO());
	}
	
	/**
	 * <p>Description: 	 加载留言信息</p>
	 * @param refundId
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-8-26
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings("unchecked")
	private void loadRefundLeavewordList(Long refundId){
		if(refundId !=null){
		Result result = tradeRefundAO.selectRefundLeavewordList(refundId);
		levList = (List<TradeRefundLeavewordDO>)result.getModel("list");
		}
	}
	public Long getRefundId() {
		if(getLong("id") != 0){
			refundId = getLong("id");
		}
		return refundId;
	}
	public Long getOrderItemId() {
		if(getLong("oid") != 0){
			orderItemId = getLong("oid");
		}
		return orderItemId;
	}
	public OrderDO getOrder() {
		return order;
	}
	public OrderItemDO getOrderItem() {
		return orderItem;
	}
	public ItemDO getItemDO() {
		return itemDO;
	}
	public RefundDO getRefundDO() {
		return refundDO;
	}
	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}
	public TradeRefundLogisticsDO getTradeRefundLogisticsDO() {
		return tradeRefundLogisticsDO;
	}
	
	public void setRefundBaseAO(RefundBaseAO refundBaseAO) {
		this.refundBaseAO = refundBaseAO;
	}
	public Map<String, String> getPriceMap() {
		return priceMap;
	}
	public Date getOutDate() {
		return outDate;
	}
	
	/**
	 * 超时剩余时间，以秒为单位
	 * */
	
	public Long getOutDateSec() {
		Date now = new Date();
		outDate = outDate == null ? now : outDate;
		Long outDateSec = (outDate.getTime() - now.getTime())/1000;
		return outDateSec;
	}
	
	public String getCouponMoneyByYuan() {
		return couponMoneyByYuan;
	}
	
	public String getLogisticsId() {
		return logisticsId;
	}
	public String getLogisticsNumber() {
		return logisticsNumber;
	}
	public String getBuyerMemo() {
		return buyerMemo;
	}
	
	public String getSellerConfirmGoodsTimeoutUrl() {
		return sellerConfirmGoodsTimeoutUrl;
	}
	
    public String getSellerReplyTimeoutUrl() {
		return sellerReplyTimeoutUrl;
	}
 
	public String getBuyerReplyTimeoutUrl() {
		return buyerReplyTimeoutUrl;
	}

	public List<LogisticsCorpDO> getLogisticsCorpDOList() {
		return logisticsCorpDOList;
	}
	public List<TradeRefundLeavewordDO> getLevList() {
		return levList;
	}
	public void setTradeRefundAO(TradeRefundAO tradeRefundAO) {
		this.tradeRefundAO = tradeRefundAO;
	}
	
	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}
	public void setLogisticsCorpAO(LogisticsCorpAO logisticsCorpAO) {
		this.logisticsCorpAO = logisticsCorpAO;
	}
	public void setRefundCheckAO(RefundCheckAO refundCheckAO) {
		this.refundCheckAO = refundCheckAO;
	}
	 public List<ItemPropertyVO> getItemPropertyList() {
			return itemPropertyList;
	}
	public void setItemPropertyAO(ItemPropertyAO itemPropertyAO) {
		this.itemPropertyAO = itemPropertyAO;
	}
	
	public void setSellerRefundTimeoutAO(SellerRefundTimeoutAO sellerRefundTimeoutAO) {
		this.sellerRefundTimeoutAO = sellerRefundTimeoutAO;
	}
	

	public void setBuyerRefundTimeoutAO(BuyerRefundTimeoutAO buyerRefundTimeoutAO) {
		this.buyerRefundTimeoutAO = buyerRefundTimeoutAO;
	}

	public void setOrderCouponAO(OrderCouponAO orderCouponAO) {
		this.orderCouponAO = orderCouponAO;
	}
}