package com.yuwang.pinju.web.module.refund.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.order.ao.LogisticsServiceAO;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundBaseAO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.order.LogisticsServiceDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/** 
 * <p> Description: 处理退款物流信息 </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-2
 */
public class RefundLogisticsAction extends RefundBaseAction{
	private static final long serialVersionUID = 648906367640374582L;
	private OrderDO order;
	private OrderItemDO orderItem;
	private RefundDO refundDO;
	private OrderLogisticsDO orderLogisticsDO;
	private LogisticsServiceDO logisticsServiceDO;
	private LogisticsCorpDO logisticsCorpDO;
	private LogisticsServiceAO logisticsServiceAO;
	private LogisticsCorpAO logisticsCorpAO;
	private RefundApplyAO refundApplyAO;

	private String cd;
	private String exp;
	private Long refundId;
	private RefundBaseAO refundBaseAO;
	private Map<String,String> priceMap = new HashMap<String,String>();

	@Override
	public String execute() {
		try {
			refundId = getLong("id");
			if (refundId != null){
				refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			}
			if (refundDO == null){
				return INVALID_BUYER_REFUND;
			}	
			// 判断当前用户是否可以访问
			Long memberId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
			if (refundDO.getBuyerId().compareTo(memberId) != 0) {
				if (refundDO.getSellerId().compareTo(memberId) != 0) {
					return INVALID_BUYER_REFUND;
				}
			}
			
			LogisticsCorpDO logisticsCorpDO1 = new LogisticsCorpDO();
			logisticsCorpDO1.setCorpCode(exp);
			List<LogisticsCorpDO> listLog = logisticsCorpAO.getLogisticsCorpByStatus(logisticsCorpDO1);
			if (listLog != null && listLog.size() > 0){
				logisticsCorpDO = listLog.get(0);
				logisticsServiceDO = logisticsServiceAO.getTrackBybillcode(cd, exp, logisticsCorpDO.getCorpHCode());
			}
			order = refundApplyAO.getOrderInfo(refundDO.getOrderId());
			orderItem = refundApplyAO.getOrderItemInfo(refundDO.getOrderItemId());
			orderLogisticsDO = refundApplyAO.queryOrderLogisticsByOrderId(orderItem.getOrderId());
			// 获取商品总价给
			Long orderPriceAmount = 0L;
			Long postPrice = 0L;
			if (order.getPriceAmount() != null) orderPriceAmount = order.getPriceAmount();
			if (orderLogisticsDO.getPostPrice() != null)postPrice = orderLogisticsDO.getPostPrice();
			Long totalPrice = orderPriceAmount - postPrice;
			priceMap.put("totalPrice", MoneyUtil.getCentToDollar(totalPrice));
			// 获取实付价格
			priceMap.put("realPayPrice",refundBaseAO.getRealPayMentamount(refundDO.getOrderId()));
		} catch (Exception e) {
			log.error("RefundLogisticsAction.execute() getting logistics infomation error", e);
			return INVALID_BUYER_REFUND;
		}
		
		return SUCCESS;
	}

	public LogisticsServiceAO getLogisticsServiceAO() {
		return logisticsServiceAO;
	}

	public void setLogisticsServiceAO(LogisticsServiceAO logisticsServiceAO) {
		this.logisticsServiceAO = logisticsServiceAO;
	}

	public LogisticsCorpAO getLogisticsCorpAO() {
		return logisticsCorpAO;
	}

	public void setLogisticsCorpAO(LogisticsCorpAO logisticsCorpAO) {
		this.logisticsCorpAO = logisticsCorpAO;
	}

	public RefundApplyAO getRefundApplyAO() {
		return refundApplyAO;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public Long getRefundId() {
		return refundId;
	}

	public OrderDO getOrder() {
		return order;
	}

	public void setOrder(OrderDO order) {
		this.order = order;
	}

	public OrderItemDO getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItemDO orderItem) {
		this.orderItem = orderItem;
	}

	public RefundDO getRefundDO() {
		return refundDO;
	}

	public void setRefundDO(RefundDO refundDO) {
		this.refundDO = refundDO;
	}

	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}

	public void setOrderLogisticsDO(OrderLogisticsDO orderLogisticsDO) {
		this.orderLogisticsDO = orderLogisticsDO;
	}

	public LogisticsServiceDO getLogisticsServiceDO() {
		return logisticsServiceDO;
	}

	public void setLogisticsServiceDO(LogisticsServiceDO logisticsServiceDO) {
		this.logisticsServiceDO = logisticsServiceDO;
	}

	public LogisticsCorpDO getLogisticsCorpDO() {
		return logisticsCorpDO;
	}

	public void setLogisticsCorpDO(LogisticsCorpDO logisticsCorpDO) {
		this.logisticsCorpDO = logisticsCorpDO;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public void setRefundBaseAO(RefundBaseAO refundBaseAO) {
		this.refundBaseAO = refundBaseAO;
	}

	public Map<String, String> getPriceMap() {
		return priceMap;
	}

	public void setPriceMap(Map<String, String> priceMap) {
		this.priceMap = priceMap;
	}
}
