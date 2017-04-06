package com.yuwang.pinju.core.item.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.ItemPicDO;

public interface ItemPicManager {
	
	
	/**
	 * 插入商品副图
	 * 
	 * @param itemPicDO
	 * @throws DaoException
	 */
	public long insertItemPic(List<ItemPicDO> itemPicList, long itemId) throws ManagerException;

	/**
	 * 根据商品编号查询关联副图
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<ItemPicDO> getItemPicByItemId(Long itemId) throws ManagerException;
	
	/**
	 * 根据商品编号 删除副图 (更新商品)
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public int deleteItemPic(ItemPicDO itemPicDO) throws DaoException;
	
	/**
	 * 根据商品编号查询关联副图(获取读库的)
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<ItemPicDO> getReadItemPicByItemId(Long itemId) throws ManagerException;

}
