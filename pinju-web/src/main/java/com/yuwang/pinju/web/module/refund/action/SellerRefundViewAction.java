package com.yuwang.pinju.web.module.refund.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.coupon.ao.OrderCouponAO;
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
import com.yuwang.pinju.domain.order.ItemPropertyVO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLeavewordDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;

/**
 * 卖家查看退款
 * 
 * @author shihongbo
 * @date 2011-6-28
 * @update 2011-10-25 [MaYuanChao]
 */
public class SellerRefundViewAction extends RefundBaseAction implements Action, SellerRefund{
	
	private static final long serialVersionUID = 1352724000364080884L;

	private static final String REFUND_STATE_ERROR = "refundStateError";
	
	private RefundApplyAO refundApplyAO;
	private ItemPropertyAO itemPropertyAO;
	private RefundCheckAO refundCheckAO;
	private TradeRefundAO tradeRefundAO;
	private RefundBaseAO refundBaseAO;
	private SellerRefundTimeoutAO sellerRefundTimeoutAO;
	private BuyerRefundTimeoutAO buyerRefundTimeoutAO;
	private OrderCouponAO orderCouponAO;
	
	private OrderDO order;
	private OrderItemDO orderItem;
	private ItemDO itemDO;
	private RefundDO refundDO;
	private OrderLogisticsDO orderLogisticsDO;
	private TradeRefundLogisticsDO tradeRefundLogisticsDO;
	private List<TradeRefundLeavewordDO> levList;
	List<ItemPropertyVO> itemPropertyList;

	private Date leftDay;
	private Long refundId;
	private Long orderItemId;
	private String tipRefundUrl;
	private String sellerReplyTimeoutUrl;
	private String sellerConfirmGoodsTimeoutUrl;
	private String buyerReplyTimeoutUrl;
	private String couponMoneyByYuan;

	private Map<String,String> priceMap = new HashMap<String,String>();
	
	@SuppressWarnings("unchecked")
	public String loadData(){
		try {
			if (refundId != null && refundId != 0) {
				refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
				if (refundDO == null){
					return INVALID_SELLER_REFUND;
				}
				order = refundApplyAO.getOrderInfo(refundDO.getOrderId());
				orderItem = refundApplyAO.getOrderItemInfo(refundDO.getOrderItemId());
				orderItemId = orderItem.getOrderItemId();
				itemDO = refundApplyAO.loadItemByID(orderItem.getItemId());
				orderLogisticsDO = refundApplyAO.queryOrderLogisticsByOrderId(orderItem.getOrderId());

				//指定子订单id
			} else if (orderItemId != null && orderItemId != 0) {
				orderItem = refundApplyAO.getOrderItemInfo(orderItemId);
				if (orderItem == null){
					return INVALID_SELLER_REFUND;
				}
				refundDO = refundApplyAO.loadRefundByOrderItem(orderItemId);
				refundId = refundDO.getId();
				order = refundApplyAO.getOrderInfo(orderItem.getOrderId());
				itemDO = refundApplyAO.loadItemByID(orderItem.getItemId());
				orderLogisticsDO = refundApplyAO.queryOrderLogisticsByOrderId(orderItem.getOrderId());
			}
			if (refundDO == null){
				return INVALID_SELLER_REFUND;
			}
			TradeRefundLeavewordDO tdo = new TradeRefundLeavewordDO();
			tdo.setRefundId(refundDO.getId());
			Result result = tradeRefundAO.selectRefundLeavewordList(tdo);
			levList = (List<TradeRefundLeavewordDO>) result.getModel("list");
			
			// 获取商品总价给
			Long orderPriceAmount = order.getPriceAmount() != null ? order.getPriceAmount() : 0L;
			Long postPrice = orderLogisticsDO.getPostPrice() != null ? orderLogisticsDO.getPostPrice(): 0L;
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
			tradeRefundLogisticsDO = refundApplyAO.getRefundLogistics(refundId);
			
			//获取优惠券优惠金额
			couponMoneyByYuan = orderCouponAO.getCouponMoneyByYuan(orderItem.getOrderId());
		} catch (Exception e) {
			log.error("execute BuyerViewRefundAction.loadBuyerRefundData() error", e);
			return INVALID_SELLER_REFUND;
		}
		return null;
	}
	
