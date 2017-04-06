package com.yuwang.pinju.web.module.pay.action;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.core.trade.ao.TenpaySinglepayAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>财付通单笔支付Action</p>
 * 
 * @author:shihongbo
 */
public class SinglePayAction extends BaseAction {

	private static final long serialVersionUID = -3875222488017592621L;

	private Long orderId;
	
	private OrderQueryAO orderQueryAO;

	private TenpaySinglepayAO tenpaySinglepayAO;
	
	private OrderDO orderDO;

	private String postUrl;
	

	public String inputSingleOrder(){
		return SUCCESS;
	}
	
	public String tenpaySinglepaySendOrder(){
		
		Result orderDOResult = orderQueryAO.getOrderDOById(orderId,this.getUserId(),"1");
		
		if(orderDOResult.isSuccess())
			 orderDO = (OrderDO) orderDOResult.getModel("orderDO");

		Result result = tenpaySinglepayAO.createSinglepayParam(orderId);
		postUrl = (String) result.getModel("postUrl");
		
		postUrl += "&spbill_create_ip=" + ServletUtil.getRemoteNumberIp();
		
		return SUCCESS;
	}
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setTenpaySinglepayAO(TenpaySinglepayAO tenpaySinglepayAO) {
		this.tenpaySinglepayAO = tenpaySinglepayAO;
	}

	public String getPostUrl() {
		return postUrl;
	}
	
	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMemberId();
		} else {

		}
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}

}
