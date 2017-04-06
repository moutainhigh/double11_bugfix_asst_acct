package com.yuwang.pinju.core.shop.ao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public interface ShopUserModuleAO {
	/**
	 * 根据传入的参数插入记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object insertShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO);

	/**
	 * 根据传入的参数修改CONFIGURATION字段
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object updateShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO);

	/**
	 * 根据传入的参数删除记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public Object deleteShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO);

	/**
	 * 根据传入的参数查询记录
	 * 
	 * @param shopUserModuleParamDO
	 * @return
	 * @throws DaoException
	 */
	public List<ShopUserModuleParamDO> queryShopUserModuleParam(
			ShopUserModuleParamDO shopUserModuleParamDO);
	
	/**
	 * 获取模板HTML
	 * @param shopUserModuleParamDO
	 * @param isRelease
	 * @param displayFltKey
	 * @param isDisplay
	 * @return
	 */
	public String getModuleHtml(ShopUserModuleParamDO shopUserModuleParamDO,boolean isRelease,String displayFltKey,boolean isDisplay);

	/**
	 * 保存用户参数列表 根据需要保存的内容进行判断是否为发布保存，还是编辑保存。 即对saveConfig及releaseConfig进行判断动态更新。
	 * 
	 * @param shopUserModuleParamDO
	 * 
	 */
	public Object saveModuleParam(ShopUserModuleParamDO shopUserModuleParamDO);
	
	/**
	 * 根据用户编号查询出此店铺的掌柜推荐的推荐商品ID，逗号分隔
	 * 
	 * @param shopUserModuleParamDO
	 * 
	 */
	public String getKeeperPromoteIds(long userId);
}
