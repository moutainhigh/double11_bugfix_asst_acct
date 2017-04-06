package com.yuwang.pinju.core.order.manager;


import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;


/**
 * 操作数据接口,实现针对订单实体的业务方法
 * @author 华致书
 * @date   2011-6-2
 * @version 1.0
 */
public interface OrderBusinessManager {
	
	/**
	 * 根据订单ID查询物流信息
	 * @retrun OrderLogisticsDO
	 * @author lixin
	 */
	public OrderLogisticsDO queryOrderLogisticsByOrderId(Long orderId) throws ManagerException;
		
	/**
	 * 延长发贷时间
	 * @return 成功 true
	 */
	public boolean updateRecevingDate(final Long orderID,final Integer day) throws ManagerException;



	
	
	
}
