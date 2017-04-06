package com.yuwang.pinju.core.item.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.CpValueRelationDAO;
import com.yuwang.pinju.domain.item.CpValueRelationDO;

public class CpValueRelationDAOImpl extends BaseDAO implements CpValueRelationDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.CpValueRelationDAO#getItemCpValueRelationByCpId(long)
	 */
	@Override
	public List<CpValueRelationDO> getItemCpValueRelation(long cpId, long sonCpId) throws DaoException {
		Map<String, Long> pro = new HashMap<String, Long>();
		pro.put("cpId", cpId);
		pro.put("sonCpId", sonCpId);
		return executeQueryForList("item_cp_value_relation.getItemCpValueRelation", pro);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.CpValueRelationDAO#getItemCpValueRelationByCpId(long)
	 */
	@Override
	public List<CpValueRelationDO> getItemCpValueRelationByCpId(long cpId) throws DaoException {
		return executeQueryForList("item_cp_value_relation.getItemCpValueRelationByCpId", cpId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.CpValueRelationDAO#getItemCpValueRelationByCpIdAndCpvId(long, long)
	 */
	@Override
	public CpValueRelationDO getItemCpValueRelationByCpIdAndCpvId(long cpId, long cpvId) throws DaoException {
		Map<String, Long> pro = new HashMap<String, Long>();
		pro.put("cpId", cpId);
		pro.put("cpvId", cpvId);
		return (CpValueRelationDO) executeQueryForObject("item_cp_value_relation.getItemCpValueRelationByCpIdAndCpvId", pro);
	}

}
