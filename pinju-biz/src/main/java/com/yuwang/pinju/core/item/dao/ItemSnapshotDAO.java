package com.yuwang.pinju.core.item.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.ItemSnapshotDO;

/**
 * 
 * @author liming 2011-06-15
 */
public interface ItemSnapshotDAO {

	/**
	 * 插入商品快照
	 * 
	 * @param itemSnapshotDO
	 * @throws DaoException
	 */
	public long insertItemItemSnapshot(ItemSnapshotDO itemSnapshotDO) throws DaoException;

	/**
	 * 根据商品编号 获得最新商品快照编号
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public ItemSnapshotDO getItemSnapshotByItemId(Long itemId) throws DaoException;
	
	/**
	 * 根据商品快照ID查找商品快照
	 * @param snapshotId
	 * @return
	 * @throws DaoException
	 */
	public ItemSnapshotDO getItemSnapshotById(Long snapshotId) throws DaoException;

}
