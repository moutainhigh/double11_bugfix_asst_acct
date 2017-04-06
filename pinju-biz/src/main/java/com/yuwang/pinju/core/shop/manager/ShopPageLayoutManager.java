package com.yuwang.pinju.core.shop.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopPageLayoutDO;

public interface ShopPageLayoutManager {
	/**
	 * 保存页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return 
	 */
	public boolean savePageLayout(Long userId,Integer shopId,Integer pageId,String layoutXml) throws ManagerException;
	
	/**
	 * 查询页面布局
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @return 
	 */
	public List<ShopPageLayoutDO> queryPageLayout(Long userPageId,Long userId,Integer shopId,Integer pageId) throws ManagerException;
	
	/**
	 * 查询页面布局从缓存中
	 * @param userPageId
	 * @param userId
	 * @param shopId
	 * @param pageId
	 * @param isRelease
	 * @return
	 * @throws ManagerException
	 */
	public List<ShopPageLayoutDO> queryPageLayoutCache(Long userPageId, Long userId,
			Integer shopId, Integer pageId, boolean isRelease) throws ManagerException;
}
