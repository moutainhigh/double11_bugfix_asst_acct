/**
 * 
 */
package com.yuwang.pinju.core.timetask.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.config.manager.ConfigManagerManager;
import com.yuwang.pinju.core.item.manager.CategoryCacheManager;
import com.yuwang.pinju.core.timetask.manager.TimeTaskManager;

/**  
 * @Project: pinju-biz
 * @Title: TimeTaskManagerImpl.java
 * @Package com.yuwang.pinju.core.timetask.manager.impl
 * @Description: 时间管理实现类
 * @author liuboen liuboen@zba.com
 * @date 2011-7-4 上午10:13:35
 * @version V1.0  
 */

public class TimeTaskManagerImpl extends BaseManager implements TimeTaskManager {
	private CategoryCacheManager categoryCacheManager;
	
	private ConfigManagerManager configManagerManager;

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.timetask.manager.TimeTaskManager#loadCategories()
	 */
	@Override
	public void loadCategories() {
		try {
			boolean isLoadTimetask=categoryCacheManager.loadFullCategory();
			if (!isLoadTimetask) {
				log.error("event=[TimeTaskManagerImpl#loadCategories]加载全类目(时间任务)错误");
			}
		} catch (ManagerException e) {
			log.error("event=[TimeTaskManagerImpl#loadCategories]加载全类目(时间任务)错误",e);
		}
		
	}


	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.timetask.manager.TimeTaskManager#incrLoadCategories()
	 */
	@Override
	public void incrLoadCategories() {
		try {
			boolean isLoadTimetask=categoryCacheManager.incrLoadCategory();
			if (!isLoadTimetask) {
				log.error("event=[TimeTaskManagerImpl#incrLoadCategories]增量加载类目(时间任务)错误");
			}
		} catch (ManagerException e) {
			log.error("event=[TimeTaskManagerImpl#incrLoadCategories]增量加载全类目(时间任务)错误",e);
		}
		
	}


	@Override
	public void loadConfigManager() {
		try {
			boolean isLoad=configManagerManager.loadConfigManagerInfo();
			if (!isLoad) {
				log.error("event=[TimeTaskManagerImpl#loadConfigManager]全量加载配置信息(时间任务)错误");
			}
		} catch (ManagerException e) {
			log.error("event=[TimeTaskManagerImpl#loadConfigManager]全量加载配置信息(时间任务)错误", e);
		}
		
	}


	@Override
	public void incrLoadConfigManager() {
		try {
			boolean isLoad=configManagerManager.incrLoadConfigManagerInfo();
			if (!isLoad) {
				log.error("event=[TimeTaskManagerImpl#loadConfigManager]增量加载配置信息(时间任务)错误");
			}
		} catch (ManagerException e) {
			log.error("event=[TimeTaskManagerImpl#loadConfigManager]增量加载配置信息(时间任务)错误", e);
		}
		
	}

	/**
	 * @param categoryCacheManager the categoryCacheManager to set
	 */
	public void setCategoryCacheManager(CategoryCacheManager categoryCacheManager) {
		this.categoryCacheManager = categoryCacheManager;
	}

	/**
	 * @param configManagerManager the configManagerManager to set
	 */
	public void setConfigManagerManager(ConfigManagerManager configManagerManager) {
		this.configManagerManager = configManagerManager;
	}


}
