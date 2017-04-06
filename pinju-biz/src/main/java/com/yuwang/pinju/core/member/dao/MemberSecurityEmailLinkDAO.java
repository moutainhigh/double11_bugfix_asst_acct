package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO;

/**
 * <p>会员安全邮件链接</p>
 *
 * @author gaobaowen
 * @since 2011-9-1 14:02:49
 */
public interface MemberSecurityEmailLinkDAO {

	/**
	 * <p>获取未确认过会员安全邮件点击链接参数</p>
	 *
	 * @param securityEmailLink 查询条件，需要指定其中：
	 * <ul>
	 *   <li>{@link MemberSecurityEmailLinkDO#memberId memberId} 会员编号</li>
	 *   <li>{@link MemberSecurityEmailLinkDO#expireTime expireTime} 设置当前时间</li>
	 *   <li>{@link MemberSecurityEmailLinkDO#messageId messageId} 邮件消息编号</li>
	 * </ul>
	 *
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-1 14:04:08
	 */
	MemberSecurityEmailLinkDO getUnconfirmEmailLink(MemberSecurityEmailLinkDO securityEmailLink) throws DaoException;

	/**
	 * <p>将增一个会员安全邮件点击链接</p>
	 *
	 * @param securityEmailLink
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-1 14:38:55
	 */
	MemberSecurityEmailLinkDO addMemberSecurityEmailLink(MemberSecurityEmailLinkDO securityEmailLink) throws DaoException;

	/**
	 * <p>确认点击链接</p>
	 *
	 * @param securityEmailLink 确认参数，需要指定其中：
	 * <ul>
	 *   <li>{@link MemberSecurityEmailLinkDO#memberId memberId} 会员编号</li>
	 *   <li>{@link MemberSecurityEmailLinkDO#messageId messageId} 邮件消息编号</li>
	 * </ul>
	 *
	 * @return 更新的记录数，若为 1 时表示确认成功，否则为无效的确认
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-1 14:41:54
	 */
	int confirmMemberSecurityEmailLink(MemberSecurityEmailLinkDO securityEmailLink) throws DaoException;

	/**
	 * <p>提交并注销 TOKEN</p>
	 *
	 * @param link 需要更新的链接信息，需要指定其中：
	 * <ul>
	 *   <li>{@link MemberSecurityEmailLinkDO#memberId memberId} 会员编号</li>
	 *   <li>{@link MemberSecurityEmailLinkDO#token token} TOKEN 值</li>
	 *   <li>{@link MemberSecurityEmailLinkDO#version version} 版本号</li>
	 * </ul>

	 * @return 更新的记录数，若为 1 时表示确认成功，否则为无效的 TOKEN 提交
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-5 11:29:16
	 */
	int confirmToken(MemberSecurityEmailLinkDO link) throws DaoException;

	/**
	 * <p>通过 TOKEN、消息编号获取确认链接</p>
	 *
	 * @param messageId 消息编号
	 * @param token 修改令牌编号
	 *
	 * @return 未被销毁的修改令牌所对应的链接数据
	 *
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-9-5 18:16:44
	 */
	MemberSecurityEmailLinkDO getSecurityEmailLinkByToken(String messageId, String token) throws DaoException;
}
