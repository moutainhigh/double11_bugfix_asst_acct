package com.yuwang.pinju.core.shop.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;

/**
 * 店铺商品分类数据接口
 * 
 * @author mike
 *
 * @since 2011-5-31
 */
public interface ShopCategroyDAO {
	/**
	 * 通过店铺id查询对应的商品分类
	 * 
	 * @param shopId,userId联合查询
	 *  
	 * @return 店铺商品分类
	 */
	public ShopCategoryDO queryShopCategory(Integer shopId,Long userId)throws DaoException;
	
	/**
	 * 新建店铺商品分类
	 * 
	 * @param shopCategory
	 */
	public void insertShopCategroy(ShopCategoryDO  shopCategory)throws DaoException;
	
	/**
	 * 更新店铺商品分类
	 * 
	 * @param shopCategory
	 */
	public void updateShopCategroy(ShopCategoryDO  shopCategory)throws DaoException;
	
	/**
	 * 获取Shop_Catyegory_seqid
	 * @return
	 * @throws DaoException
	 */
	public Long getShopCategorySeqId() throws DaoException;
}
