package com.yuwang.pinju.core.util.config;

public class PageLayoutsPathConfig extends BaseConfig {

	private static String filename = "/config/pageLayoutsPath.properties";

	private static PageLayoutsPathConfig m_conf = null;

	public PageLayoutsPathConfig() {
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
	public static PageLayoutsPathConfig getInstance(String filen) {
		filename = filen;
		if (m_conf == null) {
			m_conf = new PageLayoutsPathConfig();
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
	public static PageLayoutsPathConfig getInstance() {
		if (m_conf == null) {
			m_conf = new PageLayoutsPathConfig();
		}
		return m_conf;
	}
}
