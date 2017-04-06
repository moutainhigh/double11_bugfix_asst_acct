package com.yuwang.pinju.core.shop.manager.impl;

import java.util.List;
import java.util.Properties;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.constant.shop.ShopConstants;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.shop.dao.ShopUserModuleParamDao;
import com.yuwang.pinju.core.shop.manager.ShopBaseDesignerManager;
import com.yuwang.pinju.core.util.config.ModulePathConfig;
import com.yuwang.pinju.core.util.freemarker.FreemarkerUtil;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public abstract class ShopBaseDesignerManagerImpl extends BaseManager implements ShopBaseDesignerManager{
	protected  ShopUserModuleParamDao shopUserModuleParamDao;

	public ShopUserModuleParamDao getShopUserModuleParamDao() {
		return shopUserModuleParamDao;
	}

	public void setShopUserModuleParamDao(
			ShopUserModuleParamDao shopUserModuleParamDao) {
		this.shopUserModuleParamDao = shopUserModuleParamDao;
	}
	
	public Properties selectModuleParamList(
			ShopUserModuleParamDO shopUserModuleParamDO, boolean isRelease) {
		// TODO Auto-generated method stub
		try {
			List<ShopUserModuleParamDO> list = shopUserModuleParamDao.queryShopUserModuleParam(shopUserModuleParamDO);
			Properties prop = new Properties();
			if (list==null||list.size() == 0) {
				prop.put("_SPLIT", ShopConstants.SHOP_CATEGORY_SPLIT);

				if(shopUserModuleParamDO.getShopId()!=null){
					prop.put("_SHOPID", shopUserModuleParamDO.getShopId());
				}
				prop.put("_USERPAGEID", shopUserModuleParamDO.getRealUserPageId());
				prop.put("_MODULEID", shopUserModuleParamDO.getModuleId());
				prop.put("_SELLERID", shopUserModuleParamDO.getUserId());
				prop.put("picServer", PinjuConstant.VIEW_IMAGE_SERVER);
				if(shopUserModuleParamDO.getPreview()!= null){
					prop.put("_PREVIEW", shopUserModuleParamDO.getPreview());
				}
			}else{
				ShopUserModuleParamDO entity = list.get(0);
				prop = entity.getConfigs(isRelease);
				prop.put("_ID", entity.getId());

				if(shopUserModuleParamDO.getShopId()!=null){
					prop.put("_SHOPID", shopUserModuleParamDO.getShopId());
				}
				prop.put("_USERPAGEID", shopUserModuleParamDO.getRealUserPageId());
				prop.put("_MODULEID", shopUserModuleParamDO.getModuleId());
				prop.put("_SELLERID", shopUserModuleParamDO.getUserId());
				prop.put("_SPLIT", ShopConstants.SHOP_CATEGORY_SPLIT);
				prop.put("picServer", PinjuConstant.VIEW_IMAGE_SERVER);
				if(shopUserModuleParamDO.getPreview()!= null){
					prop.put("_PREVIEW", shopUserModuleParamDO.getPreview());
				}
				return prop;
			}
			
			return prop;
		} catch (DaoException e) {
			logger.error(e);
		}
		return null;
	}

	public Properties getAllModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, boolean isRelease) {
		Properties properties = selectModuleParamList(shopUserModuleParamDO,
				isRelease);
		getOtherModuleParam(shopUserModuleParamDO, properties);
		return properties;
	}

	/**
	 * 获取除了MODULE表中config参数以外的其它参数，需要各模块实现
	 * 
	 * @param shopUserModuleParamDO
	 * @param properties
	 */
	protected abstract void getOtherModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties);

	

	/**
	 * 获取除了MODULE表中config参数以外，需要调用其它接口获取生成HTML的数据，需要各模块实现
	 * 
	 * @param shopUserModuleParamDO
	 * @param properties
	 */
	protected abstract void getOtherModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, Properties properties);

	private Properties getAllModuleContent(
			ShopUserModuleParamDO shopUserModuleParamDO, boolean isRelease) {
		Properties properties = selectModuleParamList(shopUserModuleParamDO,
				isRelease);
		if(isRelease)
			properties.put("__ISRELEASE", "TRUE");
		getOtherModuleContent(shopUserModuleParamDO, properties);
		return properties;
	}

	public String createModuleHtml(ShopUserModuleParamDO shopUserModuleParamDO,
			boolean isRelease, String editFltKey, boolean isDisplay) {
		String moduleHtml = "";
		Properties properties = null;
		if (isDisplay)
			properties = getAllModuleContent(shopUserModuleParamDO, isRelease);
		else
			properties = getAllModuleParam(shopUserModuleParamDO, isRelease);
		try {
			moduleHtml = FreemarkerUtil.process(properties, ModulePathConfig
					.getInstance().getProperty(editFltKey));
		} catch (Exception e) {
			logger.error("生产静态模板页面发生错误",e);
		}
		return moduleHtml;
	}
}
