package com.yuwang.pinju.core.item.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.ItemSnapshotDAO;
import com.yuwang.pinju.domain.item.ItemSnapshotDO;

/**
 * 
 * @author liming
 * 
 */
public class ItemSnapshotDAOImpl extends BaseDAO implements ItemSnapshotDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemDAO#insertItemItemSnapshot(com.yuwang.pinju.domain.item.ItemSnapshotDO)
	 */
	@Override
	public long insertItemItemSnapshot(ItemSnapshotDO itemSnapshotDO) throws DaoException {
		return (Long) executeInsert("item_item_snapshot.insertItemItemSnapshot", itemSnapshotDO);
	}

	@Override
	public ItemSnapshotDO getItemSnapshotByItemId(Long itemId) throws DaoException {
		return (ItemSnapshotDO) executeQueryForObject("item_item_snapshot.getItemSnapshotByItemId", itemId);
	}

	@Override
	public ItemSnapshotDO getItemSnapshotById(Long snapshotId) throws DaoException {
		return (ItemSnapshotDO) executeQueryForObject("item_item_snapshot.getItemSnapshotById", snapshotId);
	}

}
