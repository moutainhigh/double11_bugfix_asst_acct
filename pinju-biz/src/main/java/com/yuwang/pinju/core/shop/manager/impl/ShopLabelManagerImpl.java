package com.yuwang.pinju.core.shop.manager.impl;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopLabelDAO;
import com.yuwang.pinju.core.shop.manager.ShopLabelManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
/**
 * 店铺标签
 *
 * @author 杨昭
 * 
 * @since 2011-12-5
 */
public class ShopLabelManagerImpl implements ShopLabelManager {
	private ShopLabelDAO shopLabelDAO;
	public ShopLabelDAO getShopLabelDAO() {
		return shopLabelDAO;
	}
	public void setShopLabelDAO(ShopLabelDAO shopLabelDAO) {
		this.shopLabelDAO = shopLabelDAO;
	}
	/**
	 * 添加店铺标签
	 * @author 杨昭
	 * @return Integer 受影响行数
	 * @since 2011-12-5
	 */
	@Override
	public ShopInfoDO getShopLabelByShopId(ShopInfoDO shopInfoDO) throws ManagerException {
		try {
			return shopLabelDAO.getShopLabelByShopId(shopInfoDO);	
		} catch (Exception e) {
			throw new ManagerException("添加店铺标签失败，"+e);
		}
	}
	/**
	 * 根据memberId或得店铺标签信息
	 * @param ShopInfoDO shopInfoDO
	 * @author 杨昭
	 * @return  ShopInfoDO
	 * @since 2011-12-5
	 */
	@Override
	public Integer updateShopLabel(ShopInfoDO shopInfoDO) throws ManagerException {
		try {
			return shopLabelDAO.updateShopLabel(shopInfoDO);
		} catch (Exception e) {
			throw new ManagerException("根据memberId或得店铺标签信息失败，"+e);
		}
	}

}
