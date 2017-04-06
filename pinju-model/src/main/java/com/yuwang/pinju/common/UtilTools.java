package com.yuwang.pinju.common;

import java.io.StringReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * 品聚公用工具类
 * 
 * @author mike
 *
 * @since 2011-6-8
 */

public class UtilTools  {
    /**
     * 根据配置参数生成的配置项
     */
    private static Properties properties;
    
    /**
     * 通过key=value的字符串构建properties
     * 
     * @param str   key=value "\n" key=value 形式的字符串
     * @return  properties
     */
	public static Properties getProperties(String str){
		if (properties == null && StringUtils.isNotEmpty(str)) {
            try {
                properties = new Properties();
                properties.load(new StringReader(str));
            } catch (Exception ignored) {

            }
        }
		return properties;
	}
	
}
