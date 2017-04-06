package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberPinjuLoginDAO;
import com.yuwang.pinju.domain.member.MemberPinjuLoginDO;

public class MemberPinjuLoginDAOImpl extends BaseDAO implements MemberPinjuLoginDAO {

	/**
	 * MEMBER_PINJU_LOGIN 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX = "member_pinju_login.";

	@Override
	public MemberPinjuLoginDO addMemberPinjuLogin(MemberPinjuLoginDO pinjuLogin) throws DaoException {
		Date current = DateUtil.current();
		pinjuLogin.setGmtCreate(current);
		pinjuLogin.setGmtModified(current);
		Long id = (Long)executeInsert(NAMESPACE_PREFIX + "addMemberPinjuLogin", pinjuLogin);
		pinjuLogin.setId(id);
		return pinjuLogin;
	}

	@Override
	public MemberPinjuLoginDO getMemberPinjuLoginByLoginName(String loginName) throws DaoException {
		return (MemberPinjuLoginDO)executeQueryForObject(NAMESPACE_PREFIX + "getMemberPinjuLoginByLoginName", loginName);
	}

	@Override
	public MemberPinjuLoginDO getMemberPinjuLoginByMemberId(long memberId) throws DaoException {
		return (MemberPinjuLoginDO)executeQueryForObject(NAMESPACE_PREFIX + "getMemberPinjuLoginByMemberId", memberId);
	}

	@Override
	public int updatePinjuLoginPassowrd(MemberPinjuLoginDO pinjuLogin) throws DaoException {
		pinjuLogin.setGmtModified(DateUtil.current());
		return executeUpdate(NAMESPACE_PREFIX + "updatePinjuLoginPassowrd", pinjuLogin);
	}
}
