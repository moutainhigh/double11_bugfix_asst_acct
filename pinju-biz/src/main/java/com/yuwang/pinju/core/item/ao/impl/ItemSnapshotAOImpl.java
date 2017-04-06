package com.yuwang.pinju.core.item.ao.impl;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.ao.ItemSnapshotAO;
import com.yuwang.pinju.core.item.manager.ItemSnapshotManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.ItemSnapshotDO;

/**
 * 
 * @author gongjiayun
 * 
 */
public class ItemSnapshotAOImpl extends BaseAO implements ItemSnapshotAO {
	private ItemSnapshotManager itemSnapshotManager;

	public void setItemSnapshotManager(ItemSnapshotManager itemSnapshotManager) {
		this.itemSnapshotManager = itemSnapshotManager;
	}

	@Override
	public ItemSnapshotDO getItemSnapshotDoById(Long itemSnapshotId) {
		try {
			return itemSnapshotManager.selectItemSnapshotDOById(itemSnapshotId);
		} catch (ManagerException e) {
			log.warn("查找商品快照异常,快照ID为" + itemSnapshotId, e);
			return null;
		}
	}

}
