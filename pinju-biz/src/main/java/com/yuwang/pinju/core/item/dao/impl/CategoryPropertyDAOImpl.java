package com.yuwang.pinju.core.item.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.CategoryPropertyDAO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;

/**
 * 
 * @author liming
 * 
 */
public class CategoryPropertyDAOImpl extends BaseDAO implements CategoryPropertyDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.yuwang.pinju.core.item.dao.ItemCategoryPropertyDao# selectItemCategoryPropertyById(long)
	 */
	@Override
	public List<CategoryPropertyDO> getCategoryPropertyByCategoryId(long categoryId) throws DaoException {
		return executeQueryForList("item_category_property.getCategoryPropertyById", categoryId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.CategoryPropertyDAO#ItemCategoryPropertyByCateProId(long)
	 */
	@Override
	public CategoryPropertyDO selectCategoryPropertyByCateProId(long cateProId) throws DaoException {
		return (CategoryPropertyDO) executeQueryForObject("item_category_property.selectItemCategoryPropertyByCateProId", cateProId);
	}

	@Override
	public List<CategoryPropertyDO> getPropertyNameListByIds(List propertyIdList) throws DaoException {
		return executeQueryForList("item_category_property.getPropertyNameListByIds", propertyIdList);
	}

}
