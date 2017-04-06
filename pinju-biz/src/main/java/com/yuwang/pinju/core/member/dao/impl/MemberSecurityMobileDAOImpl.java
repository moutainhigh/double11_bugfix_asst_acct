package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSecurityMobileDAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileDO;

public class MemberSecurityMobileDAOImpl extends BaseDAO implements MemberSecurityMobileDAO {

	/**
	 * MEMBER_SECURITY_MOBILE 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX = "member_security_mobile.";

	@Override
	public MemberSecurityMobileDO getSecurityMobileByMobile(String mobile) throws DaoException {
		return (MemberSecurityMobileDO)executeQueryForObject(NAMESPACE_PREFIX + "getSecurityMobileByMobile", mobile);
	}

	@Override
	public MemberSecurityMobileDO getSecurityMobileByMid(long memberId) throws DaoException {
		return (MemberSecurityMobileDO)executeQueryForObject(NAMESPACE_PREFIX + "getSecurityMobileByMid", memberId);
	}

	@Override
	public MemberSecurityMobileDO addSecurityMobile(MemberSecurityMobileDO securityMobile) throws DaoException {
		Date curr = DateUtil.current();
		securityMobile.setGmtCreate(curr);
		securityMobile.setGmtModified(curr);
		Long id = (Long)executeInsert(NAMESPACE_PREFIX + "addSecurityMobile", securityMobile);
		securityMobile.setId(id);
		return securityMobile;
	}

	@Override
	public int deleteMemberSecurityMobile(MemberSecurityMobileDO securityMobile) throws DaoException {
		return executeUpdate(NAMESPACE_PREFIX + "deleteSecurityMobile", securityMobile);
	}
}
