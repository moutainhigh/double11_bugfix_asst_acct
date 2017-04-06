package com.yuwang.pinju.core.member.dao;

import java.util.Date;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.security.MemberSecurityDO;

/**
 * <p>会员安全相关数据库操作</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 11:33:58
 */
public interface MemberSecurityDAO {

	/**
	 * <p>根据会员编号获取会员安全信息</p>
	 *
	 * @param memberId 会员编号
	 * @return 相匹配的会员安全信息，无法找到时返回 null 值
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 11:34:15
	 */
	MemberSecurityDO getSecurityByMid(long memberId) throws DaoException;

	/**
	 * <p>添加会员安全信息</p>
	 *
	 * @param security  需要添加的会员安全信息数据
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 11:34:56
	 */
	MemberSecurityDO addMemberSecurity(MemberSecurityDO security) throws DaoException;

	/**
	 * <p>更新会员安全种类的掩码</p>
	 *
	 * @param security 更新的数据及条件，需要含有以下数据：
	 * <ul>
	 * 	<li>{@link MemberSecurityDO#securityAuthMask securityAuthMask} 更新后的掩码值</li>
	 *  <li>{@link MemberSecurityDO#memberId memberId} 会员编号</li>
	 *  <li>{@link MemberSecurityDO#version version} 数据版本号</li>
	 * </ul>
	 * 
	 * @return 更新的数据数量
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 11:35:21
	 */
	int updateSecurityMask(MemberSecurityDO security) throws DaoException;

	/**
	 * <p>更新会员最后登录时间</p>
	 *
	 * @param memberId 会员编号
	 * @param currentLoginTime  当前登录时间
	 * @param currentLoginIp  当前登录 IP
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-12-2 下午03:06:51
	 */
	int updateLastLoginTime(long memberId, Date currentLoginTime, String currentLoginIp) throws DaoException;
}
