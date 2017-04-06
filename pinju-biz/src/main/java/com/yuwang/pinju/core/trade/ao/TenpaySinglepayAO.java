package com.yuwang.pinju.core.trade.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.trade.TenpaySinglepayParamDO;

public interface TenpaySinglepayAO {
	/**
	 * <p>
	 * 创建单笔支付参数
	 * </p>
	 * 
	 * @param orderId
	 * @return 
	 */
	public Result createSinglepayParam(Long orderId);
	
	/**
	 * <p>
	 * 创建单笔支付参数
	 * </p>
	 * 
	 * @param 
	 * @return 
	 */
	public TenpaySinglepayParamDO createTenpaySinglepayParamDO(String date, String desc,
			String transactionId, String spBillno,
			Long totalFee, String attach,
			String spbillCreateIp, 
			Long buyerId, Long sellerId, String sellerNick);
}
