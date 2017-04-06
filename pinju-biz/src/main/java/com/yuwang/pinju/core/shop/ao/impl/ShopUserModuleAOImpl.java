package com.yuwang.pinju.core.shop.ao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.shop.ao.ShopUserModuleAO;
import com.yuwang.pinju.core.shop.manager.ShopBaseDesignerManager;
import com.yuwang.pinju.core.shop.manager.ShopModulePrototypeManager;
import com.yuwang.pinju.core.shop.manager.ShopUserModuleParamManager;
import com.yuwang.pinju.core.shop.manager.ShopUserPageManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.core.util.ShopUserModuleFactory;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * @author KSCN
 * 
 *         2011-7-4
 */
public class ShopUserModuleAOImpl extends BaseAO implements ShopUserModuleAO {

	private MemcachedClient shopMemcachedClient;
	private ShopUserModuleParamManager shopUserModuleParamManager;
	private ShopModulePrototypeManager shopModulePrototypeManager;
	private ShopUserPageManager shopUserPageManager;

	public ShopModulePrototypeManager getShopModulePrototypeManager() {
		return shopModulePrototypeManager;
	}

	public void setShopModulePrototypeManager(
			ShopModulePrototypeManager shopModulePrototypeManager) {
		this.shopModulePrototypeManager = shopModulePrototypeManager;
	}

	public MemcachedClient getShopMemcachedClient() {
		return shopMemcachedClient;
	}

	public void setShopMemcachedClient(MemcachedClient shopMemcachedClient) {
		this.shopMemcachedClient = shopMemcachedClient;
	}

	public ShopUserModuleParamManager getShopUserModuleParamManager() {
		return shopUserModuleParamManager;
	}

	public void setShopUserModuleParamManager(
			ShopUserModuleParamManager shopUserModuleParamManager) {
		this.shopUserModuleParamManager = shopUserModuleParamManager;
	}

	public ShopUserPageManager getShopUserPageManager() {
		return shopUserPageManager;
	}

	public void setShopUserPageManager(ShopUserPageManager shopUserPageManager) {
		this.shopUserPageManager = shopUserPageManager;
	}

	public String getKeeperPromoteIds(long userId){
		String ids=null;
		try{
			ShopUserPageDO shopUserPageDO = new ShopUserPageDO();
			shopUserPageDO.setUserId(userId);
			List<ShopUserPageDO> shopUserPageDOlist = shopUserPageManager.selectShopUserPage(shopUserPageDO);
			Long userPageId = null;
			for(ShopUserPageDO tempShopUserPageDO:shopUserPageDOlist){
				if(tempShopUserPageDO.getPageId().equals(2)){
					userPageId = tempShopUserPageDO.getId();
				}
			}
			if(userPageId != null){
				ShopUserModuleParamDO shopUserModuleParamDO = new ShopUserModuleParamDO();
				shopUserModuleParamDO.setUserPageId(userPageId);
				shopUserModuleParamDO.setModuleId(11L);
				List<ShopUserModuleParamDO> shopUserModuleParamDOlist = shopUserModuleParamManager.queryShopUserModuleParam(shopUserModuleParamDO);
				if(shopUserModuleParamDOlist.size()>0){
					ids = shopUserModuleParamDOlist.get(0).getConfig("ITEMIDS", true);
				}
			}
		}catch(ManagerException e){
			log.error("获取掌柜推荐商品id出错", e);
		}
		return ids;
	}
	private static Map<Long, Integer> shopModulePrototypeMap = null;

	private void setModulePrototype() throws Exception {
		List<ShopModulePrototypeDO> list;
		try {
			list = shopModulePrototypeManager.getDesignerModuleByUserPage(null,
					null);
			if (list == null || list.size() == 0)
				return;
			shopModulePrototypeMap = new HashMap<Long, Integer>();
			for (ShopModulePrototypeDO entity : list)
				shopModulePrototypeMap.put(entity.getId().longValue(),
						new Integer(entity.getConfig("CACHE_TIME")));
		} catch (ManagerException e) {
			throw e;
		}
	}

	private Integer getCacheTime(Long moduleId) throws Exception {
		if (shopModulePrototypeMap == null)
			setModulePrototype();
		return (Integer) shopModulePrototypeMap.get(moduleId);
	}

