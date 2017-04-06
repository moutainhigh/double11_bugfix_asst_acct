package com.yuwang.pinju.web.module.order.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.coupon.ao.OrderCouponAO;
import com.yuwang.pinju.core.member.manager.asst.MemberAsstLog;
import com.yuwang.pinju.core.order.ao.OrderBusinessAO;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.core.order.ao.OrderUpAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.Paginator;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.OrderAllVO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.system.ServletUtil;

public class OrderManageSellAction {

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private OrderBusinessAO orderBusinessAO;
	private OrderCouponAO orderCouponAO;
	private OrderQueryAO orderQueryAO;
	private OrderUpAO orderUpAO;

	private MemberAsstLog memberAsstLog;
	private Result result;

	private OrderDO order;
	private Paginator paginator;
	private OrderLogisticsDO orderLogisticsDO;

	private List<Object> orderList;
	private List<OrderItemDO> orderItemList;
	// 主订单
	private List<OrderDO> orderDOList;
	private List<OrderAllVO> orderAllList;

	// 子订单
	private Map<Long, List<OrderItemDO>> map;

	private String message;
	private String orderState;
	private String jsstr;
	private String shid;
	private String shcm;
	private String tgState;
	private String orderId;
	private String seltState;
	private String seltAppraise;
	private Integer pageNum;
	private Integer pageSSS;
	private String refundState;
	// 买家昵称
	private String buyerNick;
	// 订单评价状态
	private String isBuyerRate;
	private String beginDate;
	private String endDate;
	private String itemTitle;
	private String tp;
	private String errorMessage;
	// 卖家取消订单原因
	private String failReason;

	// 卖家导出数据csv
	@SuppressWarnings({ "unchecked" })
	public String importCSV() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("application/csv;charset=GBK");
		response.setContentType("application/x-download");
		PrintWriter pw = null;
		// 获取数据源
		Map<String, Object> map = new HashMap<String, Object>();

		if (!EmptyUtil.isBlank(orderState)
				&& orderState.equals(OrderDO.ORDER_STATE_4 + "")) {
			map.put("refundState", new Integer[] {
					RefundDO.STATUS_WAIT_SELLER_AGREE,
					RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS,
					RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS,
					RefundDO.STATUS_SELLER_REFUSE_BUYER,
					RefundDO.STATUS_CS_PROCESS });
		}

		map.put("sellerId", this.getMasterUserId());
		if (StringUtil.isNotBlank(buyerNick))
			map.put("buyerNick", buyerNick.trim());
		if (!EmptyUtil.isBlank(orderState)
				&& !orderState.equals(OrderDO.ORDER_STATE_4 + "")) {
			map.put("orderState", orderState);
		}
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		map.put("isBuyerRate", isBuyerRate);
		if (StringUtil.isNotBlank(orderId))
			map.put("orderId", orderId.trim());
		if (StringUtil.isNotBlank(itemTitle))
			map.put("itemTitle", itemTitle.trim());

		result = orderQueryAO.queryOrderListNoPage(map);

		if (result.isSuccess()) {
			orderAllList = (List<OrderAllVO>) result.getModel("orderAllList");
		}

		// ====over

		StringBuffer bf = new StringBuffer();
		bf.append("订单编号").append(",").append("订单状态").append(",").append("商品编号")
				.append(",").append("商品标题").append(",").append("下单时商品价格（元）")
				.append(",").append("购买数量").append(",").append("实付金额（元）")
				.append(",").append("已退款金额（元）").append(",").append("商品sku描述")
				.append(",").append("运送方式").append(",").append("买家昵称")
				.append(",").append("省市区代码").append(",").append("省")
				.append(",").append("市").append(",").append("区").append(",")
				.append("收货地址").append(",").append("邮编").append(",")
				.append("收货人姓名").append(",").append("电话").append(",")
				.append("手机").append(",").append("备注").append("\n");

