package com.yuwang.pinju.core.trade.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.trade.dao.PaySequenceDAO;

public class PaySequenceDAOImpl extends BaseDAO implements PaySequenceDAO {

	@Override
	public Long getSequenceNextByName(String seqName) throws DaoException {
		return (Long)super.executeQueryForObject("trade_refund_log.querySequence", seqName);
	}

}
