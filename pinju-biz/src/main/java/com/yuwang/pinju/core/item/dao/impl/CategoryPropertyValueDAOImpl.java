package com.yuwang.pinju.core.item.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.CategoryPropertyValueDAO;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;

/**
 * 
 * @author liming
 * 
 */
public class CategoryPropertyValueDAOImpl extends BaseDAO implements CategoryPropertyValueDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yuwang.pinju.core.item.dao.ItemCategoryPropertyValueDao#
	 * selectItemCategoryPropertyValue(long)
	 */
	@Override
	public List<CategoryPropertyValueDO> getCategoryPropertyValue(long categoryPropertyId) throws DaoException {
		return executeQueryForList("item_category_property_value.getCategoryPropertyValue", categoryPropertyId);
	}

}
