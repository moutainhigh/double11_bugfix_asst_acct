package com.yuwang.pinju.core.order.ao.pay.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.TenPayResultCode;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.manager.PinjuMemberManager;
import com.yuwang.pinju.core.order.ao.pay.PayAO;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.order.manager.helper.OrderSplitAssistantManager;
import com.yuwang.pinju.core.trade.ao.TenpaySinglepayAO;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.member.security.PasswordValidatorVO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.trade.TenSubAccountDO;
import com.yuwang.pinju.domain.trade.TenpaySinglepayParamDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * 
 * @author 杜成
 * @date 2011-6-4
 * @version 1.0
 */
public class PayAOImpl extends BaseAO implements PayAO {
	
	protected final Log PAYLOG = LogFactory.getLog("tenpay-pay");
	
	/**
	 * 担保交易支付查询
	 */
	private VouchQueryManager vouchQueryManager;
	/**
	 * 订单效验管理
	 */
	private OrderCheckManager orderCheckManager;

	/**
	 * 分账管理
	 */
	private OrderSplitAssistantManager orderSplitAssistantManager;
	
	/**
	 *单笔支付参数 
	 */
	private TenpaySinglepayAO tenpaySinglepayAO;
	
	/**
	 * 会员密码校验管理
	 */
	private PinjuMemberManager pinjuMemberManager;
	
	/**
	 * 订单查询管理
	 */
	private OrderQueryManager orderQueryManager;
	
	@Override
	public Result getPayParams(Long[] orderId,String buyerIp) {
		Result result = new ResultSupport();
		if (orderId == null) {
			result.setSuccess(false);
			result.setResultCode(TradeResultCode.PAY_CHECK_ORDER_PARMS_FAIL);
			return result;
		}
		try {
	
			List<TenpaySinglepayParamDO> list = new ArrayList<TenpaySinglepayParamDO>();
			for (Long id : orderId) {
				VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderId(id);
				if(vouchPayDO==null){
					PAYLOG.debug("order not find 支付生成,调试订单记录神奇消失使用数据库无该支付订单记!".concat(String.valueOf(id)));
					throw new ManagerException("order not find 支付生成,调试订单记录神奇消失使用数据库无该订单记!".concat(String.valueOf(id)));
				}
				
				String orderTime = DateUtil.formatDate(PinjuConstant.TENPAY_DATE_FORMAT, vouchPayDO.getGmtCreate());
				OrderDO orderDO = orderQueryManager.getOrderDOById(vouchPayDO.getOrderId());
				if(orderDO==null){
					PAYLOG.debug("order not find 支付生成,调试订单记录神奇消失使用数据库无该订单记!".concat(String.valueOf(id)));
					throw new ManagerException("order not find 支付生成,调试订单记录神奇消失使用数据库无该订单记!".concat(String.valueOf(id)));
				}
				TenpaySinglepayParamDO tenpaySinglepayParamDO = tenpaySinglepayAO.createTenpaySinglepayParamDO(orderTime, 
						"品聚订单".concat(vouchPayDO.getOrderId().toString()),  vouchPayDO.getOrderPayId(), vouchPayDO.getOrderId().toString(), vouchPayDO.getOrderAmount(), 
						 "fail",  buyerIp, vouchPayDO.getBuyerId(), vouchPayDO.getSellerId(), orderDO.getSellerNick());
				list.add(tenpaySinglepayParamDO);
				PAYLOG.debug("order not find 支付生成,调试订单记录神奇消失使用数据库已有该订单记录ORDER_ID:".concat(String.valueOf(id)));
			}
			result.setModel("tenpaySinglepayParamList", list);
		} catch (ManagerException e) {
			log.error("Event=[CreationOrderAction#getPayParams]error ",e);
		}
		return result;
	}

	
	@Override
	public Result affirmPay(Long[] orderId, int orderState, Long buyerId) {
		Result result = new ResultSupport();
		List<TenSubAccountDO> list = new ArrayList<TenSubAccountDO>();
		try {
			for (long id : orderId) {
			
				if (!orderCheckManager.isBuyerOrder(id, buyerId)) {
					result.setSuccess(false);
					continue;
				}
				TenSubAccountDO tenSubAccountDO = orderSplitAssistantManager.getTenSubAccountDO(id);
				list.add(tenSubAccountDO);
			}
			result.setModel("tenSubAccountList",list);
		} catch (ManagerException e) {
			log.error("PayCustomerAOImpl.pay error:", e);
			return this.setResult(false, result, OrderConstant.EXCEPTION,"分账参数错误");
		}catch (Exception e) {
			log.error("PayCustomerAOImpl.pay error:", e);
			return this.setResult(false, result, OrderConstant.EXCEPTION,"分账参数错误");
		}
		return result;
	}
	
	private Result setResult(boolean flag, Result result, String resultCode,
			String message) {
		result.setSuccess(flag);
		result.setResultCode(resultCode);
		result.setModel("message", message);
		return result;
	}
	
	@Override
	public Result checkPassWord(String loginName, String passWord, String tid) {
		Result result = new ResultSupport();
		try {
			if(StringUtil.isBlank(passWord)){
				result.setSuccess(false);
				result.setResultCode(TenPayResultCode.MEMBER_PASSWORD_ERROR);
				return result;
			}
			//校验当前用户输入的密码
			if (!pinjuMemberManager.validatePassowrd(new PasswordValidatorVO(loginName, passWord, tid))) {
				result.setSuccess(false);
				result.setResultCode(TenPayResultCode.MEMBER_PASSWORD_ERROR);
			}
		} catch (Exception e) {
			log.error("Event=[CreationOrderAction#ajaxCheckCode] ", e);
			result.setSuccess(false);
			result.setResultCode(TenPayResultCode.MEMBER_PASSWORD_ERROR);
		}
		return result;
	}
	
	public void setOrderCheckManager(OrderCheckManager orderCheckManager) {
		this.orderCheckManager = orderCheckManager;
	}

	public void setOrderSplitAssistantManager(
			OrderSplitAssistantManager orderSplitAssistantManager) {
		this.orderSplitAssistantManager = orderSplitAssistantManager;
	}

	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}

	public void setTenpaySinglepayAO(TenpaySinglepayAO tenpaySinglepayAO) {
		this.tenpaySinglepayAO = tenpaySinglepayAO;
	}
	
	public void setPinjuMemberManager(PinjuMemberManager pinjuMemberManager) {
		this.pinjuMemberManager = pinjuMemberManager;
	}


	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

}
