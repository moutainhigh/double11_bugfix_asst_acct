package com.yuwang.pinju.core.shop.manager;


import java.util.Map;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;
import com.yuwang.pinju.domain.shop.ShopCategoryListDO;

/**
 * 店铺商品分类管理接口
 * 
 * @author mike
 *
 * @since 2011-6-1
 */
public interface ShopCategoryManager {
	/**
	 * 通过店铺id查询对应的商品分类
	 * 
	 * @param shopId,userId 联合查询
	 * @return 店铺商品分类
	 */
	public ShopCategoryDO queryShopCategory(Integer shopId,Long userId)throws ManagerException;
	
	/**
	 * 新建店铺商品分类
	 * 
	 * @param shopCategory
	 */
	public void insertShopCategroy(ShopCategoryDO  shopCategory)throws ManagerException;
	
	
	/**
	 * 更新店铺商品分类
	 * 
	 * @param shopCategory
	 */
	public void updateShopCategroy(ShopCategoryDO  shopCategory)throws ManagerException;
	
	/**
	 * 获取分类列表
	 * @param shopId
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, ShopCategoryListDO> queryShopCategoryList(Integer shopId) throws ManagerException;
	
	/**
	 * 获取分类列表
	 * @param shopId
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, ShopCategoryListDO> queryShopCategory(Integer shopId)throws ManagerException;
	
	/**
	 * 添加到分类
	 * @param itemId
	 * @param shopId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean moveItemToCategory(Long itemId[], String key, String fromKey[], ShopCategoryDO shopCategoryDO) throws Exception;
	
	/**
	 * 移动到分类
	 * @param itemId
	 * @param shopId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean copyItemToCateGory(Long itemId[], String key, String fromKey[], ShopCategoryDO shopCategoryDO) throws Exception;
	
	/**
	 * 获取分类列表(根据用户编号)
	 * @param memberId
	 * @return
	 * @throws ManagerException
	 */
	public Map<String, ShopCategoryListDO> queryShopCategoryByUser(Long memberId)throws ManagerException;
}
