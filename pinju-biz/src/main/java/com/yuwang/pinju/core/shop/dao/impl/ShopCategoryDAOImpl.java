package com.yuwang.pinju.core.shop.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopCategroyDAO;
import com.yuwang.pinju.domain.shop.ShopCategoryDO;

/**
 * 店铺商品分类数据接口实现
 * 
 * @author mike
 *
 * @since 2011-5-31
 */
public class ShopCategoryDAOImpl extends BaseDAO implements ShopCategroyDAO {

	@Override
	public ShopCategoryDO queryShopCategory(Integer shopId,Long userId)throws DaoException {
		ShopCategoryDO shopCategoryDO=new ShopCategoryDO();
		shopCategoryDO.setShopId(shopId);
		shopCategoryDO.setUserId(userId);
		return (ShopCategoryDO)super.executeQueryForObject("shopCategory.queryShopCategory", shopCategoryDO, false);
	}
	
	@Override
	public void insertShopCategroy(ShopCategoryDO shopCategory)throws DaoException {
		super.executeInsert("shopCategory.insertShopCategory", shopCategory);
	}

	@Override
	public void updateShopCategroy(ShopCategoryDO shopCategory)
			throws DaoException {
		super.executeUpdate("shopCategory.updateShopCategory", shopCategory);
	}

	@Override
	public Long getShopCategorySeqId() throws DaoException {
		Object ob = super.executeQueryForObject("shopCategory.getShopCategorySeqId",null);
		return (Long)ob;
	}
	
}
