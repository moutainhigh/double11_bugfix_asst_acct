package com.yuwang.pinju.core.order.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.QueryDistributeOrder;
import com.yuwang.pinju.domain.order.query.QueryOrder;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.order.query.QueryVO;

/**
 * Created on 2011-7-30
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderQueryDAOImpl extends BaseDAO implements OrderQueryDAO {

	private final static String ORDER_ITEM_NAMESPACE = "TRADE_ORDER_ITEM.";
	
	private final static String ORDER_NAMESPACE = "TRADE_ORDER.";
	
	private final static String ORDER_LOGISTICS_NAMESPACE = "TRADE_ORDER_LOGISTICS.";
	
	private final static String ORDER_CHANNEL_NAMESPACE = "TRADE_ORDER_CHANNEL.";
	
	private final static String TIMINGTASKS_ORDER_NAMESPACE = "TIMINGTASKS_ORDER.";
	
	private ReadBaseDAO readBaseDAOForOracle ;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryOrder(QueryOrder queryOrder)
			throws DaoException {
		return super.executeQueryForList(ORDER_NAMESPACE + "queryOrder",queryOrder);
	}

	@Override
	public Long queryOrderNum(QueryOrder queryOrder) throws DaoException {
		return (Long) super.executeQueryForObject(ORDER_NAMESPACE + "queryOrderNum", queryOrder);
	}

	@Override
	public OrderDO queryOrder(Long orderId) throws DaoException {
		return (OrderDO) super.executeQueryForObject(ORDER_NAMESPACE + "queryOrderSelect",
				orderId);
	}
	
	@Override
	public OrderDO queryWriteOrder(Long orderId) throws DaoException {
		return (OrderDO) super.executeQueryForObject(ORDER_NAMESPACE + "queryOrderSelect",
				orderId);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItemDO> queryOrderItemList(Long orderId) throws DaoException {
		return  super.executeQueryForList(ORDER_ITEM_NAMESPACE + "queryOrderItem_1",orderId);
	}
	
	
	@Override
	public OrderItemDO queryOrderItemDO(Long orderItemId) throws DaoException {
		return (OrderItemDO) super.executeQueryForObject(ORDER_ITEM_NAMESPACE + "queryOrderItem",
				orderItemId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItemDO> queryOrderItemList(
			QueryOrderItem queryOrderItem) throws DaoException{
		return super.executeQueryForList(ORDER_ITEM_NAMESPACE + "queryOrderItemAll",queryOrderItem);
	}

	@Override
	public long queryOrderItemNum(QueryOrderItem queryOrderItem)
			throws DaoException {
		return (Long) super.executeQueryForObject(ORDER_ITEM_NAMESPACE + "queryOrderItemNum", queryOrderItem);
	}

	/**
	 * 根据订单ID查询物流信息
	 * @return OrderLogisticsDO
	 * @author lixin
	 */
	@Override
	public OrderLogisticsDO queryOrderLogisticsByOrderId(Long orderId)
			throws DaoException {
		return (OrderLogisticsDO) super.executeQueryForObject(ORDER_LOGISTICS_NAMESPACE+"selectOrderLogisticsDOByOrderId", orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderChannelDO> queryOrderChannelList(QueryDistributeOrder queryDistributeOrder) throws DaoException {
		return readBaseDAOForOracle.executeQueryForList(ORDER_CHANNEL_NAMESPACE + "queryOrderChannel",queryDistributeOrder);

	}

	

	@Override
	public long queryOrderChannelListNum(QueryDistributeOrder queryDistributeOrder)
			throws DaoException {
		return (Long) readBaseDAOForOracle.executeQueryForObject(ORDER_CHANNEL_NAMESPACE + "queryOrderChannelNum", queryDistributeOrder);
	}
	
	/**
	 * 查出最近三个月的分销量
	 * 
	 * @param queryDistributeOrder
	 * @return
	 * @throws DaoException
	 * @author caiwei
	 */
	@Override
	public long queryOrderChannelListNumDuringMonth(QueryDistributeOrder queryDistributeOrder)
	throws DaoException {
		return (Long) readBaseDAOForOracle.executeQueryForObject(ORDER_CHANNEL_NAMESPACE + "queryOrderChannelNumDuringMonth", queryDistributeOrder);
	}
	
	@Override
	public int queryOrderListBuyBussinessCount(Map<String, Object> map)
			throws DaoException {
		return (Integer) super.executeQueryForObject(ORDER_NAMESPACE+"selectOrderListCount-Time",map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryOrderListBuyBussiness(Map<String,Object> map)
			throws DaoException{
		return super.executeQueryForList(ORDER_NAMESPACE+"selectOrderList-Time",map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItemDO> queryOrderItemDOList(List<Long> orderItemIdList)
			throws DaoException {
		return super.executeQueryForList(ORDER_ITEM_NAMESPACE + "queryOrderItem_2",orderItemIdList);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryOrderItemListByItemTitle(
			Map<String, Object> map) throws DaoException {
		return readBaseDAOForOracle.executeQueryForList(ORDER_NAMESPACE + "queryOrderItemByItemTitle",map);
	}

	@Override
	public long queryOrderItemByItemTitleNum(Map<String, Object> map)
			throws DaoException {
		return (Long) readBaseDAOForOracle.executeQueryForObject(ORDER_NAMESPACE + "queryOrderItemByItemTitleNum", map);
	}

	@Override
	public OrderChannelDO queryOrderChannelByOrderItemId(long orderItemId)
			throws DaoException {
		return (OrderChannelDO) readBaseDAOForOracle.executeQueryForObject(ORDER_CHANNEL_NAMESPACE + "queryOrderChannelByOrderItemId", orderItemId);
	}

	@Override
	public long queryOrderItemBuyNum(QueryOrderItem queryOrderItem)
			throws DaoException {
		Object obj = readBaseDAOForOracle.executeQueryForObject(ORDER_ITEM_NAMESPACE + "queryOrderItemBuyNum", queryOrderItem);
		Long count = 0L;
		if(obj != null){
			count = (Long)obj;
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderItemDO> queryRateOrderItemDOList(Long orderId)
			throws DaoException {
		return readBaseDAOForOracle.executeQueryForList(ORDER_ITEM_NAMESPACE + "queryRateOrderItem",orderId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryOrderItemListByState(Map<String, Object> map)
			throws DaoException {
		return readBaseDAOForOracle.executeQueryForList(ORDER_NAMESPACE + "queryOrderItemByState",map);
	}

	@SuppressWarnings("unchecked")
	@Override 
	public List<OrderDO> queryOrderList(Map<String, Object> map)
			throws DaoException {
		return super.executeQueryForList(ORDER_NAMESPACE+"selectOrderListToImport",map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryOrderByBuyer(QueryVO queryVO) throws DaoException {
		return super.executeQueryForList(ORDER_NAMESPACE+"queryOrderVO",queryVO);
	}

	@Override
	public Long queryOrderNumByBuyer(QueryVO queryVO) throws DaoException {
		return (Long) super.executeQueryForObject(ORDER_NAMESPACE + "queryOrderVONum", queryVO);
	}
	
	@Override
	public int queryOrderConfirmNum(Map<String, Object> map) throws DaoException{
		return (Integer)readBaseDAOForOracle.executeQueryForObject(TIMINGTASKS_ORDER_NAMESPACE + "selectOrderConfirmNum", map);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> queryOrderConfirm(Map<String, Object> map) throws DaoException {
		return (List<Long>)readBaseDAOForOracle.executeQueryForList(TIMINGTASKS_ORDER_NAMESPACE + "selectOrderConfirm", map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDO> queryOrderListByOrderId(Map<String, Long[]> map) throws DaoException {
		return super.executeQueryForList(ORDER_NAMESPACE + "queryALlOrderSelect", map);
	}


	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}
	


	@Override
	public int queryOrderPriceAmount(Map<String, Object> map) throws DaoException {
		return (Integer) super.executeQueryForObject(ORDER_NAMESPACE + "queryOrderPriceAmount", map);
	}

}

