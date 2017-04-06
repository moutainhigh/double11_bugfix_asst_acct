/**
 * 
 */
package com.yuwang.pinju.core.config.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.config.ConfigManagerDO;

/**  
 * @Project: pinju-biz
 * @Package com.yuwang.pinju.core.config.manager
 * @Description: 配置信息操作manager类
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-10-27 上午10:27:35
 * @version V1.0  
 */

public interface ConfigManagerManager {


	/**
	 * 获取品聚配置列表
	 * @return
	 * @throws DaoException
	 */
	public List<ConfigManagerDO> selectConfigManagerListByProjectType(Integer projectType)throws ManagerException;
	
	/**
	 * 全量更新配置信息
	 * @return 
	 * @throws ManagerException 
	 */
	public boolean loadConfigManagerInfo() throws ManagerException;
	/**
	 * 增量更新配置信息
	 * @return 
	 * @throws ManagerException 
	 */
	public boolean incrLoadConfigManagerInfo() throws ManagerException;
}
