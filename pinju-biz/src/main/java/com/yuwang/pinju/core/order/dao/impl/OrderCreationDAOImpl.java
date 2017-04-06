package com.yuwang.pinju.core.order.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.order.dao.OrderCreationDAO;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.OrderTradeAdDO;

/**
 * Created on 2011-7-30
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderCreationDAOImpl extends BaseDAO implements OrderCreationDAO {
	private final static String ORDER_ITEM_NAMESPACE = "TRADE_ORDER_ITEM.";
	
	private final static String ORDER_NAMESPACE = "TRADE_ORDER.";
	
	private final static String ORDER_LOGISTICS_NAMESPACE = "TRADE_ORDER_LOGISTICS.";
	
	private final static String ORDER_CHANNEL_NAMESPACE = "TRADE_ORDER_CHANNEL.";
	
	private final static String ORDER_TRADEAD_NAMESPACE = "TRADE_AD.";
	
	@Override
	public Long insertOrder(OrderDO orderDO) throws DaoException {
		return (Long) super.executeInsert(ORDER_NAMESPACE + "insertOrderDO", orderDO);
	}

	@Override
	public Long insertOrderItem(OrderItemDO orderItemDO) throws DaoException {
		return (Long) super.executeInsert(ORDER_ITEM_NAMESPACE + "insertOrderItemDO",orderItemDO);
	}

	@Override
	public Long insertOrderLogistics(OrderLogisticsDO orderLogisticsDO)
			throws DaoException {
		return (Long) super.executeInsert(ORDER_LOGISTICS_NAMESPACE + "insertOrderLogistics",orderLogisticsDO);
	}

	@Override
	public Long insertOrderChannelDO(OrderChannelDO orderChannelDO)
			throws DaoException {
		return (Long) super.executeInsert(ORDER_CHANNEL_NAMESPACE + "insertOrderChannelDO",orderChannelDO);
	}

	@Override
	public Long insertTradeAd(OrderTradeAdDO orderTradeAdDO)
			throws DaoException {
		return (Long) super.executeInsert(ORDER_TRADEAD_NAMESPACE + "insertTradeAd", orderTradeAdDO);
	}

}

