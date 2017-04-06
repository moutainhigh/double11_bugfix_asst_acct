package com.yuwang.pinju.core.coupon.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.dao.TradeCouponBatchDAO;
import com.yuwang.pinju.core.coupon.manager.TradeCouponBatchManager;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchVO;

public class TradeCouponBatchManagerImpl extends BaseManager implements TradeCouponBatchManager{

	private TradeCouponBatchDAO tradeCouponBatchDAO;
	
	private DataSourceTransactionManager zizuTransactionManager;

	@Override
	public long addTradeCouponBatch(TradeCouponBatchDO tcb) throws ManagerException {
		try {
			return tradeCouponBatchDAO.addTradeCouponBatch(tcb);
		} catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#addTradeCouponBatch]增加优惠券批次:", e);
		}
	}

	@Override
	public TradeCouponBatchDO getTradeCouponBatchById(long batchId) throws ManagerException {
		try {
			return tradeCouponBatchDAO.getTradeCouponBatchById(batchId);
		} catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#getTradeCouponBatchById]根据优惠券批次id查询:", e);
		}
	}

	@Override
	public long updateTradeCouponBatchById(TradeCouponBatchDO tcb) throws ManagerException {
		long l = 0L;
		try {
			l = tradeCouponBatchDAO.updateTradeCouponBatchById(tcb);
		} catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#updateTradeCouponBatchById]更新优惠券批次信息:", e);
		}
		return l;
	}

	@Override
	public List<TradeCouponBatchDO> queryTradeCouponBatchAll(TradeCouponBatchDO tcb) throws ManagerException {
		List<TradeCouponBatchDO> list = new ArrayList<TradeCouponBatchDO>();
		try {
			list = tradeCouponBatchDAO.queryTradeCouponBatchAll(tcb);
		} catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#queryTradeCouponBatchAll]查询所有优惠券批次:", e);
		}
		return list;
	}

	@Override
	public long queryTradeCouponBatchAllCount(TradeCouponBatchDO tcb) throws ManagerException {
		long l = 0;
		try {
			 l = tradeCouponBatchDAO.queryTradeCouponBatchAllCount(tcb);
		} catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#queryTradeCouponBatchAllCount]查询所有优惠券批次总数:", e);
		}
		return l;
	}

	@Override
	public long updateTradeCouponBatchByInvalid(TradeCouponBatchDO tcb) throws ManagerException {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		TransactionStatus status = zizuTransactionManager.getTransaction(def);
		long l = 0;
		try {
			l = tradeCouponBatchDAO.updateTradeCouponBatchByInvalid(tcb);
			zizuTransactionManager.commit(status);
		} catch (DaoException e) {
			zizuTransactionManager.rollback(status);
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#updateTradeCouponBatchByInvalid]更新所有失效优惠券批次:", e);
		}
		
		return l;
	}
	
	public void setTradeCouponBatchDAO(TradeCouponBatchDAO tradeCouponBatchDAO) {
		this.tradeCouponBatchDAO = tradeCouponBatchDAO;
	}

	public DataSourceTransactionManager getZizuTransactionManager() {
		return zizuTransactionManager;
	}

	public void setZizuTransactionManager(
			DataSourceTransactionManager zizuTransactionManager) {
		this.zizuTransactionManager = zizuTransactionManager;
	}

	@Override
	public List<TradeCouponBatchVO> getCouponBatchVO(
			TradeCouponBatchDO couponBatchDO) throws ManagerException {
		try {
			return tradeCouponBatchDAO.getCouponBatchVO(couponBatchDO);
		} catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#getCouponBatchVO]卖家统计优惠券:", e);
		}
	}

	@Override
	public int getCouponBatchVONum(TradeCouponBatchDO couponBatchDO)
			throws ManagerException {
		try {
			return tradeCouponBatchDAO.getCouponBatchVONum(couponBatchDO);
		} catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#getCouponBatchVO]卖家统计优惠券数量:", e);
		}
	}

	@Override
	public List<TradeCouponBatchDO> queryTradeCouponBatchByTop(long sellerId) throws ManagerException {
		List<TradeCouponBatchDO> list = new ArrayList<TradeCouponBatchDO>();
		TradeCouponBatchDO tcb = new TradeCouponBatchDO();
		tcb.setSellerId(sellerId);
		tcb.setBatchStatus(TradeCouponBatchDO.BATCHSTATUS_NORMAL);
		tcb.setItemsPerPage(5);
		tcb.setEndRow(5);
		
		try {
			list = tradeCouponBatchDAO.queryTradeCouponBatchByTop(tcb);
		} catch (DaoException e) {
			log.error(e);
			throw new ManagerException("Event=[TradeCouponBatchManagerImpl#queryTradeCouponBatchAll]查询优惠券批次前5条根据创建时间倒序:", e);
		}
		return list;
	}
}
