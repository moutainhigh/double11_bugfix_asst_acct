/**
 * 
 */
package com.yuwang.pinju.core.shop.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * @author liyouguo
 * 
 */
public interface ShopUserPageManager {
	/**
	 * 获取指定店铺自定义页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopUserPageDO> selectShopUserCustomerPage(
			ShopUserPageDO userPageDO) throws ManagerException;

	/**
	 * 获取相关参数获取所有可装修的页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopUserPageDO> selectShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException;

	/**
	 * 保存已增加的一条装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object insertShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException;

	/**
	 * 修改页面结构（根据USERID,PAGEID,SHOPID）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object updateShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException;

	/**
	 * 修改页面内容（根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object updateShopUserPageByKey(ShopUserPageDO userPageDO)
			throws ManagerException;

	/**
	 * 删除指定页面（主要是根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object deleteShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException;

	/**
	 * 保存用户增加的所有自定义页面
	 * 
	 * @param userPageList
	 * @return
	 * @throws ManagerException
	 */
	public Object[] saveUserCustomerPage(final List<ShopUserPageDO> userPageList)
			throws ManagerException;

	/**
	 * 发布用户装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object releaseShopUserPage(ShopUserPageDO userPageDO)
			throws ManagerException;
	
	/**
	 * 还原用户装修（还原到上一个版本）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public Object restoreDesignPage(ShopUserPageDO userPageDO)
			throws ManagerException;
	
	/**
	 * 获取店铺头部Html
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	public String getTopPageHtml(Long userId) throws ManagerException;
	
	/**
	 * 获取店铺左侧HTML
	 * @param userId
	 * @return
	 * @throws ManagerException
	 */
	public String getLeftPageHtml(Long userId) throws ManagerException;

	/**
	 * 根据主键查询用户页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public ShopUserPageDO selectShopUserPageById(Long userPageId)
			throws ManagerException;
}
