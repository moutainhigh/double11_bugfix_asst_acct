package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSecurityEmailLinkDAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO;

public class MemberSecurityEmailLinkDAOImpl extends BaseDAO implements MemberSecurityEmailLinkDAO {
	
	/**
	 * MEMBER_SECURITY_EMAIL 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX = "member_security_email_link.";

	@Override
	public MemberSecurityEmailLinkDO getUnconfirmEmailLink(MemberSecurityEmailLinkDO link) throws DaoException {
		return (MemberSecurityEmailLinkDO)executeQueryForObject(NAMESPACE_PREFIX + "getUnconfirmEmailLink", link);
	}

	@Override
	public MemberSecurityEmailLinkDO addMemberSecurityEmailLink(MemberSecurityEmailLinkDO securityEmailLink)
			throws DaoException {
		Date current = DateUtil.current();
		securityEmailLink.setGmtCreate(current);
		securityEmailLink.setGmtModified(current);
		Long id = (Long)executeInsert(NAMESPACE_PREFIX + "addMemberSecurityEmailLink", securityEmailLink);
		securityEmailLink.setId(id);
		return securityEmailLink;
	}

	@Override
	public int confirmMemberSecurityEmailLink(MemberSecurityEmailLinkDO link) throws DaoException {
		Date current = DateUtil.current();
		link.setConfirmTime(current);
		link.setGmtModified(current);
		return executeUpdate(NAMESPACE_PREFIX + "confirmMemberSecurityEmailLink", link);
	}

	@Override
	public int confirmToken(MemberSecurityEmailLinkDO link) throws DaoException {
		link.setGmtModified(DateUtil.current());
		return executeUpdate(NAMESPACE_PREFIX + "confirmToken", link);
	}

	@Override
	public MemberSecurityEmailLinkDO getSecurityEmailLinkByToken(String messageId, String token) throws DaoException {
		MemberSecurityEmailLinkDO link = new MemberSecurityEmailLinkDO();
		link.setMessageId(messageId);
		link.setToken(token);
		return (MemberSecurityEmailLinkDO)executeQueryForObject(NAMESPACE_PREFIX + "getSecurityEmailLinkByToken", link);
	}
}
