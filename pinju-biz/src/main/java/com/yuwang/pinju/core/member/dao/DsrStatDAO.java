package com.yuwang.pinju.core.member.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.member.DsrStatDO;

/**
 * <p>卖家 DSR 统计信息相关数据库操作（MEMBER_DSR_STAT）</p>
 * @author gaobaowen
 * 2011-6-17 下午03:15:28
 */
public interface DsrStatDAO {

	/**
	 * <p>根据会员编号获取该会员的 DSR 评价数据</p>
	 *
	 * @param memberId
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	List<DsrStatDO> getDsrStatsByMemberId(long memberId) throws DaoException;
}
