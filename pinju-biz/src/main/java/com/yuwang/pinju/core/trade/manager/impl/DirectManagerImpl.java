package com.yuwang.pinju.core.trade.manager.impl;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.trade.DirectPayConstant;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.trade.dao.DirectDAO;
import com.yuwang.pinju.core.trade.manager.DirectManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.trade.DirectOrderDO;
import com.yuwang.pinju.domain.trade.DirectPayDO;
import com.yuwang.pinju.domain.trade.DirectPayRevLogDO;
import com.yuwang.pinju.domain.trade.DirectPaySendLogDO;

public class DirectManagerImpl extends BaseManager implements DirectManager {
	
    private DirectDAO directDAO;
    
	private DataSourceTransactionManager zizuTransactionManager;
	
	private OrderUpDateManager orderUpDateManager;
	
	@Override
	public void insertDirectOrderRecord(DirectOrderDO directOrderDO,DirectPayDO directPayDO,DirectPaySendLogDO marginSendLogDO)throws ManagerException {
		log.debug("execute insertDirectOrderRecord");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			directOrderDO.setOrderState(DirectPayConstant.DIRECTPAY_ORDER_STATUS_UNPAID);
			//插入即时到帐订单记录
			directDAO.insertDirectOrderRecord(directOrderDO);
			//插支付表
			directPayDO.setPayState(DirectPayConstant.DIRECTPAY_PAY_STATUS_UNPAID);
			directDAO.insertDirectPayRecord(directPayDO);
			//插发送日志
			directDAO.insertDirectPaySendLogRecord(marginSendLogDO);
			//事务管理（注：有一个插入失败就回滚）
			zizuTransactionManager.commit(status);
		} catch (DaoException e) {
			zizuTransactionManager.rollback(status);
			throw new ManagerException("execute insertDirectOrderRecord faild:"+e);
		}
	}
	
	
	@Override
	public void insertDirectOrderRecord(DirectPayDO directPayDO,DirectPaySendLogDO directSendLogDO)throws ManagerException {
		log.debug("execute insertDirectOrderRecord");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			//插支付表
			directPayDO.setPayState(DirectPayConstant.DIRECTPAY_PAY_STATUS_UNPAID);
			directDAO.insertDirectPayRecord(directPayDO);
			//插发送日志
			directDAO.insertDirectPaySendLogRecord(directSendLogDO);
			//事务管理（注：有一个插入失败就回滚）
			zizuTransactionManager.commit(status);
		} catch (DaoException e) {
			zizuTransactionManager.rollback(status);
			throw new ManagerException("execute insertDirectOrderRecord faild:"+e);
		}
	}
	
	
	
	@Override
	public boolean updateDirectReceiveOrderRecord(DirectOrderDO directOrderDO,DirectPayDO directPayDO,DirectPayRevLogDO directPayRevLogDO)throws ManagerException {
		log.debug("execute updateDirectReceiveOrderRecord");
		boolean upflag = false;
		//插接收日志
		insertDirectPayRevLog(directPayRevLogDO);
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			//更新业务订单状态
			int upOrder = directDAO.updateDirectOrderDOStatus(directOrderDO);
			//更新支付订单状态
			int upPay = directDAO.updateDirectPayDOStatus(directPayDO);
			if(upOrder!=1 ||upPay !=1){ //防业务重复操作,并且在sql语句中只更新state=101即等待支付的记录防止并发情况下的重复Add By ShiXing
				zizuTransactionManager.rollback(status);
				return false;
			}
			//事务管理（注：有一个操作失败就回滚）
			upflag  = true;
			zizuTransactionManager.commit(status);
		} catch (Exception e) {
			zizuTransactionManager.rollback(status);
			throw new ManagerException("execute updateDirectReceiveOrderRecord faild:",e);
		}
		return upflag;
		
	}
	
	@Override
	public boolean updateDirectReceiveOrderRecord(OrderDO orderDO,DirectPayDO directPayDO,DirectPayRevLogDO directPayRevLogDO)throws ManagerException {
		log.debug("execute updateDirectReceiveOrderRecord");
		boolean upflag = false;
		//插接收日志
		insertDirectPayRevLog(directPayRevLogDO);
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			//更新订单状态
			boolean upOrder = orderUpDateManager.upOrderState(orderDO.getBuyerId(), orderDO.getOrderId(), orderDO.getOrderState(), "买家已付款");
			//更新支付订单状态
			int upPay = directDAO.updateDirectPayDOStatus(directPayDO);
			if(!upOrder||upPay !=1){
				zizuTransactionManager.rollback(status);
				return false;
			}
			//事务管理（注：有一个操作失败就回滚）
			upflag  = true;
			zizuTransactionManager.commit(status);
		} catch (Exception e) {
			zizuTransactionManager.rollback(status);
			throw new ManagerException("execute updateDirectReceiveOrderRecord faild:",e);
		}
		return upflag;
		
	}
	
	@Override
	public void insertDirectPayRevLog(DirectPayRevLogDO directPayRevLogDO)
			throws ManagerException {
		try {
			//插接收日志
			directDAO.insertdirectPayRevLogRecord(directPayRevLogDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[DirectManagerImpl#insertDirectPayRevLog] 财付通即时到账插入日志失败:",e);
		}
	}

	@Override
	public DirectOrderDO getDirectOrderDOById(Long orderId) throws ManagerException {
		try {
			return directDAO.getDirectOrderDOById(orderId);
		} catch (DaoException e) {
			throw new ManagerException("execute getDirectOrderDOById faild:",e);
		}
	}
	
	@Override
	public DirectPayDO getDirectPayDOById(Long payOrderId) throws ManagerException {
		try {
			return directDAO.getDirectPayDOById(payOrderId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[DirectManagerImpl#getDirectPayDOById] 财付通即时到账支付信息查询失败:",e);
		}
	}
	
	
	@Override
	public Long getOrderId() throws ManagerException {
		try {
			return directDAO.getOrderId();
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public Long getPayOrderId() throws ManagerException {
		try {
			return directDAO.getPayOrderId();
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	public void setZizuTransactionManager(DataSourceTransactionManager transactionManager) {
		this.zizuTransactionManager = transactionManager;
	}

	public void setDirectDAO(DirectDAO directDAO) {
		this.directDAO = directDAO;
	}


	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}


	@Override
	public DirectPayDO getDirectPayDO(DirectPayDO directPayDO)
			throws ManagerException {
		try {
			return directDAO.getDirectPayDO(directPayDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[DirectManagerImpl#getDirectOrderDO] 财付通即时到账支付信息查询失败:",e);
		}
	}

	
}
