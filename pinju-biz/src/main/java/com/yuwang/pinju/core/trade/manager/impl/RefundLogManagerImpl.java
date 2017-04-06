package com.yuwang.pinju.core.trade.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.trade.dao.RefundLogDAO;
import com.yuwang.pinju.core.trade.manager.RefundLogManager;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;

/** <p>Discription: 退款日志 ManagerImpl	  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-16
 */
public class RefundLogManagerImpl extends BaseManager implements RefundLogManager{
	private RefundLogDAO refundLogDAO;
	
	@Override
	public boolean insertTradeRefundLog(RefundLogDO refundLogDO)
			throws ManagerException {
		Long code=0L;
		try {
			 code=refundLogDAO.insertTradeRefundLog(refundLogDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			log.error("Event=[RefundLogManagerImpl#insertTradeRefundLog]", e);
			throw new ManagerException("Event=[RefundLogManagerImpl#insertTradeRefundLog]", e);
		}
		return code.compareTo(1L)==0 ? true : false;
	}

	@Override
	public boolean updateTradeRefundLog(RefundLogDO refundLogDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		Long code=0L;
		try {
			code=refundLogDAO.updateTradeRefundLog(refundLogDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			log.error("Event=[RefundLogManagerImpl#updateTradeRefundLog]", e);
			throw new ManagerException("Event=[RefundLogManagerImpl#updateTradeRefundLog]", e);
		}
		return code.compareTo(1L)==0 ? true : false;
	}

	@Override
	public RefundLogDO queryRefundLogById(Long logId) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return refundLogDAO.queryRefundLogById(logId);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			log.error("Event=[RefundLogManagerImpl#queryRefundLogById]", e);
			throw new ManagerException("Event=[RefundLogManagerImpl#queryRefundLogById]", e);
		}
	}

	@Override
	public RefundLogDO queryRefundLogByRefundAndCmdnoId(String refundId,
			Integer cmdno) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return refundLogDAO.queryRefundLogByRefundAndCmdnoId(refundId, cmdno);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			log.error("Event=[RefundLogManagerImpl#queryRefundLogByRefundAndCmdnoId]", e);
			throw new ManagerException("Event=[RefundLogManagerImpl#queryRefundLogByRefundAndCmdnoId]", e);
		}
	}
	
	@Override
	public RefundLogDO queryRefundLog(String transactionId, String payOrderId,
			Integer cmdno) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return refundLogDAO.queryRefundLog(transactionId, payOrderId, cmdno);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			log.error("Event=[RefundLogManagerImpl#queryRefundLog]", e);
			throw new ManagerException("Event=[RefundLogManagerImpl#queryRefundLog]", e);
		}
	}
	
	public RefundLogDAO getRefundLogDAO() {
		return refundLogDAO;
	}

	public void setRefundLogDAO(RefundLogDAO refundLogDAO) {
		this.refundLogDAO = refundLogDAO;
	}

	
}


