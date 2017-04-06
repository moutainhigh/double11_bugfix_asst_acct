package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSecurityMobileCodeDAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;

public class MemberSecurityMobileCodeDAOImpl extends BaseDAO implements MemberSecurityMobileCodeDAO {

	/**
	 * MEMBER_SECURITY_MOBILE_CODE 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX = "member_security_mobile_code.";

	@Override
	public MemberSecurityMobileCodeDO getUnconfirmMobileCode(String mobile, String messageId, Integer codeType)
			throws DaoException {
		MemberSecurityMobileCodeDO query = new MemberSecurityMobileCodeDO();
		query.setMobile(mobile);
		query.setMessageId(messageId);
		query.setExpireTime(DateUtil.current());
		query.setCodeType(codeType);
		return (MemberSecurityMobileCodeDO)executeQueryForObject(NAMESPACE_PREFIX + "getUnconfirmMobileCode", query);
	}

	@Override
	public MemberSecurityMobileCodeDO addMobileCode(MemberSecurityMobileCodeDO securityMobileCode) throws DaoException {
		assertNotNull(securityMobileCode, "securityMobileCode parameter is null");
		Date curr = DateUtil.current();
		securityMobileCode.setGmtCreate(curr);
		securityMobileCode.setGmtModified(curr);
		Long id = (Long)super.executeInsert(NAMESPACE_PREFIX + "addMobileCode", securityMobileCode);
		securityMobileCode.setId(id);
		return securityMobileCode;
	}

	@Override
	public int confirmMobileCode(MemberSecurityMobileCodeDO securityMobileCode) throws DaoException {
		assertNotNull(securityMobileCode, "securityMobileCode parameter is null");
		Date curr = DateUtil.current();
		securityMobileCode.setCodeTime(curr);
		securityMobileCode.setGmtModified(curr);
		return super.executeUpdate(NAMESPACE_PREFIX + "confirmMobileCode", securityMobileCode);
	}

	@Override
	public int confirmToken(MemberSecurityMobileCodeDO securityMobileCode) throws DaoException {
		assertNotNull(securityMobileCode, "securityMobileCode parameter is null");
		securityMobileCode.setGmtModified(DateUtil.current());
		return executeUpdate(NAMESPACE_PREFIX + "confirmToken", securityMobileCode);
	}

	@Override
	public MemberSecurityMobileCodeDO getSecurityMobileByToken(String messageId, String token) throws DaoException {
		MemberSecurityMobileCodeDO link = new MemberSecurityMobileCodeDO();
		link.setMessageId(messageId);
		link.setToken(token);
		return (MemberSecurityMobileCodeDO)executeQueryForObject(NAMESPACE_PREFIX + "getSecurityMobileByToken", link);
	}

	private void assertNotNull(Object obj, String message) throws DaoException {
		if (obj == null) {
			throw new DaoException(message);
		}
	}
}