	//卖家查看退款申请，买家已经申请，等待卖家处理
	@AssistantPermission(target = "crm", action = "refund")
	public String execute() {
		String isNull = loadData();
		if(isNull != null) return isNull;
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		//判断超时
		Boolean isSellerReplyTimeout = sellerRefundTimeoutAO.isSellerReplyTimeout(refundDO);
		
		//已经超时
		if(isSellerReplyTimeout){
			
			Result timeoutResult = sellerRefundTimeoutAO.sellerReplyTimeout(refundId, true);
			
			sellerReplyTimeoutUrl = (String)timeoutResult.getModel("sellerReplyTimeoutUrl");
			
			return "sellerReplyTimeout";	
		}
		
		//超时时间
		leftDay = DateUtil.skipDateTime(refundDO.getApplyDate(), 5);
		
		return SUCCESS;
	}
	
	//卖家查看退款申请，卖家拒绝
	@AssistantPermission(target = "crm", action = "refund")
	public String viewSellerReject(){
		String isNull = loadData();
		if(isNull != null) return isNull;
		
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_SELLER_REFUSE_BUYER) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		
		Boolean isBuyerReplyTimeout = buyerRefundTimeoutAO.isBuyerNoReplyTimeout(refundDO);
		//卖家拒绝，买家超时
		if(isBuyerReplyTimeout){
			Result timeoutResult = buyerRefundTimeoutAO.buyerReplyTimeout(refundId, false);
			if(timeoutResult.isSuccess()){
				buyerReplyTimeoutUrl = (String)timeoutResult.getModel("buyerReplyTimeoutUrl");
				if(StringUtils.isNotBlank(timeoutResult.getResultCode())){
					this.errorMessage = ResultCodeMsg.getResultMessage(timeoutResult.getResultCode());
				}
				return BUYER_REPLY_TIMEOUT;
			}
		}
		
