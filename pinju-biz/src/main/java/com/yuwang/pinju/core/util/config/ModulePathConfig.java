package com.yuwang.pinju.core.util.config;

public class ModulePathConfig extends BaseConfig {

	private static String filename = "/config/modulePath.properties";

	private static ModulePathConfig m_conf = null;

	public ModulePathConfig() {
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
	public static ModulePathConfig getInstance(String filen) {
		filename = filen;
		if (m_conf == null) {
			m_conf = new ModulePathConfig();
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
	public static ModulePathConfig getInstance() {
		if (m_conf == null) {
			m_conf = new ModulePathConfig();
		}
		return m_conf;
	}
}
