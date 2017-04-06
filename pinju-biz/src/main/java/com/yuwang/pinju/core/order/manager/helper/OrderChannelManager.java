package com.yuwang.pinju.core.order.manager.helper;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;

/**
 * Created on 2011-9-19
 * @see
 * <p>Discription: 订单分销处理</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderChannelManager {
	/**
	 * 
	 * Created on 2011-8-29
	 * <p>Discription: 处理分销</p>
	 * @param orderCreation
	 * @param orderDO
	 * @param list
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	 Map<OrderDO,List<OrderItemDO>> channelBeforeProcess(OrderCreationVO orderCreation,OrderDO orderDO,List<OrderItemDO> list);
	 
	/**
	 * 
	 * Created on 2011-8-26
	 * <p>
	 * Discription: 子订单分销后续处理
	 * </p>
	 * 
	 * @param channelId
	 * @param itemId
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	 void channelAfterProcess(String channelId,OrderItemDO orderItemDO) throws ManagerException;
}

