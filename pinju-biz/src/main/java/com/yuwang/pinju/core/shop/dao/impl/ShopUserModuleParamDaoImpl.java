package com.yuwang.pinju.core.shop.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.shop.dao.ShopUserModuleParamDao;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

/**
 * 装修模块DAO层实现
 * @author liyouguo
 *
 * @since 2011-7-4
 */
public class ShopUserModuleParamDaoImpl extends BaseDAO implements
		ShopUserModuleParamDao {

	/**
	 * 根据传入的参数删除记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException {
		return super.executeUpdate("shopUserModuleParam.deleteShopUserModuleParam",
				shopUserModuleParamDO);
	}

	/**
	 * 根据传入的参数插入记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object insertShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException {
		return super.executeInsert("shopUserModuleParam.insertShopUserModuleParam",
				shopUserModuleParamDO);
	}

	/**
	 * 根据传入的参数查询记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public List<ShopUserModuleParamDO> queryShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException {
		return super.executeQueryForList("shopUserModuleParam.queryShopUserModuleParam",
				shopUserModuleParamDO);
	}

	/**
	 * 根据传入的参数修改CONFIGURATION字段
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException {
		return super.executeUpdate("shopUserModuleParam.updateShopUserModuleParam",
				shopUserModuleParamDO);
	}

	/**
	 * 发布用户装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object releaseShopUserModule(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserModuleParam.releaseShopUserModule",
				shopUserModuleParamDO);
	}

	/**
	 * 根据页面编号删除相关记录
	 * 
	 * @param userPageId
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserModuleParamByPage(Long userPageId)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserModuleParam.deleteShopUserModuleParamByPage",
				userPageId);
	}

	/**
	 * 还原用户所有装修模块
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object restoreUserModule(ShopUserModuleParamDO userModule)
			throws DaoException {
		// TODO Auto-generated method stub
		return super.executeUpdate("shopUserModuleParam.restoreUserModule", userModule);
	}
}
