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
public interface OrderDiscountManager {
	/**
	 * 
	 * Created on 2011-8-31
	 * <p>Discription: 处理特供</p>
	 * @param orderCreation
	 * @param orderDO
	 * @param list
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Map<OrderDO,List<OrderItemDO>> discountBeforeProcess(OrderCreationVO orderCreation,OrderDO orderDO,List<OrderItemDO> list) throws ManagerException;

	/***
	 * 
	 * Created on 2011-8-31
	 * <p>Discription:特供码后续处理:调用特供接口处理 </p>
	 * @param keys
	 * @param orderId
	 * @param orderItemId
	 * @param itemId
	 * @throws ManagerException
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void discountAfterProcess(String discountCode,OrderItemDO orderItemDO) throws ManagerException;
	
	/**
	 * 
	 * Created on 2011-9-19
	 * <p>Discription: [判断特供码是否被使用]</p>
	 * @param discountCode
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Result compareCodeIsNull(String discountCode);
}

