package com.yuwang.pinju.core.logistics.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.dao.LogisticsAreaCarriageDAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsAreaCarriageManager;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;

public class LogisticsAreaCarriageManagerImpl  implements LogisticsAreaCarriageManager{
	private LogisticsAreaCarriageDAO logisticsAreaCarriageDAO;



	/**
	 * 查询地区物流运费信息列表
	 * @param logisticsCarriageDO
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> selectLogisticsAreaCarriageList(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws ManagerException{
		try {
			return logisticsAreaCarriageDAO.selectLogisticsAreaCarriageList(logisticsAreaCarriageDO);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl selectLogisticsAreaCarriageList error = ", e);
		}
	}
	
	/**
	 * 查询地区物流运费信息列表
	 * @param templateId
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> loadAreaCarriageByTemplate(Long templateId)throws ManagerException{
		try {
			return logisticsAreaCarriageDAO.loadAreaCarriageByTemplate(templateId);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl loadAreaCarriageByTemplate error = ", e);
		}
	}
	
	
	/**
	 * 删除地区物流运费信息
	 * @param templateId 模板id
	 * @return 受影响的记录数
	 */
	public int deleteLogisticsAreaCarriage(Long templateId) throws ManagerException {
		try {
			LogisticsAreaCarriageDO logisticsAreaCarriageDO = new LogisticsAreaCarriageDO();
			logisticsAreaCarriageDO.setTemplateId(templateId);
			return logisticsAreaCarriageDAO.deleteLogisticsAreaCarriage(logisticsAreaCarriageDO);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl deleteLogisticsAreaCarriage error = ", e);
		}
	}
	
	/**
	 * 删除地区物流运费信息
	 * @param logisticsAreaCarriageDO
	 * @return 受影响的记录数
	 */
	public int deleteLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws ManagerException{
		try {
			return logisticsAreaCarriageDAO.deleteLogisticsAreaCarriage(logisticsAreaCarriageDO);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl deleteLogisticsAreaCarriage error = ", e);
		}
	}
	
	/**
	 * 修改地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list)throws ManagerException{
		try {
			return logisticsAreaCarriageDAO.updateLogisticsAreaCarriageList(list);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl updateLogisticsAreaCarriageList error = ", e);
		}
	}
	
	/**
	 * 修改地区物流运费
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws ManagerException{
		try {
			return logisticsAreaCarriageDAO.updateLogisticsAreaCarriage(logisticsAreaCarriageDO);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl updateLogisticsAreaCarriage error = ", e);
		}
	}


	/**
	 * 保存地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 */
	public void insertLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list)throws ManagerException{
		try {
			logisticsAreaCarriageDAO.insertLogisticsAreaCarriageList(list);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl insertLogisticsAreaCarriageList error = ", e);
		}
	}

	
	/**
	 * 保存地区物流运费
	 * @param logisticsCarriageDO
	 * @return ID
	 */
	public Long insertLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws ManagerException{
		try {
			return logisticsAreaCarriageDAO.insertLogisticsAreaCarriage(logisticsAreaCarriageDO);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl insertLogisticsAreaCarriage error = ", e);
		}	
	}
	
	/**
	 * 获取地区运费信息数量
	 * 
	 * @param templateId 模板id
	 * @param logisticsTypeId 物流类型
	 * 
	 * @return 地区运费信息数量
	 */
	public Integer getAreaCarriageCount(Long templateId, Integer logisticsTypeId)throws ManagerException{
		try {
			LogisticsAreaCarriageDO logisticsAreaCarriageDO = new LogisticsAreaCarriageDO();
			logisticsAreaCarriageDO.setTemplateId(templateId);
			logisticsAreaCarriageDO.setLogisticsTypeId(logisticsTypeId);
			return logisticsAreaCarriageDAO.getAreaCarriageCount(logisticsAreaCarriageDO);
		} catch (DaoException e) {
			throw new ManagerException("LogisticsAreaCarriageManagerImpl getAreaCarriageCount error = ", e);
		}	
	}
	
	public void setLogisticsAreaCarriageDAO(
			LogisticsAreaCarriageDAO logisticsAreaCarriageDAO) {
		this.logisticsAreaCarriageDAO = logisticsAreaCarriageDAO;
	}
}
