package com.yuwang.pinju.core.trade.ao;

import java.util.List;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.trade.TenpayMergepayRecvParamDO;
import com.yuwang.pinju.domain.trade.TenpaySinglepayParamDO;

/**
 *  腾讯财付通支付
 * 
 * @author shihongbo
 */
public interface TenpayMergepayAO {
	/**
	 * <p>
	 * 创建合并支付参数
	 * </p>
	 * 
	 * @param orderList
	 * @return 
	 */
	public Result createMergepayParam(List<TenpaySinglepayParamDO> paramList);
	
}