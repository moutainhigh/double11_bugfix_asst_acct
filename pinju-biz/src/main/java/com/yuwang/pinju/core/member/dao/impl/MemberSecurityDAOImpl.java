package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSecurityDAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityDO;

public class MemberSecurityDAOImpl extends BaseDAO implements MemberSecurityDAO {
	
	/**
	 * MEMBER_SECURITY 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE = "member_security.";

	@Override
	public MemberSecurityDO getSecurityByMid(long memberId) throws DaoException {
		return (MemberSecurityDO)executeQueryForObject(NAMESPACE + "getSecurityByMid", memberId);
	}

	@Override
	public MemberSecurityDO addMemberSecurity(MemberSecurityDO security) throws DaoException {
		Date curr = DateUtil.current();
		security.setGmtCreate(curr);
		security.setGmtModified(curr);
		Long id = (Long)executeInsert(NAMESPACE + "addMemberSecurity", security);
		security.setId(id);
		return security;
	}

	@Override
	public int updateSecurityMask(MemberSecurityDO security) throws DaoException {
		security.setGmtModified(DateUtil.current());
		return executeUpdate(NAMESPACE + "updateSecurityMask", security);
	}

	@Override
	public int updateLastLoginTime(long memberId, Date currentLoginTime, String currentLoginIp) throws DaoException {
		MemberSecurityDO security = new MemberSecurityDO();
		security.setMemberId(memberId);
		security.setCurrentLoginTime(currentLoginTime);
		security.setCurrentLoginIp(currentLoginIp);
		security.setGmtModified(DateUtil.current());
		return executeUpdate(NAMESPACE + "updateLastLoginTime", security);
	}

}
