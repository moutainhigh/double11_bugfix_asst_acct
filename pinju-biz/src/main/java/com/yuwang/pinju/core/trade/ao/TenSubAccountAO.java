package com.yuwang.pinju.core.trade.ao;

import java.util.List;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.trade.TenSubAccountDO;

/**
 * @Discription: 分账 
 * @Project: pinju-biz
 * @Package: com.yuwang.pinju.core.trade.ao
 * @Title: TenSubAccountAO.java
 * @author: [贺泳]
 * @date 2011-9-13 上午10:11:21
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public interface TenSubAccountAO {
	/**
	 * @Description: 组装财付通分账的参数
	 * @author [贺泳]
	 * @date 2011-9-13 上午10:14:28
	 * @version 1.0
	 * @param subAccountDO: 分账List
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result createSubAccountParam(List<TenSubAccountDO> tenSubAccountList);
	
	/**
	 * @Description:根据orderId查询订单信息 
	 * @author [贺泳]
	 * @date 2011-10-21 下午04:11:08
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param orderId：订单Id
	 *        mumberId：会员Id
	 * @return
	 */
	public Result queryOrderByOrderId(Long orderId,Long mumberId);
}