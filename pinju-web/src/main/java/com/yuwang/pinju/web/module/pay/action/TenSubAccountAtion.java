package com.yuwang.pinju.web.module.pay.action;

import java.util.List;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.resultcode.TenPayResultCode;
import com.yuwang.pinju.core.trade.ao.TenSubAccountAO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.trade.TenSubAccountDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * @Discription: 向财付通发起分账请求Action类
 * @Project: pinju-web
 * @Package: com.yuwang.pinju.web.module.pay.action
 * @Title: TenSubAccountAtion.java
 * @author: [贺泳]
 * @date 2011-9-14 下午02:45:22
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class TenSubAccountAtion extends BaseAction{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 分账集合List
	 */
	private List<TenSubAccountDO> tenSubAccountList;
	
	/**
	 * 分账业务AO
	 */
	private TenSubAccountAO tenSubAccountAO;
	
	/**
	 * 订单DO
	 */
	private OrderDO orderDO;

	/**
	 * 错误信息
	 */
	private String errorMessage;

	/**
	 * 错误信息的订单号
	 */
	private String errorOrderId;

	/**
	 * @Description: 向财付通发起分账请求 和 回调财付通的分账写在同一个Action中
	 * @author [贺泳]
	 * @date 2011-9-26
	 * @version 1.0
	 * @return String
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String tenSubAccount() {
		try {
			//请求参数结果
			Result result = tenSubAccountAO.createSubAccountParam(tenSubAccountList);
			if (!result.isSuccess()) {
				errorOrderId = (String) result.getModel("errorOrderId");
				errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode());
				return ERROR;
			}
			//得到orderDO，用于成功页面显示信息
			orderDO = (OrderDO) result.getModel("orderDO");
		} catch (Exception e) {
			log.error("Event=[TenSubAccountAtion#tenSubAccount] 向财付通发起分账或回调异常:",e);
			errorMessage = ResultCodeMsg.getResultMessage(TenPayResultCode.SUBACCOUNT_EXCEPTION);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * @Description: 分账成功后，根据订单Id查询订单信息，显示成功页面。
	 * @author [贺泳]
	 * @date 2011-10-21 下午07:22:47
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	public String queryOrderDO(){
		//根据outPayId 查询订单详情，用于成功页面显示订单信息
		try {
			Long oid = super.getLong("oId");
			if(oid == null || oid == 0){
				errorMessage = ResultCodeMsg.getResultMessage(TenPayResultCode.SUNACCOUNT_POST_ADDRESS_FAIL);
				return ERROR;
			}
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			if (!loginInfo.isLogin()) {
				ServletUtil.loginCurrentResultUrl();
				return LOGIN;
			}
			Result result = tenSubAccountAO.queryOrderByOrderId(oid,loginInfo.getMemberId());
			
			if(!result.isSuccess()){
				errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode(),oid.toString());
				return ERROR;
			}
			orderDO = (OrderDO) result.getModel("orderDO");
		} catch (Exception e) {
			log.error("Event=[TenSubAccountAtion#queryOrderDO] 分账成功后查询订单异常:",e);
			errorMessage = ResultCodeMsg.getResultMessage(TenPayResultCode.SUBACCOUNT_QUERY_ORDER_EXCEPTION);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public List<TenSubAccountDO> getTenSubAccountList() {
		return tenSubAccountList;
	}
	
	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public void setTenSubAccountList(List<TenSubAccountDO> tenSubAccountList) {
		this.tenSubAccountList = tenSubAccountList;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorOrderId() {
		return errorOrderId;
	}

	public void setErrorOrderId(String errorOrderId) {
		this.errorOrderId = errorOrderId;
	}
	
	public void setTenSubAccountAO(TenSubAccountAO tenSubAccountAO) {
		this.tenSubAccountAO = tenSubAccountAO;
	}
}
