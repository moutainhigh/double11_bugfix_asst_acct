package com.yuwang.pinju.core.trade.ao;

import com.yuwang.pinju.domain.trade.DirectPayReceiveParamDO;

/**
 * Created on 2011-9-5
 * @see
 * <p>Discription: 即时到账</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface DirectPayAO {
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 更新支付状态失败处理</p>
	 * @param directPayReceiveParamDO
	 * @param payState
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void upPayStateFailure(DirectPayReceiveParamDO directPayReceiveParamDO,boolean payState);
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 通知发货失败处理</p>
	 * @param directPayReceiveParamDO
	 * @param payState
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void deliveryFailure(DirectPayReceiveParamDO directPayReceiveParamDO,boolean payState);
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 支付回调无业务类型处理</p>
	 * @param directPayReceiveParamDO
	 * @param payState
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void no_productNo(DirectPayReceiveParamDO directPayReceiveParamDO,boolean payState);
}

