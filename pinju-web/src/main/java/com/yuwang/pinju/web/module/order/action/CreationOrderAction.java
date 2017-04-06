package com.yuwang.pinju.web.module.order.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultCodeMsg;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.order.ao.OrderCreationAO;
import com.yuwang.pinju.core.order.ao.OrderQueryAO;
import com.yuwang.pinju.core.order.ao.OrderUpAO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.cookie.manager.ShoppingCarCookieManager;
import com.yuwang.pinju.web.module.BaseAction;
import com.yuwang.pinju.web.system.ServletUtil;


/**
 * 
 * Created on 2011-6-10
 * <p>Discription: 订单生成</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class CreationOrderAction extends BaseAction{

	private static final long serialVersionUID = -1680492884801221282L;

	protected final Log log = LogFactory.getLog(this.getClass().getName());

	private List<Long> orderIdList;

	private OrderCreationVO orderCreation;

	private OrderCreationAO orderCreationAO;
	
	private OrderUpAO orderUpAO;

	private String errorMessage;
	
	private int type = 1;
	
	private long errorItemId = 0;
	
	/**
	 * 订单ID 用于正式发送第三方收银台前在主订单打上标记(ajax异步处理)
	 */
	private Long[] orderId;

	/**
	 * ajax异步校验 验证码
	 * sid、validateCode都是用来校验 验证码
	 */
	private String sid;
	
	private String validateCode;
	
	private OrderQueryAO orderQueryAO;
	
	/**
	 * 支付订单总金额 Add by HeYong @2011-11-14
	 */
	private String totalFee;

	@SuppressWarnings("unchecked")
	public String creationOrder() {
		try {
			CookieLoginInfo loginInfo = CookieLoginInfo.getCookieLoginInfo();
			if (!loginInfo.isLogin()) {
				return ERROR;
			}
			Result result = null;
			orderCreation.setBuyerMemberId(this.getUserId());
			boolean flag = orderCreation.check();
			if (flag)
				result = orderCreationAO.creationOrder(orderCreation, this
						.getUserId(),String.valueOf(ServletUtil.getRemoteNumberIp()));
			if (result != null) {
				if (!result.isSuccess()) {
					this.setErrorMessage(ResultCodeMsg.getResultMessage(result.getResultCode(), new String()));
					if(result.getResultCode().equals(TradeResultCode.ORDER_CHECK_LASTTIME_BUYTIME_FAIL)||result.getResultCode().equals(TradeResultCode.ORDER_CHECK_LASTTIME_BUYNUM_FAIL)
							||result.getResultCode().equals(TradeResultCode.ORDER_CHECK_LASTTIME_FAIL)){
						type = 2;
						errorItemId = (Long) result.getModel("errorItemId");
					}
					return ERROR;
				} else {
					ShoppingCarCookieManager
							.deleteItemFromShoppingCart(getdeleteItemFromShoppingCart());
					List<Long> orderIdList = (List<Long>)result.getModel("orderIdList");
					String orderParms = "";
					for(Long orderId : orderIdList){
						orderParms +=  "orderId=".concat(String.valueOf(orderId)).concat("&");
					}
					if(StringUtil.isNotEmpty(orderParms)){
						orderParms = orderParms.substring(0, orderParms.length()-1);
					}
					response.sendRedirect("/orderPay/pay.htm?".concat(orderParms));
					return null;
				}
			}
			this.setErrorMessage(ResultCodeMsg.getResultMessage(TradeResultCode.ORDER_CHECK_EXCEPTION, new String()));
		} catch (Exception e) {
			this.setErrorMessage(ResultCodeMsg.getResultMessage(TradeResultCode.ORDER_CHECK_EXCEPTION,new Object()));
			log.error("Event=[CreationOrderAction#creationOrder] ", e);
		}

		return ERROR;
	}

	/**
	 * <p>
	 * 删除购物车中的某条数据
	 * </p>
	 * 
	 * @param itemList
	 *            需要删除的购物车商品 ID，删除购物车的商品id + ' ' + skuid,
	 *            如果skuid没有，则为0；如果有多组，用,分隔
	 */
	private String getdeleteItemFromShoppingCart() {
		StringBuffer sb = new StringBuffer();
		if (orderCreation.getItemId() != null
				&& orderCreation.getItemSkuId() != null) {
			for (int i = 0; i < orderCreation.getItemId().length; i++) {
				if(i > 0){
					sb.append(",");
				}
				sb.append(orderCreation.getItemId()[i]).append(" ").append(
						orderCreation.getItemSkuId()[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * @Description: Ajax异步校验验证码是否输入正确
	 * @author [贺泳]
	 * @date 2011-9-14 上午09:33:20
	 * @version 1.0
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String ajaxCheckCode(){
		JSONObject jsonObject = new JSONObject();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			Result result = orderCreationAO.ajaxCheckCode(sid, validateCode);
			if(!result.isSuccess()){
				jsonObject.put(ERROR, ResultCodeMsg.getResultMessage(result.getResultCode(), new String()));
			}
			ServletActionContext.getResponse().getWriter().println(jsonObject);
			ServletActionContext.getResponse().getWriter().flush();
			ServletActionContext.getResponse().getWriter().close();
		} catch (IOException e) {
			log.error("Event=[CreationOrderAction#ajaxCheckCode] ", e);
		}
		return null;
	}
	
	/**
	 * @Description: 用于正式发送第三方收银台前在主订单打上标记(ajax异步处理)
	 * @author [贺泳]
	 * @date 2011-11-4 上午11:38:49
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return
	 */
	public String ajaxupdateOrder(){
		JSONObject jsonObject = new JSONObject();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			Result result = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			
			result = orderQueryAO.queryOrderPriceAmount(map);
			if(!result.isSuccess()){
				jsonObject.put(INPUT, "查询订单总价格异常");
				return null;
			}
			int sum = (Integer) result.getModel("sum");
			
			if(StringUtil.isBlank(totalFee)){
				jsonObject.put(INPUT, "查询订单总价格异常");
				return null;
			}
			
			if(!totalFee.equals(MoneyUtil.getCentToDollar(sum))){
				jsonObject.put("pricerror", "订单总金额而不一致");
				return null;
			}
			
			result = orderUpAO.updateOrderPostPayState(orderId);
			if(!result.isSuccess()){
				jsonObject.put(ERROR, "在继续支付时，ajax异步修改订单order_pay_state异常");
				return null;
			}
			
		} catch (Exception e){
			jsonObject.put(ERROR, "查询订单总价格异常");
			log.error("Event=[PayAction#checkPassWord] ", e);
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
	
	private long getUserId() {
		long userId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			userId = login.getMemberId();
		}
		if (log.isDebugEnabled()) {
			log.debug("member id: " + userId);
		}
		return userId;
	}

	public List<Long> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<Long> orderIdList) {
		this.orderIdList = orderIdList;
	}

	public OrderCreationVO getOrderCreation() {
		return orderCreation;
	}
	
	public void setOrderUpAO(OrderUpAO orderUpAO) {
		this.orderUpAO = orderUpAO;
	}

	public void setOrderCreation(OrderCreationVO orderCreation) {
		this.orderCreation = orderCreation;
	}

	public void setOrderCreationAO(OrderCreationAO orderCreationAO) {
		this.orderCreationAO = orderCreationAO;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getErrorItemId() {
		return errorItemId;
	}

	public void setErrorItemId(long errorItemId) {
		this.errorItemId = errorItemId;
	}

	public Long[] getOrderId() {
		return orderId;
	}

	public void setOrderId(Long[] orderId) {
		this.orderId = orderId;
	}
	
	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	
	public void setOrderQueryAO(OrderQueryAO orderQueryAO) {
		this.orderQueryAO = orderQueryAO;
	}
}
