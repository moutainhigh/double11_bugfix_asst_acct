package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.ItemPicDO;

public interface ItemPicDAO {
	/**
	 * 插入商品副图
	 * 
	 * @param itemPicDO
	 * @throws DaoException
	 */
	public long insertItemPic(ItemPicDO itemPicDO) throws DaoException;

	/**
	 * 根据商品编号查询关联副图
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<ItemPicDO> getItemPicByItemId(Long itemId) throws DaoException;
	
	/**
	 * 根据商品编号 删除副图 (更新商品)
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public int deleteItemPic(ItemPicDO itemPicDO) throws DaoException;
	
	/**
	 * 根据商品编号查询关联副图(read database)
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<ItemPicDO> getReadItemPicByItemId(Long itemId) throws DaoException;
}
