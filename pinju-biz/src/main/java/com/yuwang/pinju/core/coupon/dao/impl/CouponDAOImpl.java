package com.yuwang.pinju.core.coupon.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.coupon.dao.CouponDAO;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.coupon.TradeCouponQueryDO;

public class CouponDAOImpl extends BaseDAO implements CouponDAO{
	private static final String COUPON_NAME_SPACE = "trade_coupon.";
	
	/**
	 * 保存优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新或者保存的优惠券id
	 */
	public Long saveTradeCoupon(TradeCouponDO tradeCouponDO)throws DaoException{
		return (Long)super.executeInsert(COUPON_NAME_SPACE + "insertTradeCoupon", tradeCouponDO);
	}

	/**
	 * 更新优惠券信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新的记录条数
	 */
	public Integer updateTradeCoupon(TradeCouponDO tradeCouponDO)throws DaoException{
		return (Integer)super.executeUpdate(COUPON_NAME_SPACE + "updateTradeCoupon", tradeCouponDO);
	}
	
	/**
	 * 更新优惠券关联的订单信息
	 * 
	 * @param tradeCouponDO 优惠券信息
	 * 
	 * @return 更新的记录条数
	 */
	public Integer updateCouponOrder(TradeCouponDO tradeCouponDO)throws DaoException{
		return (Integer)super.executeUpdate(COUPON_NAME_SPACE + "updateCouponOrder", tradeCouponDO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCouponDO> getTradeCouponDOs(TradeCouponQueryDO queryDO) throws DaoException {
		return super.executeQueryForList(COUPON_NAME_SPACE + "selectTradeCouponForList",queryDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCouponDO> getValidCoupon(TradeCouponDO tradeCouponDO) throws DaoException{
		return super.executeQueryForList(COUPON_NAME_SPACE + "selectValidCouponList", tradeCouponDO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TradeCouponDO> getTimeoutCoupon() throws DaoException{
		return super.executeQueryForList(COUPON_NAME_SPACE + "selectTimeoutCouponList", null);
	}
	
	@Override  
	public int getTradeCouponDOsNum(TradeCouponQueryDO queryDO)
			throws DaoException {
		return  (Integer) super.executeQueryForObject(COUPON_NAME_SPACE + "selectTradeCouponForListNum", queryDO);
	}

	@Override
	public TradeCouponDO getTradeCouponDOByID(Long Id) throws DaoException {
		return (TradeCouponDO) super.executeQueryForObject(COUPON_NAME_SPACE + "selectTradeCouponByid",
				Id);
	}

	public TradeCouponDO getTradeCouponDOByOrderId(Long orderId)throws DaoException{
		return (TradeCouponDO) super.executeQueryForObject(COUPON_NAME_SPACE + "selectTradeCouponByOrderid", orderId);
	}
	
	@Override
	public int buyerDeleteCoupon(TradeCouponDO couponDO) throws DaoException {
		return super.executeUpdate(COUPON_NAME_SPACE + "buyerDeleteCoupon",
				couponDO);
	}

	@Override
	public TradeCouponDO getTradeCouponDO(TradeCouponDO couponDO)
			throws DaoException {
		return (TradeCouponDO) super.executeQueryForObject(COUPON_NAME_SPACE + "selectTradeCoupon",
				couponDO);
	}
}
