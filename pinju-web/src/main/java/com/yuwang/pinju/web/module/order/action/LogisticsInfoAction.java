package com.yuwang.pinju.web.module.order.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.order.ao.LogisticsServiceAO;
import com.yuwang.pinju.core.order.ao.OrderBusinessAO;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.order.LogisticsServiceDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

public class LogisticsInfoAction {

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private LogisticsServiceAO logisticsServiceAO;
	private LogisticsCorpAO logisticsCorpAO;
	private OrderBusinessAO orderBusinessAO;
	private OrderQueryAO orderQueryAO;
	private OrderCheckManager orderCheckManager;

	private LogisticsServiceDO logisticsServiceDO;

	private LogisticsCorpDO logisticsCorpDO;
	private OrderLogisticsDO orderLogisticsDO;
	private OrderDO orderDO;
	private List<OrderItemDO> orderItemList;
	private MemberInfoDO memberInfoDO = new MemberInfoDO();
	private String cd;

	private String exp;
	private String orderId;
	private String errorMessage;
	private String corpHCode;


	@SuppressWarnings("unchecked")
	public String executeBuyer() {
		try {
			if (cd == null || exp == null || "".equals(cd) || "".equals(exp)) { // 无参数
				if(exp.compareTo("noLogisCorp") != 0){
					errorMessage = "非法查看订单物流信息！";
					return "error";
				}
			}

			if (EmptyUtil.isBlank(this.orderId)) {
				errorMessage = "非法查看订单物流信息！";
				return "error";
			}
			long orderid = Long.valueOf(orderId);

			// 判断订单是否属于买家
			if (!orderCheckManager.isBuyerOrder(orderid, this.getUserId())) {
				errorMessage = "非法查看订单物流信息！";
				return "error";
			}

			LogisticsCorpDO logisticsCorpDO1 = new LogisticsCorpDO();
			logisticsCorpDO1.setCorpCode(exp);
			List<LogisticsCorpDO> _logisticsCorpList = logisticsCorpAO.getLogisticsCorpByStatus(logisticsCorpDO1);
			if (_logisticsCorpList != null&& _logisticsCorpList.size() > 0){
				logisticsCorpDO = _logisticsCorpList.get(0);
//				logisticsServiceDO = logisticsServiceAO.getTrackBybillcode(cd,exp,logisticsCorpDO.getCorpHCode());
			}
			
//			Result orderDOResult = orderQueryAO.getOrderDOById(orderid, this.getUserId(), "1");
//			if (orderDOResult.isSuccess())
//				orderDO = (OrderDO) orderDOResult.getModel("orderDO");
			orderDO = orderQueryAO.queryOrderDO(orderid);
			
			Result orderItemResult = orderQueryAO.queryOrderItemList(orderid);
			if (orderItemResult.isSuccess())
				orderItemList = (List<OrderItemDO>) orderItemResult
						.getModel("list");

			Result orderLogisticsDoResult = orderBusinessAO
					.queryOrderLogisticsByOrderId(orderid);
			if (orderLogisticsDoResult.isSuccess())
				orderLogisticsDO = (OrderLogisticsDO) orderLogisticsDoResult
						.getModel("obj");

			if (orderLogisticsDO == null)
				orderLogisticsDO = new OrderLogisticsDO();

			if (logisticsCorpDO == null)
				logisticsCorpDO = new LogisticsCorpDO();

			if (orderDO == null)
				orderDO = new OrderDO();

			return "success";
		} catch (Exception e) {
			errorMessage = "获取物流信息失败，请稍后重试！";
			return "error";
		}

	}

