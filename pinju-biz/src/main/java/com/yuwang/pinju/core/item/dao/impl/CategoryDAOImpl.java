package com.yuwang.pinju.core.item.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.CategoryDAO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;

/**
 * 
 * @author liming
 * 
 */
public class CategoryDAOImpl extends BaseDAO implements CategoryDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemItemDao#insertItemItem(com.yuwang. pinju.domain.item.ItemItemDO)
	 */
	@Override
	public List<CategoryDO> selectItemCategoryByParentId(long parentId) throws DaoException {
		return executeQueryForList("item_category.selectItemCategoryByParentId", parentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemItemDao#insertItemItem(com.yuwang. pinju.domain.item.ItemItemDO)
	 */
	@Override
	public CategoryDO selectItemCategoryById(long id) throws DaoException {
		return (CategoryDO) executeQueryForObject("item_category.selectItemCategoryById", id);
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.dao.CategoryDAO#selectFullItemCategory(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryDO> selectFullItemCategory(long status)
			throws DaoException {
		return executeQueryForList("item_category.selectFullItemCategory",status);
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.dao.CategoryDAO#selectModifyItemCategory(long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CategoryDO> selectModifyItemCategory(
			String startDate,String endDate) throws DaoException {
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("startDate", startDate);
		map.put("endDate",endDate);
		return executeQueryForList("item_category.selectModifyItemCategory",map);
	}


}
