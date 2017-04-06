package com.yuwang.pinju.web.module.order.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.member.MemberInfoDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

public class OrderInfoAction {
	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private OrderQueryAO orderQueryAO;
	private LogisticsCorpAO logisticsCorpAO;

	private Result result;

	private MemberInfoDO memberInfo;
	private OrderLogisticsDO orderLogisticsDO;
	private LogisticsCorpDO logisticsCorpDO;
	private VouchPayDO vouchPayDO;
	private OrderDO orderDO;

	private Map<Long, String[]> descaMap;
	private List<OrderItemDO> orderItemList;

	private String oid;
	// 订单关闭原因
	private String failReason;
	// 订单关闭类型
	private String closeType;
	// 订单优惠券
	private String couponInfo;
	// 不含运费的钱
	private String orderAountPriceNoCost;
	// 倒计时时间
	private Date delineDate = new Date();
	// 订单总价
	private String orderAllPrice;
	// 退款总金额
	private String refundAllPrice;
	// 卖家是否可修改订单价格
	private Integer postPay;
	private Long leftDaySec;
	private long isHaveTime; // 0:有，1：无
	private String errorMessage;

	/**
	 * 订单状态文本
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List createStateTxt(List list) {
		for (HashMap map : (List<HashMap>) list) {
			String btstr = "";
			// if(OrderDO.ORDER_STATE_1.equals(map.get("ORDER_STATE"))){ //未付款
			Integer flag = Integer.valueOf(map.get("ORDER_STATE").toString());
			if (flag.compareTo(OrderDO.ORDER_STATE_1) == 0) { // 未付款

				btstr = "未付款";
			}
			if (flag.compareTo(OrderDO.ORDER_STATE_2) == 0) { // 已付款
				btstr = "已付款";
			}
			if (flag.compareTo(OrderDO.ORDER_STATE_3) == 0) { // 已发贷
				btstr = "已发贷";
			}
			if (flag.compareTo(OrderDO.ORDER_STATE_5) == 0) { // 订单完成
				btstr = "订单完成";
			}
			map.put("STATE_TXT", btstr);
		}
		return list;
	}

	/**
	 * ִ 事件处理
	 */
	@SuppressWarnings("unchecked")
	public String executeBuyer() {
		try {
			if (StringUtil.isBlank(oid)) {
				errorMessage = "对不起，获取页面内容失败，请刷新重试！";
				return "error";
			}

			result = orderQueryAO.getOrderDOById(Long.valueOf(oid),
					this.getUserId(), "1");

			if (!result.isSuccess()) {
				errorMessage = (String) result.getModel("message");
				return "error";
			}

			orderDO = (OrderDO) result.getModel("orderDO");
			orderItemList = (List<OrderItemDO>) result
					.getModel("orderItemList");
			orderAountPriceNoCost = (String) result
					.getModel("orderAountPriceNoCost");
			delineDate = (Date) result.getModel("delineDate");
			orderLogisticsDO = (OrderLogisticsDO) result
					.getModel("orderLogisticsDO");
			logisticsCorpDO = (LogisticsCorpDO) result
					.getModel("logisticsCorpDO");
			memberInfo = (MemberInfoDO) result.getModel("memberInfo");
			vouchPayDO = (VouchPayDO) result.getModel("vouchPayDO");
			descaMap = (Map<Long, String[]>) result.getModel("dMap");
			orderAllPrice = (String) result.getModel("orderAllPrice");
			refundAllPrice = (String) result.getModel("refundAllPrice");
			couponInfo = (String) result.getModel("couponInfo");
			failReason = (String) result.getModel("failReason");
			closeType = (String) result.getModel("closeType");

			if (!DateUtil.isIndate(delineDate, 0))
				isHaveTime = 0;
			else
				isHaveTime = 1;

			if (memberInfo == null)
				memberInfo = new MemberInfoDO();
			if (orderLogisticsDO == null)
				orderLogisticsDO = new OrderLogisticsDO();
			if (orderDO == null)
				orderDO = new OrderDO();
			if (orderItemList == null)
				orderItemList = new ArrayList<OrderItemDO>();
			if (logisticsCorpDO == null)
				logisticsCorpDO = new LogisticsCorpDO();
			if (vouchPayDO == null)
				vouchPayDO = new VouchPayDO();

			return "success";

		} catch (Exception e) {
			log.error("OrderInfoAction#executeBuyer", e);
			errorMessage = "对不起，获取页面内容失败，请刷新重试！";
			return "error";
		}
	}

