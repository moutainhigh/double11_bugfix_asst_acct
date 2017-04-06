package com.yuwang.pinju.core.shop.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import com.yuwang.pinju.Constant.ShopConstant;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.shop.manager.ShopUserPageManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

@SuppressWarnings("unchecked")
public class ShopTopShopSignModuleManagerImpl extends
		ShopBaseDesignerManagerImpl implements
		com.yuwang.pinju.core.shop.manager.ShopTopShopSignModuleManager {
	private MemcachedClient shopMemcachedClient;
	private ShopShowInfoManager shopShowInfoManager;
	private ShopUserPageManager shopUserPageManager;

	public MemcachedClient getShopMemcachedClient() {
		return shopMemcachedClient;
	}

	public void setShopMemcachedClient(MemcachedClient shopMemcachedClient) {
		this.shopMemcachedClient = shopMemcachedClient;
	}

	@Override
	protected void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		try {
			properties.put("shopInfoDO", shopShowInfoManager
					.queryShopInfoByShopId(shopUserModuleParamDO.getShopId(), ShopConstant.APPROVE_STATUS_YES));
			ShopUserPageDO shopUserPageDO = new ShopUserPageDO();
			shopUserPageDO.setUserId(shopUserModuleParamDO.getUserId());
			shopUserPageDO.setShopId(shopUserModuleParamDO.getShopId());
			List<ShopUserPageDO> list = new ArrayList<ShopUserPageDO>();
			List<ShopUserPageDO> tmp = getShopUserPage(shopUserPageDO, "TRUE"
					.equals(properties.getProperty("__ISRELEASE", "FALSE")));
			for (ShopUserPageDO page : tmp) {
				if (page.getPageId().intValue() == ShopConstants.SHOP_FIRST_PAGE
						.intValue()) {
					properties.put("_FIRST_USERPAGE", page.getId());// 放置首页的编号
					continue;
				}
				if (page.getPageId().intValue() == ShopConstants.SHOP_CUSTOMER_PAGE
						.intValue()
						&& "Y".equals(page.getConfig("displayNavigate")))
					list.add(page);
			}
			properties.put("shopId", shopUserModuleParamDO.getShopId());
			properties.put("shopUserPageList", list);// 放置所有自定义页面
			properties.put("fileServer", PinjuConstant.VIEW_IMAGE_SERVER);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private List<ShopUserPageDO> getShopUserPage(ShopUserPageDO shopUserPageDO,
			boolean isRelease) throws ManagerException {
		if (!isRelease)
			return shopUserPageManager.selectShopUserPage(shopUserPageDO);
		String key = "shop.design.allpage." + shopUserPageDO.getShopId();
		try {
			Map<String, String> map = (Map<String, String>) shopMemcachedClient
					.get(ShopConstants.SHOP_MODULE_CACHE_KEY
							+ shopUserPageDO.getShopId());
			if (map == null)
				map = new HashMap<String, String>();
			List<ShopUserPageDO> list = null;
			if (!map.containsKey(key)) {
				list = shopUserPageManager.selectShopUserPage(shopUserPageDO);
				shopMemcachedClient.set(key, 0, list);
				map.put(key, key);
				shopMemcachedClient.set(ShopConstants.SHOP_MODULE_CACHE_KEY
						+ shopUserPageDO.getShopId(), 0, map);
				return list;
			}
			list = shopMemcachedClient.get(key);
			if (list != null) {
				log.debug("#################get all page from cache; KEY="
						+ key);
				return list;
			}
		} catch (TimeoutException e) {
			log.error("操作分布式缓存超时", e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("操作分布式缓存出错", e);
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			log.error("操作分布式缓存出错", e);
		}
		return shopUserPageManager.selectShopUserPage(shopUserPageDO);
	}

	@Override
	protected void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		try {
			ShopInfoDO shopInfoDO = shopShowInfoManager
					.queryShopBaseInfoByUser(shopUserModuleParamDO
							.getUserId(), ShopConstant.APPROVE_STATUS_YES);
			properties.put("shopInfoDO", shopInfoDO);
			properties.put("fileServer", PinjuConstant.VIEW_IMAGE_SERVER);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public ShopShowInfoManager getShopShowInfoManager() {
		return shopShowInfoManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public ShopUserPageManager getShopUserPageManager() {
		return shopUserPageManager;
	}

	public void setShopUserPageManager(ShopUserPageManager shopUserPageManager) {
		this.shopUserPageManager = shopUserPageManager;
	}
}
