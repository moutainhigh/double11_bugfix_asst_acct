package com.yuwang.pinju.core.logistics.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;

/**
 * <p>默认物流运费</p>
 *
 * @author shihongbo
 * @since   2011-07-09
 */

public interface LogisticsCarriageDAO {
	/**
	 * 取得默认物流运费信息
	 * 
	 * @param logisticsCarriageId 默认物流运费信息编号
	 * 
	 * @throws DaoException
	 * 
	 * @return 默认物流运费
	 */
	public LogisticsCarriageDO loadLogisticsCarriageById(Long logisticsCarriageId)throws DaoException;
	
	/**
	 * 取得默认运费信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 默认运费信息
	 */
	public List<LogisticsCarriageDO> loadLogisticsCarriageByTemplate(Long templateId)throws DaoException;
	
	/**
	 * 取得默认物流运费信息
	 * 
	 * @param templateId 物流模板编号
	 * @param logisticsTypeId 物流类型
	 * 
	 * @throws DaoException
	 * 
	 * @return 默认物流运费
	 */
	public LogisticsCarriageDO loadLogisticsCarriage(Long templateId, Integer logisticsTypeId)throws DaoException;
	
	
	/**
	 * 保存默认物流运费信息
	 * 
	 * @param logisticsCarriageDO 
	 * 
	 * @throws DaoException
	* 
	 * @return 更新或者保存的默认物流运费id
	 */
	public Long saveLogisticsCarriage(LogisticsCarriageDO logisticsCarriageDO)throws DaoException;
	
	/**
	 * 获取模板相关的默认运费
	 * 
	 * @param templateId 
	 * 
	 * @return 默认运费列表
	 */
	public List<LogisticsCarriageDO> getLogisticsCarriageList(Long templateId)throws DaoException;
	
	/**
	 * 根据模板id删除物流默认运费
	 * 
	 * @param templateId 模板id
	 * 
	 * @return 
	 */
	public void deleteCarriageByTemplate(Long templateId)throws DaoException;

}
