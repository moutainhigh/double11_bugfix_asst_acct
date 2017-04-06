package com.yuwang.pinju.core.order.dao.impl;

import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.order.dao.OrderUpDdateDAO;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;

/**
 * Created on 2011-7-30
 * <p>
 * Discription:
 * </p>
 * 
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderUpDdateDAOImpl extends BaseDAO implements OrderUpDdateDAO {
	
	private final static String ORDER_ITEM_NAMESPACE = "TRADE_ORDER_ITEM.";

	private final static String ORDER_NAMESPACE = "TRADE_ORDER.";

	private final static String ORDER_LOGISTICS_NAMESPACE = "TRADE_ORDER_LOGISTICS.";

	private final static String CHANNEL_NAMESPACE = "TRADE_ORDER_CHANNEL.";
	
	@Override
	public int upOrderItem(OrderItemDO orderItemDO) throws DaoException {
		return super.executeUpdate(ORDER_ITEM_NAMESPACE + "updateOrderItem",
				orderItemDO);
	}

	@Override
	public int upOrderLogistics(OrderLogisticsDO orderLogisticsDO)
			throws DaoException {
		return super.executeUpdate(ORDER_LOGISTICS_NAMESPACE
				+ "updateOrderLogistics", orderLogisticsDO);
	}

	@Override
	public int updateOrder(OrderDO orderDO) throws DaoException {
		return super.executeUpdate(ORDER_NAMESPACE + "updateOrder", orderDO);
	}

	@Override
	public int updateOrderBuyerRate(OrderDO orderDO) throws DaoException{
		return super.executeUpdate(ORDER_NAMESPACE + "updateOrderBuyerRate", orderDO);
	}

	@Override
	public int upChannelAmount(OrderChannelDO orderChannelDO) throws DaoException{
		return super.executeUpdate(CHANNEL_NAMESPACE + "updateChannelAmount", orderChannelDO);
	}

	@Override
	public int updateOrderItemDealAmount(OrderItemDO orderItemDO) throws DaoException{
		return super.executeUpdate(ORDER_ITEM_NAMESPACE + "updateOrderItemDealAmount",orderItemDO);
	}

	@Override
	public int updateOrderConfirmTime(OrderDO orderDO) throws DaoException{
		return super.executeUpdate(ORDER_NAMESPACE + "updateOrderConfirmTime", orderDO);
	}

	@Override
	public int upRefundOrderItem(OrderItemDO orderItemDO) throws DaoException {
		return super.executeUpdate(ORDER_ITEM_NAMESPACE + "updateOrderItemRefund",
				orderItemDO);
	}

	@Override
	public int upAllOrderItemState(OrderItemDO orderItemDO) throws DaoException {
		return super.executeUpdate(ORDER_ITEM_NAMESPACE + "updateAllOrderItem",
				orderItemDO);
	}

	@Override
	public int updateOrderAttribute(Map<String, Long[]> map) throws DaoException{
		return super.executeUpdate(ORDER_NAMESPACE + "updateOrderAttributes", map);
	}

}
