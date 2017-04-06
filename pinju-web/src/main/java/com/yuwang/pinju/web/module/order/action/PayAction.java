package com.yuwang.pinju.web.module.order.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.resultcode.TenPayResultCode;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.core.order.ao.pay.PayAO;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.order.manager.check.OrderStateManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.trade.TenSubAccountDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.module.TokenAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * 处理去继续支付页面，确认收货发起分账操作
 * @author 杜成
 * @date 2011-6-10
 * @version 1.0
 */
public class PayAction extends TokenAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -761899472269965642L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private OrderQueryAO orderQueryAO;
	private OrderCheckManager orderCheckManager;
	private OrderStateManager orderStateManager;
	
	/**
	 * 错误信息
	 */
	private String errorMessage;


	//订单优惠信息
	private String couponInfo; 

	/**
	 * 支付的订单编号
	 */
	private Long[] orderId;

	/**
	 * 判断提交类型 1为拼装提交
	 */
	private String type = "0";

	/**
	 * 客户支付
	 */
	private PayAO payAO;

	/**
	 * 支付总额
	 */
	private String priceAmount;

	/**
	 * 订单AO
	 */
	private OrderDO orderDO;

	/**
	 * 确认收货输入的密码 @Add By HeYong 2011-10-09
	 */
	private String passWord;
	
	private String tid;

	private Map<Long, String[]> descaMap;
	
	/**
	 * 原来总价(不含运费)
	 */
	private String orderOriginalPrice;
	
	/**
	 * 订单实际总价（退款金额，运费，优惠）
	 */
	private String orderTotalPrice;
	
	/**
	 *  订单退款总价
	 */
	private String refundTotalPrice;

	/**
	 * 支付记录DO
	 */
	private VouchPayDO vouchPayDO;
	
	/**
	 * 子订单List
	 */
	private List<OrderItemDO> orderItemList;
	
	/**
	 * 物流DO
	 */
	private OrderLogisticsDO orderLogisticsDO;

	/**
	 * 发起支付，去支付页面(担保交易)
	 * @return
	 */
	public String pay() {
		try {
			// 1.判断订单是否属于买家  2.判断订单状态
			for (long orderid : orderId) {
				if (!orderCheckManager.isBuyerOrder(orderid, this.getUserId())) {
					errorMessage = "您无权限结算该笔交易！";
					return ERROR;
				}
				if (!orderStateManager.checkOrderState(this.getUserId(),orderid, OrderDO.ORDER_STATE_6)) {
					errorMessage = "订单状态已更改，您不能对该笔交易进行 “付款” 操作！";
					return ERROR;
				}
			}
			String buyerIp = String.valueOf(ServletUtil.getRemoteIp());
			Result result  = payAO.getPayParams(orderId, buyerIp);
			if(!result.isSuccess()){
				errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode());
				return ERROR;
			}
			// 传参给支付ACTION
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("payList", result.getModel("tenpaySinglepayParamList"));
			ActionContext.getContext().setParameters(paramMap);
		}
		catch (Exception e) {
			log.error("Event=[PayAction#pay] 担保支付参数封装错误", e);
			errorMessage = ResultCodeMsg.getResultMessage(TradeResultCode.PAY_CHECK_ORDER_PARMS_FAIL);
			return ERROR;
		}
		return "vouchsuccess";
	}
	
	/**
	 * 跳转到确认收货页面
	 */
	@SuppressWarnings("unchecked")
	public String receiveItem() {
		try {
			// 根据订单ID获得订单相关信息(一个订单确认收货)
			for (Long orderid : orderId) {
				// 判断订单是否属于买家
				if (!orderCheckManager.isBuyerOrder(orderid, this.getUserId())) {
					errorMessage = "您无权限对该订单进行确认收货操作！";
					return ERROR;
				}

				// 订单状态判断
				if (!orderStateManager.checkOrderState(this.getUserId(),orderid, OrderDO.ORDER_STATE_5)) {
					errorMessage = "订单状态已更改，您不能对该笔交易进行 “确认收货” 操作！";
					return ERROR;
				}

				// 订单信息
				Result orderDoResult = orderQueryAO.getOrderDOById(orderid,this.getUserId(), "1");
				if (!orderDoResult.isSuccess()) {
					errorMessage = "订单状态已更改，您不能对该笔交易进行 “确认收货” 操作！";
					return ERROR;
				}

				orderDO = (OrderDO) orderDoResult.getModel("orderDO");
				if (orderDO.getIsRefund().compareTo(OrderConstant.IS_REFUND_YES) == 0) {
					errorMessage = "您有商品正在退款，请完成退款或关闭退款后再确认收货操作！";
					return ERROR;
				}

				orderItemList      = (List<OrderItemDO>) orderDoResult.getModel("orderItemList");
				descaMap           = (Map<Long, String[]>) orderDoResult.getModel("dMap");
				orderLogisticsDO   = (OrderLogisticsDO) orderDoResult.getModel("orderLogisticsDO");
				orderOriginalPrice = (String) orderDoResult.getModel("orderAountPriceNoCost");
				refundTotalPrice   = (String) orderDoResult.getModel("refundAllPrice");
				orderTotalPrice    = (String) orderDoResult.getModel("orderAllPrice");
				vouchPayDO         = (VouchPayDO) orderDoResult.getModel("vouchPayDO");
				couponInfo         = (String) orderDoResult.getModel("couponInfo");
			}

		} catch (Exception e) {
			log.debug("PayAction.receiveItem" + e);
			errorMessage = "订单状态已更改，您不能对该笔交易进行 “确认收货” 操作！";
			return ERROR;
		}
		
		ServletUtil.forbidBrowserCache();
		
		return SUCCESS;
	}

	/**
	 * 确认收货操作，并且发起分账
	 */
	@SuppressWarnings("unchecked")
	public String affirmPay() {
		try {
			// 判断是否登录，和确认收货密码不为空
			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			if (!login.isLogin() || !StringUtil.isNotBlank(passWord)) {
				errorMessage = ResultCodeMsg.getResultMessage(TenPayResultCode.MEMBER_NOTLOGIN);
				return ERROR;
			}
			
			// 判断当前会员是否有操作该笔交易
			if (!orderCheckManager.isBuyerOrder(orderId[0], this.getUserId())) {
				errorMessage = "您无权限结算该笔交易！";
				return ERROR;
			}
			
			// 订单状态判断
			if (!orderStateManager.checkOrderState(this.getUserId(),orderId[0], OrderDO.ORDER_STATE_5)) {
				errorMessage = "订单状态已更改，您不能对该笔交易进行 “确认收货” 操作！";
				return ERROR;
			}

			// 校验用户输入在确认收货时，判断密码是否正确
			Result result = payAO.checkPassWord(login.getNickname(), passWord, tid);
			if (!result.isSuccess()) {
				errorMessage = ResultCodeMsg.getResultMessage(result.getResultCode());
				return ERROR;
			}

			result = payAO.affirmPay(orderId, OrderDO.ORDER_STATE_5,this.getUserId());
			if (!result.isSuccess()) {
				return ERROR;
			}
			
			List<TenSubAccountDO> list = (List<TenSubAccountDO>) result.getModel("tenSubAccountList");
			if (list == null || list.isEmpty()) {
				errorMessage = "分账数据错误";
				return ERROR;
			}
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("tenSubAccountList", list);
			ActionContext.getContext().setParameters(paramMap);
		} catch (Exception e) {
			errorMessage = "发起确认收货操作数据错误，请重新操作！";
			log.debug("Event=[PayAction#affirmPay]" + e);
			return ERROR;
		}
		return SUCCESS;
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

	/**
	 * @Description: ajax异步校验确认收货密码是否输入正确
	 * @author [贺泳]
	 * @date 2011-10-09 上午09:33:20
	 * @version 1.0
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String ajaxCheckPassWord() {
		JSONObject jsonObject = new JSONObject();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
			if (!login.isLogin()) {
				jsonObject.put(ERROR, ResultCodeMsg.getResultMessage(TenPayResultCode.MEMBER_NOTLOGIN));
				return null;
			}
			
			Result result = payAO.checkPassWord(login.getNickname(), passWord, tid);
			if (!result.isSuccess()) {
				jsonObject.put(ERROR, ResultCodeMsg.getResultMessage(result.getResultCode(), new String()));
				return null;
			}
			jsonObject.put(SUCCESS, SUCCESS);
		} catch (Exception e) {
			log.error("Event=[PayAction#checkPassWord] ", e);
			jsonObject.put(ERROR, "系统异常，请稍后重试！");
		}finally{
			try {
				ServletActionContext.getResponse().getWriter().println(jsonObject);
				ServletActionContext.getResponse().getWriter().flush();
				ServletActionContext.getResponse().getWriter().close();
			} catch (Exception e) {
				log.error("Event=[CreationOrderAction#ajaxupdateOrder] ", e);
			}
		}
		return null;
	}

	public void setOrderStateManager(OrderStateManager orderStateManager) {
		this.orderStateManager = orderStateManager;
	}

	public void setOrderCheckManager(OrderCheckManager orderCheckManager) {
		this.orderCheckManager = orderCheckManager;
	}

	public void setPayAO(PayAO payAO) {
		this.payAO = payAO;
	}

	public Long[] getOrderId() {
		return orderId;
	}

	public void setOrderId(Long[] orderId) {
		this.orderId = orderId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
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

	public String getRefundTotalPrice() {
		return refundTotalPrice;
	}

	public void setRefundTotalPrice(String refundTotalPrice) {
		this.refundTotalPrice = refundTotalPrice;
	}

	public String getPriceAmount() {
		return priceAmount;
	}

	public void setPriceAmount(String priceAmount) {
		this.priceAmount = priceAmount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	public Map<Long, String[]> getDescaMap() {
		return descaMap;
	}

	public void setDescaMap(Map<Long, String[]> descaMap) {
		this.descaMap = descaMap;
	}

	public void setOrderDO(OrderDO orderDO) {
		this.orderDO = orderDO;
	}

	public OrderDO getOrderDO() {
		return orderDO;
	}

	public List<OrderItemDO> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemDO> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public OrderLogisticsDO getOrderLogisticsDO() {
		return orderLogisticsDO;
	}

	public void setOrderLogisticsDO(OrderLogisticsDO orderLogisticsDO) {
		this.orderLogisticsDO = orderLogisticsDO;
	}
	
	public VouchPayDO getVouchPayDO() {
		return vouchPayDO;
	}

	public void setVouchPayDO(VouchPayDO vouchPayDO) {
		this.vouchPayDO = vouchPayDO;
	}

	public String getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(String couponInfo) {
		this.couponInfo = couponInfo;
	}

}
