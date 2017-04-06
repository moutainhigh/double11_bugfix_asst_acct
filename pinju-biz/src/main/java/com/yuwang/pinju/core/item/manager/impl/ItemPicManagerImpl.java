package com.yuwang.pinju.core.item.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.dao.ItemPicDAO;
import com.yuwang.pinju.core.item.manager.ItemPicManager;
import com.yuwang.pinju.domain.item.ItemPicDO;

public class ItemPicManagerImpl implements ItemPicManager {

	private ItemPicDAO itemPicDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.SkuManager#getItemSkuByItemId(java.lang.Long)
	 */
	@Override
	public List<ItemPicDO> getItemPicByItemId(Long itemId) throws ManagerException {
		try {
			return itemPicDAO.getItemPicByItemId(itemId);
		} catch (DaoException e) {
			throw new ManagerException("获得商品副图", e);
		}
	}

	@Override
	public int deleteItemPic(ItemPicDO itemPicDO) throws DaoException {
		// TODO Auto-generated method stub
		return itemPicDAO.deleteItemPic(itemPicDO);
		
	}

	@Override
	public long insertItemPic(List<ItemPicDO> itemPicList, long itemId) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			for (ItemPicDO itemPicDO : itemPicList) {
				itemPicDO.setItemId(itemId);
				itemPicDAO.insertItemPic(itemPicDO);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("插入商品副图", e);
		}
		return 0;
	}

	public void setItemPicDAO(ItemPicDAO itemPicDAO) {
		this.itemPicDAO = itemPicDAO;
	}

	@Override
	public List<ItemPicDO> getReadItemPicByItemId(Long itemId)
			throws ManagerException {
		try {
			return itemPicDAO.getReadItemPicByItemId(itemId);
		} catch (DaoException e) {
			throw new ManagerException("获得商品副图(read store)", e);
		}
	}
}
