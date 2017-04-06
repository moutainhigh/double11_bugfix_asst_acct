package com.yuwang.pinju.core.trade.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.trade.dao.VouchCreateDAO;
import com.yuwang.pinju.core.trade.manager.VouchCreateManager;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * @Discription: 担保交易日志实现类
 * @Project: pinju-biz
 * @Package: com.yuwang.pinju.core.trade.manager.impl
 * @Title: VouchCreateManagerImpl.java
 * @author: [贺泳]
 * @date 2011-9-15 上午11:23:13
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public class VouchCreateManagerImpl extends BaseManager implements VouchCreateManager{
	private VouchCreateDAO vouchCreateDAO;

	@Override
	public boolean insertTradePaySendLog(PaySendLogDO paySendLogDO)throws ManagerException {
		log.debug("execute insertTradePaySendLog");
		try {
			return vouchCreateDAO.insertTradePaySendLog(paySendLogDO) > 0;
		} catch (DaoException e) {
			log.error("Event=[VouchCreateManagerImpl#insertTradePaySendLog]", e);
			throw new ManagerException("Event=[VouchCreateManagerImpl#insertTradePaySendLog]", e);
		}
	}

	@Override
	public boolean updateTradePaySendLog(PaySendLogDO paySendLogDO)throws ManagerException {
		log.debug("execute insertTradePaySendLog");
		try {
			return vouchCreateDAO.updateTradePaySendLog(paySendLogDO) > 0;
		} catch (DaoException e) {
			log.error("Event=[VouchCreateManagerImpl#updateTradePaySendLog]", e);
			throw new ManagerException("Event=[VouchCreateManagerImpl#updateTradePaySendLog]", e);
		}
	}

	@Override
	public boolean insertTradePayBackLog(PayBackLogDO payBackLogDO)throws ManagerException {
		log.debug("execute insertTradePayBackLog");
		try {
			return vouchCreateDAO.insertTradePayBackLog(payBackLogDO) > 0;
		} catch (DaoException e) {
			log.error("Event=[VouchCreateManagerImpl#insertTradePayBackLog]", e);
			throw new ManagerException("Event=[VouchCreateManagerImpl#insertTradePayBackLog]", e);
		}
	}

	@Override
	public boolean updateTradePayBackLog(PayBackLogDO payBackLogDO)throws ManagerException {
		log.debug("execute updateTradePayBackLog");
		try {
			return vouchCreateDAO.updateTradePayBackLog(payBackLogDO) > 0;
		} catch (DaoException e) {
			log.error("Event=[VouchCreateManagerImpl#updateTradePayBackLog]", e);
			throw new ManagerException("Event=[VouchCreateManagerImpl#updateTradePayBackLog]", e);
		}
	}
	
	@Override
	public boolean insertTrderOrderPay(VouchPayDO vouchPayDO)throws ManagerException {
		log.debug("execute insertTrderOrderPay");
		try {
			return vouchCreateDAO.insertTrderOrderPay(vouchPayDO) > 0;
		} catch (DaoException e) {
			log.error("Event=[VouchCreateManagerImpl#insertTrderOrderPay]", e);
			throw new ManagerException("Event=[VouchCreateManagerImpl#insertTrderOrderPay]", e);
		}
	}
	
	@Override
	public boolean updateOrderPay(VouchPayDO vouchPayDO) throws ManagerException {
		log.debug("execute updateOrderPay");
		try {
			return vouchCreateDAO.updateOrderPay(vouchPayDO) > 0;
		} catch (DaoException e) {
			log.error("Event=[VouchCreateManagerImpl#updateOrderPay]", e);
			throw new ManagerException("Event=[VouchCreateManagerImpl#updateOrderPay]", e);
		}
	}
	
	public void setVouchCreateDAO(VouchCreateDAO vouchCreateDAO) {
		this.vouchCreateDAO = vouchCreateDAO;
	}

}
