package com.yuwang.pinju.core.trade.ao.impl;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.MarginResultCode;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.margin.ao.MarginAO;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.trade.ao.TenDirectPayAO;
import com.yuwang.pinju.core.trade.manager.DirectManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.trade.DirectOrderDO;
import com.yuwang.pinju.domain.trade.DirectPayDO;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;
import com.yuwang.pinju.domain.trade.DirectPayRevLogDO;
import com.yuwang.pinju.domain.trade.DirectPaySendLogDO;

/**
 * Created on 2011-8-31
 * 
 * @see <p>Discription:财富通即时到帐支付</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class TenDirectPayAOImpl extends TenPayAbstractBaseAO implements TenDirectPayAO {

	/**
	 * 
	 */
	private DirectManager directManager;
	
	/**
	 * 担保订单查询
	 */
	private OrderQueryManager orderQueryManager;
	/**
	 * 
	 */
	private ItemManager itemManager;
	
	private MarginAO marginAO;

	private DataSourceTransactionManager zizuTransactionManager;
	
	@Override
	public Result groupTenDirectPay(DirectPayParamDO directPayParamDO) {
		Result result = new ResultSupport();
		try {
			//支付订单编号
			final String payOrderID = this.getPayOrderID();
	
			//封装支付参数
			SortedMap<String, String> parameters = createParameters(directPayParamDO,payOrderID);
			//生成签名
			this.createSign(parameters);
			//参数报文带签名
			final String sendDetail = this.parametersToString(parameters);

			DirectPayDO directPayDO = DirectPayDO.createVouchDirectPayDO(directPayParamDO,Long.valueOf(payOrderID));

			DirectPaySendLogDO marginSendLogDO = DirectPaySendLogDO.createDirectPaySendLogDO(directPayParamDO,Long.valueOf(payOrderID),sendDetail);
			
			directManager.insertDirectOrderRecord(directPayDO, marginSendLogDO);
			/** ducheng@zba.com 页面post传参 2011-10-10 13:51*/
			result.setModel("parametersUrl", PATMENTGATEWAYURL);
			
			
			result.setModel("orderAmount", String.valueOf(directPayParamDO.getOrderAmount()));
			
			result.setModel("payOrderID", String.valueOf(payOrderID));
			/** ducheng@zba.com 页面post传参 2011-10-10 13:51*/
			result.setModel("parameters",parameters);
			
		} catch (ManagerException e) {
			log.error("Event=[TenDirectPayAOImpl#tenDirectPay]:", e);
			result.setResultCode(TradeResultCode.PAYMARGIN_EXCEPTION);
			result.setSuccess(false);
		}catch (Exception e) {
			log.error("Event=[TenDirectPayAOImpl#tenDirectPay]:", e);
			result.setResultCode(TradeResultCode.PAYMARGIN_EXCEPTION);
			result.setSuccess(false);
		}
		return result;
	}
	
	@Override
	public Result tenDirectPay(DirectPayParamDO directPayParamDO) {
		Result result = new ResultSupport();
		try {
			//支付订单编号
			final String payOrderID = this.getPayOrderID();
			//订单ID
			final Long orderId = directManager.getOrderId();
			//封装支付参数
			SortedMap<String, String> parameters = createParameters(directPayParamDO,payOrderID);
			//生成签名
			this.createSign(parameters);
			//参数报文带签名
			final String sendDetail = this.parametersToString(parameters);

			DirectOrderDO directOrderDO = DirectOrderDO.createDirectOrderDO(directPayParamDO,orderId,Long.valueOf(payOrderID));

			DirectPayDO directPayDO = DirectPayDO.createDirectPayDO(directPayParamDO,Long.valueOf(payOrderID),orderId);

			DirectPaySendLogDO marginSendLogDO = DirectPaySendLogDO.createDirectPaySendLogDO(directPayParamDO,Long.valueOf(payOrderID),sendDetail);
			
			directManager.insertDirectOrderRecord(directOrderDO,directPayDO, marginSendLogDO);
			/** ducheng@zba.com 页面post传参 2011-10-10 13:51*/
			result.setModel("parametersUrl", PATMENTGATEWAYURL);
			
			result.setModel("orderAmount", String.valueOf(directPayParamDO.getOrderAmount()));
			
			result.setModel("payOrderID", String.valueOf(payOrderID));
			/** ducheng@zba.com 页面post传参 2011-10-10 13:51*/
			result.setModel("parameters",parameters);
			
		} catch (ManagerException e) {
			log.error("Event=[TenDirectPayAOImpl#tenDirectPay]:", e);
			result.setResultCode(TradeResultCode.PAYMARGIN_EXCEPTION);
			result.setSuccess(false);
		}
		return result;
	}

	@Override
	public String getSendDetail(String amount, Long orderId, String dateString,
			String productNo) {
		return productNo;
	}
	
	@Override
	public boolean verifySign(SortedMap<String, String> parameters,String signString){
		return super.verifySign(parameters, signString);
	}
	
	/****** 财付通通知后更新订单状态及处理保证金后续业务Modified By ShiXing@2011.10.17 ******/
	@Override
	public Result tenDirectPayNotify(DirectPayReceiveParamDO directPayReceiveParamDO, boolean payState) {
		Result result = new ResultSupport();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);//设置事务传播级别为PROPAGATION_REQUIRED
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			Integer orderState = DirectPayConstant.DIRECTPAY_ORDER_STATUS_FAIL;
			Integer orderPayState = DirectPayConstant.DIRECTPAY_PAY_STATUS_FAIL;
			if(payState){
				orderState = DirectPayConstant.DIRECTPAY_ORDER_STATUS_SUCCESS;
				orderPayState = DirectPayConstant.DIRECTPAY_PAY_STATUS_SUCCESS;
			}
			final DirectOrderDO  directOrderDO = DirectOrderDO.upRevDirectOrderDO(directPayReceiveParamDO,orderState);
			final DirectPayDO  directPayDO = DirectPayDO.upRevDirectPayDO(directPayReceiveParamDO,orderPayState);
			final DirectPayRevLogDO directPayRevLogDO = DirectPayRevLogDO.createPayRevLogDODirectPayRevLogDO(directPayReceiveParamDO);
			DirectOrderDO directOrderDOQuery = directManager.getDirectOrderDOById(directOrderDO.getPayOrderId());
			//判断本订单是否已正常处理,如果正常处理,则不做任何操作
			if(directOrderDOQuery==null){
				log.warn("direct pay debug margin tenDirectPayNotify no payOrderId 无该支付订单号,支付平台回传报文信息--->".concat(directPayReceiveParamDO.getRevDetail()));
				result.setResultCode(TradeResultCode.DIRECTPAY_ORDER_NOT_EXITS);
				result.setSuccess(false);
				return result;
			}else if (directOrderDOQuery.getOrderState()==DirectPayConstant.DIRECTPAY_ORDER_STATUS_SUCCESS) {
				log.warn("direct pay debug margin tenDirectPayNotify order already 该支付订单号已处理--->".concat(directPayReceiveParamDO.getRevDetail()));
				result.setSuccess(false);
				result.setResultCode(MarginResultCode.PAYMARGIN_ORDER_ALREADY);
				return result;
			}
			boolean orderFlag = directManager.updateDirectReceiveOrderRecord(directOrderDO, directPayDO, directPayRevLogDO);
			if (!orderFlag) { //更新订单状态出错
				log.warn("TenDirectPayAOImpl#tenDirectPayNotify#margin debug update.margin.order.state.fail");
				zizuTransactionManager.rollback(status);
				result.setSuccess(orderFlag);
				result.setResultCode(MarginResultCode.UPDATE_MARGIN_ORDER_STATE_FAIL);
				return result;
			}else {
				Result marginResult = marginAO.receiveMargin(directPayReceiveParamDO, payState);
				if (!marginResult.isSuccess()) { //处理保证金后续业务失败,如乐观锁导致的失败,回滚事务,等待财付通补单处理
					log.warn("TenDirectPayAOImpl#tenDirectPayNotify#margin debug directpay.margin.operation.fail");
					zizuTransactionManager.rollback(status);
					result.setSuccess(false);
					result.setSubResultCode(MarginResultCode.DIRECTPAY_MARGIN_OPERATION_FAIL);
					return result;
				}
				zizuTransactionManager.commit(status); //订单处理成功,保证金后续逻辑处理成功,提交事务
				/****** 通知店铺更新店铺状态为开店成功 ******/
				marginAO.notifyShopUpdate(directOrderDOQuery.getBuyerId());
			}
		} catch (ManagerException e) {
			log.error("Event=[TenDirectPayAOImpl#tenDirectPayNotify#ManagerException]", e);
			log.warn("direct pay debug margin tenDirectPayNotify ManagerException 支付平台回传报文信息--->".concat(directPayReceiveParamDO.getRevDetail()));
			zizuTransactionManager.rollback(status);
			result.setSuccess(false);
			result.setResultCode(TradeResultCode.PAYMARGIN_EXCEPTION);
		} catch (Exception e) {
			log.error("Event=[TenDirectPayAOImpl#tenDirectPayNotify#Exception]财付通通知后更新订单或处理保证金后续业务失败", e);
			zizuTransactionManager.rollback(status);
			result.setSuccess(false);
			result.setResultCode(TradeResultCode.PAYMARGIN_EXCEPTION);
		}
		return result;
	}

	@Override
	public Result vouchTenDirectPayNotify(
			DirectPayReceiveParamDO directPayReceiveParamDO, boolean payState) {
		Result result = new ResultSupport();
		try {
			//生成日志对象
			final DirectPayRevLogDO directPayRevLogDO = DirectPayRevLogDO.createPayRevLogDODirectPayRevLogDO(directPayReceiveParamDO);
			Integer orderState = OrderDO.ORDER_STATE_1;
			Integer orderPayState = DirectPayConstant.DIRECTPAY_PAY_STATUS_FAIL;
			
			final Long payOrderID = directPayReceiveParamDO.getOrderNo();
			//得到支付对象
			DirectPayDO querydirectPayDO = directManager.getDirectPayDOById(payOrderID);
		
			if (querydirectPayDO == null) {
				log.warn("direct pay debug vouchTenDirectPayNotify 财付通即时到账回调处理无此支付订单,第3方回传支付订单号--->".concat(String.valueOf(payOrderID)));
				result.setSuccess(false);
				result.setResultCode(TradeResultCode.DIRECTPAY_ORDER_NOT_EXITS);
				return result;
			}
		
			if(payState){
				orderState = OrderDO.ORDER_STATE_2;
				orderPayState = DirectPayConstant.DIRECTPAY_PAY_STATUS_SUCCESS;
			}else{
				//第三方收银台支付失败
				log.warn("direct pay debug 财付通即时到账收银台支付失败。财付通报文:".concat(directPayRevLogDO.getDetail()));
				log.warn("direct pay debug 插入财付通即时到账接受日志");
				log.warn("direct pay debug 财付通即时到账收银台支付失败。业务更新参数directPayRevLogDO-->".concat(directPayRevLogDO.toString()));
				directManager.insertDirectPayRevLog(directPayRevLogDO);
				return result;
			}
			
			final Long orderId = querydirectPayDO.getOrderId();
			OrderDO orderDO = orderQueryManager.getOrderDOById(orderId);
			List<OrderItemDO> orderItemList = orderQueryManager.queryOrderItemList(orderId);
			result.setModel("orderId", orderId);
			if (orderDO ==null || orderItemList==null || orderItemList.isEmpty()){
				log.warn("direct pay debug 内部订单信息异常,支付订单ID无法对应业务订单ID。当前支付订单ID".concat(String.valueOf(payOrderID)));
				log.warn("direct pay debug 内部订单信息异常,支付订单ID无法对应业务订单ID。通过支付订单ID获取的业务订单ID".concat(String.valueOf(orderId)));
				result.setSuccess(false);
				result.setResultCode(TradeResultCode.DIRECTPAY_ORDER_NOT_EXITS);
				return result;
			}
			//效验支付是否已处理
			if (!orderDO.getOrderState().equals(OrderDO.ORDER_STATE_1)) {
				log.warn("direct pay debug 当前订单支付成功状态已处理,当前支付订单ID".concat(String.valueOf(payOrderID)));
				log.warn("direct pay debug 当前订单支付成功状态已处理,当前业务订单ID。通过支付订单ID获取的业务订单ID".concat(String.valueOf(orderId)));
				log.warn("direct pay debug 当前业务订单状态为:".concat(String.valueOf(orderDO.getOrderState())));
				result.setSuccess(false);
				result.setResultCode(TradeResultCode.PAY_PROCESS_ORDER_ALREADY);
				return result;
			}
			
			//更新订单状态
			orderDO.setOrderState(orderState);
			final DirectPayDO  directPayDO = DirectPayDO.upRevDirectPayDO(directPayReceiveParamDO,orderPayState);
			
			result.setSuccess(directManager.updateDirectReceiveOrderRecord(orderDO,directPayDO,directPayRevLogDO));
			
			//一口价扣库存 当前930 一个主订单只对应一个子订单
			OrderItemDO orderItemDO = orderItemList.get(0);
			boolean isCutStock = orderItemDO.getBussinessType().equals(DirectPayConstant.BIZ_TYPE_PRICE);
			if(isCutStock){
				itemManager.cutStock(orderItemDO.getItemId(), orderItemDO.getItemSkuId(), orderItemDO.getBuyNum());
			}
			
		} catch (ManagerException e) {
			log.error("Event=[TenDirectPayAOImpl#tenDirectPayNotify]", e);
			log.warn("direct pay debug vouchTenDirectPayNotify ManagerException 支付平台回传报文信息--->".concat(directPayReceiveParamDO.getRevDetail()));
			result.setResultCode(TradeResultCode.PAY_PROCESS_DIRECT_EXCEPTION);
			result.setSuccess(false);
		}
		return result;
	}

	/**
	 * 
	 * Created on 2011-8-31
	 * <p>Discription:封装财付通参数
	 * </p>
	 * 
	 * @param amount
	 * @param payOrderId
	 * @param dateString
	 * @param productNo
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private SortedMap<String, String> createParameters(DirectPayParamDO directPayParamDO,String payOrderID) throws ManagerException {
		String orderTime = DateUtil.formatDate(PinjuConstant.TENPAY_DATE_FORMAT, directPayParamDO
				.getCreateTime());
		String temp = directPayParamDO.getItemTitle();
		try {
			temp = new String(temp.getBytes(),CHARSET);
		} catch (UnsupportedEncodingException e) {
			log.error("createParameters itemTitle EncodingException");
		}
		SortedMap<String, String> createParameters = new TreeMap<String, String>();
		createParameters.put("sign_type", SIGNTYPE);
		createParameters.put("input_charset", CHARSET);
		createParameters.put("bank_type", BANKTYPE);
		createParameters.put("body",temp);
		createParameters.put("attach", directPayParamDO.getBizType().toString());
		createParameters.put("return_url", RETURNURL);
		createParameters.put("notify_url", NOTIFYURL);
		createParameters.put("partner", MERCHANTNO);
		createParameters.put("out_trade_no", payOrderID);
		createParameters.put("total_fee", directPayParamDO.getOrderAmount().toString());
		createParameters.put("fee_type", CURRENCYTYPE);
		createParameters.put("spbill_create_ip",String.valueOf(directPayParamDO.getBuyerIp()));
		createParameters.put("time_start", orderTime);
		String time_expire =  DateUtil.formatDate(PinjuConstant.TENPAY_DATE_FORMAT,changeMinute(directPayParamDO
				.getCreateTime(),TIME_EXPIRE_MINUTE));
		createParameters.put("time_expire",time_expire);
		return createParameters;
	}

	/**
	 * 
	 * Created on 2011-10-10
	 * <p>Discription: 财富通收银台超时控制添加</p>
	 * @param date
	 * @param change
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private static Date changeMinute(Date date, int change) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, change);
		return c.getTime();
	}
	/**
	 * 
	 * Created on 2011-9-1
	 * <p>Discription: 获取支付订单号可根据需求进行修正</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	protected String getPayOrderID() throws ManagerException{
		return directManager.getPayOrderId().toString();
	}
	
	public void setDirectManager(DirectManager directManager) {
		this.directManager = directManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}
	//收银台超时限制,单位分
	/** ducheng@zba.com 页面post传参 2011-10-10 13:51*/
	protected final int TIME_EXPIRE_MINUTE = 25;
	//财付通支付网关地址
	protected final String PATMENTGATEWAYURL = PinjuConstant.TENPAY_DIRECTPAY_PAYURL;
	//财付通后台回调地址
	protected final String NOTIFYURL = PinjuConstant.TENPAY_DIRECTPAY_NOTIFY_URL;
	//财付通页面回调地址
	protected final String RETURNURL = PinjuConstant.TENPAY_DIRECTPAY_RETURN_URL;
	//财付通支付渠道
	protected final String BANKTYPE = PinjuConstant.TENPAY_DIRECTPAY_BANK_TYPE;

	@Override
	protected String getMD5Key() {
		return PinjuConstant.TENPAY_DIRECTPAY_MD5KEY;
	}

	@Override
	public Result queryOrderId(Long payOrderID) {
		//得到支付对象
		Result result = new ResultSupport();
		try {
			DirectPayDO querydirectPayDO = directManager.getDirectPayDOById(payOrderID);
			result.setModel("orderId", querydirectPayDO.getOrderId());
		} catch (ManagerException e) {
			log.error("Event=[TenDirectPayAOImpl#tenDirectPay]:", e);
			result.setResultCode(TradeResultCode.PAYMARGIN_EXCEPTION);
			result.setSuccess(false);
		}catch (Exception e) {
			log.error("Event=[TenDirectPayAOImpl#tenDirectPay]:", e);
			result.setResultCode(TradeResultCode.PAYMARGIN_EXCEPTION);
			result.setSuccess(false);
		}
		return result;
		
	}
	
	public void setZizuTransactionManager(DataSourceTransactionManager zizuTransactionManager) {
		this.zizuTransactionManager = zizuTransactionManager;
	}
	
	public void setMarginAO(MarginAO marginAO) {
		this.marginAO = marginAO;
	}

}
