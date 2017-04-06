package com.yuwang.api.common;

import java.io.StringReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ConfigurableSupport extends BaseDO {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 6919261160072067642L;
	/**
     * 配置参数，properties文件样式
     */
    private String configuration;
    /**
     * 根据配置参数生成的配置项
     */
    private Properties properties;

    public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
     * 获取配置参数
     *
     * @return 配置参数
     */
    public String getConfiguration() {
        return configuration == null ? "" : configuration;
    }

    /**
     * 设置配置参数
     *
     * @param configuration 配置参数
     */
    public void setConfiguration(String configuration) {
        this.configuration = configuration;
        getConfig("");
    }

    /**
     * 获取配置参数值
     *
     * @param key key
     * @return 配置参数值
     */
    public String getConfig(String key) {
        if (properties == null && StringUtils.isNotEmpty(configuration)) {
            try {
                properties = new Properties();
                properties.load(new StringReader(configuration));
            } catch (Exception ignored) {

            }
        }
        return properties != null ? properties.getProperty(key) : null;
    }
    
    /**
     * 获取配置参数值
     *
     * @param key key
     * @return 配置参数值
     */
    public String getConfig(String content, String key) {
    	Properties prop = new Properties();
        if (StringUtils.isNotEmpty(content)) {
            try {
            	prop.load(new StringReader(content));
            } catch (Exception ignored) {

            }
        }
        return prop != null ? prop.getProperty(key) : null;
    }
}
