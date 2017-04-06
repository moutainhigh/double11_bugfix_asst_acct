package com.yuwang.pinju.core.item.ao;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;

public interface ItemSalesStatAO {
	/**
	 * 根据商品ID查找商品近30天的销量
	 * 
	 * @param itemId
	 * @return
	 * @throws ManagerException
	 */
	public ItemSalesStatDO getItemSalesStatById(Long itemId);
}
