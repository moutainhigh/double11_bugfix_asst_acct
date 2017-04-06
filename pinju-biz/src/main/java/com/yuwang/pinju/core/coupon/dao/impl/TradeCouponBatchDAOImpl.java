package com.yuwang.pinju.core.coupon.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.coupon.dao.TradeCouponBatchDAO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchDO;
import com.yuwang.pinju.domain.coupon.TradeCouponBatchVO;

public class TradeCouponBatchDAOImpl extends BaseDAO implements TradeCouponBatchDAO {
	private final static String NAMESPACE = "trade_coupon_batch.";

	@Override
	public long addTradeCouponBatch(TradeCouponBatchDO tcb) throws DaoException {
		return (Long) super.executeInsert(NAMESPACE.concat("insertTradeCouponBatch"), tcb);
	}

	@Override
	public TradeCouponBatchDO getTradeCouponBatchById(long batchId) throws DaoException {
		return (TradeCouponBatchDO) super.executeQueryForObject(NAMESPACE.concat("selectTradeCouponBatchByid"), batchId);
	}

	@Override
	public long updateTradeCouponBatchById(TradeCouponBatchDO tcb) throws DaoException {
		return super.executeUpdate(NAMESPACE.concat("updateTradeCouponBatch"), tcb);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCouponBatchDO> queryTradeCouponBatchAll(TradeCouponBatchDO tcb) throws DaoException {
		return super.executeQueryForList(NAMESPACE.concat("selectTradeCouponBatchAllList"), tcb);
	}

	@Override
	public long queryTradeCouponBatchAllCount(TradeCouponBatchDO tcb) throws DaoException {
		return (Long) super.executeQueryForObject(NAMESPACE.concat("selectTradeCouponBatchAllListCount"), tcb);
	}

	@Override
	public long updateTradeCouponBatchByInvalid(TradeCouponBatchDO tcb) throws DaoException {
		return super.executeUpdate(NAMESPACE.concat("updateTradeCouponBatchByInvalid"), tcb);
	}
	
	public Integer buyerGetCoupon(TradeCouponBatchDO tcb) throws DaoException{
		return super.executeUpdate(NAMESPACE.concat("buyerGetOneCoupon"), tcb);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCouponBatchVO> getCouponBatchVO(
			TradeCouponBatchDO couponBatchDO) throws DaoException {
		return super.executeQueryForList(NAMESPACE.concat("getTradeCouponBatchVO"), couponBatchDO);
	}

	@Override
	public int getCouponBatchVONum(TradeCouponBatchDO couponBatchDO)
			throws DaoException {
		return (Integer) super.executeQueryForObject(NAMESPACE.concat("getTradeCouponBatchVONum"), couponBatchDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCouponBatchDO> queryTradeCouponBatchByTop(TradeCouponBatchDO tcb) throws DaoException {
		return super.executeQueryForList(NAMESPACE.concat("selectTradeCouponBatchByTop"), tcb);
	}
}
