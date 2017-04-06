package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.MemberSnsInfoDO;

/**
 * <p>会员社区信息（MEMBER_SNS_INFO）</p>
 *
 * @author gaobaowen
 * @since 2011-6-30 下午03:07:12
 */
public interface MemberSnsInfoDAO {

	/**
	 * <p>新插入一条会员社区信息</p>
	 *
	 * @param memberSnsInfo
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 下午03:47:32
	 */
	MemberSnsInfoDO insertMemberSnsInfo(MemberSnsInfoDO memberSnsInfo) throws DaoException;

	/**
	 * <p>更新会员社区信息</p>
	 *
	 * @param memberSnsInfo
	 * @return  更新的记录数（一般更新成功将会返回 1，否则会返回 0）
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 下午03:47:55
	 */
	int updateMemberSnsInfo(MemberSnsInfoDO memberSnsInfo) throws DaoException;

	/**
	 * <p>根据会员编号查询会员社区信息</p>
	 *
	 * @param memberId  会员编号
	 * @return  查询到的会员社区信息
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-6-30 下午03:48:29
	 */
	MemberSnsInfoDO getMemberSnsInfoByMemberId(long memberId) throws DaoException;
}
