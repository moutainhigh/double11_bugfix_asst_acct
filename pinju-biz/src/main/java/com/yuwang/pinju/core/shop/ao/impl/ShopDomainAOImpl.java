package com.yuwang.pinju.core.shop.ao.impl;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.ao.ShopDomainAO;
import com.yuwang.pinju.core.shop.manager.ShopDomainManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class ShopDomainAOImpl extends BaseAO implements ShopDomainAO {
	private ShopDomainManager shopDomainManager;
	public ShopDomainManager getShopDomainManager() {
		return shopDomainManager;
	}
	public void setShopDomainManager(ShopDomainManager shopDomainManager) {
		this.shopDomainManager = shopDomainManager;
	}
	/**
	 * 根据domian店铺域名查询店铺信息
	 * @author 杨昭
	 * @return shopbaseinfo对象
	 * @since 2011-10-9
	 */
	@Override
	public ShopInfoDO getShopDomainObject(Long userId, String domain){
		try {
			return shopDomainManager.getShopDomainObject(userId, domain);
		}catch (ManagerException e) {
			log.error("根据domian店铺域名查询店铺信息",e);
		}
		return null;
	}
	 /**
	  * 根据userId修改店铺域名
	  * @author 杨昭
	  * @param userId
	  * @param domain
	  * @return boolean
	  * @since 2011-10-9
	  */
	@Override
	public boolean updateShopDomain(ShopInfoDO shopInfoDO){
		try {
			Integer number = (Integer)shopDomainManager.updateShopDomain(shopInfoDO);
			if(number<0){
				return false;
			}
		}catch (ManagerException e) {
			log.error("根据userId修改店铺域名",e);
		}
		return true;
	}

}
