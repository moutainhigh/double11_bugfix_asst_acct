package com.yuwang.pinju.core.item.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.ItemSnapshotDO;

/**
 * 
 * @author gongjiayun
 * 
 */
public interface ItemSnapshotManager {
	/**
	 * 根据商品快照ID查找商品信息
	 * 
	 * @param itemSnapshotId
	 * @return
	 * @throws ManagerException
	 */
	public ItemSnapshotDO selectItemSnapshotDOById(Long itemSnapshotId) throws ManagerException;
}
