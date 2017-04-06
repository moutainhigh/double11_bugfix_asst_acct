package com.yuwang.pinju.core.shop.dao;


import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * 店铺标签
 *
 * @author 杨昭
 * 
 * @since 2011-12-5
 */
public interface ShopLabelDAO {
	/**
	 * 添加店铺标签
	 * @author 杨昭
	 * @return Integer 受影响行数
	 * @since 2011-12-5
	 */
	Integer updateShopLabel(ShopInfoDO shopInfoDO) throws DaoException;
	/**
	 * 根据memberId或得店铺标签信息
	 * @param ShopInfoDO shopInfoDO
	 * @author 杨昭
	 * @return ShopInfoDO
	 * @throws DaoException 
	 * @since 2011-12-5
	 */
	ShopInfoDO getShopLabelByShopId(ShopInfoDO shopInfoDO)throws DaoException;
}