	@SuppressWarnings("unchecked")
	public String executeSeller() {
		try {
			if (cd == null || exp == null || "".equals(cd) || "".equals(exp)) { // 无参数
				if(exp.compareTo("noLogisCorp") != 0){
					errorMessage = "非法查看订单物流信息！";
					return "error";
				}
			}

			if (EmptyUtil.isBlank(this.orderId)) {
				errorMessage = "非法查看订单物流信息！";
				return "error";
			}
			long orderid = Long.valueOf(orderId);

			// 判断订单是否属于卖家
			if (!orderCheckManager.isSellerOrder(orderid, this.getUserId())) {
				errorMessage = "非法查看订单物流信息！";
				return "error";
			}

			LogisticsCorpDO logisticsCorpDO1 = new LogisticsCorpDO();
			logisticsCorpDO1.setCorpCode(exp);
			List<LogisticsCorpDO> _logisticsCorpList = logisticsCorpAO.getLogisticsCorpByStatus(logisticsCorpDO1);
			if (_logisticsCorpList != null&& _logisticsCorpList.size() > 0){
				logisticsCorpDO = _logisticsCorpList.get(0);
//				logisticsServiceDO = logisticsServiceAO.getTrackBybillcode(cd,exp,logisticsCorpDO.getCorpHCode());
			}

//			Result orderDOResult = orderQueryAO.getOrderDOById(orderid, this.getUserId(), "2");
//			if (orderDOResult.isSuccess())
//				orderDO = (OrderDO) orderDOResult.getModel("orderDO");
			orderDO = orderQueryAO.queryOrderDO(orderid);
			
			Result orderItemResult = orderQueryAO.queryOrderItemList(orderid);
			if (orderItemResult.isSuccess())
				orderItemList = (List<OrderItemDO>) orderItemResult
						.getModel("list");

			Result orderLogisticsDoResult = orderBusinessAO
					.queryOrderLogisticsByOrderId(orderid);
			if (orderLogisticsDoResult.isSuccess())
				orderLogisticsDO = (OrderLogisticsDO) orderLogisticsDoResult
						.getModel("obj");

			if (orderLogisticsDO == null)
				orderLogisticsDO = new OrderLogisticsDO();

			if (logisticsCorpDO == null)
				logisticsCorpDO = new LogisticsCorpDO();

			if (orderDO == null)
				orderDO = new OrderDO();

			return "success";
		} catch (Exception e) {
			errorMessage = "获取物流信息失败，请稍后重试！";
			return "error";
		}

	}
	
	
	public String getLogisticsInfo(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			logisticsServiceDO = logisticsServiceAO.getTrackBybillcode(cd,exp,corpHCode);
			StringBuffer xml = new StringBuffer(
			"<?xml version=\"1.0\" encoding=\"GBK\"?><rows>");
			xml.append("<row id='x:pinju'>");
			xml.append("<mess>" + logisticsServiceDO + "</mess>");
			xml.append("</row>");
			xml.append("</rows>");
			JSONObject obj = new JSONObject();
			obj.put("root", logisticsServiceDO);
			out = response.getWriter();
			out.print(obj.toString());
		} catch (Exception e) {
			log.error("LogisticsInfoAction#getLogisticsInfo：",e);
		}
		
		return null;
	}
	
	public String getCd() {
		return cd;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getExp() {
		return exp;
	}

	public LogisticsCorpDO getLogisticsCorpDO() {
		return logisticsCorpDO;
	}

	public LogisticsServiceDO getLogisticsServiceDO() {
		return logisticsServiceDO;
	}

	public MemberInfoDO getMemberInfoDO() {
		return memberInfoDO;
	}

	public OrderBusinessAO getOrderBusinessAO() {
		return orderBusinessAO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public String getOrderId() {
		return orderId;
	}

	public List<OrderItemDO> getOrderItemList() {
		return orderItemList;
	}

	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}

	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			//userId = login.getMemberId();
			userId = login.getMasterMemberId();
		} else {

		}
		return userId;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public void setLogisticsCorpAO(LogisticsCorpAO logisticsCorpAO) {
		this.logisticsCorpAO = logisticsCorpAO;
	}

	public void setLogisticsCorpDO(LogisticsCorpDO logisticsCorpDO) {
		this.logisticsCorpDO = logisticsCorpDO;
	}

	public void setLogisticsServiceAO(LogisticsServiceAO logisticsServiceAO) {
		this.logisticsServiceAO = logisticsServiceAO;
	}

	public void setMemberInfoDO(MemberInfoDO memberInfoDO) {
		this.memberInfoDO = memberInfoDO;
	}

	public void setOrderBusinessAO(OrderBusinessAO orderBusinessAO) {
		this.orderBusinessAO = orderBusinessAO;
	}

	public void setOrderCheckManager(OrderCheckManager orderCheckManager) {
		this.orderCheckManager = orderCheckManager;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderItemList(List<OrderItemDO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public void setOrderLogisticsDO(OrderLogisticsDO orderLogisticsDO) {
		this.orderLogisticsDO = orderLogisticsDO;
	}

	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
	}
	
	public String getCorpHCode() {
		return corpHCode;
	}

	public void setCorpHCode(String corpHCode) {
		this.corpHCode = corpHCode;
	}
}
