package com.yuwang.pinju.core.member.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.login.MemberSinaAccountDO;

/**
 * <p>新浪微博会员账号数据</p>
 *
 * @author gaobaowen
 * @since 2011-11-21 下午01:28:19
 */
public interface MemberSinaAccountDAO {

	/**
	 * <p>根据会员编号获取新浪账号信息</p>
	 *
	 * @param memberId  会员编号
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:28:36
	 */
	MemberSinaAccountDO getSinaAccountByMid(long memberId) throws DaoException;

	/**
	 * <p>根据新浪微博会员编号获取新浪账号信息</p>
	 *
	 * @param sinaUid
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:28:54
	 */
	MemberSinaAccountDO getSinaAccountBySinaUid(String sinaUid) throws DaoException;

	/**
	 * <p>新增新浪微博账号数据</p>
	 *
	 * @param sinaAccount
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 * @since 2011-11-21 13:29:18
	 */
	MemberSinaAccountDO addSinaAccount(MemberSinaAccountDO sinaAccount) throws DaoException;
}
