package com.yuwang.pinju.core.item.manager.impl;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.dao.ItemSnapshotDAO;
import com.yuwang.pinju.core.item.manager.ItemSnapshotManager;
import com.yuwang.pinju.domain.item.ItemSnapshotDO;

/**
 * 
 * @author gongjiayun
 * 
 */
public class ItemSnapshotManagerImpl implements ItemSnapshotManager {
	private ItemSnapshotDAO itemSnapshotDao;

	public void setItemSnapshotDao(ItemSnapshotDAO itemSnapshotDao) {
		this.itemSnapshotDao = itemSnapshotDao;
	}

	@Override
	public ItemSnapshotDO selectItemSnapshotDOById(Long itemSnapshotId) throws ManagerException {
		try {
			return itemSnapshotDao.getItemSnapshotById(itemSnapshotId);
		} catch (DaoException e) {
			throw new ManagerException("查找商品快照异常,快照ID:" + itemSnapshotId, e);
		}
	}

}
