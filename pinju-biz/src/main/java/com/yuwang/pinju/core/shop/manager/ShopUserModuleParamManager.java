package com.yuwang.pinju.core.shop.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public interface ShopUserModuleParamManager {
	/**
	 * 根据传入的参数插入记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object insertShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws ManagerException;

	/**
	 * 根据传入的参数修改CONFIGURATION字段
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws ManagerException;

	/**
	 * 根据传入的参数删除记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws ManagerException;

	/**
	 * 根据传入的参数查询记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserModuleParamDO> queryShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO) throws ManagerException;
	
	/**
	 * 保存用户参数列表 根据需要保存的内容进行判断是否为发布保存，还是编辑保存。 即对saveConfig及releaseConfig进行判断动态更新。
	 * 
	 * @param shopUserModuleParamDO
	 * 
	 */
	public Object saveModuleParam(ShopUserModuleParamDO shopUserModuleParamDO) throws ManagerException;
}
