package com.yuwang.pinju.core.item.dao.impl;


import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.item.dao.ItemSalesStatDAO;
import com.yuwang.pinju.domain.item.ItemSalesStatDO;

/**
 * 
 * @author gongjiayun
 * 
 */
public class ItemSalesStatDAOImpl extends BaseDAO implements ItemSalesStatDAO {
	private ReadBaseDAO readBaseDAOForOracle ;
	
	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}

	@Override
	public ItemSalesStatDO selectItemSalesStatById(Long id) throws DaoException {
		return (ItemSalesStatDO) readBaseDAOForOracle.executeQueryForObject("item_sales_stat.selectItemSoldById", id);
	}
}
