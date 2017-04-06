package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.MemberSequenceDO;

/**
 * <p>会员编号序号数据操作</p>
 *
 * @author gaobaowen
 * 2011-6-8 上午11:30:41
 */
public interface MemberSequenceDao {

	/**
	 * <p>根据序列名字查找序列对象</p>
	 *
	 * @param name
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	public MemberSequenceDO findMemberSequence(String name) throws DaoException;

	/**
	 * <p>更新序列号</p>
	 *
	 * @param sequence
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	public int updateMemberSequence(MemberSequenceDO sequence) throws DaoException;
}
