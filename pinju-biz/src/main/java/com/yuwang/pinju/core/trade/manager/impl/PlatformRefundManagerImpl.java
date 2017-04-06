package com.yuwang.pinju.core.trade.manager.impl;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.order.dao.OrderQueryDAO;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.dao.RefundDAO;
import com.yuwang.pinju.core.refund.dao.TradeRefundManualQueryDAO;
import com.yuwang.pinju.core.refund.dao.TradeRefundManualUpDateDAO;
import com.yuwang.pinju.core.trade.dao.RefundLogDAO;
import com.yuwang.pinju.core.trade.manager.PlatformRefundManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;

/**
 * <p>Description: 平台退款处理业务类 </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-15
 */
public class PlatformRefundManagerImpl extends BaseManager implements PlatformRefundManager {

	private DataSourceTransactionManager zizuTransactionManager;
	private RefundLogDAO refundLogDAO;
	private RefundDAO refundDAO;
	private TradeRefundManualUpDateDAO refundManualUpDateDAO;
	private TradeRefundManualQueryDAO refundManualQueryDAO;
	private OrderQueryDAO orderQueryDAO;
	
	private OrderUpDateManager orderUpDateManager; 

	/**
	 * 全额或部分退款成功 处理业务 ->更新退款状态 退款成功->更新子订单状态 退款成功 ->插入订单修改日志->更新主订单状态
	 * 为交易关闭->更新退款日志状态 为成功
	 */
	@Override
	public boolean updateAllRefundNotifyForSuccess(Long orderId, boolean isAll,String successReason) throws ManagerException {
		// TODO Auto-generated method stub
		if (log.isInfoEnabled()) {
			log.info("execute PlatformRefundManagerImpl.updateAllRefundNotifyForSuccess begin");
		}
		boolean upflag = true;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			RefundLogDO refundLogDO = refundLogDAO.queryRefundLogByOrderId(orderId,Integer.valueOf(PinjuConstant.TENPAY_PLATFORM_REFUND_CMDNO));
			upflag = this.updateRefundAndOrder(orderId,RefundDO.STATUS_SUCCESS, isAll);
			if (!upflag) {
				zizuTransactionManager.rollback(status);
				return upflag;
			}
			// 更新退款日志状态
			if (refundLogDO != null) {
				if(refundLogDO.getRefundState().compareTo(RefundLogDO.REFUND_LOG_IS_FAIL)== 0){
					refundLogDO.setRefundState(RefundLogDO.REFUND_LOG_IS_SUCCESS);
					refundLogDO.setMemo("平台退款成功");
				}else if(refundLogDO.getRefundState().compareTo(RefundLogDO.REDUND_LOG_BY_HAND)== 0){
					refundLogDO.setRefundState(RefundLogDO.REFUND_LOG_IS_SUCCESS);
					refundLogDO.setMemo("定时平台退款成功");
				}
				upflag = refundLogDAO.updateTradeRefundLog(refundLogDO) == 1;
				if (!upflag) {
					zizuTransactionManager.rollback(status);
					return upflag;
				}
			}
			// 更新手工单状态
			TradeRefundManualDO manualDO = refundManualQueryDAO.loadRefundManualByOrderId(orderId);
			if (manualDO != null) {
				manualDO.setRefundState(TradeRefundManualDO.STATUS_PAYBACK_YES);
				manualDO.setGmtModified(new Date());
				manualDO.setMemo(successReason);
				upflag = refundManualUpDateDAO.updateRefundManual(manualDO) == 1;
				if (!upflag) {
					zizuTransactionManager.rollback(status);
					return upflag;
				}
			}
			zizuTransactionManager.commit(status);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			zizuTransactionManager.rollback(status);
			upflag = false;
			throw new ManagerException("execute PlatformRefundManagerImpl.updateAllRefundNotifyForSuccess() faild:",e);
		} catch (Exception e) {
			zizuTransactionManager.rollback(status);
			upflag = false;
			throw new ManagerException("execute PlatformRefundManagerImpl.updateAllRefundNotifyForSuccess faild:",e);
		}
		return upflag;
	}

	/**
	 * 
	 * <p>Description: 更新退款状态和订单</p>
	 * @param orderId
	 * @param orderRefundState
	 * @param isAll
	 * @return
	 * @throws DaoException
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-12
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean updateRefundAndOrder(Long orderId, int orderRefundState,
			boolean isAll) throws DaoException, ManagerException {
		boolean upflag = true;
		OrderDO orderDO = orderQueryDAO.queryOrder(orderId);
		if(orderDO == null) return false;
			// 根据订单号 获取所有的退款
		List<RefundDO> refundDOList = refundDAO.queryRefundByOrderId(orderDO.getOrderId());
		if (refundDOList != null && refundDOList.size() > 0) {
			for (RefundDO refundDo : refundDOList) {
				if(refundDo.getRefundState().compareTo(RefundDO.STATUS_CLOSED) != 0){
						/*if(isAll){
							OrderItemDO orderItem = orderQueryDAO.queryOrderItemDO(refundDo.getOrderItemId());
							Long totalPrice = orderItem.getOrderItemPrice()*orderItem.getBuyNum();
							if(refundDo.getApplySum().compareTo(totalPrice) == 0){
								upflag = orderUpDateManager.orderItemClose(orderItem.getOrderItemId(), "子订单全额退款关闭");
								if(!upflag) return upflag;
							}
						}*/
					upflag = orderUpDateManager.upRefundState(refundDo.getOrderItemId(), orderRefundState);
					refundDo.setRefundState(orderRefundState);
					int upRef = refundDAO.updateRefundDO(refundDo);
					if (!upflag || upRef != 1) {
						return false;
					}
				}
			}
		}
			
		if(isAll){
			upflag = orderUpDateManager.orderClose(orderId, "全额退款关闭订单");
		}
		return upflag;
	}
	/**
	 * 全额或部分退款失败 处理业务 ->更新退款状态 退款成功->更新子订单状态 退款成功 ->插入订单修改日志->更新主订单状态
	 * 为交易关闭->更新退款日志状态 为成功
	 */
	@Override
	public boolean updateAllRefundNotifyForManual(Long orderId,TradeRefundManualDO manualDO, boolean isAll)
			throws ManagerException {
		if (log.isInfoEnabled()) {
			log.info("execute PlatformRefundManagerImpl.updateAllRefundNotifyForSuccess begin");
		}
		boolean upflag = true;
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		try {
			RefundLogDO refundLogDO = refundLogDAO.queryRefundLogByOrderId(orderId,Integer.valueOf(PinjuConstant.TENPAY_PLATFORM_REFUND_CMDNO));
			upflag = this.updateRefundAndOrder(orderId, RefundDO.STATUS_REFUND_FAIL, isAll);
			if (!upflag) {
				zizuTransactionManager.rollback(status);
				return upflag;
			}
			
			// 根据orderId查询工单  如果不存在就   插入手工单
			if(manualDO.getId() == null){
				Long manualId = refundManualUpDateDAO.saveRefundManual(manualDO);
				// 设置退款日志状态
				refundLogDO.setRefundState(RefundLogDO.REDUND_LOG_BY_HAND);
				refundLogDO.setMemo("平台退款失败,插入手工单："+manualId);
				// 如果财付通返回成功 更新退款日志表为成手工插单
				upflag = refundLogDAO.updateTradeRefundLog(refundLogDO) == 1;
				if (manualId < 0 || !upflag) {
					zizuTransactionManager.rollback(status);
					upflag = false;
					return upflag;
				}
			}
			zizuTransactionManager.commit(status);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			zizuTransactionManager.rollback(status);
			upflag = false;
			throw new ManagerException("execute PlatformRefundManagerImpl.updateAllRefundNotifyForManual faild:",e);
		} catch (Exception e) {
			zizuTransactionManager.rollback(status);
			upflag = false;
			throw new ManagerException("execute PlatformRefundManagerImpl.updateAllRefundNotifyForManual faild",e);
		}
		return upflag;
	}

	public void setZizuTransactionManager(
			DataSourceTransactionManager zizuTransactionManager) {
		this.zizuTransactionManager = zizuTransactionManager;
	}

	public void setRefundLogDAO(RefundLogDAO refundLogDAO) {
		this.refundLogDAO = refundLogDAO;
	}

	public void setRefundDAO(RefundDAO refundDAO) {
		this.refundDAO = refundDAO;
	}
	
	public void setRefundManualUpDateDAO(
			TradeRefundManualUpDateDAO refundManualUpDateDAO) {
		this.refundManualUpDateDAO = refundManualUpDateDAO;
	}

	public void setRefundManualQueryDAO(
			TradeRefundManualQueryDAO refundManualQueryDAO) {
		this.refundManualQueryDAO = refundManualQueryDAO;
	}

	public void setOrderQueryDAO(OrderQueryDAO orderQueryDAO) {
		this.orderQueryDAO = orderQueryDAO;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}
}