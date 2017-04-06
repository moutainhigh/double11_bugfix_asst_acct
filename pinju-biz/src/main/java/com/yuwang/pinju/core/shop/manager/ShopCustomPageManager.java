package com.yuwang.pinju.core.shop.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;


/**
 * 店铺自定义页面
 * 
 * @author yisong
 *
 * @since 2011-6-20
 */
public interface ShopCustomPageManager {
	public List<ShopModulePrototypeDO> getCustomModuleByPage(ShopModulePrototypeDO shopModulePrototypeDO) throws ManagerException;
	
	/**
	 * 保存自定义模块
	 * @param shopUserPageDO
	 * @return
	 * @throws ManagerException
	 */
	Object saveCustomModuleByPage(ShopUserPageDO shopUserPageDO) throws ManagerException;
	
	/**
	 * 更新自定义模块
	 * @param shopUserPageDO
	 * @return
	 * @throws ManagerException
	 */
	Object updateCustomModuleByPage(ShopUserPageDO shopUserPageDO) throws ManagerException;
	
	/**
	 * 获取自定义模块
	 * @param shopUserPageDO
	 * @return
	 * @throws ManagerException
	 */
	ShopUserPageDO queryCustomPageModule(ShopUserPageDO shopUserPageDO)throws ManagerException;
}
