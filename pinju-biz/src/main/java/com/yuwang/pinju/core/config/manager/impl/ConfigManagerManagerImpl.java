/**
 * 
 */
package com.yuwang.pinju.core.config.manager.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.config.dao.ConfigManagerDAO;
import com.yuwang.pinju.core.config.manager.ConfigManagerManager;
import com.yuwang.pinju.domain.config.ConfigManagerDO;
import com.yuwang.pinju.singleton.ConfigManagerInfo;

/**  
 * @Project: pinju-biz
 * @Package com.yuwang.pinju.core.config.manager.impl
 * @Description: 配置信息Manager实现类
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-10-27 上午10:32:37
 * @version V1.0  
 */

public class ConfigManagerManagerImpl extends BaseManager implements
		ConfigManagerManager {
	private ConfigManagerDAO configManagerDAO;
	
	public Map<String ,String> configManagerMap=new HashMap<String, String>();
	
	private Date lastModifyTime=null;
	
	private boolean isLoadNow=Boolean.FALSE; 
	
	private final static long ONE_MINUTE=1000*60;
	
	public void init(){
		try {
			if (!isLoadNow) {
				isLoadNow=Boolean.TRUE;
				List<ConfigManagerDO> list = selectConfigManagerListByProjectType(1);
				for (ConfigManagerDO configManagerDO : list) {
					configManagerMap.put(configManagerDO.getKey(),configManagerDO.getValue());
				}
				ConfigManagerInfo.setConfigMapInfo(configManagerMap);
				lastModifyTime=new Date();
				isLoadNow=Boolean.FALSE; 
			}
			
		} catch (ManagerException e) {
			log.error("Event=[#selectConfigManagerListByProjectType]init configManagerMap,query list error ,projectType="+1, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.config.manager.ConfigManagerManager#selectConfigManagerListByProjectType(java.lang.Integer)
	 */
	@Override
	public List<ConfigManagerDO> selectConfigManagerListByProjectType(
			Integer projectType) throws ManagerException {
		try {
			return configManagerDAO.selectConfigManagerListByProjectType(projectType);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public boolean loadConfigManagerInfo() throws ManagerException{
		synchronized (this) {
			if (!isLoadNow) {
				isLoadNow=Boolean.TRUE;
				List<ConfigManagerDO> list;
				try {
					destroy();
					list = selectConfigManagerListByProjectType(1);
					for (ConfigManagerDO configManagerDO : list) {
						configManagerMap.put(configManagerDO.getKey(),configManagerDO.getValue());
					}
					ConfigManagerInfo.setConfigMapInfo(configManagerMap);
					return Boolean.TRUE;
				} catch (ManagerException e) {
					throw new  ManagerException(e);
				}finally{
					isLoadNow=Boolean.FALSE;
				}
			}
		}
		return false;
	}
	
	@Override
	public boolean incrLoadConfigManagerInfo() throws ManagerException {
		synchronized (this) {
			if (!isLoadNow) {
				isLoadNow=Boolean.TRUE;
				//允许前后一分钟误差
				String startTime=DateUtil.formatLongToString(lastModifyTime.getTime()-ONE_MINUTE);
				Date endDate=new Date();
				String endTime=DateUtil.formatDatetime(endDate);
				try {
					List<ConfigManagerDO> list=configManagerDAO.selectIncrConfigManagerList(1, startTime, endTime);
					if (list.size()>0) {
						for (ConfigManagerDO configManagerDO : list) {
							if (configManagerDO.getStatus()!=1) {
								configManagerMap.put(configManagerDO.getKey(),null);
							}else {
								configManagerMap.put(configManagerDO.getKey(),configManagerDO.getValue());
							}
						}
						ConfigManagerInfo.setConfigMapInfo(configManagerMap);
					}
					lastModifyTime=endDate;
					return Boolean.TRUE;
				} catch (DaoException e) {
					throw new ManagerException(e);
				}finally{
					isLoadNow=Boolean.FALSE;
				}
			}
		}
		return Boolean.FALSE;
	}

	private void destroy(){
		if (!configManagerMap.isEmpty()) {
			configManagerMap.clear();
		}
	}
	
	public void setConfigManagerDAO(ConfigManagerDAO configManagerDAO) {
		this.configManagerDAO = configManagerDAO;
	}

	
}
