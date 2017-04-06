/**
 * 
 */
package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopPagePrototypeDAO;
import com.yuwang.pinju.core.shop.manager.ShopPagePrototypeManager;
import com.yuwang.pinju.domain.shop.ShopPagePrototypeDO;

/**
 * @author liyouguo
 * 
 */
public class ShopPagePrototypeManagerImpl extends BaseManager implements
		ShopPagePrototypeManager {

	private ShopPagePrototypeDAO shopPagePrototypeDAO;

	public ShopPagePrototypeDAO getShopPagePrototypeDAO() {
		return shopPagePrototypeDAO;
	}

	public void setShopPagePrototypeDAO(
			ShopPagePrototypeDAO shopPagePrototypeDAO) {
		this.shopPagePrototypeDAO = shopPagePrototypeDAO;
	}

	/**
	 * 获取所有待编辑的页面
	 * 
	 * @return
	 */
	public List<ShopPagePrototypeDO> getAllDesignerPage() throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopPagePrototypeDAO.getAllDesignerPage();
		} catch (DaoException e) {
			throw new ManagerException("获取所有待装修的页面出错", e);
		}
	}
	
	/**
	 * 获取指定类型页面
	 * 
	 * @param pageId
	 * @return
	 * @throws ManagerException
	 *
	 */
	public ShopPagePrototypeDO queryPageProtoByPageid(Integer pageId)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopPagePrototypeDAO.queryPageProtoByPageid(pageId);
		} catch (DaoException e) {
			throw new ManagerException("获取指定装修原型页面出错", e);
		}
	}
}
