package com.yuwang.pinju.core.item.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;
/**
 * 
 * @author gongjiayun
 *
 */
public interface ItemSalesStatDAO {
	/**
	 * 根据商品ID查找商品销量,走读库
	 * @return
	 * @throws DaoException
	 */
	public ItemSalesStatDO selectItemSalesStatById(Long id) throws DaoException;
}
