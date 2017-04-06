/**
 * 
 */
package com.yuwang.pinju.singleton;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Project: pinju-model
 * @Package com.yuwang.pinju.singleton
 * @Description: 配置信息详情
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a>
 * @date 2011-10-27 下午6:23:41
 * @version V1.0
 */

public class ConfigManagerInfo {

	private Map<String, String> configMapInfo = new ConcurrentHashMap<String, String>();

	private static ConfigManagerInfo singletonInstance;

	public static ConfigManagerInfo getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new ConfigManagerInfo();
		}
		return singletonInstance;
	}

	public Map<String, String> getConfigMapInfo() {
		return configMapInfo;
	}

	/**
	 * 获取单个value值
	 * 
	 * @param key
	 * @return String
	 */
	public static String getStringValue(String key) {
		String value = singletonInstance.getConfigMapInfo().get(key);
		return value;
	}

	/**
	 * 提供以,分割的数据
	 * 
	 * @param key
	 * @return String[]
	 */
	public static String[] getStringValues(String key) {
		String values = singletonInstance.getConfigMapInfo().get(key);
		if (values != null && !values.equals("")) {
			String[] value = values.split(",");
			return value;
		}
		return null;
	}

	/**
	 * 设置系统配置值
	 * 
	 * @param configMapInfo
	 */
	public static void setConfigMapInfo(Map<String, String> configMapInfo) {
		ConfigManagerInfo.getInstance().getConfigMapInfo().putAll(configMapInfo);
	}

}
