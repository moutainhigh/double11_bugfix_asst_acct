package com.yuwang.pinju.Constant;

import com.yuwang.pinju.singleton.ConfigManagerInfo;

/**  
 * @Project: pinju-model
 * @Package com.yuwang.pinju.Constant
 * @Description: 通过数据库配置配置项(只用于永久不变的)
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-10-27 下午7:24:30
 * @version V1.0  
 */

public class ConfigManagerConstant {

	/**
	 * 品聚公司办公地点IP。
	 */
	public static String[] PINJU_COMPANY_IPS=ConfigManagerInfo.getStringValues("pinju.company.ips");
}
