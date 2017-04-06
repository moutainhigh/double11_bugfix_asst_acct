package com.yuwang.pinju.core.trade.ao;

/** <p>Description: CRM 裁决成功后  如果是全额的话  调用该接口 执行平台退款	  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-11-1
 */
public interface TenPlatRefundCrmAO {
	/**
	 * <p>Description: CRM 裁决成功后  如果是全额的话  调用该接口 执行平台退款</p>
	 * @param orderId
	 * @return
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-11-1
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean judgeSuccessForPlatRefund(Long orderId);
}


