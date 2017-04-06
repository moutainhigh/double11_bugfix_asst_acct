package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSinaAccountDAO;
import com.yuwang.pinju.domain.member.login.MemberSinaAccountDO;

public class MemberSinaAccountDAOImpl extends BaseDAO implements MemberSinaAccountDAO {
	
	/**
	 * MEMBER_SINA_ACCOUNT 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE = "member_sina_account.";

	@Override
	public MemberSinaAccountDO getSinaAccountByMid(long memberId) throws DaoException {
		return (MemberSinaAccountDO)executeQueryForObject(NAMESPACE + "getSinaAccountByMid", memberId);
	}

	@Override
	public MemberSinaAccountDO getSinaAccountBySinaUid(String sinaUid) throws DaoException {
		return (MemberSinaAccountDO)executeQueryForObject(NAMESPACE + "getSinaAccountBySinaUid", sinaUid);
	}

	@Override
	public MemberSinaAccountDO addSinaAccount(MemberSinaAccountDO sinaAccount) throws DaoException {
		Date curr = DateUtil.current();
		sinaAccount.setGmtCreate(curr);
		sinaAccount.setGmtModified(curr);
		Long id = (Long)executeInsert(NAMESPACE + "addSinaAccount", sinaAccount);
		sinaAccount.setId(id);
		return sinaAccount;
	}
}
