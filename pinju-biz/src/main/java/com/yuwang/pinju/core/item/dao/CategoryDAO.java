package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.item.CategoryPropertyDO;

/**
 * 
 * @author liming
 * 
 */
public interface CategoryDAO {

	/**
	 * 查询类目名
	 * 
	 * @param parentId
	 *            父类编号
	 * @return
	 * @throws DaoException
	 */
	public List<CategoryDO> selectItemCategoryByParentId(long parentId) throws DaoException;

	/**
	 * 通过编号查询类目
	 * 
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public CategoryDO selectItemCategoryById(long id) throws DaoException;

	/**
	 * 查询所有类目
	 * @return
	 * @throws DaoException
	 */
	public List<CategoryDO> selectFullItemCategory(long status) throws DaoException;
	/**
	 * 查询某个时间段更改类目
	 * @return
	 * @throws DaoException
	 */
	public List<CategoryDO> selectModifyItemCategory(String startDate,String endDate) throws DaoException;
	
	
}
