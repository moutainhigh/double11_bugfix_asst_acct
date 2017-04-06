/**
 * 
 */
package com.yuwang.pinju.core.config;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**  
 * @Project: pinju-biz
 * @Title: ConfigHelper.java
 * @Package com.yuwang.pinju.core.config
 * @Description: 获取一些配置信息
 * @author liuboen liuboen@zba.com
 * @date 2011-6-30 下午02:04:08
 * @version V1.0  
 */

public class ConfigHelper {
	protected  final   Log log =LogFactory.getLog(this.getClass().getName());
	  private static final String CONFIG_PATH = "config.properties";

	    private final static ConfigHelper instance = new ConfigHelper(CONFIG_PATH);

	    private Configuration config;
	    ConfigHelper(String fileName) {
	    	try {
				config = new PropertiesConfiguration(fileName);
			} catch (ConfigurationException e) {
				log.error("event=[ConfigHelper#ConfigHelper]load file=["+fileName+"],error",e);
			}
	    }
	    
	    public static ConfigHelper getDefault() {
	    	return instance;
	    }
	    
	   public String  getString(String key){
		   if (key==null||key.equals("")) {
			return null;
		   }
		   return config.getString(key);
	   }
}
