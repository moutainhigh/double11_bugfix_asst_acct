package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.MemberExtDO;

public interface MemberExtDAO {

	/**
	 * 查询会员的扩展信息
	 * @param memberId
	 * @return
	 */
	MemberExtDO findMemberExtDOByMemberId(long memberId) throws DaoException;
	
	/**
	 * 插入会员扩展信息
	 * @param memberExtDO
	 * @throws DaoException
	 */
	void insertMemberExtDO(MemberExtDO memberExtDO) throws DaoException;
	
	/**
	 * 更新会员扩展信息
	 * @param memberExtDO
	 * @throws DaoException
	 */
	void updateMemberExtDO(MemberExtDO memberExtDO) throws DaoException;
}
