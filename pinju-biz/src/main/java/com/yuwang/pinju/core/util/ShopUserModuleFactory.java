/**
 * 
 */
package com.yuwang.pinju.core.util;

import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.core.shop.manager.ShopBaseDesignerManager;
import com.yuwang.pinju.core.util.config.ModuleManagerConfig;

/**
 * @author liyouguo
 * 
 */
public class ShopUserModuleFactory {
	private static ShopUserModuleFactory shopUserModuleFactory = null;
	private static Map<Integer, ShopBaseDesignerManager> moduleKeys = new HashMap<Integer, ShopBaseDesignerManager>();

	private ShopUserModuleFactory() {
		for (Object obj : ModuleManagerConfig.getInstance().getProperties()
				.keySet()) {
			String key = (String) obj;
			moduleKeys.put(new Integer(key),
					(ShopBaseDesignerManager) SpringBeanFactory
							.getBean(ModuleManagerConfig.getInstance()
									.getProperty(key)));
		}
	}

	public static ShopUserModuleFactory getInstance() {
		if (shopUserModuleFactory == null)
			shopUserModuleFactory = new ShopUserModuleFactory();
		return shopUserModuleFactory;
	}

	public ShopBaseDesignerManager getModuleManager(Integer moduleId) {
		return moduleKeys.get(moduleId);
	}
}
