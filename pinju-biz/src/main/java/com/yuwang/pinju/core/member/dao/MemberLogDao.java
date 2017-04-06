package com.yuwang.pinju.core.member.dao;

import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;

/**
 * <p>会员日志相关 DAO</p>
 * @author gaobaowen
 * 2011-6-10 下午04:18:53
 */
public interface MemberLogDao {

	/**
	 * <p>保存用户登录日志</p>
	 *
	 * @param memberLoginHis
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	MemberLoginHisDO persistMemberLoginHis(MemberLoginHisDO memberLoginHis) throws DaoException;

	/**
	 * <p>更新用户登录日志</p>
	 *
	 * @param parameters  更新参数（logoutTime:Date, MEMBER_ID:long, SESSION_ID:String）
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	int updateMemberLoginHis(Map<String, Object> parameters) throws DaoException;
}
