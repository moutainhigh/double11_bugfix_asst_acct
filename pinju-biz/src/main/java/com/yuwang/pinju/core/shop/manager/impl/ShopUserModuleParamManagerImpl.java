package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopUserModuleParamDao;
import com.yuwang.pinju.core.shop.manager.ShopUserModuleParamManager;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

/**
 * 用户装修模块管理
 * @author liyouguo
 *
 * @since 2011-7-4
 */
public class ShopUserModuleParamManagerImpl extends BaseManager implements
		ShopUserModuleParamManager {

	private ShopUserModuleParamDao shopUserModuleParamDao;
	
	
	public ShopUserModuleParamDao getShopUserModuleParamDao() {
		return shopUserModuleParamDao;
	}

	public void setShopUserModuleParamDao(
			ShopUserModuleParamDao shopUserModuleParamDao) {
		this.shopUserModuleParamDao = shopUserModuleParamDao;
	}

	/**
	 * 根据传入的参数删除记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserModuleParamDao
					.deleteShopUserModuleParam(shopUserModuleParamDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("删除模块配置信息出错", e);
		}
	}

	/**
	 * 根据传入的参数插入记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object insertShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserModuleParamDao
					.insertShopUserModuleParam(shopUserModuleParamDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("插入模块配置信息出错", e);
		}
	}

	/**
	 * 根据传入的参数查询记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserModuleParamDO> queryShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserModuleParamDao
					.queryShopUserModuleParam(shopUserModuleParamDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("获取模块配置信息出错", e);
		}
	}

	/**
	 * 根据传入的参数修改CONFIGURATION字段
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			return shopUserModuleParamDao
					.updateShopUserModuleParam(shopUserModuleParamDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			throw new ManagerException("更新模块配置信息出错", e);
		}
	}

	/**
	 * 保存用户编辑模块的内容（需要前台传入ID来区分 新增、修改）
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws ManagerException
	 *
	 */
	public Object saveModuleParam(ShopUserModuleParamDO shopUserModuleParamDO)
			throws ManagerException {
		// TODO Auto-generated method stub
		try {
			if (shopUserModuleParamDO.getId() == null
					|| shopUserModuleParamDO.getId() == 0) {
				return shopUserModuleParamDao
						.insertShopUserModuleParam(shopUserModuleParamDO);
			} else
				return shopUserModuleParamDao
						.updateShopUserModuleParam(shopUserModuleParamDO);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			logger.error(e);
			throw new ManagerException("保存装修模块配置出错", e);
		}
	}
}
