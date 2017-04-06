package com.yuwang.pinju.core.shop.ao.impl;

import com.yuwang.pinju.core.shop.ao.ShopLabelAO;
import com.yuwang.pinju.core.shop.manager.ShopLabelManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
/**
 * 店铺标签
 *
 * @author 杨昭
 * 
 * @since 2011-12-5
 */
public class ShopLabelAOImpl extends BaseAO implements ShopLabelAO {
	private ShopLabelManager shopLabelManager;
	public ShopLabelManager getShopLabelManager() {
		return shopLabelManager;
	}
	public void setShopLabelManager(ShopLabelManager shopLabelManager) {
		this.shopLabelManager = shopLabelManager;
	}
	/**
	 * 根据memberId或得店铺标签信息
	 * @param ShopInfoDO shopInfoDO
	 * @author 杨昭
	 * @return  ShopInfoDO
	 * @since 2011-12-5
	 */
	@Override
	public ShopInfoDO getShopLabelByShopId(ShopInfoDO shopInfoDO){
		try {
			return shopLabelManager.getShopLabelByShopId(shopInfoDO);
		} catch (Exception e) {
			log.error("根据memberId或得店铺标签信息失败", e);
		}
		return null;
	}
	/**
	 * 添加店铺标签
	 * @author 杨昭
	 * @return Integer 受影响行数
	 * @since 2011-12-5
	 */
	@Override
	public Integer updateShopLabel(ShopInfoDO shopInfoDO){
		try {
			return shopLabelManager.updateShopLabel(shopInfoDO);
		} catch (Exception e) {
			log.error("添加店铺标签失败", e);
		}
		return null;
	}
	
}
