/**
 * 
 */
package com.yuwang.pinju.core.shop.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopUserCategoryDO;

/**
 * @author liyouguo
 * 
 */
public interface ShopUserCategoryDAO {
	/**
	 * 获取指定店铺的所有类目
	 * 
	 * @param memeberId
	 * @param shopId
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserCategoryDO> selectAllShopUserCategory(Long userId,
			Integer shopId) throws DaoException;

	/**
	 * 按照指定查询条件查询类目
	 * 
	 * @param shopUserCategory
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserCategoryDO> selectShopUserCategory(
			ShopUserCategoryDO shopUserCategory) throws DaoException;
	
	/**
	 * 插入一条类目
	 * @param shopUserCategory
	 * @return
	 * @throws DaoException
	 */
	public Object insertShopUserCategory(ShopUserCategoryDO shopUserCategory)
			throws DaoException;
	
	/**
	 * 删除指定条件的类目
	 * @param shopUserCategory
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserCategory(ShopUserCategoryDO shopUserCategory)
			throws DaoException;
}
