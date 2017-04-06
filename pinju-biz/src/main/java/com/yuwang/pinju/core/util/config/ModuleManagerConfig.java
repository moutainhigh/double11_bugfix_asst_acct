package com.yuwang.pinju.core.util.config;

public class ModuleManagerConfig extends BaseConfig {

	private static String filename = "/config/moduleManager.properties";

	private static ModuleManagerConfig m_conf = null;

	public ModuleManagerConfig() {
	}

	public String getPorpFileName() {
		return filename;
	}

	/**
	 * 通过给定的配置文件名实例化 ConnectConfig
	 * 
	 * @param String filen 配置文件名
	 * @exception
	 * @return ConnectConfig对象
	 */
	public static ModuleManagerConfig getInstance(String filen) {
		filename = filen;
		if (m_conf == null) {
			m_conf = new ModuleManagerConfig();
		}
		return m_conf;
	}

	/**
	 * 实例化 ConnectConfig
	 * 
	 * @param
	 * @exception
	 * @return ConnectConfig对象
	 */
	public static ModuleManagerConfig getInstance() {
		if (m_conf == null) {
			m_conf = new ModuleManagerConfig();
		}
		return m_conf;
	}
}
