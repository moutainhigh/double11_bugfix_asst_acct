/**
 * 
 */
package com.yuwang.pinju.core.config.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.config.ConfigManagerDO;

/**  
 * @Project: pinju-biz
 * @Package com.yuwang.pinju.core.config.dao
 * @Description: 配置信息管理
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-10-27 上午9:47:12
 * @version V1.0  
 */

public interface ConfigManagerDAO {

	/**
	 * 获取品聚配置列表
	 * @return
	 * @throws DaoException
	 */
	public List<ConfigManagerDO> selectConfigManagerListByProjectType(Integer projectType) throws DaoException;
	
	/**
	 * 增量获取品聚配置列表
	 * @return
	 * @throws DaoException
	 */
	public List<ConfigManagerDO> selectIncrConfigManagerList(Integer projectType,String startTime,String endTime) throws DaoException;
	
	
}
