package com.yuwang.pinju.core.item.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.SpuDO;

public interface SpuDAO {

	/**
	 * 获得SPU 根据 属性编号和属性值编号
	 * 
	 * @param keyPropertyId
	 * @param keyPropertyValue
	 * @return
	 * @throws DaoException
	 */
	public SpuDO getItemSpuByKey(Long keyPropertyId, Long keyPropertyValue) throws DaoException;
	
	/**
	 * 获得SPU 根据编号
	 * 
	 * @param keyPropertyId
	 * @param keyPropertyValue
	 * @return
	 * @throws DaoException
	 */
	public SpuDO getItemSpuById(Long spuId) throws DaoException;

}
