/**
 * 
 */
package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopModulePrototypeDAO;
import com.yuwang.pinju.core.shop.manager.ShopModulePrototypeManager;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;

/**
 * @author liyouguo
 * 
 */
public class ShopModulePrototypeManagerImpl extends BaseManager implements
		ShopModulePrototypeManager {
	
	private ShopModulePrototypeDAO shopModulePrototypeDAO;
	

	public ShopModulePrototypeDAO getShopModulePrototypeDAO() {
		return shopModulePrototypeDAO;
	}


	public void setShopModulePrototypeDAO(
			ShopModulePrototypeDAO shopModulePrototypeDAO) {
		this.shopModulePrototypeDAO = shopModulePrototypeDAO;
	}


	/**
	 * 获取待编辑的页面的指定部分可添加的模块
	 * 
	 * @param 
	 * 			pageId 		--页面编号与表SHOP_PAGE_PROTOTYPE关联 
	 * 			type 		--页面的哪个部分（上，左，右，下）
	 * @return
	 */
	@Override
	public List<ShopModulePrototypeDO> getDesignerModuleByUserPage(
			Integer pageId, Integer type) throws ManagerException {
		// TODO Auto-generated method stub
		try {
			ShopModulePrototypeDO shopModulePrototypeDO = new ShopModulePrototypeDO();
			shopModulePrototypeDO.setPageId(pageId);
			shopModulePrototypeDO.setType(type);
			return shopModulePrototypeDAO.getDesignerModuleByPage(shopModulePrototypeDO);
		} catch (DaoException e) {
			throw new ManagerException("获取所有待装修的页面出错", e);
		}
	}
}
