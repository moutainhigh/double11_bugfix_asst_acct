package com.yuwang.pinju.core.logistics.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.logistics.dao.LogisticsCarriageDAO;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;

public class LogisticsCarriageDAOImpl extends BaseDAO implements  LogisticsCarriageDAO{
	private final String LOGISTICS_CARRIAGE_NAMESPACE = "trade_logistics_carriage.";
	
	/**
	 * 数据库读写分离之 读
	 */
	private ReadBaseDAO readBaseDAOForOracle ;
	
	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}
	
	/**
	 * 取得默认物流运费信息
	 * 
	 * @param logisticsCarriageId 默认物流运费信息编号
	 * 
	 * @throws DaoException
	 * 
	 * @return 默认物流运费
	 */
	public LogisticsCarriageDO loadLogisticsCarriageById(Long logisticsCarriageId)throws DaoException{
		return (LogisticsCarriageDO)super.executeQueryForObject(LOGISTICS_CARRIAGE_NAMESPACE + "selectTradeLogisticsCarriageByid", logisticsCarriageId);
	}
	
	/**
	 * 取得默认运费信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 默认运费信息
	 */
	public List<LogisticsCarriageDO> loadLogisticsCarriageByTemplate(Long templateId)throws DaoException{
		return super.executeQueryForList(LOGISTICS_CARRIAGE_NAMESPACE + "selectTradeLogisticsCarriageByTemplate", templateId);
	}
	
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
	public LogisticsCarriageDO loadLogisticsCarriage(Long templateId, Integer logisticsTypeId)throws DaoException{
		LogisticsCarriageDO logisticsCarriageDO = new LogisticsCarriageDO();
		logisticsCarriageDO.setTemplateId(templateId);
		logisticsCarriageDO.setLogisticsTypeId(logisticsTypeId);
		return (LogisticsCarriageDO)super.executeQueryForObject(LOGISTICS_CARRIAGE_NAMESPACE + "selectTradeLogisticsCarriage", logisticsCarriageDO);
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
	public Long saveLogisticsCarriage(LogisticsCarriageDO logisticsCarriageDO)throws DaoException{
		LogisticsCarriageDO carriage = loadLogisticsCarriage(logisticsCarriageDO.getTemplateId(), logisticsCarriageDO.getLogisticsTypeId());
		if(carriage == null){
			return (Long)super.executeInsert(LOGISTICS_CARRIAGE_NAMESPACE + "insertTradeLogisticsCarriage", logisticsCarriageDO);	
		}else{
			super.executeUpdate(LOGISTICS_CARRIAGE_NAMESPACE + "updateTradeLogisticsCarriageByTemplate", logisticsCarriageDO);
			return carriage.getId();
		}
		
	}
	
	/**
	 * 获取模板相关的默认运费
	 * 
	 * @param templateId 
	 * 
	 * @return 默认运费列表
	 */
	public List<LogisticsCarriageDO> getLogisticsCarriageList(Long templateId)throws DaoException{
		LogisticsCarriageDO logisticsCarriageDO = new LogisticsCarriageDO();
		logisticsCarriageDO.setTemplateId(templateId);
		return readBaseDAOForOracle.executeQueryForList(LOGISTICS_CARRIAGE_NAMESPACE + "selectTradeLogisticsCarriage", logisticsCarriageDO);
	}
	
	/**
	 * 根据模板id删除物流默认运费
	 * 
	 * @param templateId 模板id
	 * 
	 * @return 
	 */
	public void deleteCarriageByTemplate(Long templateId)throws DaoException{
		super.executeUpdate(LOGISTICS_CARRIAGE_NAMESPACE + "deleteTradeLogisticsCarriageByTemplate", templateId);
	}
	
}
