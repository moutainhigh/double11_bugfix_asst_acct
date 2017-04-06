package com.yuwang.pinju.core.rate.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.rate.DsrResultDO;

/**
 * <p>买家对于订单的动态评分数据库操作，表名：RATE_DSR_RESULT</p>
 *
 * @author gaobaowen
 * 2011-6-15 上午09:56:47
 */
public interface DsrResultDAO {

	/**
	 * <p>根据评论 ID 获取动态评分记录</p>
	 *
	 * @param id
	 * @return
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	DsrResultDO getDsrResult(long id) throws DaoException;

	/**
	 * <p>保存动态评分记录</p>
	 *
	 * @param dsrResult  需要保存的数据
	 * @return  含有保存后主键 ID 值的数据
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	DsrResultDO saveDsrResult(DsrResultDO dsrResult) throws DaoException;

	/**
	 * <p>新增订单的评论</p>
	 *
	 * @param dsrResults
	 * @throws DaoException
	 *
	 * @author gaobaowen
	 */
	void saveDsrResults(List<DsrResultDO> dsrResults) throws DaoException;
}
