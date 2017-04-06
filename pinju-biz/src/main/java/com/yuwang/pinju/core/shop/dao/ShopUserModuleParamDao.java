package com.yuwang.pinju.core.shop.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public interface ShopUserModuleParamDao {
	/**
	 * 根据传入的参数插入记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object insertShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException;

	/**
	 * 根据传入的参数修改CONFIGURATION字段
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException;

	/**
	 * 根据页面编号删除相关记录
	 * 
	 * @param userPageId
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserModuleParamByPage(Long userPageId)
			throws DaoException;

	/**
	 * 根据传入的参数删除记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException;

	/**
	 * 根据传入的参数查询记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserModuleParamDO> queryShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException;

	/**
	 * 发布用户装修页面
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object releaseShopUserModule(
			ShopUserModuleParamDO shopUserModuleParamDO) throws DaoException;

	/**
	 * 还原用户所有装修模块
	 * 
	 * @param userPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object restoreUserModule(ShopUserModuleParamDO userModule)
			throws DaoException;
}
