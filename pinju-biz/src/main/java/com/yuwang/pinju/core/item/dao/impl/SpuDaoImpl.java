package com.yuwang.pinju.core.item.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.SpuDAO;
import com.yuwang.pinju.domain.item.SpuDO;

public class SpuDaoImpl extends BaseDAO implements SpuDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.SpuDAO#getItemSpuByKey(long, long)
	 */
	@Override
	public SpuDO getItemSpuByKey(Long keyPropertyId, Long keyPropertyValue) throws DaoException {
		Map<String, Long> m = new HashMap<String, Long>();
		m.put("keyPropertyId", keyPropertyId);
		m.put("keyPropertyValue", keyPropertyValue);
		return (SpuDO) executeQueryForObject("item_spu.getItemSpuByKey", m);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.SpuDAO#getItemSpuById(java.lang.Long)
	 */
	@Override
	public SpuDO getItemSpuById(Long spuId) throws DaoException {
		return (SpuDO) executeQueryForObject("item_spu.getItemSpuById", spuId);
	}
}
