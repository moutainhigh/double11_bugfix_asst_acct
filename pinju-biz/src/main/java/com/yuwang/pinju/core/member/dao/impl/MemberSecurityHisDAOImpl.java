package com.yuwang.pinju.core.member.dao.impl;

import java.util.Date;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.member.dao.MemberSecurityHisDAO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailHisDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileHisDO;

public class MemberSecurityHisDAOImpl extends BaseDAO implements MemberSecurityHisDAO {

	@Override
	public MemberSecurityEmailHisDO addSecurityEmailHis(MemberSecurityEmailHisDO securityEmailHis) throws DaoException {
		Date curr = DateUtil.current();
		securityEmailHis.setGmtCreate(curr);
		securityEmailHis.setGmtModified(curr);
		Long id = (Long)executeInsert("member_security_email_his.addSecurityEmailHis", securityEmailHis);
		securityEmailHis.setId(id);
		return securityEmailHis;
	}

	@Override
	public MemberSecurityMobileHisDO addSecurityMobileHis(MemberSecurityMobileHisDO securityMobileHis)
			throws DaoException {
		Date curr = DateUtil.current();
		securityMobileHis.setGmtCreate(curr);
		securityMobileHis.setGmtModified(curr);
		Long id = (Long)executeInsert("member_security_mobile_his.addSecurityMobileHis", securityMobileHis);
		securityMobileHis.setId(id);
		return securityMobileHis;
	}
}
