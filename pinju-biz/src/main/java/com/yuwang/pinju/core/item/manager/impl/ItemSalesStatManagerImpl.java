package com.yuwang.pinju.core.item.manager.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.dao.ItemSalesStatDAO;
import com.yuwang.pinju.core.item.manager.ItemSalesStatManager;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;

public class ItemSalesStatManagerImpl extends BaseDAO implements ItemSalesStatManager {
	private ItemSalesStatDAO itemSalesStatDAO;

	public void setItemSalesStatDAO(ItemSalesStatDAO itemSalesStatDAO) {
		this.itemSalesStatDAO = itemSalesStatDAO;
	}

	@Override
	public ItemSalesStatDO getItemSalesStatDO(Long itemId) throws ManagerException {
		try {
			return itemSalesStatDAO.selectItemSalesStatById(itemId);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

}
