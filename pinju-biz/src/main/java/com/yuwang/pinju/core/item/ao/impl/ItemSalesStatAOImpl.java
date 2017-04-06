package com.yuwang.pinju.core.item.ao.impl;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.ao.ItemSalesStatAO;
import com.yuwang.pinju.core.item.manager.ItemSalesStatManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;

public class ItemSalesStatAOImpl extends BaseAO implements ItemSalesStatAO {
	private ItemSalesStatManager itemSalesStatManager;

	public void setItemSalesStatManager(ItemSalesStatManager itemSalesStatManager) {
		this.itemSalesStatManager = itemSalesStatManager;
	}

	@Override
	public ItemSalesStatDO getItemSalesStatById(Long itemId) {
		ItemSalesStatDO itemSalesStatDO = null;
		try {
			itemSalesStatDO = itemSalesStatManager.getItemSalesStatDO(itemId);
		} catch (ManagerException e) {
			log.error("查找商品销量异常:itemId=" + itemId);
			log.error(e);
		}
		return itemSalesStatDO;
	}

}