	/**
	 * 获取模板HTML
	 * @param shopUserModuleParamDO
	 * @param isRelease
	 * @param displayFltKey
	 * @param isDisplay
	 * @return
	 */
	@Override
	public String getModuleHtml(ShopUserModuleParamDO shopUserModuleParamDO,
			boolean isRelease, String displayFltKey, boolean isDisplay) {
		// TODO Auto-generated method stub
		ShopBaseDesignerManager manager = ShopUserModuleFactory.getInstance()
				.getModuleManager(
						shopUserModuleParamDO.getModuleId().intValue());
		if (manager == null){
			log.error(shopUserModuleParamDO.getModuleId().intValue()+"没有获取到模块的Manager");
			return "";
		}
		try {
			return getModuleHtml(manager, shopUserModuleParamDO, isRelease,
					displayFltKey, isDisplay);
		} catch (Exception e) {
			log.error("获取模板HMTL错误",e);
		}
		return "";
	}

	/**
	 * 获取模板HTML
	 * @param shopUserModuleParamDO
	 * @param isRelease
	 * @param displayFltKey
	 * @param isDisplay
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getModuleHtml(ShopBaseDesignerManager manager,
			ShopUserModuleParamDO shopUserModuleParamDO, boolean isRelease,
			String displayFltKey, boolean isDisplay) throws Exception {
		if (!isRelease
				||shopUserModuleParamDO.getModuleId().equals(10L)
				||shopUserModuleParamDO.getModuleId().equals(11L))
			return manager.createModuleHtml(shopUserModuleParamDO, isRelease,
					displayFltKey, isDisplay);
		String key = ShopConstants
				.createDesignModuleCacheKey(shopUserModuleParamDO);// 生成缓存的KEY
		try {
			Map<String, String> map = (Map<String, String>) shopMemcachedClient
					.get(ShopConstants.SHOP_MODULE_CACHE_KEY
							+ shopUserModuleParamDO.getShopId());// 取一级缓存，即缓存店铺所有页面及模块的缓存KEY
			if (map == null)
				map = new HashMap<String, String>();
			String html = null;
			if (!map.containsKey(key)) {// 未命中，从数据库中或者接口中取相应数据，同时保存到缓存中
				html = manager.createModuleHtml(shopUserModuleParamDO,
						isRelease, displayFltKey, isDisplay);
				shopMemcachedClient.set(key, getCacheTime(shopUserModuleParamDO
						.getModuleId()), html);
				map.put(key, key);
				shopMemcachedClient.set(ShopConstants.SHOP_MODULE_CACHE_KEY
						+ shopUserModuleParamDO.getShopId(), 0, map);
				return html;
			}
			html = shopMemcachedClient.get(key);// 直接从缓存中取数据
			if (html != null) {
				log
						.debug("#################module html get out from cache; KEY="
								+ key);
				return html;
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
		return manager.createModuleHtml(shopUserModuleParamDO, isRelease,
				displayFltKey, isDisplay);
	}

	@Override
	public Object deleteShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) {
		try {
			return shopUserModuleParamManager
					.deleteShopUserModuleParam(shopUserModuleParamDO);
		} catch (ManagerException e) {
			log.error("删除模板发生错误",e);
		}
		return null;
	}

	/**
	 * 根据传入的参数插入记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Object insertShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) {
		try {
			return shopUserModuleParamManager
					.insertShopUserModuleParam(shopUserModuleParamDO);
		} catch (ManagerException e) {
			log.error("插入模板发生错误",e);
		}
		return null;
	}

	/**
	 * 根据传入的参数查询记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public List<ShopUserModuleParamDO> queryShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) {
		try {
			return shopUserModuleParamManager
					.queryShopUserModuleParam(shopUserModuleParamDO);
		} catch (ManagerException e) {
			log.error("查询模板发生错误",e);
		}
		return null;
	}

	/**
	 * 根据传入的参数修改CONFIGURATION字段
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Object updateShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) {
		try {
			return shopUserModuleParamManager
					.updateShopUserModuleParam(shopUserModuleParamDO);
		} catch (ManagerException e) {
			log.error("修改模板发生错误",e);
		}
		return null;
	}

	/**
	 * 保存用户参数列表 根据需要保存的内容进行判断是否为发布保存，还是编辑保存。 即对saveConfig及releaseConfig进行判断动态更新。
	 * 
	 * @param shopUserModuleParamDO
	 * 
	 */
	@Override
	public Object saveModuleParam(ShopUserModuleParamDO shopUserModuleParamDO) {
		try {
			return shopUserModuleParamManager
					.saveModuleParam(shopUserModuleParamDO);
		} catch (ManagerException e) {
			log.error("保存模板发生错误",e);
		}
		return null;
	}
}
