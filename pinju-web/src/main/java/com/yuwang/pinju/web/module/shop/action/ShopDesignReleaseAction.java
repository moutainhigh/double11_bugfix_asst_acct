/**
 * 
 */
package com.yuwang.pinju.web.module.shop.action;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.sf.json.JSONObject;

import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.shop.ao.ShopUserPageAO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;
import com.yuwang.pinju.web.module.shop.BaseWithUserAction;

/**
 * @author liyouguo
 * 
 */
public class ShopDesignReleaseAction extends BaseWithUserAction {
	private String result = "操作成功";
	private ShopUserPageAO shopUserPageAO;
	private MemcachedClient shopMemcachedClient;

	public MemcachedClient getShopMemcachedClient() {
		return shopMemcachedClient;
	}

	public void setShopMemcachedClient(MemcachedClient shopMemcachedClient) {
		this.shopMemcachedClient = shopMemcachedClient;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public ShopUserPageAO getShopUserPageAO() {
		return shopUserPageAO;
	}

	public void setShopUserPageAO(ShopUserPageAO shopUserPageAO) {
		this.shopUserPageAO = shopUserPageAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.opensymphony.xwork2.Action#execute()
	 */
	@Override
	@AssistantPermission(target = "shop", action = "deco-pub")
	public String execute() throws Exception {
		Integer shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		ShopUserPageDO userPageDO = new ShopUserPageDO();
		userPageDO.setShopId(getUserShopId());
		userPageDO.setUserId(getUserId());
		try {
			Object o = shopUserPageAO.releaseUserPage(userPageDO);
			if (o == null)
				result = "发布失败";
			else
				clearCache(userPageDO);
		} catch (Exception e) {
			log.error(e);
			result = "发布失败";
		}

		JSONObject json = new JSONObject();
		json.put("root", result);
		return SUCCESS;
	}

	public String restore() throws Exception {
		Integer shopId = getUserShopId();
		if(shopId == null){
			errorMessage = "用户未开店";
			return "error";
		}
		ShopUserPageDO userPageDO = new ShopUserPageDO();
		userPageDO.setShopId(getUserShopId());
		userPageDO.setUserId(getUserId());
		try {
			Object o = shopUserPageAO.restoreDesignPage(userPageDO);
			if (o == null)
				result = "还原失败";
		} catch (Exception e) {
			log.error(e);
			result = "还原失败";
		}

		JSONObject json = new JSONObject();
		json.put("root", result);
		return SUCCESS;
	}

	/**
	 * 清理缓存
	 */
	private void clearCache(ShopUserPageDO userPageDO) {
		// TODO Auto-generated method stub
		try {
			shopMemcachedClient.delete(ShopConstants.SHOP_MODULE_CACHE_KEY
					+ userPageDO.getShopId());
			StringBuffer key = new StringBuffer("shop.skin");
			key.append(".SHOPID=").append(userPageDO.getShopId());
			shopMemcachedClient.delete(key.toString());
			key = new StringBuffer("shop.fristpageconfiguration");
			key.append(".SHOPID=").append(userPageDO.getShopId());
			shopMemcachedClient.delete(key.toString());
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			log.error(e);
		}
	}
}
