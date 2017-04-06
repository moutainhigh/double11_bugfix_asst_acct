package com.yuwang.pinju.core.rights.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.rights.dao.RightsDAO;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsMsgQuery;
import com.yuwang.pinju.domain.rights.RightsQuery;

/**  
 * @Project: pinju-biz
 * @Description: 消保维权DAO
 * @author 石兴 shixing@zba.com
 * @date 2011-6-29 上午10:22:50
 * @update 2011-6-29 上午10:22:50
 * @version V1.0  
 */
public class RightsDAOImpl extends BaseDAO implements RightsDAO{
	
	private ReadBaseDAO readBaseDAOForOracle ;
	
	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}

	@Override
	public RightsDO getApplyRightsDO(RightsDO rightsDO) throws DaoException {
		return (RightsDO)executeQueryForObject("TradeRights.getApplyRightsDO", rightsDO);
	}

	@Override
	public void insertRightsRecord(RightsDO rightsDO) throws DaoException {
		rightsDO.setId((Long) executeInsert("TradeRights.insertRightsRecord", rightsDO));
	}

	@Override
	public int updateRightsRecord(RightsDO rightsDO) throws DaoException {
		return executeUpdate("TradeRights.updateRightsRecord", rightsDO);		
	}

	@Override
	public RightsLogisticsDO getRightsLogisticsDO(RightsLogisticsDO rightsLogisticsDO)
			throws DaoException {
		return (RightsLogisticsDO)executeQueryForObject("TradeRightsLogistics.getRightsLogisticsDO", rightsLogisticsDO);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RightsMessageDO> getRightsMessageDOs(RightsMsgQuery rightsMsgQuery)
			throws DaoException {
		rightsMsgQuery.setItems((Integer)executeQueryForObject("TradeRightsMsg.getRightsMsgsCount", rightsMsgQuery));
		return executeQueryForList("TradeRightsMsg.getRightsMessageDOs", rightsMsgQuery);
	}

	@Override
	public void insertLogisticsRecord(RightsLogisticsDO rightsLogisticsDO)
			throws DaoException {
		rightsLogisticsDO.setLogisticsId((Long) executeInsert("TradeRightsLogistics.insertLogisticsRecord", rightsLogisticsDO));
	}

	@Override
	public void insertMessageRecord(RightsMessageDO rightsMessageDO)
			throws DaoException {
		rightsMessageDO.setId((Long) executeInsert("TradeRightsMsg.insertMessageRecord", rightsMessageDO));
	}

	@Override
	public int updateRightsLogisticsRecord(RightsLogisticsDO rightsLogisticsDO) throws DaoException {
		return executeUpdate("TradeRightsLogistics.updateRightsLogisticsRecord", rightsLogisticsDO);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RightsDO> getRightsDOs(RightsQuery rightsQuery) throws DaoException {
		rightsQuery.setItems((Integer)readBaseDAOForOracle.executeQueryForObject("TradeRights.getRightsDOsCount", rightsQuery));
		return readBaseDAOForOracle.executeQueryForList("TradeRights.getApplyRightsDOs", rightsQuery);
	}
	
	@Override
	public RightsDO getRightsDOById(Long id) throws DaoException {
		return (RightsDO)executeQueryForObject("TradeRights.getRightsDOById", id);
	}

	@Override
	public int getRightsCount(RightsQuery rightsQuery) throws DaoException {
		return (Integer)executeQueryForObject("TradeRights.getRightsDOsCount", rightsQuery);
	}	

}
