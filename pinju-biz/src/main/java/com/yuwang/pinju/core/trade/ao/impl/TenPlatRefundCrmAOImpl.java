package com.yuwang.pinju.core.trade.ao.impl;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.trade.ao.TenPlatRefundCrmAO;
import com.yuwang.pinju.core.trade.ao.TenPlatformRefundAO;
import com.yuwang.pinju.core.trade.manager.VouchQueryManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.trade.VouchPayDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;

/** <p>Description: 为CRM的客服裁决提工的平台退款接口  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-11-1
 */
public class TenPlatRefundCrmAOImpl extends BaseAO implements TenPlatRefundCrmAO{
	
	private TenPlatformRefundAO tenPlatformRefundAO;
	private VouchQueryManager vouchQueryManager;
	
	/**
	 * 1.组装参数   2.插入发请求前日志 3.发请求给财付通  4.接受财付通返回结果   5.插入接受日志
	 * 6.if(成功:[1.更新订单和退款状态 ， 退款日志状态])  
	 * else(失败)[是指定的错误code  重发5次，否则  插入手工单   更改订单状态为关闭  更改退款为退款失败]
	 */
	@Override
	public boolean judgeSuccessForPlatRefund(Long orderId) {
		Result result = new ResultSupport();
		try {
			VouchPayDO vouchPayDO = vouchQueryManager.selectOrderPayByOrderId(orderId);
			// 组装 发送参数    财付通交易号， 内部交易号， 总金额 ，退款金额
			PlatformRefundParamDO sendParams= PlatformRefundParamDO.createRefundParamDO(vouchPayDO.getOutPayId(), vouchPayDO.getOrderPayId(), vouchPayDO.getRealPayMentamount(), vouchPayDO.getRealPayMentamount());
			sendParams.setOrderId(orderId);
			result = tenPlatformRefundAO.createPlatformRefundParam(sendParams);
			if(result.isSuccess()) {
				sendParams = (PlatformRefundParamDO) result.getModel("paramDO");
				String postUrl = (String) result.getModel("refundParamUrl");
				result =tenPlatformRefundAO.platformRefundSendRequest(postUrl,sendParams);
			}
		} catch (ManagerException e) {
			log.error("Event=[TenPlatformRefundAOImpl.judgeSuccessForPlatRefund() exception]", e);
			result.setSuccess(false);
		}
		return result.isSuccess();
	}
	
	
	public void setTenPlatformRefundAO(TenPlatformRefundAO tenPlatformRefundAO) {
		this.tenPlatformRefundAO = tenPlatformRefundAO;
	}
	public void setVouchQueryManager(VouchQueryManager vouchQueryManager) {
		this.vouchQueryManager = vouchQueryManager;
	}
}


