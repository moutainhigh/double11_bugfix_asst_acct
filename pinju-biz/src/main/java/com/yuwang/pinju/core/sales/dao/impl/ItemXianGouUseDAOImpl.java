/**
 * ItemXianGouUseDAOImpl
 */
package com.yuwang.pinju.core.sales.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.sales.dao.ItemXianGouUseDAO;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;
import com.yuwang.pinju.domain.sales.query.ItemXianGouUseQuery;

/**  
 * @Project: crm-biz
 * @Discription: [限购码领用记录DAOImpl]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-30 下午07:13:08
 * @update 2011-8-30 下午07:13:08
 * @version V1.0  
 */
public class ItemXianGouUseDAOImpl extends BaseDAO implements ItemXianGouUseDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemXianGouUseDO> getItemXianGouUseDOs(ItemXianGouUseQuery itemXianGouUseQuery) throws DaoException{
		itemXianGouUseQuery.setItems(getItemXianGouUseDOsCount(itemXianGouUseQuery));
		return (List<ItemXianGouUseDO>)executeQueryForList("ItemXianGouUse.getItemXianGouUseDOs",itemXianGouUseQuery);
	}

	@Override
	public Integer getItemXianGouUseDOsCount(ItemXianGouUseQuery itemXianGouUseQuery) throws DaoException{
		return (Integer)executeQueryForObject("ItemXianGouUse.getItemXianGouUseDOsCount",itemXianGouUseQuery);
	}

	@Override
	public void insertItemXianGouUseRecord(ItemXianGouUseDO itemXianGouUseDO) throws DaoException{
		itemXianGouUseDO.setId((Long)executeInsert("ItemXianGouUse.insertItemXianGouUseRecord",itemXianGouUseDO));
	}

	@Override
	public int updateItemXianGouUseRecord(ItemXianGouUseDO itemXianGouUseDO) throws DaoException{
		return executeUpdate("ItemXianGouUse.updateItemXianGouUseRecord",itemXianGouUseDO);
	}

	@Override
	public Long getItemXianGouUseDOCount(ItemXianGouUseDO itemXianGouUseDO)
			throws DaoException {
		return (Long) executeQueryForObject("ItemXianGouUse.getXianGouCounts", itemXianGouUseDO);
	}

	@Override
	public ItemXianGouUseDO getItemXianGouUseDOByCode(Long code) throws DaoException{
		return (ItemXianGouUseDO)executeQueryForObject("ItemXianGouUse.getItemXianGouUseDOByCode",code);
	}

}
