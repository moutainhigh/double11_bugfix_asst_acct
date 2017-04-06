package com.yuwang.pinju.core.order.manager.helper;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;

/**
 * Created on 2011-9-19
 * @see
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderActivityManager {
	/**
	 * 
	 * Created on 2011-9-19
	 * <p>Discription: 订单生成活动前置处理</p>
	 * @param orderDO
	 * @param list
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Map<OrderDO, List<OrderItemDO>> activityBeforeProcess(OrderCreationVO orderCreation,OrderDO orderDO,List<OrderItemDO> list)throws ManagerException ;
	
	
	
	/**
	 * 
	 * Created on 2011-8-31
	 * <p>Discription: 活动效验</p>
	 * @param orderCreation 生成订单相关对象
	 * @return
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result activityCheck(OrderCreationVO orderCreation);
	
	
	
}

