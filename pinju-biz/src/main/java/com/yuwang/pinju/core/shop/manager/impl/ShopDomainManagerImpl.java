package com.yuwang.pinju.core.shop.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopDomainDAO;
import com.yuwang.pinju.core.shop.manager.ShopDomainManager;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

public class ShopDomainManagerImpl extends BaseManager implements ShopDomainManager {
	private ShopDomainDAO shopDomainDAO;
	public ShopDomainDAO getShopDomainDAO() {
		return shopDomainDAO;
	}

	public void setShopDomainDAO(ShopDomainDAO shopDomainDAO) {
		this.shopDomainDAO = shopDomainDAO;
	}

	/**
	 * 根据domian店铺域名查询店铺信息
	 * @author 杨昭
	 * @return shopbaseinfo对象
	 * @since 2011-10-9
	 */
	@Override
	public ShopInfoDO getShopDomainObject(Long userId, String domain) throws ManagerException {
		try {
			return shopDomainDAO.getShopDomainObject(userId, domain);
		} catch (DaoException e) {
			log.error("根据domian店铺域名查询店铺信息!", e);
			throw new ManagerException("根据domian店铺域名查询店铺信息!", e);
		}
	}

	 /**
	  * 根据userId修改店铺域名
	  * @author 杨昭
	  * @param userId
	  * @param domain
	  * @return 受影响行数
	  * @throws DaoException 
	  * @since 2011-10-9
	  */
	@Override
	public Object updateShopDomain(ShopInfoDO shopInfoDO) throws ManagerException {
		try {
			return shopDomainDAO.updateShopDomain(shopInfoDO);
		} catch (DaoException e) {
			log.error("根据userId修改店铺域名!", e);
			throw new ManagerException("根据userId修改店铺域名!", e);
		}
	}

}
