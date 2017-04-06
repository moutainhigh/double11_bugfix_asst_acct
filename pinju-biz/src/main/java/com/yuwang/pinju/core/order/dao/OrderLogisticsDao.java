package com.yuwang.pinju.core.order.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;


public interface OrderLogisticsDao {

	/**
	 * 查询订单物流信息
	 * @return OrderLogisticsDO
	 */
	public OrderLogisticsDO queryOrderLogisticsById(Long orderLogisticsId) throws DaoException;
	
	
	/**
	 * 插入订单物流记录
	 * @param orderLogisticsDO
	 * @return
	 * @throws DaoException
	 */
	public Long insertOrderLogistics(OrderLogisticsDO orderLogisticsDO)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-7-14
	 * @see
	 * <p>Discription:更新订单物流记录 </p>
	 * @param orderLogisticsDO
	 * @return 返回更新的记录数
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public int upOrderLogistics(OrderLogisticsDO orderLogisticsDO)throws DaoException;
}
