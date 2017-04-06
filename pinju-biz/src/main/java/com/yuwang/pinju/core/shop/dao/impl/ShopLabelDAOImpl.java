package com.yuwang.pinju.core.shop.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopLabelDAO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
/**
 * 店铺标签
 *
 * @author 杨昭
 * 
 * @since 2011-12-5
 */
public class ShopLabelDAOImpl extends BaseDAO implements ShopLabelDAO{
	/**
	 * 根据memberId或得店铺标签信息
	 * @param memberId   用户ID
	 * @author 杨昭
	 * @return ShopInfoDO
	 * @throws DaoException 
	 * @since 2011-12-5
	 * WordFilterFacade.scan(str, 6);
	 */
	@Override
	public ShopInfoDO getShopLabelByShopId(ShopInfoDO shopInfoDO) throws DaoException {
		return (ShopInfoDO) this.executeQueryForObject("shop_Label.getShopLabelByShopId", shopInfoDO);
	}
	/**
	 * 添加店铺标签
	 * @author 杨昭
	 * @return Integer
	 * @since 2011-12-5
	 */
	@Override
	public Integer updateShopLabel(ShopInfoDO shopInfoDO) throws DaoException {
		return this.executeUpdate("shop_Label.updateShopLabel", shopInfoDO);
	}

}
