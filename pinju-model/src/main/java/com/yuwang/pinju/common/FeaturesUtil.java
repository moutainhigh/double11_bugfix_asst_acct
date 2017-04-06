/**
 * 
 */
package com.yuwang.pinju.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**  
 * @Project: pinju-model
 * @Title: FeaturesUtil.java
 * @Package com.yuwang.pinju.common
 * @Description: 基础features字段处理(主要针对 key1:value1;key2:value2 格式)
 * @author liuboen liuboen@zba.com
 * @date 2011-7-28 上午10:51:10
 * @version V1.0  
 */

public class FeaturesUtil {

	/**
	 * 将key-value 转换为map
	 * @param features
	 * @return
	 */
	public static Map<String,String> getFeaturesMapByFeatures(String features){
			Map<String,String> map=new HashMap<String, String>();
			if (features!=null&&!features.equals("")) {
				String featuresStrs[]=features.split(";");
				for (String string : featuresStrs) {
					String key=StringUtil.substringBefore(string, ":");
					String value=StringUtil.substringAfter(string, ":");
					map.put(key, value);
				}
			}
			return map;
	}

	/**
	 * 将map转换为key-value形式
	 * @param featuresMap
	 * @return
	 */
	public static String getFeaturesByMap(Map<String,String> featuresMap){
		StringBuffer buffer=new StringBuffer();
		for (Entry<String, String> entry : featuresMap.entrySet()) {
			buffer.append(entry.getKey()).append(":").append(entry.getValue()).append(";");
		}
		return StringUtil.substringBeforeLast(buffer.toString(), ";");
	}

}
