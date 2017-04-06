package com.yuwang.pinju.web.module.pay.action;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.trade.ao.TenSubAccountAO;
import com.yuwang.pinju.domain.trade.TenSplitRollBackDO;

/**
 * @Discription: 向财付通发起分账请求的回调处理
 * @Project: pinju-web
 * @Package: com.yuwang.pinju.web.module.pay.action
 * @Title: TenSubAccountNotifyAtion.java
 * @author: [贺泳]
 * @date 2011-9-14 下午02:48:06
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class TenSubAccountNotifyAtion extends TenPayNotifyBaseAction {
	private static final long serialVersionUID = 1L;
	/**
	 * 分账 AO
	 */
	private TenSubAccountAO tenSubAccountAO;

	/**
	 * @Description: 分账回调处理
	 * @author [贺泳]
	 * @date 2011-9-16 下午02:56:03
	 * @version 1.0
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public String tenSubAccountNoity() {
		try {
			// 1、校验签名
			if (!verifySign()) {
				//return ERROR;
			}
			// 2、插入接收日志
			//tenSubAccountAO.SubAccountBackNotify(getParameters());
			// 3、检验分账状态
			if (!isTenState()) {
				return ERROR;
			}
			// 4、判断该订单是否已被处理，如果没有处理则修改订单状态
//			if(!tenSubAccountAO.orderIsDisposal(getParameters().get("sp_billno"))){
//				return ERROR;
//			}
			// 5、判断是否有退款，如果有 掉退款接口，传对象 TenSplitRollBackDO
//			if(tenSubAccountAO.orderWhetherRefund(getParameters().get("sp_billno"))){
//				TenSplitRollBackDO tenSplitRollBackDO = new TenSplitRollBackDO();
//				tenSplitRollBackDO.setTransaction_id(getParameters().get("transaction_id"));
//				tenSplitRollBackDO.setOrderId(getParameters().get("sp_billno"));
//				Map<String,Object> paramMap = new HashMap<String,Object>();
//				paramMap.put("tenSplitRollBackDO", tenSplitRollBackDO);
//				//传参给退款
//				ActionContext.getContext().setParameters(paramMap);
//				return "refund";
//			}
		} catch (Exception e) {
			log.error("Event=[TenSubAccountNotifyAtion#tenSubsCcountNoity] 财付通分账回调异常:", e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * @Description: 接受回调参数
	 * @author [贺泳]
	 * @date 2011-9-14 下午04:07:55
	 * @version 1.0
	 * @param parameters
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	protected SortedMap<String, String> setParameters(SortedMap<String, String> parameters) {
		// 任务代码 3
		parameters.put("cmdno", getString("cmdno"));

		// 分账处理结果 0—成功
		parameters.put("pay_result", getString("pay_result"));

		// 分账结果信息，分账成功时为空
		parameters.put("pay_info", getString("pay_info"));

		// 卖方账号（商户spid）
		parameters.put("bargainor_id", getString("bargainor_id"));

		// 财付通交易号(订单号)
		parameters.put("transaction_id", getString("transaction_id"));
		
		// 商户系统内部的定单号，此参数仅在对账时提供。 
		parameters.put("sp_billno", getString("sp_billno"));

		// 订单总金额，以分为单位
		parameters.put("total_fee", getString("total_fee"));

		// 现金支付币种
		parameters.put("fee_type", getString("fee_type"));

		// 业务类型，整数值，代表分账处理规则与业务参数编码规则，暂固定为97
		parameters.put("bus_type", getString("bus_type"));

		// 业务参数，特定格式的字符串 (账户^金额^角色)[|(账户^金额^角色)]*
		parameters.put("bus_args", getString("bus_args"));

		// 版本号必须填 4
		parameters.put("version", getString("version"));
		return parameters;
	}

	/**
	 * @Description: 校验回调签名
	 * @author [贺泳]
	 * @date 2011-9-14 下午03:40:32
	 * @version 1.0
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	protected boolean verifySign() {
//		String _sign = getString("sign"); // 签名字符串
//		return tenSubAccountAO.verifySignByGbk(getParameters(), _sign);
		return false;
	}

	/**
	 * @Description: 校验回调的分账状态  0—成功
	 * @author [贺泳]
	 * @date 2011-9-14 下午03:40:49
	 * @version 1.0
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	protected boolean isTenState() {
		return StringUtils.equalsIgnoreCase(getParameters().get("pay_result"),PinjuConstant.TENPAY_DIRECTPAY_TRADE_STATE);
	}

	@Override
	protected boolean notifyDelivery() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setTenSubAccountAO(TenSubAccountAO tenSubAccountAO) {
		this.tenSubAccountAO = tenSubAccountAO;
	}

	@Override
	protected Integer getBizType() {
		// TODO Auto-generated method stub
		return null;
	}

}