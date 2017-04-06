package com.yuwang.pinju.core.constant.system;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * @author yejingfeng
 * 配置文件读取工具
 *
 */
public class ConfigHelper {

	private static final String CONFIG_PATH = "cfg.properties";

	private final static ConfigHelper instance = new ConfigHelper(CONFIG_PATH);

	private AbstractConfiguration config;

	private ConfigHelper(String fileName) {
		try {
			config = new PropertiesConfiguration(fileName);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

	static ConfigHelper getDefault() {
		return instance;
	}

	String getString(String infoLabel) {
		return instance.config.getString(infoLabel);
	}
	
	byte[] getBytesFromBase64(String infoLabel) {
		String base64 = getString(infoLabel);
		if (base64 == null) {
			throw new NullPointerException("read " + CONFIG_PATH + " [" + infoLabel + "] is null, can not byte[]");
		}
		return Base64.decodeBase64(base64);
	}

	String[] getStringArray(String infoLabel) {
		return instance.config.getStringArray(infoLabel);
	}

	boolean getBoolean(String infoLabel) {
		return instance.config.getBoolean(infoLabel);
	}

	int getInt(String infoLabel) {
		return instance.config.getInt(infoLabel);
	}

	int getInt(String infoLabel, int defaultValue) {
		try {
			return instance.config.getInt(infoLabel);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	double getDouble(String infoLabel) {
		return instance.config.getDouble(infoLabel);
	}

	public static void main(String[] args) {
		System.out.println(ConfigHelper.getDefault().getString(
				"snda.api.query.user.info.key"));
	}
}
