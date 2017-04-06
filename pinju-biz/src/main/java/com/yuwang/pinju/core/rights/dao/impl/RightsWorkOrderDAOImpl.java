package com.yuwang.pinju.core.rights.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.rights.dao.RightsWorkOrderDAO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderQuery;

/**
 * @Project: 品聚
 * @Description: <p>业务描述</p>
 * @author 石兴 shixing@zba.com
 * @date 2011-10-25 下午5:42:28
 * @update 石兴 2011-10-25 下午5:42:28
 * @version V1.0
 */
public class RightsWorkOrderDAOImpl extends BaseDAO implements RightsWorkOrderDAO{

	@SuppressWarnings("unchecked")
	@Override
	public List<FinanceWorkOrderDO> getRightsWorkOrderDOs(FinanceWorkOrderQuery rightsWorkOrderQuery) throws DaoException {
		rightsWorkOrderQuery.setItems(getRightsWorkOrderDOsCount(rightsWorkOrderQuery));
		return executeQueryForList("RightsWorkOrder.getRightsWorkOrderDOs", rightsWorkOrderQuery);
		
	}

	@Override
	public Integer getRightsWorkOrderDOsCount(FinanceWorkOrderQuery rightsWorkOrderQuery) throws DaoException {
		return (Integer) executeQueryForObject("RightsWorkOrder.getRightsWorkOrderDOsCount", rightsWorkOrderQuery);
	}

	@Override
	public void insertRightsWorkOrderDO(FinanceWorkOrderDO rightsWorkOrderDO)throws DaoException {
		rightsWorkOrderDO.setId((Long) executeInsert("RightsWorkOrder.insertRightsWorkOrder", rightsWorkOrderDO));
	}

	@Override
	public Integer updateRightsWorkOrderDO(FinanceWorkOrderDO rightsWorkOrderDO)throws DaoException {
		return executeUpdate("RightsWorkOrder.updateRightsWorkOrderDO", rightsWorkOrderDO);
	}

	@Override
	public FinanceWorkOrderDO getRightsWorkOrderDOByBizType(FinanceWorkOrderQuery rightsWorkOrderQuery) throws DaoException {
		return (FinanceWorkOrderDO)executeQueryForObject("RightsWorkOrder.getRightsWorkOrderDOByBizType", rightsWorkOrderQuery);
	}

	@Override
	public List<FinanceWorkOrderDO> queryRightsWorkOrderDOByFail(Map<String, Object> map) throws DaoException {
		return executeQueryForList("RightsWorkOrder.queryRightsWorkOrderDOByFail", map);
	}

	@Override
	public FinanceWorkOrderDO getFinanceWorkOrderDOByOrderId(FinanceWorkOrderQuery financeWorkOrderQuery) throws DaoException{
		return (FinanceWorkOrderDO)executeQueryForObject("RightsWorkOrder.getFinanceWorkOrderDOByOrderId", financeWorkOrderQuery);
	}

}
