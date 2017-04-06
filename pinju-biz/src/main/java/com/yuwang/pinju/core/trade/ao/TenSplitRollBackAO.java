package com.yuwang.pinju.core.trade.ao;

import java.util.SortedMap;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.trade.TenSplitRollBackDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;

public interface TenSplitRollBackAO {
	
	/**
	 * 
	 * Created on 2011-9-14
	 * <p>Discription: 创建分账回退参数</p>
	 * @param tenSplitRollBackDO
	 * @return
	 * @author:[曹晓]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result createSplitRollBackParam(TenSplitRollBackDO tenSplitRollBackDO);
	
	/**
	 * Created on 2011-9-5
	 * <p>Discription: 验证回调签名</p>
	 */
	boolean verifySign(SortedMap<String, String> parameters,String signString);
	
	/**
	 * Created on 2011-11-01
	 * <p>Discription: 分账回退处理</p>
	 */
	public Result tenSplitRollBack(TenSplitRollBackDO tenSplitRollBackDO) ;
	
	public PlatformRefundParamDO updateTenSplitRollbackSuccess(TenSplitRollBackDO tenSplitRollbackDO);
	
	public boolean updateTenSplitRollbackFail(TenSplitRollBackDO tenSplitRollbackDO) ;
}
