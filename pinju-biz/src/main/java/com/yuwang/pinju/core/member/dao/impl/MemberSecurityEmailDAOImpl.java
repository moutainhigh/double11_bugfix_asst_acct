package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSecurityEmailDAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailDO;

public class MemberSecurityEmailDAOImpl extends BaseDAO implements MemberSecurityEmailDAO {

	/**
	 * MEMBER_SECURITY_EMAIL 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX = "member_security_email.";
	
	@Override
	public MemberSecurityEmailDO findMemberSecurityEmailByMemberId(long memberId) throws DaoException {
		return (MemberSecurityEmailDO)executeQueryForObject(NAMESPACE_PREFIX + "findMemberSecurityEmailByMemberId", memberId);
	}

	@Override
	public MemberSecurityEmailDO getMemberSecurityEmailByEmail(String email) throws DaoException {
		return (MemberSecurityEmailDO)executeQueryForObject(NAMESPACE_PREFIX + "getMemberSecurityEmailByEmail", email);
	}

	@Override
	public MemberSecurityEmailDO addMemberSecurityEmail(MemberSecurityEmailDO memberSecurityEmail) throws DaoException {
		Date current = DateUtil.current();
		memberSecurityEmail.setGmtCreate(current);
		memberSecurityEmail.setGmtModified(current);
		Long id = (Long)executeInsert(NAMESPACE_PREFIX + "addMemberSecurityEmail", memberSecurityEmail);
		memberSecurityEmail.setId(id);
		return memberSecurityEmail;
	}

	@Override
	public void deleteMemberSecurityEmail(long memberId) throws DaoException {
		executeUpdate(NAMESPACE_PREFIX + "delMemberSecurityEmailByMemberId", memberId);
	}
}
