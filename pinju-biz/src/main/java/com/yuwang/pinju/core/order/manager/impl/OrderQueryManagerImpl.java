package com.yuwang.pinju.core.order.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.cache.ItemBuyNumCacheManager;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.QueryDistributeOrder;
import com.yuwang.pinju.domain.order.query.QueryOrder;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.order.query.QueryVO;

/**
 * Created on 2011-7-26
 * <p>Discription: 订单查询管理</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderQueryManagerImpl implements OrderQueryManager {

	private OrderQueryDAO orderQueryDAO;

	private ItemBuyNumCacheManager itemBuyNumCacheManager;
	
	@Override
	public OrderDO getOrderDOById(long orderId) throws ManagerException {
		try {
			return orderQueryDAO.queryOrder(orderId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getOrderDOById] 查询主订单详情错误:", e);
		}
	}

	@Override
	public OrderItemDO getOrderItemDOById(long orderItemId)
			throws ManagerException {
		try {
			return orderQueryDAO.queryOrderItemDO(orderItemId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getOrderItemDOById] 查询子订单详情错误:", e);
		}
	}

	@Override
	public List<OrderDO> getOrderItemListByItemTitle(Map<String, Object> map)
			throws ManagerException {
		try {
			return orderQueryDAO.queryOrderItemListByItemTitle(map);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getOrderItemListByItemTitle] :", e);
		}
	}

	@Override
	public int getOrderListInTimeCount(Map<String, Object> map)	throws ManagerException {
		try {
			return this.orderQueryDAO.queryOrderListBuyBussinessCount(map);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getOrderListInTimeCount] :", e);
		}
	}

	
	@Override
	public List<OrderDO> getOrderListInTime(Map<String, Object> map)
			throws ManagerException {
		try {
			return this.orderQueryDAO.queryOrderListBuyBussiness(map);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getOrderListInTime] :", e);
		}
	}

	
	@Override
	public Result queryDistributeOrderList(
			QueryDistributeOrder queryDistributeOrder) throws ManagerException {
		Result result = new ResultSupport();
		try {
			long num = orderQueryDAO.queryOrderChannelListNum(queryDistributeOrder);
			
			
			queryDistributeOrder.setItems(Long.valueOf(num).intValue());
			
			List<OrderChannelDO> list = orderQueryDAO.queryOrderChannelList(queryDistributeOrder);
			//三个月以内的分销订单
			queryDistributeOrder.setMonth(3);
			long selleredNum = orderQueryDAO.queryOrderChannelListNumDuringMonth(queryDistributeOrder);
			
			result.setModel("orderChannelList", list);
			
			result.setModel("num", num);
			result.setModel("selleredCount",selleredNum);
		} catch (DaoException e) {
			result.setSuccess(false);
			throw new ManagerException(
					"Event=[OrderQueryManagerImpl#queryDistributeOrderList] 分销订单分页查询总数错误:"
							+ e);
		}
		
		return result;
	}

	@Override
	public List<OrderDO> queryOrder(Long sellerId,String sellerNick, Integer[] orderState,
			Date beginDate, Date endDate, Integer isBuyerRate,
			Integer isSellerRate, Long buyerId, int currentPage, int rowPerPage)
			throws ManagerException {
		try {
			QueryOrder queryOrder = new QueryOrder();
			queryOrder.setSellerNick(sellerNick);
			queryOrder.setSellerId(sellerId);
			queryOrder.setOrderState(orderState);
			queryOrder.setGmtCreateStart(beginDate);
			queryOrder.setGmtCreateEnd(endDate);
			queryOrder.setIsSellerRate(isSellerRate);
			queryOrder.setIsBuyerRate(isBuyerRate);
			queryOrder.setBuyerId(buyerId);
			queryOrder.setPage(currentPage);
			queryOrder.setStartNum((currentPage-1)*rowPerPage+1);
			queryOrder.setEndNum(currentPage*rowPerPage);
			return orderQueryDAO.queryOrder(queryOrder);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	

	@Override
	public List<OrderItemDO> queryOrderItemDOList(
			QueryOrderItem queryOrderItemDO) throws ManagerException {
		try {
			return orderQueryDAO.queryOrderItemList(queryOrderItemDO);
		} catch (DaoException e) {
			throw new ManagerException(
					"Event=[OrderQueryManagerImpl#queryOrderItemDOList] 子订单分页查询错误:"
							+ e);
		}
	}

	@Override
	public long queryOrderItemDONum(QueryOrderItem queryOrderItem)
			throws ManagerException {
		try {
			return orderQueryDAO.queryOrderItemNum(queryOrderItem);
		} catch (DaoException e) {
			throw new ManagerException(
					"Event=[OrderQueryManagerImpl#queryOrderItemDONum] 子订单分页查询总数错误:"
							+ e);
		}
	}
	

	public List<OrderItemDO> queryOrderItemList(Long orderId)
			throws ManagerException {
		try {
			return orderQueryDAO.queryOrderItemList(orderId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Map<OrderDO, List<OrderItemDO>> queryOrderList(Long orderId)
			throws ManagerException {
		try {
			Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
			map.put(orderQueryDAO.queryOrder(orderId), this.queryOrderItemList(orderId));
			return map;
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public Long queryOrderNum(Long sellerId,String sellerNick, Integer[] orderState,
			Date beginDate, Date endDate, Integer isBuyerRate,
			Integer isSellerRate, Long buyerId) throws ManagerException {
		try {
			QueryOrder queryOrder = new QueryOrder();
			queryOrder.setSellerNick(sellerNick);
			queryOrder.setSellerId(sellerId);
			queryOrder.setOrderState(orderState);
			queryOrder.setGmtCreateStart(beginDate);
			queryOrder.setGmtCreateEnd(endDate);
			queryOrder.setIsSellerRate(isSellerRate);
			queryOrder.setIsBuyerRate(isBuyerRate);
			queryOrder.setBuyerId(buyerId);
			return orderQueryDAO.queryOrderNum(queryOrder);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	public void setOrderQueryDAO(OrderQueryDAO orderQueryDAO) {
		this.orderQueryDAO = orderQueryDAO;
	}


	@Override
	public List<OrderItemDO> queryOrderItemList(List<Long> orderItemIdList)throws ManagerException {
		try {
			return orderQueryDAO.queryOrderItemDOList(orderItemIdList);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#queryOrderItemList] 查询子订单列表错误:", e);
		}
	}

	@Override
	public long getOrderItemListByItemTitleNum(Map<String, Object> map)
			throws ManagerException {
		try {
			return orderQueryDAO.queryOrderItemByItemTitleNum(map);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getOrderItemListByItemTitleNum] 查询子订单数据记录错误:", e);
		}
	}

	@Override
	public OrderChannelDO queryOrderChannelByOrderItemId(long orderItemId)
			throws ManagerException {
		try {
			return orderQueryDAO.queryOrderChannelByOrderItemId(orderItemId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#queryOrderChannelByOrderItemId] 查询分销商表错误:", e);
		}
	}

	@Override
	public long queryOrderItemBuyNum(QueryOrderItem queryOrderItem)
			throws ManagerException {
			return itemBuyNumCacheManager.queryCacheItemBuyNum(queryOrderItem);
	}

	@Override
	public Map<OrderDO, List<OrderItemDO>> queryRateOrderList(Long orderId)
			throws ManagerException {
		try {
			Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
			map.put(orderQueryDAO.queryOrder(orderId), orderQueryDAO.queryRateOrderItemDOList(orderId));
			return map;
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<OrderDO> queryOrderItemListByState(Map<String, Object> map)
			throws ManagerException {
		try {
			return orderQueryDAO.queryOrderItemListByState(map);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#queryOrderItemListByState] :", e);
		}
	}

	@Override
	public List<OrderDO> getOrderList(Map<String, Object> map)
			throws ManagerException {
		try {
			return this.orderQueryDAO.queryOrderList(map);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getOrderList] :", e);
		}
	}

	@Override
	public List<OrderDO> queryOrderByBuyer(QueryVO queryVO) throws ManagerException {
		try {
			return orderQueryDAO.queryOrderByBuyer(queryVO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getQueryVO] :", e);
		}
	}

	@Override
	public Long queryOrderNumByBuyer(QueryVO queryVO) throws ManagerException {
		try {
			return orderQueryDAO.queryOrderNumByBuyer(queryVO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#getQueryVONum] :", e);
		}
	}

	@Override
	public List<Long> queryOrderConfirm(int startNum,int endNum ) throws ManagerException {
		Map<String, Object> conMap = setOrderConfirmQueryMap();
		conMap.put("startNum", startNum);
		conMap.put("endNum", endNum);
		try {
			return orderQueryDAO.queryOrderConfirm(conMap);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#queryOrderConfirm] :", e);
		}
	}

	@Override
	public int queryOrderConfirmNum() throws ManagerException {
		try {
			return 	orderQueryDAO.queryOrderConfirmNum(setOrderConfirmQueryMap());
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#queryOrderConfirmNum] :", e);
		}
	}
	
	@Override
	public List<OrderDO> queryOrderListByOrderId(Long[] orderId) throws ManagerException {
		try {
			Map<String, Long[]> map = new HashMap<String, Long[]>();
			map.put("orderId", orderId);
			return orderQueryDAO.queryOrderListByOrderId(map);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#queryOrderListByOrderId] :", e);
		}
	}

	/**
	 * Created on 2011-11-2
	 * <p>Discription: 确认收货条件</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Map<String, Object> setOrderConfirmQueryMap() {
		Map<String, Object> conMap = new HashMap<String, Object>();
		conMap.put("orderState", OrderDO.ORDER_STATE_3);
		conMap.put("refundState", OrderConstant.IS_REFUND_NO);
		return conMap;
	}
	
	@Override
	public int queryOrderPriceAmount(Map<String, Object> map) throws ManagerException {
		try {
			return this.orderQueryDAO.queryOrderPriceAmount(map);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderQueryManagerImpl#queryOrderPriceAmount] :", e);
		}
	}

	public void setItemBuyNumCacheManager(
			ItemBuyNumCacheManager itemBuyNumCacheManager) {
		this.itemBuyNumCacheManager = itemBuyNumCacheManager;
	}
	
}
