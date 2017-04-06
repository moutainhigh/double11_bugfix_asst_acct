package com.yuwang.pinju.core.coupon.manager.impl;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.dao.TradeCouponTallyDAO;
import com.yuwang.pinju.core.coupon.manager.TradeCouponTallyManager;
import com.yuwang.pinju.domain.coupon.TradeCouponTallyDO;

public class TradeCouponTallyManagerImpl implements TradeCouponTallyManager {
	private TradeCouponTallyDAO tradeCouponTallyDAO;
	
	@Override
	public Long addTradeCouponTally(TradeCouponTallyDO tctd)
			throws ManagerException {
		try {
			return tradeCouponTallyDAO.addTradeCouponTally(tctd);
		} catch (DaoException e) {
			throw new ManagerException("Event=[TradeCouponTallyManagerImpl#addTradeCouponTally]新增买家领取优惠券计数表: ", e);
		}
	}

	@Override
	public int updateTradeCouponTally(TradeCouponTallyDO tctd)
			throws ManagerException {
		try {
			return tradeCouponTallyDAO.updateTradeCouponTally(tctd);
		} catch (DaoException e) {
			throw new ManagerException("Event=[TradeCouponTallyManagerImpl#updateTradeCouponTally]修改  优惠券领取记录: ", e);
		}
	}

	/**
	 * Created on 2011-11-24
	 * <p>Discription:查询买家领取优惠券数量 </p>
	 * @param batchId 批次id
	 * @param buyerId 买家id
	 * @return
	 * @throws ManagerException
	 * @author:[石洪波]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public TradeCouponTallyDO loadCouponTally(Long batchId, Long buyerId)throws ManagerException{
		try {
			return tradeCouponTallyDAO.loadCouponTally(batchId, buyerId);
		} catch (DaoException e) {
			throw new ManagerException("Event=[TradeCouponTallyManagerImpl#updateTradeCouponTally]修改  优惠券领取记录: ", e);
		}
	
	}
	
	public void setTradeCouponTallyDAO(TradeCouponTallyDAO tradeCouponTallyDAO) {
		this.tradeCouponTallyDAO = tradeCouponTallyDAO;
	}

}
