package com.yuwang.pinju.core.member.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberExtDAO;
import com.yuwang.pinju.domain.member.MemberExtDO;

public class MemberExtDAOImpl extends BaseDAO implements MemberExtDAO {

	private static final String NAMESPACE = "MemberExtSqlMap.";
	
	@Override
	public MemberExtDO findMemberExtDOByMemberId(long memberId) throws DaoException {
		return (MemberExtDO)executeQueryForObject(NAMESPACE + "queryMemberExtByMemberId", memberId);
	}

	@Override
	public void insertMemberExtDO(MemberExtDO memberExtDO) throws DaoException {
		executeInsert(NAMESPACE + "insertMemberExtBuyerExt", memberExtDO);
	}

	@Override
	public void updateMemberExtDO(MemberExtDO memberExtDO) throws DaoException {
		executeUpdate(NAMESPACE + "updateMemberExtSellerExtById", memberExtDO);
	}
}
