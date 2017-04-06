package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailHisDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileHisDO;

/**
 * <p>会员安全绑定历史记录操作</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 13:14:46
 */
public interface MemberSecurityHisDAO {

	/**
	 * <p>会员安全邮箱验证历史记录</p>
	 *
	 * @param securityEmailHis
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:15:05
	 */
	MemberSecurityEmailHisDO addSecurityEmailHis(MemberSecurityEmailHisDO securityEmailHis) throws DaoException;

	/**
	 * <p>会员安全手机号码历史记录</p>
	 *
	 * @param securityMobileHis
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:15:25
	 */
	MemberSecurityMobileHisDO addSecurityMobileHis(MemberSecurityMobileHisDO securityMobileHis) throws DaoException;
}
