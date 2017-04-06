package com.yuwang.pinju.core.logistics.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.dao.LogisticsCarriageDAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsCarriageManager;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;

/**
 * 物流默认运费
 * @author shihongbo
 * @since   2011-07-11
 */


public class LogisticsCarriageManagerImpl implements LogisticsCarriageManager{
	private LogisticsCarriageDAO logisticsCarriageDAO;

	/**
	 * 取得物流默认运费
	 * 
	 * @param logisticsCarriageId 物流默认运费编号
	 * 
	 * @throws DaoException
	 * 
	 * @return 物流模板
	 */
	public LogisticsCarriageDO loadLogisticsCarriageById(Long logisticsCarriageId)throws ManagerException{
		try {
			return logisticsCarriageDAO.loadLogisticsCarriageById(logisticsCarriageId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCarriageManagerImpl#loadLogisticsCarriage]取得物流默认运费", e);
		}
	}
	
	/**
	 * 取得默认运费信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @throws DaoException
	 * 
	 * @return 默认运费信息
	 */
	public List<LogisticsCarriageDO> loadLogisticsCarriageByTemplate(Long templateId)throws ManagerException{
		try {
			return logisticsCarriageDAO.loadLogisticsCarriageByTemplate(templateId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCarriageManagerImpl#loadLogisticsCarriageByTemplate]取得默认运费信息", e);
		}
	}
	
	
	/**
	 * 保存默认物流运费信息
	 * 
	 * @param logisticsCarriageDO 
	 * 
	 * @throws DaoException
	* 
	 * @return 更新或者保存的默认物流运费id
	 */
	public Long saveLogisticsCarriage(LogisticsCarriageDO logisticsCarriageDO)throws ManagerException{
		try {
			return logisticsCarriageDAO.saveLogisticsCarriage(logisticsCarriageDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCarriageManagerImpl#saveLogisticsCarriage]保存默认物流运费信息", e);
		}
	}

	/**
	 * 获取模板相关的默认运费
	 * 
	 * @param templateId 
	 * 
	 * @return 默认运费列表
	 */
	public List<LogisticsCarriageDO> getLogisticsCarriageList(Long templateId)throws ManagerException{
		try {
			return logisticsCarriageDAO.getLogisticsCarriageList(templateId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCarriageManagerImpl#getLogisticsCarriageList] 获取模板相关的默认运费", e);
		}
	}
	
	
	/**
	 * 根据模板id删除物流默认运费
	 * 
	 * @param templateId 模板id
	 * 
	 * @return 
	 */
	public void deleteCarriageByTemplate(Long templateId)throws ManagerException{
		try {
			logisticsCarriageDAO.deleteCarriageByTemplate(templateId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCarriageManagerImpl#getLogisticsCarriageList] 获取模板相关的默认运费", e);
		}
	}

	/**
	 *获取模板相关的默认运费
	 * 
	 * @param templateId 
	 * 
	 * @return 默认运费
	 */
	public LogisticsCarriageDO getLogisticsCarriage(Long templateId, Integer logisticsTypeId)throws ManagerException{
		try {
			return logisticsCarriageDAO.loadLogisticsCarriage(templateId, logisticsTypeId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCarriageManagerImpl#getLogisticsCarriageList] 获取模板相关的默认运费", e);
		}
	}
	
	public void setLogisticsCarriageDAO(LogisticsCarriageDAO logisticsCarriageDAO) {
		this.logisticsCarriageDAO = logisticsCarriageDAO;
	}

}
