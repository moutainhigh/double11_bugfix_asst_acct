package com.yuwang.pinju.core.coupon.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.dao.TradeCouponTallyDAO;
import com.yuwang.pinju.domain.coupon.TradeCouponTallyDO;

public class TradeCouponTallyDAOImpl extends BaseDAO implements TradeCouponTallyDAO {

	private static final String NAME_SPACE = "trade_coupon_tally.";
	
	@Override
	public Long addTradeCouponTally(TradeCouponTallyDO tctd)
			throws DaoException {
		return (Long)super.executeInsert(NAME_SPACE + "insertTradeCouponTally", tctd);
	}

	@Override
	public int updateTradeCouponTally(TradeCouponTallyDO tctd)
			throws DaoException {
		return super.executeUpdate(NAME_SPACE + "updateTradeCouponTally",
				tctd);
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
	public TradeCouponTallyDO loadCouponTally(Long batchId, Long buyerId)throws DaoException{
		TradeCouponTallyDO tradeCouponTallyDO = new TradeCouponTallyDO();
		tradeCouponTallyDO.setBuyerId(buyerId);
		tradeCouponTallyDO.setCouponBatchId(batchId);
		return (TradeCouponTallyDO)super.executeQueryForObject(NAME_SPACE + "selectTradeCouponTallyByBatchAndBuyer", tradeCouponTallyDO);
	}
}
