package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;
import java.util.Properties;

import com.yuwang.pinju.common.JsoupUtil;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.shop.dao.ShopCustomPageDAO;
import com.yuwang.pinju.core.shop.manager.ShopCustomPageManager;
import com.yuwang.pinju.core.shop.manager.ShopUserModuleParamManager;
import com.yuwang.pinju.domain.shop.ShopModulePrototypeDO;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;
import com.yuwang.pinju.domain.shop.ShopUserPageDO;

/**
 * 自定义页面
 * 
 * @author xueqi
 * 
 * @since 2011-7-4
 */
public class ShopCustomPageManagerImpl extends ShopBaseDesignerManagerImpl
		implements ShopCustomPageManager {

	/**
	 * 店铺自定义数据接口
	 */
	private ShopCustomPageDAO ShopCustomPageDAO;

	private ShopUserModuleParamManager shopUserModuleParamManager;

	public ShopUserModuleParamManager getShopUserModuleParamManager() {
		return shopUserModuleParamManager;
	}

	public void setShopUserModuleParamManager(
			ShopUserModuleParamManager shopUserModuleParamManager) {
		this.shopUserModuleParamManager = shopUserModuleParamManager;
	}

	public void setShopCustomPageDAO(ShopCustomPageDAO shopCustomPageDAO) {
		ShopCustomPageDAO = shopCustomPageDAO;
	}

	/**
	 * 获取所有自定义页面
	 * 
	 * @param shopModulePrototypeDO
	 * @return List<ShopModulePrototypeDO>
	 * @throws ManagerException
	 */
	/**
	 * 获得自定义模块
	 * 
	 * @param shopModulePrototypeDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public List<ShopModulePrototypeDO> getCustomModuleByPage(
			ShopModulePrototypeDO shopModulePrototypeDO)
			throws ManagerException {
		try {
			return ShopCustomPageDAO
					.getCustomModuleByPage(shopModulePrototypeDO);
		} catch (DaoException e) {
			throw new ManagerException("获取所有自定义页面出错", e);
		}
	}

	/**
	 * 保存自定义页面
	 * 
	 * @param shopUserPageDO
	 * @return 保存的返回信息
	 * @throws ManagerException
	 */

	/**
	 * 保存自定义模块
	 * 
	 * @param shopUserPageDO
	 * @return
	 * @throws DaoException
	 */
	public Object saveCustomModuleByPage(ShopUserPageDO shopUserPageDO)
			throws ManagerException {
		try {
			return ShopCustomPageDAO.saveCustomModuleByPage(shopUserPageDO);
		} catch (DaoException e) {
			throw new ManagerException("保存自定义页面出错", e);
		}
	}

	/**
	 * 获取所有自定义页面
	 * 
	 * @param shopUserPageDO
	 * @return
	 * @throws ManagerException
	 */
	/**
	 * 获取自定义模块
	 * 
	 * @param shopUserPageDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public ShopUserPageDO queryCustomPageModule(ShopUserPageDO shopUserPageDO)
			throws ManagerException {
		try {
			return ShopCustomPageDAO.queryCustomPageModule(shopUserPageDO);
		} catch (DaoException e) {
			throw new ManagerException("获取所有自定义页面出错", e);
		}

	}

	/**
	 * 更新自定义页面
	 * 
	 * @param shopUserPageDO
	 * @return
	 * @throws ManagerException
	 */
	/**
	 * 更新自定义模块
	 * 
	 * @param shopUserPageDO
	 * @return
	 * @throws DaoException
	 */
	@Override
	public Object updateCustomModuleByPage(ShopUserPageDO shopUserPageDO)
			throws ManagerException {
		try {
			return ShopCustomPageDAO.updateCustomModuleByPage(shopUserPageDO);
		} catch (DaoException e) {
			throw new ManagerException("更新自定义页面出错", e);
		}
	}

	/**
	 * 获取除了MODULE表中config参数以外的其它参数，需要各模块实现
	 * 
	 * @param shopUserModuleParamDO
	 * @param properties
	 */
	@Override
	protected void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		try {
			List<ShopUserModuleParamDO> paramList = shopUserModuleParamManager
					.queryShopUserModuleParam(shopUserModuleParamDO);
			if (paramList != null && paramList.size() > 0) {
				ShopUserModuleParamDO shopUserModuleParamDO2 = paramList.get(0);
				if(properties.get("__ISRELEASE")!=null&&properties.get("__ISRELEASE").equals("TRUE")){
					properties.put("html", JsoupUtil.getDetailDescription(shopUserModuleParamDO2.getRelease_html() == null ? "" : shopUserModuleParamDO2.getRelease_html()));
				}else{
					properties.put("html", JsoupUtil.getDetailDescription(shopUserModuleParamDO2.getSave_html() == null ? "" : shopUserModuleParamDO2.getSave_html()));
				}
				
			}
			properties.put("pageId", shopUserModuleParamDO.getUserPageId());
			properties.put("moduleId", shopUserModuleParamDO.getModuleId());

		} catch (NullPointerException nException) {
			log.error("获取模块信息用来显示展示页", nException);

		} catch (Exception e) {
			log.error("获取模块信息用来显示展示页", e);
		}
	}

	/**
	 * 获取除了MODULE表中config参数以外，需要调用其它接口获取生成HTML的数据，需要各模块实现
	 * 
	 * @param shopUserModuleParamDO
	 * @param properties
	 */
	@Override
	protected void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties) {
		try {
			List<ShopUserModuleParamDO> paramList = shopUserModuleParamManager
					.queryShopUserModuleParam(shopUserModuleParamDO);
			if (paramList != null && paramList.size() > 0) {
				ShopUserModuleParamDO shopUserModuleParamDO2 = paramList.get(0);
				properties.put("html", shopUserModuleParamDO2.getSave_html() == null ? "" : shopUserModuleParamDO2.getSave_html());
			}
			properties.put("pageId", shopUserModuleParamDO.getUserPageId());
			properties.put("moduleId", shopUserModuleParamDO.getModuleId());

		} catch (NullPointerException nException) {
			log.error("获取模块信息用来显示编辑页", nException);
		} catch (Exception e) {
			log.error("获取模块信息用来显示编辑页", e);
		}
	}
}
