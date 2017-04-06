/**
 * 
 */
package com.yuwang.pinju.core.shop.ao;

import java.util.List;

import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * @author liyouguo
 * 
 */
public interface ShopUserPageAO {
	/**
	 * 获取指定店铺自定义页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopUserPageDO> selectShopUserCustomerPage(
			ShopUserPageDO userPageDO, boolean isRelease) throws Exception;

	/**
	 * 获取相关参数获取所有可装修的页面列表
	 * 
	 * @param userPageDO
	 * @return
	 * @throws Exception
	 */
	public List<ShopUserPageDO> selectShopUserPage(ShopUserPageDO userPageDO,
			boolean isRelease) throws Exception;

	/**
	 * 保存已增加的一条装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws Exception
	 */
	public Object insertShopUserPage(ShopUserPageDO userPageDO)
			throws Exception;

	/**
	 * 修改页面结构（根据USERID,PAGEID,SHOPID）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws Exception
	 */
	public Object updateShopUserPage(ShopUserPageDO userPageDO)
			throws Exception;

	/**
	 * 修改页面内容（根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws Exception
	 */
	public Object updateShopUserPageByKey(ShopUserPageDO userPageDO)
			throws Exception;

	/**
	 * 删除指定页面（主要是根据主键）
	 * 
	 * @param userPageDO
	 * @return
	 * @throws Exception
	 */
	public Object deleteShopUserPage(ShopUserPageDO userPageDO)
			throws Exception;

	/**
	 * 获取页面html
	 * 
	 * @param list
	 * @return
	 */
	public String getPageHtml(List<ShopPageLayoutDO> list, Integer pageId,
			Long userId, Integer shopId, String preview, String seller, boolean isRelease,String ip);

	/**
	 * 保存用户增加的所有自定义页面
	 * 
	 * @param userPageList
	 * @return
	 * @throws Exception
	 */
	public Object[] saveUserCustomerPage(List<ShopUserPageDO> userPageList)
			throws Exception;

	/**
	 * 发布用户装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws Exception
	 */
	public Object releaseUserPage(final ShopUserPageDO userPageDO)
			throws Exception;

	/**
	 * 还原用户装修（还原到上一个版本）
	 * 
	 * @param userPageDO
	 * @return
	 */
	public Object restoreDesignPage(ShopUserPageDO userPageDO) throws Exception;
	
	/**
	 * 获取页面皮肤 shopId可以为空
	 * @param userId
	 * @param shopId
	 * @param isRelease
	 * @return
	 */
	public String getSkinColor(Long userId,Integer shopId, boolean isRealse); 
	
	/**
	 * 获取店铺首页的Configuration配置字符串
	 * @param userId
	 * @param shopId
	 * @param isRelease
	 * @return String
	 */
	public String getFristPageConfiguration(Long userId, Integer shopId, boolean isRelease);
}
