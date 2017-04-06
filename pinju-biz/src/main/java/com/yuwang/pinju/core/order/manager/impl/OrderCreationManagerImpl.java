package com.yuwang.pinju.core.order.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.order.dao.OrderCreationDAO;
import com.yuwang.pinju.core.order.manager.OrderCreationManager;
import com.yuwang.pinju.core.order.manager.OrderLogManager;
import com.yuwang.pinju.core.order.manager.helper.OrderCouponManager;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.OrderTradeAdDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * Created on 2011-7-30
 * 
 * @see <p>
 *      Discription:
 *      </p>
 * @returnOrderCreationDAO
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderCreationManagerImpl extends BaseManager implements
		OrderCreationManager {
	protected final Log PAYLOG = LogFactory.getLog("tenpay-pay");
	private OrderCreationDAO orderCreationDAO;

	private ItemManager itemManager;
	
	private VouchCreateManager vouchCreateManager;

	private OrderLogManager orderLogManager;

	private DataSourceTransactionManager zizuTransactionManager;

	private OrderCouponManager orderCouponManager;
	
	@Override
	public Map<Long,List<String>> creationOrder(final OrderDO orderDO, final List<OrderItemDO> orderItemList,
			final OrderLogisticsDO orderLogisticsDO,final VouchPayDO vouchPayDO) throws ManagerException {
		Map<Long,List<String>> map = new HashMap<Long,List<String>>();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		long orderId = 0;
		List<String> list = new ArrayList<String>();
		try {
	
			orderId = orderCreationDAO.insertOrder(orderDO);
			PAYLOG.debug("order create,调试订单记录神奇消失,生成订单ORDER_ID:".concat(String.valueOf(orderId)));
			orderDO.setOrderId(orderId);
			// 生成子订单
			if (orderId > 0) {
				for (OrderItemDO orderItemDO : 	orderItemList) {
					orderItemDO.setOrderId(orderId);
					String value = orderCreationDAO.insertOrderItem(orderItemDO).toString();
					OrderLogDO orderLogDO = OrderLogDO.createOrderLogDO(orderDO.getBuyerId(), orderDO.getBuyerId().toString(), "订单生成", orderDO.getBuyIp(), OrderConstant.OPERATE_LOG_CREATION, orderId, orderDO.getSellerId(),Long.valueOf(value));
					orderLogManager.createOrderLog(orderLogDO);
					orderItemDO.setOrderItemId(Long.valueOf(value));
					value=value.concat(":").concat(orderItemDO.getItemId().toString());
					list.add(value);
					boolean isCutStock = orderItemDO.getBussinessType().equals(OrderItemDO.ORDER_ITEM_TYPE_3)||orderItemDO.getBussinessType().equals(OrderItemDO.ORDER_ITEM_TYPE_4);
					if(isCutStock){
						itemManager.cutStock(orderItemDO.getItemId(), orderItemDO.getItemSkuId(), orderItemDO.getBuyNum());
					}
			
				}
			} else {
				throw new DaoException();
			}
			orderLogisticsDO.setOrderId(orderId);
			//插入物流
			if(orderCreationDAO.insertOrderLogistics(orderLogisticsDO)<0){
				throw new DaoException();
			}
			vouchPayDO.setOrderId(orderId);
			if(!vouchCreateManager.insertTrderOrderPay(vouchPayDO)){
				throw new DaoException();
			}
			//优惠券后置处理
			orderCouponManager.couponAfterProcess(orderDO);
			zizuTransactionManager.commit(status);
			PAYLOG.debug("order create,调试订单记录神奇消失,订单记录事务已提交ORDER_ID:".concat(String.valueOf(orderId)));
			map.put(orderId, list);
		} catch (Exception e) {
			map = null;
			PAYLOG.debug("order create,调试订单记录神奇消失,订单记录事务开始回滚ORDER_ID:".concat(String.valueOf(orderId)));
			zizuTransactionManager.rollback(status);
			PAYLOG.debug("order create,调试订单记录神奇消失,订单记录事务已回滚ORDER_ID:".concat(String.valueOf(orderId)));
			throw new ManagerException(e);
		}
		return map;
	}


	@Override
	public boolean insertOrderChannelDO(OrderChannelDO orderChannelDO)throws ManagerException {
		try {
			return orderCreationDAO.insertOrderChannelDO(orderChannelDO)>0;
		} catch (DaoException e) {
			log.error("Event=[OrderUpDateManagerImpl#insertOrderChannelDO]", e);
			throw new ManagerException("Event=[OrderUpDateManagerImpl#insertOrderChannelDO]", e);
		}
	}
	
	@Override
	public boolean insertTradeAd(OrderTradeAdDO orderTradeAdDO)throws ManagerException {
		log.debug("execute insertTradeAd");
		try {
			return orderCreationDAO.insertTradeAd(orderTradeAdDO) > 0;
		} catch (DaoException e) {
			log.error("Event=[OrderCreationManagerImpl#insertTradeAd]", e);
			throw new ManagerException("Event=[OrderCreationManagerImpl#insertTradeAd]", e);
		}
	}

	public void setOrderCreationDAO(OrderCreationDAO orderCreationDAO) {
		this.orderCreationDAO = orderCreationDAO;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setVouchCreateManager(VouchCreateManager vouchCreateManager) {
		this.vouchCreateManager = vouchCreateManager;
	}

	public void setOrderLogManager(OrderLogManager orderLogManager) {
		this.orderLogManager = orderLogManager;
	}

	public void setZizuTransactionManager(
			DataSourceTransactionManager zizuTransactionManager) {
		this.zizuTransactionManager = zizuTransactionManager;
	}

	public void setOrderCouponManager(OrderCouponManager orderCouponManager) {
		this.orderCouponManager = orderCouponManager;
	}

	
	
}
