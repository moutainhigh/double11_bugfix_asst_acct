package com.yuwang.pinju.core.shop.manager;

import java.util.Properties;

import com.yuwang.pinju.domain.shop.ShopUserModuleParamDO;

public interface ShopBaseDesignerManager {
	/**
	 * 生成模块对应的HTML内容
	 * 
	 * @param shopUserModuleParamDO
	 * @param isRelease
	 *            --是否生成发布内容
	 * @param displayFltKey
	 *            --生成html的模板KEY,从相关配置中读取（包括生成编辑层的HTML及显示模块的HTML）
	 * @param isDisplay
	 *            --是否生成显示的HTML
	 * @return
	 */
	public String createModuleHtml(ShopUserModuleParamDO shopUserModuleParamDO,
			boolean isRelease, String displayFltKey, boolean isDisplay);

	/**
	 * 获取对应模块的参数列表（properties）
	 * 
	 * @param shopUserModuleParamDO
	 * @param isRelease
	 *            --是否取发布配置
	 * @return
	 */
	public Properties selectModuleParamList(
			ShopUserModuleParamDO shopUserModuleParamDO, boolean isRelease);

	
}
