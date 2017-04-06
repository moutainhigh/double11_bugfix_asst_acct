package com.yuwang.pinju.core.order.manager.helper;

import java.util.List;
import java.util.Map;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;

/**
 * Created on 2011-9-19
 * @see
 * <p>Discription: 拆单管理</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderSplitManager {
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>
	 * Discription: 数据拆单
	 * </p>
	 * 
	 * @param itemDOList
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Map<OrderDO, List<OrderItemDO>> dismantleOrder(
			OrderCreationVO orderCreation, List<ItemDO> itemDOList) throws ManagerException;
}

