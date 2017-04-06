package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileDO;

/**
 * <p>会员安全手机号码相关操作</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 13:25:00
 */
public interface MemberSecurityMobileDAO {

	/**
	 * <p>根据手机号码获取会员的安全手机号码信息</p>
	 *
	 * @param mobile 手机号码
	 * @return 安全验证后的手机号码数据
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:25:15
	 */
	MemberSecurityMobileDO getSecurityMobileByMobile(String mobile) throws DaoException;

	/**
	 * <p>根据会员编号获取会员的安全手机号码信息</p>
	 *
	 * @param memberId  会员编号
	 * @return 安全验证后的手机号码数据
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:26:14
	 */
	MemberSecurityMobileDO getSecurityMobileByMid(long memberId) throws DaoException;

	/**
	 * <p>将增会员安全验证后的手机信息</p>
	 *
	 * @param securityMobile
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:27:44
	 */
	MemberSecurityMobileDO addSecurityMobile(MemberSecurityMobileDO securityMobile) throws DaoException;
	
	int deleteMemberSecurityMobile(MemberSecurityMobileDO securityMobile) throws DaoException;
}
