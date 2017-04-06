package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;

/**
 * <p>手机验证码数据操作</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 13:15:45
 */
public interface MemberSecurityMobileCodeDAO {

	/**
	 * <p>获取未确认的手机验证码</p>
	 *
	 * @param mobile 手机号码
	 * @param messageId  消息编号
	 * @param codeType 验证码业务类型（取值见 {@link MemberSecurityMobileCodeDO#CODE_TYPE_MOBILE_VALIDATION} 和
	 * {@link MemberSecurityMobileCodeDO#CODE_TYPE_RETRIEVE_PASSWORD}）
	 *
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:18:48
	 */
	MemberSecurityMobileCodeDO getUnconfirmMobileCode(String mobile, String messageId, Integer codeType) throws DaoException;

	/**
	 * <p>新增手机验证码数据</p>
	 *
	 * @param securityMobileCode
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:23:19
	 */
	MemberSecurityMobileCodeDO addMobileCode(MemberSecurityMobileCodeDO securityMobileCode) throws DaoException;

	/**
	 * <p>确认手机验证码</p>
	 *
	 * @param securityMobileCode
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:24:05
	 */
	int confirmMobileCode(MemberSecurityMobileCodeDO securityMobileCode) throws DaoException;

	int confirmToken(MemberSecurityMobileCodeDO securityMobileCode) throws DaoException;

	MemberSecurityMobileCodeDO getSecurityMobileByToken(String messageId, String token) throws DaoException;
}
