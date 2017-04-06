/**
 * 
 */
package com.yuwang.pinju.core.constant.shop;

import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

/**
 * @author liyouguo
 * 
 */
public final class ShopConstants {
	public final static String SHOP_CATEGORY_SPLIT = "@!@";
	public final static String SHOP_VALUE_SPLIT = ",";
	public final static String SHOP_DEFAULT_CATEGORYNAME="未分类";
	public final static String SHOP_PAGE_TYPE1 = "";
	public final static String SHOP_NEWLINE = "\r\n";
	public final static Integer SHOP_FIRST_PAGE = 2;// 首页原型编号
	public final static Integer SHOP_CUSTOMER_PAGE = 3;// 自定义页面原型编号
	public final static String SHOP_MODULE_CACHE_KEY = "shop.SHOP_MODULE_CACHE_KEY";
	public final static String SHOP_SHOPID_CACHE_KEY = "shop.userid.";
	public final static Long SHOP_TOPSHOPSIGN_MODULEID = 7L;//店铺招牌moduleId
	
	/**
	 * 生成装修模块的缓存KEY
	 * @param userModule
	 * @return
	 */
	public static String createDesignModuleCacheKey(ShopUserModuleParamDO userModule) {
		return "shop.design.module." + userModule.getRealUserPageId() + "_"
				+ userModule.getModuleId();
	}
	
	/**
	 * 生成装修页面的缓存KEY
	 * @param userPageId
	 * @param pageId
	 * @return
	 */
	public static String createDesignPageCacheKey(Long userPageId, Integer pageId, Integer shopId) {
		StringBuffer key = new StringBuffer("shop.design.page.");
		if (userPageId != null)
			key.append("ID=").append(userPageId);
		else
			key.append("PAGEID=").append(pageId);
		key.append(".SHOPID=").append(shopId);
		return key.toString();
	}
}