		if (orderAllList != null) {
			for (OrderAllVO orderAllVO : orderAllList) {
				OrderDO orderDO = orderAllVO.getOrderDO();
				List<OrderItemDO> orderItemDOs = orderAllVO.getOrderItemList();
				OrderLogisticsDO orderLogisticsDO = orderAllVO.getLogisticsDO();
				for (OrderItemDO report : orderItemDOs) {
					// 订单状态
					String stateString = "";
					// 运送方式
					String consignmentMode = "";

					// 已退款金额
					String refundprice = null;
					if (report.getRefundState().compareTo(
							RefundDO.STATUS_SUCCESS) == 0
							|| report.getRefundState().compareTo(
									RefundDO.STATUS_REFUND_PROTOCAL_AGREE) == 0) {
						refundprice = report.getRefundPriceByYuan();
					}

					if (orderDO.getOrderState()
							.compareTo(OrderDO.ORDER_STATE_1) == 0) {
						stateString = "等待买家付款";
					}
					if (orderDO.getOrderState()
							.compareTo(OrderDO.ORDER_STATE_2) == 0) {
						stateString = "等待卖家发货";
					}
					if (orderDO.getOrderState()
							.compareTo(OrderDO.ORDER_STATE_3) == 0) {
						stateString = "卖家已发货";
					}
					if (orderDO.getOrderState()
							.compareTo(OrderDO.ORDER_STATE_5) == 0) {
						stateString = "交易成功";
					}
					if (orderDO.getOrderState()
							.compareTo(OrderDO.ORDER_STATE_6) == 0) {
						stateString = "交易关闭";
					}

					if (orderLogisticsDO.getConsignmentMode().compareTo(
							OrderLogisticsDO.CONSIGNMENT_MODE_MAIL) == 0) {
						consignmentMode = "平邮";
					}
					if (orderLogisticsDO.getConsignmentMode().compareTo(
							OrderLogisticsDO.CONSIGNMENT_MODE_EXPRESS) == 0) {
						consignmentMode = "快递";
					}
					if (orderLogisticsDO.getConsignmentMode().compareTo(
							OrderLogisticsDO.CONSIGNMENT_MODE_EMS) == 0) {
						consignmentMode = "EMS";
					}
					if (orderLogisticsDO.getConsignmentMode().compareTo(
							OrderLogisticsDO.CONSIGNMENT_MODE_FREE) == 0) {
						consignmentMode = "卖家承担运费";
					}

					bf.append(report.getOrderId())
							.append(",")
							.append(stateString)
							.append(",")
							.append(report.getItemId())
							.append(",")
							.append(report.getItemTitle())
							.append(",")
							.append(report.getOriginalPriceByYuan())
							.append(",")
							.append(report.getBuyNum())

							.append(",")
							.append(StringUtil.defaultIfNull(report
									.getTotalAmountByYuan()))
							.append(",")
							.append(StringUtil.defaultIfNull(refundprice))

							.append(",")
							.append(StringUtil.defaultIfNull(report
									.getItemSkuAttributes()))
							.append(",")
							.append(consignmentMode)
							.append(",")
							.append(StringUtil.defaultIfNull(report
									.getBuyerNick()))
							.append(",")
							.append(StringUtil.defaultIfNull(orderLogisticsDO
									.getPcdCode()))
							.append(",")
							.append(StringUtil.defaultIfNull(orderLogisticsDO
									.getProv()))
							.append(",")
							.append(StringUtil.defaultIfNull(orderLogisticsDO
									.getCity()))
							.append(",")
							.append(orderLogisticsDO.getArea())
							.append(",")
							.append(StringUtil.defaultIfNull(orderLogisticsDO
									.getAddress()))
							.append(",")
							.append(StringUtil.defaultIfNull(orderLogisticsDO
									.getPost()))
							.append(",")
							.append(StringUtil.defaultIfNull(orderLogisticsDO
									.getFullName()))
							.append(",")
							.append(StringUtil.defaultIfNull(orderLogisticsDO
									.getPhone()))
							.append(",")
							.append(StringUtil.defaultIfNull(orderLogisticsDO
									.getMobilePhone()))
							.append(",")
							.append(StringUtil.defaultIfNull(orderDO
									.getBuyerMeMo())).append("\n");
				}

			}
		}
		try {
			String exportDate = "导出 " + beginDate + " 至 " + endDate + " 销售表";
			String beginString = this.getBeginDate().replace(":", "：")
					.replace(" ", "(");
			String endString = this.getEndDate().replace(":", "：")
					.replace(" ", "(").concat(")");
			if (!EmptyUtil.isBlank(beginString)) {
				beginString = beginString.concat(")");
			} else {
				exportDate = "导出截止至 " + endDate + " 销售表";
			}
			String filenamedisplay = "卖家商品销售记录" + beginString + "至" + endString
					+ ".csv";

			if (EmptyUtil.isBlank(this.getEndDate())) {
				filenamedisplay = "卖家商品销售记录"
						+ beginString
						+ "至"
						+ DateUtil
								.formatDate("yyyy-MM-dd HH:mm:ss", new Date())
								.replace(" ", "(").replace(":", "：")
								.concat(")") + ".csv";
				exportDate = "导出 "
						+ beginDate
						+ " 至 "
						+ DateUtil
								.formatDate("yyyy-MM-dd HH:mm:ss", new Date())
						+ " 销售表";
			}
			if (EmptyUtil.isBlank(this.getBeginDate())
					&& EmptyUtil.isBlank(this.getEndDate())) {
				filenamedisplay = "卖家商品销售记录（全部记录）.csv";
				exportDate = "导出全部销售表";
			}

			// 记录操作日志
			memberAsstLog.log("trade", "exportData", exportDate);

			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE")) {
				// filenamedisplay = URLEncoder.encode(filenamedisplay, "UTF8");
				filenamedisplay = new String(filenamedisplay.getBytes("gbk"),
						"ISO8859-1");
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {
				filenamedisplay = "=?UTF-8?B?"
						+ (new String(Base64.encodeBase64(filenamedisplay
								.getBytes("UTF-8")))) + "?=";
			}

			response.addHeader("Content-Disposition", "attachment;filename="
					+ filenamedisplay.trim());
			pw = response.getWriter();
			pw.write(bf.toString());

		} catch (UnsupportedEncodingException e) {
			log.error("OrderManageSellAction#importCSV: ", e);
		} catch (IOException e) {
			log.error("OrderManageSellAction#importCSV: ", e);
		} catch (Exception e) {
			log.error("OrderManageSellAction#importCSV: ", e);
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}
		return null;

	}

	/**
	 * ִ 事件处理
	 */
	@SuppressWarnings({ "unchecked" })
	public String execute() {
		try {
			if (shcm == null) {
				beginDate = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",
						DateUtils.addMonths(new java.util.Date(), -1));
				endDate = DateUtil.formatDate("yyyy-MM-dd HH:mm:ss",
						new java.util.Date());
			}

			if (paginator == null)
				paginator = new Paginator();

			Map<String, Object> map = new HashMap<String, Object>();

			if (!EmptyUtil.isBlank(orderState)
					&& orderState.equals(OrderDO.ORDER_STATE_4 + "")) {
				map.put("refundState", new Integer[] {
						RefundDO.STATUS_WAIT_SELLER_AGREE,
						RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS,
						RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS,
						RefundDO.STATUS_SELLER_REFUSE_BUYER,
						RefundDO.STATUS_CS_PROCESS });
			}

			map.put("sellerId", this.getMasterUserId());
			if (StringUtil.isNotBlank(buyerNick))
				map.put("buyerNick", buyerNick.trim());
			if (!EmptyUtil.isBlank(orderState)
					&& !orderState.equals(OrderDO.ORDER_STATE_4 + "")) {
				map.put("orderState", orderState);
			}
			map.put("beginDate", beginDate);
			map.put("endDate", endDate);
			map.put("isBuyerRate", isBuyerRate);
			if (!EmptyUtil.isBlank(orderId)) {
				map.put("orderId", Long.valueOf(orderId.trim()));
			}
			if (StringUtil.isNotBlank(itemTitle))
				map.put("itemTitle", itemTitle.trim());

			paginator.setItems(orderQueryAO.queryOrderListCount(map));
			if (orderQueryAO.queryOrderListCount(map) == 0)
				paginator.setItems(1);
			log.debug("\n\n\n\n\n" + paginator.getItemsPerPage());
			paginator.setItemsPerPage(20);
			map.put("startNum", paginator.getStartRow());
			map.put("endNum", paginator.getEndRow());

			result = orderQueryAO.queryOrderList(map);

			if (result.isSuccess()) {
				orderAllList = (List<OrderAllVO>) result
						.getModel("orderAllList");
			}

		} catch (Exception e) {
			// log.error("OrderManageSellAction#execute",e);
			paginator.setItems(1);
			orderAllList = new ArrayList<OrderAllVO>();
			return "success";
		}

		return "success";
	}

	@AssistantPermission(target = "trade", action = "close")
	public String closeOrderCheck() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			result = orderBusinessAO.sellerCloseOrderCheck(
					Long.valueOf(orderId), this.getUserId());
			String message = "";
			if (!result.isSuccess()) {
				if (result.getResultCode().compareTo(
						OrderConstant.PARAMETERRROR) == 0) {
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
			xml.append("<isSuccess>" + result.isSuccess() + "</isSuccess>");
			xml.append("<errorConstant>" + message + "</errorConstant>");
			xml.append("</row>");
			xml.append("</rows>");
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(xml.toString());
		} catch (IOException e) {
			log.error("cancelOrder id: " + orderId, e);
		} catch (Exception e) {
			log.error("cancelOrder id: " + orderId, e);
		}

		return null;
	}

	/**
	 * 获取关闭订单原因,订单关闭类型
	 */
	@SuppressWarnings("unused")
	private String closeReason(String closeCode) {
		String failReason = "";
		if (!StringUtil.isNumeric(closeCode)) {
			return failReason;
		}
		Integer code = Integer.valueOf(closeCode);
		switch (code) {
		case 2001:
			failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_NOBUY;
			break;
		case 2002:
			failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_ERRORINFO;
			break;
		case 2003:
			failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_NOGOODS;
			break;
		case 2004:
			failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_FACETOFACE;
			break;
		case 2005:
			failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_OTHER;
			break;
		case 2006:
			failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_PAY_BANK;
			break;
		case 2007:
			failReason = OrderConstant.ORDER_SELLER_CLOSE_REASON_PAY_INLINE;
			break;
		}

		return failReason;
	}

	/** 关闭订单 **/
	@AssistantPermission(target = "trade", action = "close")
	public String closeOrder() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", tgState);
			map.put("orderState", OrderItemDO.ORDER_ITEM_STATE_6);
			map.put("failReason", failReason);
			map.put("userid", this.getUserId());
			result = orderBusinessAO.sellCloseOrder(map);

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
			} else {
				// memberAsstLog.log("trade", "close",
				// "关闭订单："+tgState+"； 关闭原因："+this.closeReason(failReason));
				memberAsstLog.log("trade", "close", "关闭订单：" + tgState);
			}

			boolean bln = result.isSuccess();
			StringBuffer xml = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"GBK\"?><rows>");
			xml.append("<row id='x:pinju'>");
			xml.append("<orderId>" + tgState + "</orderId>");
			xml.append("<isSuccess>" + bln + "</isSuccess>");
			xml.append("<errorConstant>" + errorConstant + "</errorConstant>");
			xml.append("</row>");
			xml.append("</rows>");
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(xml.toString());
		} catch (IOException e) {
			log.error("cancelOrder id: " + tgState, e);
			return "error";
		} catch (Exception e) {
			log.error("cancelOrder id: " + tgState, e);
			return "error";
		}
		return null;
	}

	/** 延长买家收货时间 **/
	@AssistantPermission(target = "trade", action = "extend-ship")
	public String prolongOutOrder() {
		try {
			result = orderBusinessAO.updateRecevingDate(Long.valueOf(shid),
					Integer.parseInt(message), this.getUserId());
			String resultCode = "";
			if (!result.isSuccess()) {
				if (result.getResultCode().compareTo(
						OrderConstant.SELLERMEMBERERROR) == 0) {
					resultCode = "您无权限对该订单进行操作！";
				} else if (result.getResultCode().compareTo(
						OrderConstant.ORDERSTATEEERROR) == 0) {
					resultCode = "订单状态已更改，您不能再进行“延长收货时间”操作！";
				} else if (result.getResultCode().compareTo(
						OrderConstant.ORDERISNO) == 0) {
					resultCode = "您访问的订单不存在！";
				} else {
					resultCode = "订单状态已更改，您不能再进行“延长收货时间”操作！";
				}
			} else {
				memberAsstLog.log("trade", "extend-ship", "延长买家收货时间。订单号："
						+ shid + "；延长时限：" + message + " 天");
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			StringBuffer xml = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><rows>");
			xml.append("<row id='x:pinju'>");
			xml.append("<orderId>" + shid + "</orderId>");
			xml.append("<resultCode>" + resultCode + "</resultCode>");
			xml.append("<isSuccess>" + result.isSuccess() + "</isSuccess>");
			xml.append("</row>");
			xml.append("</rows>");
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(xml.toString());
		} catch (IOException e) {
			return null;
		}
		return null;
	}

	/** 延长买家收货时间判断 **/
	@AssistantPermission(target = "trade", action = "extend-ship")
	public String prolongOrderCheck() {
		try {
			result = orderBusinessAO.prolongOrderCheck(Long.valueOf(shid),
					this.getUserId());
			String resultCode = "";
			if (!result.isSuccess()) {
				if (result.getResultCode().compareTo(
						OrderConstant.SELLERMEMBERERROR) == 0) {
					resultCode = "您无权限对该订单进行操作！";
				} else if (result.getResultCode().compareTo(
						OrderConstant.ORDERSTATEEERROR) == 0) {
					resultCode = "订单状态已更改，您不能再进行“延长收货时间”操作！";
				} else if (result.getResultCode().compareTo(
						OrderConstant.PARAMETERRROR) == 0) {
					resultCode = "您访问的订单不存在！";
				} else {
					resultCode = "订单状态已更改，您不能再进行“延长收货时间”操作！";
				}
			}

			HttpServletResponse response = ServletActionContext.getResponse();
			StringBuffer xml = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"UTF-8\"?><rows>");
			xml.append("<row id='x:pinju'>");
			xml.append("<resultCode>" + resultCode + "</resultCode>");
			xml.append("<isSuccess>" + result.isSuccess() + "</isSuccess>");
			xml.append("</row>");
			xml.append("</rows>");
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(xml.toString());
		} catch (Exception e) {
			return null;
		}

		return null;
	}

	/** 翻页 **/
	public String pageOrder() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", tgState);
		map.put("orderState", "3");
		// orderBusinessAO.upOrder(map);
		return "success";
	}

	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMemberId();
		}
		return userId;
	}
	
	private long getMasterUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMasterMemberId();
		}
		return userId;
	}

	// 商品原价（订单），不含运费
	private long originalPrice;
	// 原来总价(不含运费)
	private String orderOriginalPrice;
	// (含运费)调整后的总价
	private String orderTotalPrice;

	/**
	 * 买家实付： 0.05+0.05 + 0.01 + 0.03 = 0.14元 买家实付 = 原价 + 运费 + 涨价或减价
	 * **/
	// 涨价或减价(显示用)
	private String sellerAllUpdatePrice;
	// 原价(显示用)
	private String originalAllPrice;
	private long sellerUpdatePrice;

	private String couponPriceByYuan;

	@SuppressWarnings("unchecked")
	@AssistantPermission(target = "trade", action = "price")
	public String toModifyPrice() {
		tp = "2";
		try {
			result = orderQueryAO.checkModifyPrice(this.getUserId(),
					Long.valueOf(orderId));
			if (!result.isSuccess()) {
				if (result.getResultCode().compareTo(
						OrderConstant.SELLERMEMBERERROR) == 0) {
					errorMessage = "您无权限修改该订单的价格！";
					return "error";
				}
				if (result.getResultCode().compareTo(
						OrderConstant.ORDERSTATEEERROR) == 0) {
					errorMessage = "订单状态已更改，您不能修改价格！";
					return "error";
				}
				if (result.getResultCode().compareTo(
						OrderConstant.ORDERMODIFYPRICE) == 0) {
					errorMessage = "买家已进入网银流程，您不能修改价格！";
					return "error";
				}
				if (result.getResultCode().compareTo(OrderConstant.ORDERISNO) == 0) {
					errorMessage = "抱歉，未找到您要的订单！";
					return "error";
				} else {
					errorMessage = "订单状态已更改，您不能修改价格！";
					return "error";
				}
			}

			order = (OrderDO) result.getModel("orderDO");

			result = orderQueryAO.queryOrderItemList(Long.valueOf(orderId));
			if (!result.isSuccess()) {
				errorMessage = "错误的操作！";
				return "error";
			}
			orderItemList = (List<OrderItemDO>) result.getModel("list");

			// 判断订单是否有使用优惠券
			TradeCouponDO couponDO = orderCouponAO.getCouponDOByOrderId(Long
					.valueOf(orderId));
			Long couponPrice = 0L;
			if (couponDO != null) {
				couponPrice = couponDO.getCouponMoney();
			}

			int index = 0;
			for (OrderItemDO orderItemDO : orderItemList) {
				index++;
				if (orderItemDO.getSellerMarginPrice() != null)
					sellerUpdatePrice = sellerUpdatePrice
							+ orderItemDO.getSellerMarginPrice();

				originalPrice = originalPrice + orderItemDO.getOrderItemPrice()
						* orderItemDO.getBuyNum();

				if (!EmptyUtil.isBlank(originalAllPrice)) {
					originalAllPrice = originalAllPrice
							+ " + "
							+ MoneyUtil.getCentToDollar(orderItemDO
									.getOrderItemPrice()
									* orderItemDO.getBuyNum());
				} else {
					originalAllPrice = "("
							+ MoneyUtil.getCentToDollar(orderItemDO
									.getOrderItemPrice()
									* orderItemDO.getBuyNum());
				}

				if (index == orderItemList.size()) {
					originalAllPrice = originalAllPrice + ")";
				}

			}

			couponPriceByYuan = MoneyUtil.getCentToDollar(couponPrice);
			orderOriginalPrice = MoneyUtil.getCentToDollar(originalPrice
					- couponPrice);
			orderLogisticsDO = (OrderLogisticsDO) orderBusinessAO
					.queryOrderLogisticsByOrderId(Long.valueOf(orderId))
					.getModel("obj");
			orderTotalPrice = MoneyUtil.getCentToDollar(order.getPriceAmount());

			sellerAllUpdatePrice = MoneyUtil.getCentToDollar(sellerUpdatePrice);
			if (sellerUpdatePrice >= 0) {
				sellerAllUpdatePrice = " + "
						+ MoneyUtil.getCentToDollar(sellerUpdatePrice);
			}

			return "success";
		} catch (NumberFormatException e) {
			errorMessage = "输入非法数据！";
			return "error";
		} catch (Exception e) {
			errorMessage = "订单状态已更改，您不能修改价格！";
			return "error";
		}

	}

	// 运费修改值
	private String modifyLogistics;
	// 修改的商品单价
	private String[] modifyPrice;
	// 子订单ID
	private long[] orderItemId;
	// 修改后的总价
	private String modifyTotalPrice;
	// 记录商品总额原修改记录
	private Long[] modifyBefore;

	@AssistantPermission(target = "trade", action = "price")
	public String modifyPrice() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			result = orderQueryAO.checkModifyPrice(this.getUserId(),
					Long.valueOf(orderId));
			if (!result.isSuccess()) {
				StringBuffer xml = new StringBuffer(
						"<?xml version=\"1.0\" encoding=\"GBK\"?><rows>");
				xml.append("<row id='x:pinju'>");
				xml.append("<orderId>" + orderId + "</orderId>");
				xml.append("<isSuccess>" + false + "</isSuccess>");
				xml.append("<resultCode>" + result.getResultCode()
						+ "</resultCode>");
				xml.append("</row>");
				xml.append("</rows>");
				response.setContentType("text/xml;charset=UTF-8");
				response.getOutputStream().print(xml.toString());
				return null;
			}

			long[] _modifyPrice = null;
			if (modifyPrice != null && modifyPrice.length > 0) {
				_modifyPrice = new long[modifyPrice.length];
				for (int i = 0; i < modifyPrice.length; i++) {
					if (!EmptyUtil.isBlank(modifyPrice[i])) {
						_modifyPrice[i] = MoneyUtil
								.getDollarToCent(modifyPrice[i]);
					} else {
						_modifyPrice[i] = MoneyUtil.getDollarToCent("0");
					}
				}
			}
			long _modifyLogistics = 0;
			if (!EmptyUtil.isBlank(modifyLogistics)) {
				_modifyLogistics = MoneyUtil.getDollarToCent(modifyLogistics);
			}
			result = orderUpAO.sellerUpOrderPrice(orderItemId, _modifyPrice,
					_modifyLogistics, this.getUserId(),
					String.valueOf(ServletUtil.getRemoteNumberIp()),
					modifyBefore);

			StringBuffer xml = new StringBuffer(
					"<?xml version=\"1.0\" encoding=\"GBK\"?><rows>");
			xml.append("<row id='x:pinju'>");
			xml.append("<orderId>" + orderId + "</orderId>");
			xml.append("<isSuccess>" + result.isSuccess() + "</isSuccess>");
			xml.append("</row>");
			xml.append("</rows>");
			response.setContentType("text/xml;charset=UTF-8");
			response.getOutputStream().print(xml.toString());
		} catch (IOException e) {
			log.error("cancelOrder id: " + tgState, e);
		} catch (Exception e) {
			log.error("cancelOrder id: " + tgState, e);
		}
		return null;
	}

	public String getModifyTotalPrice() {
		return modifyTotalPrice;
	}

	public void setModifyTotalPrice(String modifyTotalPrice) {
		this.modifyTotalPrice = modifyTotalPrice;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}

	public Integer getPageSSS() {
		return pageSSS;
	}

	public void setPageSSS(Integer pageSSS) {
		this.pageSSS = pageSSS;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public String getSeltAppraise() {
		return seltAppraise;
	}

	public void setSeltAppraise(String seltAppraise) {
		this.seltAppraise = seltAppraise;
	}

	public void setSeltState(String seltState) {
		this.seltState = seltState;
	}

	public String getSeltState() {
		return seltState;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getShcm() {
		return shcm;
	}

	public void setShcm(String shcm) {
		this.shcm = shcm;
	}

	public String getTgState() {
		return tgState;
	}

	public void setTgState(String tgState) {
		this.tgState = tgState;
	}

	public String getShid() {
		return shid;
	}

	public void setShid(String shid) {
		this.shid = shid;
	}

	public String getJsstr() {
		return jsstr;
	}

	public String getOrderState() {
		return orderState;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setOrderBusinessAO(OrderBusinessAO orderBusinessAO) {
		this.orderBusinessAO = orderBusinessAO;
	}

	public OrderDO getOrder() {
		return order;
	}

	public void setOrder(OrderDO order) {
		this.order = order;
	}

	public List<Object> getOrderList() {
		return orderList;
	}

	public List<OrderAllVO> getOrderAllList() {
		return orderAllList;
	}

	public void setOrderAllList(List<OrderAllVO> orderAllList) {
		this.orderAllList = orderAllList;
	}

	public String getItemTitle() {
		return itemTitle;
	}

	public void setItemTitle(String itemTitle) {
		this.itemTitle = itemTitle;
	}

	public String[] getModifyPrice() {
		return modifyPrice;
	}

	public void setModifyPrice(String[] modifyPrice) {
		this.modifyPrice = modifyPrice;
	}

	public long[] getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(long[] orderItemId) {
		this.orderItemId = orderItemId;
	}

	public String getOrderOriginalPrice() {
		return orderOriginalPrice;
	}

	public void setOrderOriginalPrice(String orderOriginalPrice) {
		this.orderOriginalPrice = orderOriginalPrice;
	}

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getRefundState() {
		return refundState;
	}

	public void setRefundState(String refundState) {
		this.refundState = refundState;
	}

	public void setOrderUpAO(OrderUpAO orderUpAO) {
		this.orderUpAO = orderUpAO;
	}

	public List<OrderItemDO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
	}

	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}

	public void setOrderLogisticsDO(OrderLogisticsDO orderLogisticsDO) {
		this.orderLogisticsDO = orderLogisticsDO;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<OrderDO> getOrderDOList() {
		return orderDOList;
	}

	public void setOrderDOList(List<OrderDO> orderDOList) {
		this.orderDOList = orderDOList;
	}

	public Map<Long, List<OrderItemDO>> getMap() {
		return map;
	}

	public void setMap(Map<Long, List<OrderItemDO>> map) {
		this.map = map;
	}

	public String getIsBuyerRate() {
		return isBuyerRate;
	}

	public void setIsBuyerRate(String isBuyerRate) {
		this.isBuyerRate = isBuyerRate;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getTp() {
		return tp;
	}

	public void setTp(String tp) {
		this.tp = "2";
	}

	public String getModifyLogistics() {
		return modifyLogistics;
	}

	public void setModifyLogistics(String modifyLogistics) {
		this.modifyLogistics = modifyLogistics;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSellerAllUpdatePrice() {
		return sellerAllUpdatePrice;
	}

	public void setSellerAllUpdatePrice(String sellerAllUpdatePrice) {
		this.sellerAllUpdatePrice = sellerAllUpdatePrice;
	}

	public String getOriginalAllPrice() {
		return originalAllPrice;
	}

	public void setOriginalAllPrice(String originalAllPrice) {
		this.originalAllPrice = originalAllPrice;
	}

	public void setOrderCouponAO(OrderCouponAO orderCouponAO) {
		this.orderCouponAO = orderCouponAO;
	}

	public String getCouponPriceByYuan() {
		return couponPriceByYuan;
	}

	public void setCouponPriceByYuan(String couponPriceByYuan) {
		this.couponPriceByYuan = couponPriceByYuan;
	}

	public Long[] getModifyBefore() {
		return modifyBefore;
	}

	public void setModifyBefore(Long[] modifyBefore) {
		this.modifyBefore = modifyBefore;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	public void setMemberAsstLog(MemberAsstLog memberAsstLog) {
		this.memberAsstLog = memberAsstLog;
	}
}