	@SuppressWarnings("unchecked")
	public String executeSeller() {
		try {
			if (StringUtil.isBlank(oid)) {
				errorMessage = "对不起，获取页面内容失败，请刷新重试！";
				return "error";
			}

			result = orderQueryAO.getOrderDOById(Long.valueOf(oid),
					this.getUserId(), "2");

			if (!result.isSuccess()) {
				errorMessage = (String) result.getModel("message");
				return "error";
			}

			orderDO = (OrderDO) result.getModel("orderDO");
			orderItemList = (List<OrderItemDO>) result
					.getModel("orderItemList");
			orderAountPriceNoCost = (String) result
					.getModel("orderAountPriceNoCost");
			delineDate = (Date) result.getModel("delineDate");
			orderLogisticsDO = (OrderLogisticsDO) result
					.getModel("orderLogisticsDO");
			logisticsCorpDO = (LogisticsCorpDO) result
					.getModel("logisticsCorpDO");
			memberInfo = (MemberInfoDO) result.getModel("memberInfo");
			vouchPayDO = (VouchPayDO) result.getModel("vouchPayDO");
			descaMap = (Map<Long, String[]>) result.getModel("dMap");
			orderAllPrice = (String) result.getModel("orderAllPrice");
			refundAllPrice = (String) result.getModel("refundAllPrice");
			postPay = (Integer) result.getModel("postPay");
			couponInfo = (String) result.getModel("couponInfo");
			failReason = (String) result.getModel("failReason");
			closeType = (String) result.getModel("closeType");

			if (!DateUtil.isIndate(delineDate, 0))
				isHaveTime = 0;
			else
				isHaveTime = 1;

			if (memberInfo == null)
				memberInfo = new MemberInfoDO();
			if (orderLogisticsDO == null)
				orderLogisticsDO = new OrderLogisticsDO();
			if (orderDO == null)
				orderDO = new OrderDO();
			if (orderItemList == null)
				orderItemList = new ArrayList<OrderItemDO>();
			if (logisticsCorpDO == null)
				logisticsCorpDO = new LogisticsCorpDO();
			if (vouchPayDO == null)
				vouchPayDO = new VouchPayDO();

			return "success-seller";

		} catch (Exception e) {
			log.error("OrderInfoAction#executeSeller", e);
			errorMessage = "对不起，获取页面内容失败，请刷新重试！";
			return "error";
		}
	}

	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		} else {

		}
		return userId;
	}

	public MemberInfoDO getMemberInfo() {
		return memberInfo;
	}

	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public List<OrderItemDO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getOrderAountPriceNoCost() {
		return orderAountPriceNoCost;
	}

	public void setOrderAountPriceNoCost(String orderAountPriceNoCost) {
		this.orderAountPriceNoCost = orderAountPriceNoCost;
	}

	public Date getDelineDate() {
		return delineDate;
	}

	public void setDelineDate(Date delineDate) {
		this.delineDate = delineDate;
	}

	public LogisticsCorpAO getLogisticsCorpAO() {
		return logisticsCorpAO;
	}

	public void setLogisticsCorpAO(LogisticsCorpAO logisticsCorpAO) {
		this.logisticsCorpAO = logisticsCorpAO;
	}

	public LogisticsCorpDO getLogisticsCorpDO() {
		return logisticsCorpDO;
	}

	public void setLogisticsCorpDO(LogisticsCorpDO logisticsCorpDO) {
		this.logisticsCorpDO = logisticsCorpDO;
	}

	public String getOid() {
		return oid;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRefundAllPrice() {
		return refundAllPrice;
	}

	public void setRefundAllPrice(String refundAllPrice) {
		this.refundAllPrice = refundAllPrice;
	}

	public Integer getPostPay() {
		return postPay;
	}

	public void setPostPay(Integer postPay) {
		this.postPay = postPay;
	}

	public String getCloseType() {
		return closeType;
	}

	public void setCloseType(String closeType) {
		this.closeType = closeType;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public String getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(String couponInfo) {
		this.couponInfo = couponInfo;
	}

	public Map<Long, String[]> getDescaMap() {
		return descaMap;
	}

	public void setDescaMap(Map<Long, String[]> descaMap) {
		this.descaMap = descaMap;
	}

	public VouchPayDO getVouchPayDO() {
		return vouchPayDO;
	}

	public void setVouchPayDO(VouchPayDO vouchPayDO) {
		this.vouchPayDO = vouchPayDO;
	}

	public long getIsHaveTime() {
		return isHaveTime;
	}

	public void setIsHaveTime(long isHaveTime) {
		this.isHaveTime = isHaveTime;
	}

	public String getOrderAllPrice() {
		return orderAllPrice;
	}

	public void setOrderAllPrice(String orderAllPrice) {
		this.orderAllPrice = orderAllPrice;
	}

	public Long getLeftDaySec() {
		Date now = new Date();
		leftDaySec = (delineDate.getTime() - now.getTime()) / 1000;
		return leftDaySec;
	}

	public void setLeftDaySec(Long leftDaySec) {
		this.leftDaySec = leftDaySec;
	}
}
