package com.yuwang.pinju.core.member.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.DsrStatDAO;
import com.yuwang.pinju.domain.member.DsrStatDO;

public class DsrStatDAOImpl extends BaseDAO implements DsrStatDAO {

	/**
	 * MEMBER_DSR_STAT 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE = "DsrStat.";

	@Override
	@SuppressWarnings("unchecked")
	public List<DsrStatDO> getDsrStatsByMemberId(long memberId) throws DaoException {
		List<DsrStatDO> dsrStats = super.executeQueryForList(NAMESPACE + "getDsrStatsByMemberId", memberId);
		if (dsrStats == null) {
			return new ArrayList<DsrStatDO>(0);
		}
		return dsrStats;
	}
}
