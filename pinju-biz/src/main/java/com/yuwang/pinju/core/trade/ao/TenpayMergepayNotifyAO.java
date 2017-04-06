package com.yuwang.pinju.core.trade.ao;

import java.util.Map;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.trade.TenpayMergepayRecvParamDO;

public interface TenpayMergepayNotifyAO {
	/**
	 * <p>
	 * 前台合并支付回调通知
	 * </p>
	 * 
	 * @param param
	 * @return 
	 */
	public Result mergePayNotify(TenpayMergepayRecvParamDO param);	
	
	
	/**
	 * 
	 * Created on 2011-11-11
	 * <p>Discription: 担保交易后台单笔支付回调通知</p>
	 * @param parameters
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result backGroundMergePayNotify(Map<String, String> parameters);
}
