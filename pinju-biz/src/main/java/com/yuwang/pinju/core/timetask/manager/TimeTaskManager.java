/**
 * 
 */
package com.yuwang.pinju.core.timetask.manager;

/**  
 * @Project: pinju-biz
 * @Title: TimeTaskManager.java
 * @Package com.yuwang.pinju.core.timetask
 * @Description: 时间任务管理
 * @author liuboen liuboen@zba.com
 * @date 2011-7-4 上午10:10:09
 * @version V1.0  
 */

public interface TimeTaskManager {

	/**
	 * 定时加载类目
	 */
	public void loadCategories();
	
	/**
	 * 增量更新类目
	 */
	public void incrLoadCategories();
	
	/**
	 * 定时更新配置信息
	 */
	public void loadConfigManager();
	/**
	 * 定时增量更新配置信息
	 */
	public void incrLoadConfigManager();
}
