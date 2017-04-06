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
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderCouponManager {
	/**
	 * 
	 * Created on 2011-8-29
	 * <p>Discription: 处理优惠券</p>
	 * @param orderCreation
	 * @param orderDO
	 * @param list
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Map<OrderDO,List<OrderItemDO>> couponBeforeProcess(OrderCreationVO orderCreation,OrderDO orderDO,List<OrderItemDO> list)throws ManagerException ;
	
	/***
	 * 
	 * Created on 2011-8-31
	 * <p>Discription:优惠券后续处理:调用广告接口处理 </p>
	 * @param keys
	 * @param orderId
	 * @param orderItemId
	 * @param itemId
	 * @throws ManagerException
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void couponAfterProcess(OrderDO orderDO) throws ManagerException;
}

