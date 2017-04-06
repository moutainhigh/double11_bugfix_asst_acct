package com.yuwang.pinju.core.item.ao;

import com.yuwang.pinju.domain.item.ItemSnapshotDO;

/**
 * 
 * @author gongjiayun
 * 
 */
public interface ItemSnapshotAO {
	/**
	 * 根据快照ID查找商品信息
	 * 
	 * @param itemSnapshotId
	 * @return
	 */
	public ItemSnapshotDO getItemSnapshotDoById(Long itemSnapshotId);
}
