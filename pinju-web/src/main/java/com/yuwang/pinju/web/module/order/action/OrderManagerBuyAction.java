package com.yuwang.pinju.web.module.order.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.core.order.ao.OrderUpAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.OrderAllVO;
import com.yuwang.pinju.domain.order.query.QueryVO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.OrderMessageName;
import com.yuwang.pinju.web.module.TokenAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * 买家订单管理
 * 
 * @author 杜成
 * @date 2011-6-10
 * @version 1.0
 */
public class OrderManagerBuyAction extends TokenAction implements
		OrderMessageName {

	private static final long serialVersionUID = -3762503763019533125L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private OrderQueryAO orderQueryAO;
	private OrderUpAO orderUpAO;

	private QueryVO query;

	private Map<Long, List<OrderLogisticsDO>> logisticsMap;
	private List<OrderAllVO> orderAllList;
	private Map<Long, List<OrderItemDO>> orderMap;
	private List<OrderDO> orderList;

	private String orderItemState;
	private String errorMessage;
	private String refundState;
	private String startDate;
	private String endDate;
	private String searchTy;
	private String page;
	private String currentPage;

	// 要关闭的订单号
	private String cancelOrderId;
	// 关闭理由
	private String failReason;
	// 评价状态
	private String isBuyerRate;
	// 商品名称
	private String itemTitle;
	// 总记录数
	private Long num;
	// 交易状态
	private Integer[] orderState;
	// 卖家昵称
	private String sellerNick;

	/**
	 * 用户名 @Add By HeYong 2011-10-13
	 */
	private String userName;

	/**
	 * 用户ID @Add By HeYong 2011-10-13
	 */
	private Long userMemberId;

	/**
	 * 存放不同状态订单数量的数量 @Add By HeYong 2011-10-13
	 */
	private int[] items = new int[5];

	/**
	 * 会员 version值 @Add By HeYong 2011-12-05
	 */
	private Long version;

	/**
	 * 取消订单
	 * 
	 * @return ajax 异步处理
	 */
	public String cancelOrder() {

		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			Result result = orderUpAO.cancelOrder(Long.valueOf(cancelOrderId),
					this.getUserId(), failReason);
			String errorConstant = "";
			if (!result.isSuccess()) {
				if (result.getResultCode().compareTo(
						OrderConstant.ORDERSTATEEERROR) == 0) {
					errorConstant = "订单状态已更改，您不能再“取消”该订单！";
				} else if (result.getResultCode().compareTo(
						OrderConstant.PARAMETERRROR) == 0) {
					errorConstant = "您无权限对该订单进行操作！";
				} else {
					errorConstant = "订单状态已更改，您不能再“取消”该订单！";
				}
			}
			StringBuffer xml = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"GBK\"?><rows>");
			xml.append("<row id='x:pinju'>");
			xml.append("<orderId>" + cancelOrderId + "</orderId>");
			xml.append("<isSuccess>" + result.isSuccess() + "</isSuccess>");
			xml.append("<errorConstant>" + errorConstant + "</errorConstant>");
			xml.append("</row>");
			xml.append("</rows>");
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(xml.toString());
		} catch (IOException e) {
			log.error("cancelOrder id: " + cancelOrderId, e);
			return ERROR;
		} catch (Exception e) {
			log.error("cancelOrder id: " + cancelOrderId, e);
			return ERROR;
		}

		return null;
	}

	// 取消订单异步验证
	public String orderCancelCheck() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			Result result = orderUpAO.cancelOrderCheck(
					Long.valueOf(cancelOrderId), this.getUserId());
			String message = "";
			if (!result.isSuccess()) {
				if (result.getResultCode().compareTo(OrderConstant.CHECKERROR) == 0) {
					message = "您无权限对该订单进行操作！";
				} else if (result.getResultCode().compareTo(
						OrderConstant.ORDERSTATEEERROR) == 0) {
					message = "订单状态已更改，您不能再“取消”该订单！";
				} else {
					message = "订单状态已更改，您不能再“取消”该订单！";
				}
			}

			StringBuffer xml = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"GBK\"?><rows>");
			xml.append("<row id='x:pinju'>");
			xml.append("<orderId>" + cancelOrderId + "</orderId>");
			xml.append("<isSuccess>" + result.isSuccess() + "</isSuccess>");
			xml.append("<errorConstant>" + message + "</errorConstant>");
			xml.append("</row>");
			xml.append("</rows>");
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(xml.toString());
		} catch (IOException e) {
			log.error("cancelOrder id: " + cancelOrderId, e);
		} catch (Exception e) {
			log.error("cancelOrder id: " + cancelOrderId, e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public String get() {
		try {
			if (EmptyUtil.isBlank(searchTy) || !"1".equals(searchTy)) {
				endDate = DateUtil
						.formatDate("yyyy-MM-dd HH:mm:ss", new Date());
				startDate = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",
						DateUtils.addMonths(new Date(), -3));
			}

			Integer[] refundStates = null;

			if (!EmptyUtil.isBlank(orderItemState)
					&& orderItemState.equals(OrderDO.ORDER_STATE_4 + "")) {
				refundStates = new Integer[] {
						RefundDO.STATUS_WAIT_SELLER_AGREE,
						RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS,
						RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS,
						RefundDO.STATUS_SELLER_REFUSE_BUYER,
						RefundDO.STATUS_CS_PROCESS, RefundDO.STATUS_REFUND_FAIL };
			}

			if (query == null) {
				query = new QueryVO();
			}
			if (!EmptyUtil.isBlank(cancelOrderId))
				query.setOrderId(Long.valueOf(cancelOrderId.trim()));
			if (StringUtil.isNotBlank(itemTitle))
				query.setItemTitle(itemTitle.trim());
			if (StringUtil.isNotBlank(sellerNick))
				query.setSellerNick(sellerNick.trim());
			query.setStartDate(startDate);
			query.setEndDate(endDate);
			query.setBuyerId(this.getUserId());
			if (!EmptyUtil.isBlank(orderItemState)
					&& !orderItemState.equals(OrderDO.ORDER_STATE_4 + ""))
				query.setOrderState(Long.valueOf(orderItemState));
			if (!EmptyUtil.isBlank(isBuyerRate))
				query.setIsRate(Long.valueOf(isBuyerRate));
			query.setRefundState(refundStates);
			Long count = orderQueryAO.queryOrderNumByBuyer(query);
			Integer counts = 0;
			if (count == 0)
				counts = 1;
			else {
				counts = Integer.valueOf(count + "");
			}
			query.setItems(counts);
			query.setItemsPerPage(10);
			query.setAction("/orderBuyer/orderManager.htm?currentPage=");
			Result result = orderQueryAO.queryOrderByBuyer(query);
			if (result.isSuccess())
				orderAllList = (List<OrderAllVO>) result
						.getModel("orderAllList");

			return SUCCESS;
		} catch (Exception e) {
			// log.error("OrderManagerBuyAction#get",e);
			query.setItems(1);
			orderAllList = new ArrayList<OrderAllVO>();
			return SUCCESS;
		}

	}

	/**
	 * @Description: 买家首页信息
	 * @author [贺泳]
	 * @date 2011-10-13 下午04:45:17
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String myBuyer() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			if (!loginInfo.isLogin()) {
				ServletUtil.loginCurrentResultUrl();
				return LOGIN;
			}
			// 取得会员名称
			this.userName = loginInfo.getNickname();
			this.version = loginInfo.getInfoVersion();
			Result result = null;

			// 取得当前用户名编号
			Long memberId = getUserId();

			// 获取某状态下订单数量(待付款,状态为 1 )
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("buyerId", memberId);
			map.put("orderState", 1);
			result = orderQueryAO.selectOrderListCount(map);
			if (!result.isSuccess()) {
				items[0] = 0;
			} else {
				items[0] = (Integer) result.getModel("count");
			}

			// 待确认收货(状态为 3 )
			map.clear();
			map.put("buyerId", memberId);
			map.put("orderState", 3);
			result = orderQueryAO.selectOrderListCount(map);
			if (!result.isSuccess()) {
				items[1] = 0;
			} else {
				items[1] = (Integer) result.getModel("count");
			}

			// 等待评价(状态为 5, 评价状态为 0 )
			map.clear();
			map.put("buyerId", memberId);
			map.put("isBuyerRate", 0);
			map.put("orderState", 5);
			result = orderQueryAO.selectOrderListCount(map);
			if (!result.isSuccess()) {
				items[2] = 0;
			} else {
				items[2] = (Integer) result.getModel("count");
			}

			// 退款
			map.clear();
			map.put("buyerId", memberId);
			List<Integer> refundState = new ArrayList<Integer>();
			refundState.add(RefundDO.STATUS_WAIT_SELLER_AGREE);
			refundState.add(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS);
			refundState.add(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS);
			refundState.add(RefundDO.STATUS_SELLER_REFUSE_BUYER);
			refundState.add(RefundDO.STATUS_REFUND_PROTOCAL_AGREE);
			refundState.add(RefundDO.STATUS_CS_PROCESS);
			map.put("refundState", refundState);
			result = orderQueryAO.selectOrderListCount(map);
			if (!result.isSuccess()) {
				items[3] = 0;
			} else {
				items[3] = (Integer) result.getModel("count");
			}

			// 维权
			result = orderQueryAO.getRightsManagerNum(memberId);
			if (!result.isSuccess()) {
				items[4] = 0;
			} else {
				items[4] = (Integer) result.getModel("count");
			}

			this.userMemberId = memberId;
			this.page = "1";
			// 订单状态(未付款 或 卖家已发货)
			int[] orderState = new int[] { OrderDO.ORDER_STATE_1,
					OrderDO.ORDER_STATE_2, OrderDO.ORDER_STATE_3 };
			result = orderQueryAO.queryOrderListByState(memberId,
					Integer.parseInt(page), orderState);
			if (result.isSuccess()) {
				orderAllList = (List<OrderAllVO>) result
						.getModel("orderAllList");
			} else {
				// 取不到查询结果不影响我是买家首页正常显示
				orderAllList = new ArrayList<OrderAllVO>();
			}
		} catch (Exception e) {
			log.error("Event=[OrderManagerBuyAction#myBuyer] 查询不同状态订单的异常:", e);
			// 出异常不影响我是买家首页正常显示
			items[1] = 0;
			items[2] = 0;
			items[3] = 0;
			items[4] = 0;
			orderAllList = new ArrayList<OrderAllVO>();
			return SUCCESS;
		}
		return SUCCESS;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getCancelOrderId() {
		return cancelOrderId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getFailReason() {
		return failReason;
	}

	public String getIsBuyerRate() {
		if (isBuyerRate == null || isBuyerRate.trim().equals(""))
			return null;
		return isBuyerRate;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public Long getNum() {
		return num;
	}

	public String getOrderItemState() {
		return orderItemState;
	}

	public List<OrderDO> getOrderList() {
		return orderList;
	}

	public Map<Long, List<OrderItemDO>> getOrderMap() {
		return orderMap;
	}

	public Integer[] getOrderState() {
		return orderState;
	}

	public String getSellerNick() {
		if (sellerNick == null || sellerNick.trim().equals(""))
			return null;
		return sellerNick;
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

	public void setCancelOrderId(String cancelOrderId) {
		this.cancelOrderId = cancelOrderId;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public void setIsBuyerRate(String isBuyerRate) {
		this.isBuyerRate = isBuyerRate;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public void setOrderUpAO(OrderUpAO orderUpAO) {
		this.orderUpAO = orderUpAO;
	}

	public void setOrderItemState(String orderItemState) {
		this.orderItemState = orderItemState;
	}

	public void setOrderList(List<OrderDO> orderList) {
		this.orderList = orderList;
	}

	public void setOrderMap(Map<Long, List<OrderItemDO>> orderMap) {
		this.orderMap = orderMap;
	}

	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
	}

	public void setOrderState(Integer[] orderState) {
		this.orderState = orderState;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public List<OrderAllVO> getOrderAllList() {
		return orderAllList;
	}

	public void setOrderAllList(List<OrderAllVO> orderAllList) {
		this.orderAllList = orderAllList;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public Map<Long, List<OrderLogisticsDO>> getLogisticsMap() {
		return logisticsMap;
	}

	public void setLogisticsMap(Map<Long, List<OrderLogisticsDO>> logisticsMap) {
		this.logisticsMap = logisticsMap;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int[] getItems() {
		return items;
	}

	public void setItems(int[] items) {
		this.items = items;
	}

	public Long getUserMemberId() {
		return userMemberId;
	}

	public void setUserMemberId(Long userMemberId) {
		this.userMemberId = userMemberId;
	}

	public QueryVO getQuery() {
		return query;
	}

	public String getSearchTy() {
		return searchTy;
	}

	public void setSearchTy(String searchTy) {
		this.searchTy = searchTy;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public void setQuery(QueryVO query) {
		this.query = query;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
