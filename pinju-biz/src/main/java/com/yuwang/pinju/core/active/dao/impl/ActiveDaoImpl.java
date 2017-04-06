package com.yuwang.pinju.core.active.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.active.dao.ActiveDao;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.domain.active.ActiveDescDO;
import com.yuwang.pinju.domain.active.ActiveInfoDO;
import com.yuwang.pinju.domain.active.ActiveInfoQuery;
import com.yuwang.pinju.domain.active.ActiveRegtDO;

public class ActiveDaoImpl extends BaseDAO implements ActiveDao {
	private ReadBaseDAO readBaseDAOForMySql ;

	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveInfoDO> selectActiveList(ActiveInfoQuery query)
			throws DaoException {
		return readBaseDAOForMySql.executeQueryForList("ActiveInfo.selectActiveInfoList", query);
	}

	@Override
	public int selectActiveCount(ActiveInfoQuery activeInfoQeury)
			throws DaoException {
		return (Integer)readBaseDAOForMySql.executeQueryForObject("ActiveInfo.selectActiveInfoCount", activeInfoQeury);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveRegtDO> selectActiveRegtList(ActiveInfoQuery query)
			throws DaoException {
		return readBaseDAOForMySql.executeQueryForList("ActiveInfo.selectActiveRegtByActiveQuery", query);
	}

	@Override
	public ActiveInfoDO selectActiveInfoById(Long id) throws DaoException {
		return (ActiveInfoDO) readBaseDAOForMySql.executeQueryForObject("ActiveInfo.selectActiveInfoById", id);
	}

	@Override
	public ActiveDescDO selectActiveDescByActiveInfo(ActiveInfoDO activeInfo)
			throws DaoException {
		return (ActiveDescDO) readBaseDAOForMySql.executeQueryForObject("ActiveInfo.selectActiveDescByActiveInfo", activeInfo);
	}

	@Override
	public void insertActiveRegistInfo(ActiveRegtDO activeRegt)
			throws DaoException {
		Long id = (Long) executeInsert("ActiveInfo.insertActiveRegist", activeRegt);
		activeRegt.setId(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveInfoDO> selectActivePageList(
			ActiveInfoQuery activeInfoQeury) throws DaoException {
		return readBaseDAOForMySql.executeQueryForList("ActiveInfo.selectActiveInfoPageList", activeInfoQeury);
	}

	@Override
	public int selectActivePageCount(ActiveInfoQuery activeInfoQeury)
			throws DaoException {
		return (Integer)readBaseDAOForMySql.executeQueryForObject("ActiveInfo.selectActiveInfoPageCount", activeInfoQeury);
	}

	@Override
	public int updateActiveRegistCheckStatus(ActiveRegtDO activeRegt)
			throws DaoException {
		return executeUpdate("ActiveInfo.updateActiveRegistStatus", activeRegt);
	}

	@Override
	public int deleteActiveRegistInfoById(Long id) throws DaoException {
		return executeUpdate("ActiveInfo.deleteActiveRegistInfoById", id);
	}

	@Override
	public int selectCanRegistActiveNum(ActiveInfoQuery query)
			throws DaoException {
		return (Integer)readBaseDAOForMySql.executeQueryForObject("ActiveInfo.checkMemberCanRegistGoodNum", query);
	}

	@Override
	public int updateActiveInfoRegistNum(Map<String, Object> map) throws DaoException {
		return executeUpdate("ActiveInfo.updateActiveInfoRegistNum", map);
	}

	public void setReadBaseDAOForMySql(ReadBaseDAO readBaseDAOForMySql) {
		this.readBaseDAOForMySql = readBaseDAOForMySql;
	}

}
