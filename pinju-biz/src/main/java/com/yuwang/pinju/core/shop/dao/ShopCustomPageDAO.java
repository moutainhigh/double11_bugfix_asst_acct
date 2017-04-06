package com.yuwang.pinju.core.shop.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;


/**
 * 店铺自定义页面
 * 
 * @author yisong
 *
 * @since 2011-6-20
 */
public interface ShopCustomPageDAO {
	
	/**
	 * 获得自定义模块
	 * @param shopModulePrototypeDO
	 * @return
	 * @throws DaoException 
	 */
	public List<ShopModulePrototypeDO> getCustomModuleByPage(ShopModulePrototypeDO shopModulePrototypeDO) throws DaoException;
	
	/**
	 * 保存自定义模块
	 * @param shopUserPageDO
	 * @return
	 * @throws DaoException
	 */
	Object saveCustomModuleByPage(ShopUserPageDO shopUserPageDO) throws DaoException;
	
	/**
	 * 更新自定义模块
	 * @param shopUserPageDO
	 * @return
	 * @throws DaoException
	 */
	Object updateCustomModuleByPage(ShopUserPageDO shopUserPageDO) throws DaoException;
	
	/**
	 * 获取自定义模块
	 * @param shopUserPageDO
	 * @return
	 * @throws DaoException
	 */
	ShopUserPageDO queryCustomPageModule(ShopUserPageDO shopUserPageDO) throws DaoException;
}
