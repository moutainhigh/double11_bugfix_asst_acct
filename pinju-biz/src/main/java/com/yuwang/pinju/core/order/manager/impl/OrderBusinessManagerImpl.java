package com.yuwang.pinju.core.order.manager.impl;


import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.core.order.dao.OrderUpDdateDAO;
import com.yuwang.pinju.core.order.manager.OrderBusinessManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;


public class OrderBusinessManagerImpl extends BaseManager implements OrderBusinessManager {
	
	private OrderQueryDAO orderQueryDAO;

	
	private OrderUpDdateDAO orderUpDdateDAO;
	
	
	/**
	 * 根据订单ID查询物流信息
	 * @retrun OrderLogisticsDO
	 * @author lixin
	 */
	@Override
	public OrderLogisticsDO queryOrderLogisticsByOrderId(Long orderId)
			throws ManagerException {
		try {
			return orderQueryDAO.queryOrderLogisticsByOrderId(orderId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[OrderBusinessManagerImpl#queryOrderLogisticsByOrderId] 查询订单物流信息错误:" +  e);
		}
	}



	/**
	 * 延长发贷时间
	 * @return 成功 true   加事务
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean updateRecevingDate(final Long orderID, final Integer day) throws ManagerException{
		boolean flag = false;
		try {
			flag = (Boolean) getZizuTransactionTemplate().execute(
					new TransactionCallback() {
						@Override
						public Object doInTransaction(TransactionStatus arg0) {
							boolean flag = false;
							
							try {
//								List<OrderItemDO> orderItemList = orderQueryDAO.queryOrderItemList(orderID);
//								for(OrderItemDO orderItemDO : orderItemList){
//									if(orderItemDO.getConfirmTime() != null){
//										orderItemDO.setConfirmTime(orderItemDO.getConfirmTime()+day);
//									}
//									else {
//										orderItemDO.setConfirmTime(day);
//									}
//									orderItemDO.setOrderId(null);
//									flag = orderUpDdateDAO.upOrderItem(orderItemDO) > 0;
//									if(! flag) break;
//								}
								
								OrderDO orderDO = orderQueryDAO.queryOrder(orderID);
								//orderDO.setOrderId(orderID);
								if(!EmptyUtil.isBlank(orderDO.getResidueTimeHour())){
									orderDO.setResidueTimeHour(orderDO.getResidueTimeHour()+day*24);
								}else {
									orderDO.setResidueTimeHour(day*24);
								}
								flag = orderUpDdateDAO.updateOrder(orderDO) > 0;
								
								//出错回滚
								if(! flag)
									throw new DaoException();
								
							} catch (Exception e) {
								arg0.setRollbackOnly();
								throw new RuntimeException(e);
							}
							return flag;
						}
					});
		}catch (Exception e) {
			throw new ManagerException(
					"Event=[OrderBusinessManagerImpl#updateRecevingDate] 延长买家收货时间出错 ", e);
		}
		return flag;		
	}


	


	

	public void setOrderQueryDAO(OrderQueryDAO orderQueryDAO) {
		this.orderQueryDAO = orderQueryDAO;
	}



	public void setOrderUpDdateDAO(OrderUpDdateDAO orderUpDdateDAO) {
		this.orderUpDdateDAO = orderUpDdateDAO;
	}




}
