package com.yuwang.pinju.core.logistics.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;

public interface LogisticsAreaCarriageManager {

	/**
	 * 查询地区物流运费信息列表
	 * @param logisticsCarriageDO
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> selectLogisticsAreaCarriageList(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws ManagerException;
	
	/**
	 * 查询地区物流运费信息列表
	 * @param templateId
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> loadAreaCarriageByTemplate(Long templateId)throws ManagerException;
	
	
	/**
	 * 删除地区物流运费信息
	 * @param templateId 模板id
	 * @return 受影响的记录数
	 */
	public int deleteLogisticsAreaCarriage(Long templateId) throws ManagerException;
	
	/**
	 * 删除地区物流运费信息
	 * @param logisticsAreaCarriageDO
	 * @return 受影响的记录数
	 */
	public int deleteLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws ManagerException;
	
	/**
	 * 修改地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list)throws ManagerException;
	
	/**
	 * 修改地区物流运费
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws ManagerException;


	/**
	 * 保存地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 */
	public void insertLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list)throws ManagerException;

	
	/**
	 * 保存地区物流运费
	 * @param logisticsCarriageDO
	 * @return ID
	 */
	public Long insertLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws ManagerException;
	
	/**
	 * 获取地区运费信息数量
	 * 
	 * @param templateId 模板id
	 * @param logisticsTypeId 物流类型
	 * 
	 * @return 地区运费信息数量
	 */
	public Integer getAreaCarriageCount(Long templateId, Integer logisticsTypeId)throws ManagerException;
}
