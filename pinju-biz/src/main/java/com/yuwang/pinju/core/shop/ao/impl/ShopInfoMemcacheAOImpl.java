package com.yuwang.pinju.core.shop.ao.impl;


import net.rubyeye.xmemcached.MemcachedClient;

import com.yuwang.pinju.core.item.manager.CategoryManager;
import com.yuwang.pinju.core.shop.ao.ShopCategoryAO;
import com.yuwang.pinju.core.shop.ao.ShopInfoMemcacheAO;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class ShopInfoMemcacheAOImpl extends BaseAO implements ShopInfoMemcacheAO{
	/**
	 * 分布式缓存
	 */
	private MemcachedClient shopMemcachedClient;
	private ShopShowInfoManager shopShowInfoManager;
	private ShopCategoryAO shopCategoryAO;
	private CategoryManager categoryManager;
	public MemcachedClient getShopMemcachedClient() {
		return shopMemcachedClient;
	}
	public void setShopMemcachedClient(MemcachedClient shopMemcachedClient) {
		this.shopMemcachedClient = shopMemcachedClient;
	}
	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}
	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
	public ShopCategoryAO getShopCategoryAO() {
		return shopCategoryAO;
	}
	public void setShopCategoryAO(ShopCategoryAO shopCategoryAO) {
		this.shopCategoryAO = shopCategoryAO;
	}
	public ShopShowInfoAO getShopShowInfoAO() {
		return shopShowInfoAO;
	}
	public void setShopShowInfoAO(ShopShowInfoAO shopShowInfoAO) {
		this.shopShowInfoAO = shopShowInfoAO;
	}
	private ShopShowInfoAO shopShowInfoAO;
	/**
	 * 获得店铺信息
	 * @author 杨昭
	 * @param sellerId
	 * @return ShopInfoDO
	 * @since 2011-12-12
	 */
	public ShopInfoDO getShopInfoDO(Long sellerId){
		ShopInfoDO shopInfoDO = null;
		try {
			shopInfoDO = shopMemcachedClient
			.get(sellerId.toString());
		}catch(Exception e){
			log.warn(e);
		}
		if(shopInfoDO==null){
				try {
					shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(sellerId, null);
					shopMemcachedClient
							.set(sellerId.toString(),
									60 * 30, shopInfoDO);// 过期时间30分钟
				} catch (Exception e) {
					// ignore
					log.warn(e);
				}
		}
		return shopInfoDO;
	}
	public CategoryManager getCategoryManager() {
		return categoryManager;
	}
	public void setCategoryManager(CategoryManager categoryManager) {
		this.categoryManager = categoryManager;
	}
}
