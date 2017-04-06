package com.yuwang.pinju.core.shop.ao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.shop.ao.ShopShowInfoAO;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.CategoryDO;
import com.yuwang.pinju.domain.shop.ShopBusinessInfoDO;
import com.yuwang.pinju.domain.shop.ShopCustomerInfoDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopIshopInfoDO;

/**
 * 店铺基本信息
 * @author xueqi
 *
 * @since 2011-7-4
 */
public class ShopShowInfoAOImpl extends BaseAO implements ShopShowInfoAO {
	private MemcachedClient shopMemcachedClient;
	private ShopShowInfoManager shopShowInfoManager;
	private CategoryCacheManager categoryCacheManager;
	
	
	/**
	 * 保存店铺基本信息
	 * @param shopInfoDO
	 * @return
	 * @throws ManagerException
	 */
	@Override
	public Object saveShopBaseInfo(ShopInfoDO shopInfoDO) {
		Object back = null;
		try {
			back = shopShowInfoManager.updateShopBaseInfo(shopInfoDO);
			
		} catch (ManagerException e) {
			log.error("保存店铺基本信息出错",e);
		}
		return back;

		
	}
	
	@Override
	public Integer queryShopIdByUserId(Long userId) {
		if(userId!=0){
			String key = ShopConstants.SHOP_SHOPID_CACHE_KEY + userId;
			try {
				Integer shopId = shopMemcachedClient.get(key);
				if(shopId!=null){
					log.debug("#########get ShopId From Cache; Key="+key);
					return shopId;
				}
			
				ShopInfoDO shopInfoDO;
				try {
					shopInfoDO = shopShowInfoManager.queryShopBaseInfoByUser(userId, ShopConstant.APPROVE_STATUS_YES);
					if(shopInfoDO!=null && shopInfoDO.getShopId()!=null){
						shopId = shopInfoDO.getShopId();
						shopMemcachedClient.add(key, 0, shopId);
						return shopId;
					}
				} catch (ManagerException e) {
					log.error("",e);
				}
				
			} catch (TimeoutException e) {
				log.error("调用缓存超时",e);
			} catch (InterruptedException e) {
				log.error("调用缓存错误",e);
			} catch (MemcachedException e) {
				log.error("调用缓存错误",e);
			}
		}
		return null;
	}
	
	/**
	 * 获取类目信息
	 */
	@Override
	public Map<Long, String> initShopCategoryList() {
		try {
			List<CategoryDO> categoryList = categoryCacheManager
			.getRootCategoryList();
			if (categoryList != null && categoryList.size() > 0) {
				Map<Long, String> shopCategoryList = new HashMap<Long, String>();
				for (int i = 0; i < categoryList.size(); i++) {
					shopCategoryList.put(((CategoryDO)categoryList.get(i)).getId(),((CategoryDO)categoryList.get(i))
							.getName());			
				}
				return shopCategoryList;
			}
			
		} catch (ManagerException e) {
			log.error("开店--初始化类目信息",e);
		}
		return null;
	}
	
	
	/**
	 * 更新旗舰店铺联系人信息
	 * 
	 * @param shopBusinessInfoDO
	 * @return
	 */
	public boolean updateBusinessInfo(ShopBusinessInfoDO shopBusinessInfoDO){
		try {
			shopShowInfoManager.updateBusinessInfo(shopBusinessInfoDO);
		} catch (ManagerException e) {
			log.error("更新旗舰店铺联系人信息出错",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 更新普通店铺联系人信息
	 * 
	 * @param shopCustomerInfoDO
	 * @return
	 */
	public boolean updateCustomerInfo(ShopCustomerInfoDO shopCustomerInfoDO){
		try {
			 shopShowInfoManager.updateCustomerInfo(shopCustomerInfoDO);
			
		} catch (ManagerException e) {
			log.error("更新普通店铺联系人信息出错",e);
			return false;
		}
		return true;
	}
	
	/**
	 * 更新i小铺联系人信息
	 * 
	 * @param shopIshopInfoDO
	 * @return
	 */
	public boolean updateIshopInfo(ShopIshopInfoDO shopIshopInfoDO){
		try {
			 shopShowInfoManager.updateIshopInfo(shopIshopInfoDO);
		} catch (ManagerException e) {
			log.error("更新i小铺联系人信息出错",e);
			return false;
		}
		return true;
	}
	
	public MemcachedClient getShopMemcachedClient() {
		return shopMemcachedClient;
	}

	public void setShopMemcachedClient(MemcachedClient shopMemcachedClient) {
		this.shopMemcachedClient = shopMemcachedClient;
	}

	public CategoryCacheManager getCategoryCacheManager() {
		return categoryCacheManager;
	}

	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
}
