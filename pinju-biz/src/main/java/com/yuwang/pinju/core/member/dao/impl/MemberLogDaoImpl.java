package com.yuwang.pinju.core.member.dao.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberLogDao;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;

public class MemberLogDaoImpl extends BaseDAO implements MemberLogDao {

	private final static Log log = LogFactory.getLog(MemberLogDaoImpl.class);

	/**
	 * MEMBER_MEMBER_LOGIN_HIS 表 iBatis 命名空间前缀
	 */
	private final static String NAMESPACE_PREFIX_MEMBER_LOGIN_HIS = "MemberLoginHis.";

	@Override
	public MemberLoginHisDO persistMemberLoginHis(MemberLoginHisDO memberLoginHis) throws DaoException {
		try {
			Number id = (Number)super.executeInsert(NAMESPACE_PREFIX_MEMBER_LOGIN_HIS + "persistMemberLoginHis", memberLoginHis);
			memberLoginHis.setId(id.longValue());
			return memberLoginHis;
		} catch (Exception e) {
			log.error("persistMemberLoginHis failed", e);
			throw new DaoException("persistMemberLoginHis failed", e);
		}
	}

	@Override
	public int updateMemberLoginHis(Map<String, Object> parameters) throws DaoException {
		try {
			return super.executeUpdate(NAMESPACE_PREFIX_MEMBER_LOGIN_HIS + "updateMemberLoginHis", parameters);
		} catch (Exception e) {
			log.error("updateMemberLoginHis failed", e);
			throw new DaoException("updateMemberLoginHis failed", e);
		}
	}
}
