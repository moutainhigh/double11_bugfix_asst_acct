package com.yuwang.pinju.core.logistics.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.logistics.dao.LogisticsAreaCarriageDAO;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;

public class LogisticsAreaCarriageDAOImpl extends BaseDAO implements LogisticsAreaCarriageDAO {
	private final String LOGISTICS_NAME_SPACE="trade_logistics_area_carriage.";

	private ReadBaseDAO readBaseDAOForOracle ;
	
	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}
	
	
	/**
	 * 查询地区物流运费信息列表
	 * @param logisticsCarriageDO
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	@SuppressWarnings("unchecked")
	public List<LogisticsAreaCarriageDO> selectLogisticsAreaCarriageList(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws DaoException{
		return (List<LogisticsAreaCarriageDO>)readBaseDAOForOracle.executeQueryForList(LOGISTICS_NAME_SPACE + "selectTradeLogisticsAreaCarriage", logisticsAreaCarriageDO);
	}
	
	/**
	 * 查询地区物流运费信息列表
	 * @param templateId
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> loadAreaCarriageByTemplate(Long templateId)throws DaoException{
		return (List<LogisticsAreaCarriageDO>)super.executeQueryForList(LOGISTICS_NAME_SPACE + "selectTradeLogisticsAreaCarriageByTemplate", templateId);
	}
	
	/**
	 * 删除地区物流运费信息
	 * @param logisticsAreaCarriageDO
	 * @return 受影响的记录数
	 */
	public int deleteLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO) throws DaoException {
		return super.executeUpdate(LOGISTICS_NAME_SPACE + "deleteTradeLogisticsAreaCarriage", logisticsAreaCarriageDO);
	}
	
	/**
	 * 修改地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list)throws DaoException{
		return super.executeBatchUpdate(LOGISTICS_NAME_SPACE + "updateTradeLogisticsAreaCarriage",list,1000);
	}
	
	/**
	 * 修改地区物流运费
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws DaoException{
		return super.executeUpdate(LOGISTICS_NAME_SPACE + "updateTradeLogisticsAreaCarriage",logisticsAreaCarriageDO);
	}


	/**
	 * 保存地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 */
	public void insertLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list)throws DaoException{
		super.executeBatchInsert(LOGISTICS_NAME_SPACE + "insertTradeLogisticsAreaCarriage",list,1000);
	}                                                    
	
	/**
	 * 保存地区物流运费
	 * @param logisticsCarriageDO
	 * @return ID
	 */
	public Long insertLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws DaoException{
		return (Long)super.executeInsert(LOGISTICS_NAME_SPACE + "insertTradeLogisticsAreaCarriage",logisticsAreaCarriageDO);
	}

	/**
	 * 获取地区运费信息数量
	 * 
	 * @param templateId 模板id
	 * @param logisticsTypeId 物流类型
	 * 
	 * @return 地区运费信息数量
	 */
	public Integer getAreaCarriageCount(LogisticsAreaCarriageDO logisticsAreaCarriageDO)throws DaoException{
		return (Integer)super.executeQueryForObject(LOGISTICS_NAME_SPACE + "selectAreaCarriageCount",logisticsAreaCarriageDO);
	}
}
