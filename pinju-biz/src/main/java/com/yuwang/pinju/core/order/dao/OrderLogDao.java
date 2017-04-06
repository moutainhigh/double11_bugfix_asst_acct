package com.yuwang.pinju.core.order.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.order.OrderLogDO;

public interface OrderLogDao {
	/**
	 * 
	 * Created on 2011-8-19
	 * <p>Discription: 插入日志记录</p>
	 * @param logDO
	 * @return
	 * @throws DaoException
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long insertOrderLogDO(OrderLogDO logDO)throws DaoException;
}
