package com.yuwang.pinju.core.item.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;

public interface ItemSalesStatManager {
	/**
	 * 根据商品ID查找商品销量
	 * @param itemId
	 * @return
	 */
	public ItemSalesStatDO getItemSalesStatDO(Long itemId) throws ManagerException;
}
