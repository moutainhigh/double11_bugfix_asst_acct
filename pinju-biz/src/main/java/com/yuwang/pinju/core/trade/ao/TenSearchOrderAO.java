package com.yuwang.pinju.core.trade.ao;

import java.util.SortedMap;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.trade.TenSearchOrderDO;


public interface TenSearchOrderAO {

	/**
	 * Created on 2011-9-9
	 * <p>
	 * Discription: 得到发送报文详情
	 * </p>
	 * 
	 * @param tenpaySearchOrderDO
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result getOrderDetail(TenSearchOrderDO tenSearchOrderDO);

	
	/**
	 * Created on 2011-9-9
	 * <p>
	 * Discription:验证回调签名
	 * </p>
	 * 
	 * @param parameters
	 * @param signString
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 *//*
	boolean verifySign(SortedMap<String, String> parameters,String signString);*/
	
	
	/**
	 * Created on 2011-9-23
	 * <p>
	 * Discription:获得订单详情
	 * </p>
	 * 
	 * @param parameters
	 * @param signString
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result getOrderInfo(SortedMap<String, String> createParameters,String sign,Integer cmdno,Integer version,Long sp_billno);
	
}