		//超时时间
		leftDay = DateUtil.skipDateTime(refundDO.getReplyDate(), 5);
		return SUCCESS;
	}
	
	//卖家查看退款申请，退款关闭
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerViewRefundClosed(){
		String isNull = loadData();
		if(isNull != null) return isNull;
		
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_CLOSED) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		return SUCCESS;
	}
	
	//卖家查看退款申请，退款成功
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerViewRefundSuccess(){
		String isNull = loadData();
		if(isNull != null) return isNull;
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_SUCCESS) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		return SUCCESS;
	}
	
	//卖家查看退款申请，退款协议达成
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerViewRefundProtocalAgree(){
		String isNull = loadData();
		if(isNull != null) return isNull;
		
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		return SUCCESS;
	}
	
	//卖家查看退款申请，退款失败
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerViewRefundFail(){
		String isNull = loadData();
		if(isNull != null) return isNull;
		
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_FAIL) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		return SUCCESS;
	}
	
	
	//卖家查看退款申请，客服仲裁
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerViewRefundCustProcess(){
		String isNull = loadData();
		if(isNull != null) return isNull;
		
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_CS_PROCESS) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		return SUCCESS;
	}
	
	//卖家查看退款申请，等待买家发货
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerViewWaitGoodsReturn(){
		String isNull = loadData();
		if(isNull != null) return isNull;
		
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		leftDay = DateUtil.skipDateTime(refundDO.getStateModified(), RefundBaseAO.BUYER_RETURN_GOODS);
		
		//判断是否超时   卖家同意退款，而买家在5天内不退还商品 超时  -- 关闭该退款(买家)
		Boolean isBuyerReplyTimeout = buyerRefundTimeoutAO.isBuyerNotReturnGoods(refundDO);
		if(isBuyerReplyTimeout){
			Result timeoutResult = buyerRefundTimeoutAO.buyerNotReturnGoods(refundId, false);
			if(timeoutResult.isSuccess()){
				buyerReplyTimeoutUrl = (String)timeoutResult.getModel("buyerReplyTimeoutUrl");
				if(StringUtils.isNotBlank(timeoutResult.getResultCode())){
					this.errorMessage = ResultCodeMsg.getResultMessage(timeoutResult.getResultCode());
				}
				return BUYER_REPLY_TIMEOUT;
			}
		}
		
		return SUCCESS;
	}
	
	//卖家查看退款申请，等待卖家确认收货
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerViewConfirmGoods(){
		String isNull = loadData();
		if(isNull != null) return isNull;
		
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS) != 0){
			tipRefundUrl = sellerRefundUrlMap.get(refundDO.getRefundState());
			tipRefundUrl += "?id=" + refundId;
			return REFUND_STATE_ERROR;
		}
		
		if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS) == 0) {
			if(tradeRefundLogisticsDO!=null){
				// 1 是平邮 2,3是快递
				Long logisticsType = tradeRefundLogisticsDO.getLogisticsType() == null ? 1L : tradeRefundLogisticsDO.getLogisticsType();
				if (logisticsType.compareTo(1L) == 0) {
					leftDay = DateUtil.skipDateTime(tradeRefundLogisticsDO.getSendDate(),RefundBaseAO.SELLER_ACCETP_SNAIL);
				} else {
					leftDay = DateUtil.skipDateTime(tradeRefundLogisticsDO.getSendDate(),RefundBaseAO.SELLER_ACCETP_EMS);
				}
				
				Date now = new Date();
				
				//已经超时
				if(leftDay.compareTo(now) < 0){
					Result sellerRefundTimeoutResult = sellerRefundTimeoutAO.sellerConfirmGoodsTimeout(refundId, true);
					sellerConfirmGoodsTimeoutUrl = (String)sellerRefundTimeoutResult.getModel("sellerConfirmGoodsTimeoutUrl");
					return "sellerConfirmGoodsTimeout";					
				}
			}
			return SUCCESS;
		}

		return SUCCESS;
	}

	public String getSellerConfirmGoodsTimeoutUrl() {
		return sellerConfirmGoodsTimeoutUrl;
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
	
	public RefundDO getRefundDO() {
		return refundDO;
	}
	
	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public List<TradeRefundLeavewordDO> getLevList() {
		return levList;
	}
	
	public OrderDO getOrder() {
		return order;
	}

	public OrderItemDO getOrderItem() {
		return orderItem;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}
	
	
	public ItemDO getItemDO() {
		return itemDO;
	}
	
	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	
	public void setItemDO(ItemDO itemDO) {
		this.itemDO = itemDO;
	}
	
	public void setTradeRefundAO(TradeRefundAO tradeRefundAO) {
		this.tradeRefundAO = tradeRefundAO;
	}

	public List<ItemPropertyVO> getItemPropertyList() {
		return itemPropertyList;
	}
	
	public void setItemPropertyAO(ItemPropertyAO itemPropertyAO) {
		this.itemPropertyAO = itemPropertyAO;
	}	
	
	public TradeRefundLogisticsDO getTradeRefundLogisticsDO() {
		return tradeRefundLogisticsDO;
	}
	
	public Date getLeftDay() {
		return leftDay;
	}

	
	public Long getLeftDaySec() {
		Date now = new Date();
		leftDay = leftDay == null ? now: leftDay;
		Long leftDaySec = (leftDay.getTime() - now.getTime())/1000;
		return leftDaySec;
	}
	
	public void setLeftDay(Date leftDay) {
		this.leftDay = leftDay;
	}
	
	public String getSellerReplyTimeoutUrl() {
		return sellerReplyTimeoutUrl;
	}

	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}

	public String getTipRefundUrl() {
		return tipRefundUrl;
	}

	public void setRefundBaseAO(RefundBaseAO refundBaseAO) {
		this.refundBaseAO = refundBaseAO;
	}

	public void setOrderCouponAO(OrderCouponAO orderCouponAO) {
		this.orderCouponAO = orderCouponAO;
	}
	
	public Map<String, String> getPriceMap() {
		return priceMap;
	}

	public void setRefundCheckAO(RefundCheckAO refundCheckAO) {
		this.refundCheckAO = refundCheckAO;
	}

	public void setSellerRefundTimeoutAO(SellerRefundTimeoutAO sellerRefundTimeoutAO) {
		this.sellerRefundTimeoutAO = sellerRefundTimeoutAO;
	}
	
	public void setBuyerRefundTimeoutAO(BuyerRefundTimeoutAO buyerRefundTimeoutAO) {
		this.buyerRefundTimeoutAO = buyerRefundTimeoutAO;
	}
	
	public String getBuyerReplyTimeoutUrl() {
		return buyerReplyTimeoutUrl;
	}
	
	public String getCouponMoneyByYuan() {
		return couponMoneyByYuan;
	}
}
