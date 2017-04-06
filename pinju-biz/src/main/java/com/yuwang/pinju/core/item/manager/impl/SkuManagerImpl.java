package com.yuwang.pinju.core.item.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.dao.SkuDAO;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.domain.item.SkuDO;

public class SkuManagerImpl implements SkuManager {

	private SkuDAO skuDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.SkuManager#getItemSkuById(java.lang.Long)
	 */
	@Override
	public SkuDO getItemSkuById(Long id) throws ManagerException {
		try {
			return skuDAO.getItemSkuById(id);
		} catch (DaoException e) {
			throw new ManagerException("根据编号 获得商品SKU", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.SkuManager#getItemSkuByItemId(java.lang.Long)
	 */
	@Override
	public List<SkuDO> getItemSkuByItemId(Long itemId) throws ManagerException {
		try {
			return skuDAO.getItemSkuByItemId(itemId);
		} catch (DaoException e) {
			throw new ManagerException("获得商品SKU", e);
		}
	}

	@Override
	public List<SkuDO> getReadItemSkuByItemId(Long itemId)
			throws ManagerException {
		try {
			return skuDAO.getReadItemSkuByItemId(itemId);
		} catch (DaoException e) {
			throw new ManagerException("获得商品SKU,read database", e);
		}
	}
	public void setSkuDAO(SkuDAO skuDAO) {
		this.skuDAO = skuDAO;
	}



}
