package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.CategoryPropertyValueDO;

/**
 * 
 * @author liming
 * 
 */
public interface CategoryPropertyValueDAO {

	/**
	 * 获得 属性值 列表
	 * 
	 * @param categoryPropertyId
	 * @return
	 * @throws DaoException
	 */
	public List<CategoryPropertyValueDO> getCategoryPropertyValue(long categoryPropertyId)
			throws DaoException;

}
