package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSnsInfoDAO;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;

public class MemberSnsInfoDAOImpl extends BaseDAO implements MemberSnsInfoDAO {

	private final static String NAMESPACE = "MemberSnsInfo.";

	@Override
	public MemberSnsInfoDO insertMemberSnsInfo(MemberSnsInfoDO memberSnsInfo) throws DaoException {
		Date current = DateUtil.current();
		memberSnsInfo.setGmtCreate(current);
		memberSnsInfo.setGmtModified(current);
		Number pk = (Number)super.executeInsert(ibatisId("insertMemberSnsInfo"), memberSnsInfo);
		if(pk == null) {
			return null;
		}
		memberSnsInfo.setId(pk.longValue());
		return memberSnsInfo;
	}

	@Override
	public int updateMemberSnsInfo(MemberSnsInfoDO memberSnsInfo) throws DaoException {
		memberSnsInfo.setGmtModified(DateUtil.current());
		return super.executeUpdate(ibatisId("updateMemberSnsInfo"), memberSnsInfo);
	}

	@Override
	public MemberSnsInfoDO getMemberSnsInfoByMemberId(long memberId) throws DaoException {
		return (MemberSnsInfoDO)super.executeQueryForObject(ibatisId("getMemberSnsInfoByMemberId"), memberId);
	}

	private String ibatisId(String id) {
		return NAMESPACE + id;
	}
}
