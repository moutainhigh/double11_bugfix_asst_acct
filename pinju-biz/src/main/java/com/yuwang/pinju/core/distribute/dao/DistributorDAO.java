package com.yuwang.pinju.core.distribute.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;

/**
 * @author caiwei
 * 
 */
public interface DistributorDAO {

	/**
	 * 保存分销商
	 * 
	 * @param param
	 *            分销商
	 * @throws DaoException
	 */
	void saveDistributor(DistributeDistributorDO param) throws DaoException;

	/**
	 * 设置分销商分享购页面信息
	 * 
	 * @param channelId
	 *            --分销商编号
	 * @param configuration
	 *            --分享购页面信息集合
	 * @throws DaoException
	 */
	void setShareDesign(Long channelId, String configuration)
			throws DaoException;

	/**
	 * 根据MemberId查询分销商信息
	 * 
	 * @param memberId
	 *            用户ID
	 * @throws DaoException
	 */
	DistributeDistributorDO findDistributorByMemberId(Long memberId)
			throws DaoException;
}
