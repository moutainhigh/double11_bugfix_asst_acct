package com.yuwang.pinju.core.member.dao.impl;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSequenceDao;
import com.yuwang.pinju.domain.member.MemberSequenceDO;

public class MemberSequenceDaoImpl extends BaseDAO implements MemberSequenceDao{

	@Override
	public MemberSequenceDO findMemberSequence(String name) throws DaoException {
		return (MemberSequenceDO)executeQueryForObject("MemberSequence.findMemberSequence", name);
	}

	@Override
	public int updateMemberSequence(MemberSequenceDO sequence) throws DaoException {
		return executeUpdate("MemberSequence.updateMemberSequence", sequence);
	}
}
