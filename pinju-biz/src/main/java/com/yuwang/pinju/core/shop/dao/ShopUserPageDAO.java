/**
 * 
 */
package com.yuwang.pinju.core.shop.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * @author liyouguo
 * 
 */
public interface ShopUserPageDAO {
	/**
	 * 获取指定店铺自定义页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserPageDO> selectShopUserCustomerPage(
			ShopUserPageDO userPageDO) throws DaoException;

	/**
	 * 根据主键查询用户页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public ShopUserPageDO selectShopUserPageById(ShopUserPageDO userPageDO)
			throws DaoException;

	/**
	 * 获取相关参数获取所有可装修的页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserPageDO> selectShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException;

	/**
	 * 保存已增加的一条装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object insertShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException;

	/**
	 * 修改页面结构（根据USERID,PAGEID,SHOPID）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException;

	/**
	 * 修改页面内容（根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserPageByKey(ShopUserPageDO userPageDO)
			throws DaoException;

	/**
	 * 删除指定页面（主要是根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException;

	/**
	 * 发布用户装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object releaseShopUserPage(ShopUserPageDO userPageDO)
			throws DaoException;

	/**
	 * 还原用户所有装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object restoreDesignUserPage(ShopUserPageDO userPageDO)
			throws DaoException;
}
