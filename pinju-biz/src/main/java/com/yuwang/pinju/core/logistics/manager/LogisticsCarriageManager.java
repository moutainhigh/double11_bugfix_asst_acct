package com.yuwang.pinju.core.logistics.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;

/**
 * <p>物流默认运费</p>
 *
 * @author shihongbo
 * @since   2011-07-11
 */

public interface LogisticsCarriageManager {
	/**
	 * 取得物流默认运费
	 * 
	 * @param logisticsCarriageId 物流默认运费编号
	 * 
	 * @throws DaoException
	 * 
	 * @return 物流模板
	 */
	public LogisticsCarriageDO loadLogisticsCarriageById(Long logisticsCarriageId)throws ManagerException;
	
	/**
	 * 取得默认运费信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 默认运费信息
	 */
	public List<LogisticsCarriageDO> loadLogisticsCarriageByTemplate(Long templateId)throws ManagerException;
	
	/**
	 * 保存默认物流运费信息
	 * 
	 * @param logisticsCarriageDO 
	 * 
	 * @throws DaoException
	* 
	 * @return 更新或者保存的默认物流运费id
	 */
	public Long saveLogisticsCarriage(LogisticsCarriageDO logisticsCarriageDO)throws ManagerException;
	
	/**
	 * 获取模板相关的默认运费
	 * 
	 * @param templateId 
	 * 
	 * @return 默认运费列表
	 */
	public List<LogisticsCarriageDO> getLogisticsCarriageList(Long templateId)throws ManagerException;
	
	/**
	 *获取模板相关的默认运费
	 * 
	 * @param templateId 
	 * 
	 * @return 默认运费
	 */
	public LogisticsCarriageDO getLogisticsCarriage(Long templateId, Integer logisticsTypeId)throws ManagerException;
	
	
	/**
	 * 根据模板id删除物流默认运费
	 * 
	 * @param templateId 模板id
	 * 
	 * @return 
	 */
	public void deleteCarriageByTemplate(Long templateId)throws ManagerException;
	
}
