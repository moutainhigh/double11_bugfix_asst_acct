package com.yuwang.pinju.core.logistics.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;

public interface LogisticsAreaCarriageDAO {

	/**
	 * 查询地区物流运费信息列表
	 * @param logisticsCarriageDO
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> selectLogisticsAreaCarriageList(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws DaoException;
	
	/**
	 * 查询地区物流运费信息列表
	 * @param templateId
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> loadAreaCarriageByTemplate(Long templateId)throws DaoException ;
	
	/**
	 * 删除地区物流运费信息
	 * @param logisticsAreaCarriageDO
	 * @return 受影响的记录数
	 */
	public int deleteLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO) throws DaoException ;
	
	/**
	 * 修改地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list)throws DaoException;
	
	/**
	 * 修改地区物流运费
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws DaoException;


	/**
	 * 保存地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 */
	public void insertLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list)throws DaoException;

	
	/**
	 * 保存地区物流运费
	 * @param logisticsCarriageDO
	 * @return ID
	 */
	public Long insertLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws DaoException;
	
	/**
	 * 获取地区运费信息数量
	 * 
	 * @param templateId 模板id
	 * @param logisticsTypeId 物流类型
	 * 
	 * @return 地区运费信息数量
	 */
	public Integer getAreaCarriageCount(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws DaoException;
}
