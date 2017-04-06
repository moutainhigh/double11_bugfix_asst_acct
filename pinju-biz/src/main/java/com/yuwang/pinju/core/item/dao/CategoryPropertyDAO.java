package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;

/**
 * 
 * @author liming
 * 
 */
public interface CategoryPropertyDAO {

	/**
	 * 根据编号 查询类目属性
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public List<CategoryPropertyDO> getCategoryPropertyByCategoryId(long categoryId) throws DaoException;

	/**
	 * 通过类目属性ID,查询类目属性
	 * 
	 * @param cateProId
	 * @return
	 * @throws DaoException
	 */
	public CategoryPropertyDO selectCategoryPropertyByCateProId(long cateProId) throws DaoException;

	/**
	 * 获得类目属性名称
	 * 
	 * @param propertyIdList
	 * @return
	 * @throws DaoException
	 */
	public List<CategoryPropertyDO> getPropertyNameListByIds(List propertyIdList) throws DaoException;

}
