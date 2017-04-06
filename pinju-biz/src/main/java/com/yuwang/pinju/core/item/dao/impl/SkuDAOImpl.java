package com.yuwang.pinju.core.item.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.item.dao.SkuDAO;
import com.yuwang.pinju.domain.item.SkuDO;

/**
 * 
 * @author liming
 * @since 2011-06-16
 * 
 */
public class SkuDAOImpl extends BaseDAO implements SkuDAO {
	private ReadBaseDAO readBaseDAOForOracle ;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#createItemSku(com.yuwang.pinju.domain.item.SkuDO)
	 */
	@Override
	public long createItemSku(SkuDO skuDO) throws DaoException {
		return (Long) executeInsert("item_sku.createItemSku", skuDO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#deleteItemSku(java.lang.Long)
	 */
	@Override
	public void deleteItemSku(Long itemId) throws DaoException {
		executeUpdate("item_sku.deleteItemSku", itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#getItemSku(com.yuwang.pinju.domain.item.SkuDO)
	 */
	@Override
	public List<SkuDO> getItemSku(SkuDO skuDO) throws DaoException {
		return executeQueryForList("item_sku.getItemSku", skuDO);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#getItemSkuById(java.lang.Long)
	 */
	@Override
	public SkuDO getItemSkuById(Long id) throws DaoException {
		return (SkuDO) executeQueryForObject("item_sku.getItemSkuById", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#getItemSkuByItemId(java.lang.Long)
	 */
	@Override
	public List<SkuDO> getItemSkuByItemId(Long itemId) throws DaoException {
		return executeQueryForList("item_sku.getItemSkuByItemId", itemId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#updateItemSku(com.yuwang.pinju.domain.item.SkuDO)
	 */
	@Override
	public int updateItemSku(SkuDO skuDO) throws DaoException {
		return executeUpdate("item_sku.updateItemSku", skuDO);
	}

	@Override
	public List<SkuDO> getReadItemSkuByItemId(Long itemId) throws DaoException {
		return readBaseDAOForOracle.executeQueryForList("item_sku.getItemSkuByItemId", itemId);
	}

	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}

}
