package com.yuwang.pinju.core.order.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.OrderTradeAdDO;

/**
 * Created on 2011-7-30
 * @see
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderCreationDAO {
	/**
	 * @see 插入主订单记录
	 * @param order
	 *            要插入的父订单实体
	 * @return 返回生成记录的主键
	 * @throws DaoException
	 * @author ducheng
	 */
	public Long insertOrder(OrderDO orderDO) throws DaoException;

	/**
	 * @see
	 * <p>插入子订单记录</p>
	 * @return 返回生成记录的主键
	 * @throws DaoException
	 * @author ducheng
	 */
	public Long insertOrderItem(OrderItemDO orderItemDO)throws DaoException;
	
	
	/**
	 * 插入订单物流记录
	 * @param orderLogisticsDO
	 * @return
	 * @throws DaoException
	 */
	public Long insertOrderLogistics(OrderLogisticsDO orderLogisticsDO)throws DaoException;
	
	/**
	 * 
	 * Created on 2011-8-1
	 * <p>Discription: 分销中间表插入</p>
	 * @param orderChannelDO
	 * @return
	 * @throws DaoException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long insertOrderChannelDO(OrderChannelDO orderChannelDO)throws DaoException;
	
	/**
	 * @Description: 向订单广告表中插入数据
	 * @author [贺泳]
	 * @date 2011-9-8 上午09:00:09
	 * @version 1.0
	 * @param orderTradeAdDO: 订单广告对象
	 * @return
	 * @throws DaoException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long insertTradeAd(OrderTradeAdDO orderTradeAdDO)throws DaoException;
}

