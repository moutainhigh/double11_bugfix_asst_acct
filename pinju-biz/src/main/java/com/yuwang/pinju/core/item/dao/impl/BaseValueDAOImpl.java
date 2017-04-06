package com.yuwang.pinju.core.item.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.BaseValueDAO;
import com.yuwang.pinju.domain.item.BaseValueDO;

/**
 * 
 * @author liming
 * 
 */
public class BaseValueDAOImpl extends BaseDAO implements BaseValueDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.BaseValueDAO#selectItemBaseValueById(long)
	 */
	@Override
	public BaseValueDO selectItemBaseValueById(long id) throws DaoException {
		return (BaseValueDO) executeQueryForObject("item_base_value.selectItemBaseValueById", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.BaseValueDAO#getBaseValueByValue(java.lang.String)
	 */
	@Override
	public BaseValueDO getBaseValueByValue(String value) throws DaoException {
		return (BaseValueDO) executeQueryForObject("item_base_value.getBaseValueByValue", value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.BaseValueDAO#insertItemBaseValue(com.yuwang .pinju.domain.item.BaseValueDO)
	 */
	@Override
	public Object insertItemBaseValue(BaseValueDO baseValueDO) throws DaoException {
		return executeInsert("item_base_value.insertItemBaseValue", baseValueDO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.BaseValueDAO#getBaseValueNameByIds(java.util.List)
	 */
	@Override
	public List getBaseValueNameByIds(List propertyValueList) throws DaoException {
		return executeQueryForList("item_base_value.getBaseValueNameByIds", propertyValueList);
	}

}
