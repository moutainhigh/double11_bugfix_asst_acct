package com.yuwang.pinju.core.distribute.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.distribute.dao.DistributorDAO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;

/**
 * @author caiwei
 * 
 */
@SuppressWarnings("unchecked")
public class DistributorDAOImpl extends BaseDAO implements DistributorDAO {

	/**
	 * 保存分销商
	 * 
	 * @param param
	 *            分销商
	 * @throws DaoException
	 */
	@Override
	public void saveDistributor(DistributeDistributorDO param)
			throws DaoException {
		executeInsert("distribute_distributor.insertDistributeDistributor",
				param);
	}

	/**
	 * 根据MemberId查询分销商信息
	 * 
	 * @param memberId
	 *            用户ID
	 * @throws DaoException
	 */

	@Override
	public DistributeDistributorDO findDistributorByMemberId(Long memberId)
			throws DaoException {
		List<DistributeDistributorDO> resultList = super.executeQueryForList(
				"distribute_distributor.selectDistributeDistributorByMemberId",
				memberId);
		if (resultList != null && resultList.size() > 1) {
			throw new DaoException(
					"[DistributorDAOImpl#findDistributorByMemberId->returned too many results]");
		} else if (resultList != null && resultList.size() == 1) {
			return (DistributeDistributorDO) resultList.get(0);
		} else {
			return new DistributeDistributorDO();
		}
	}

	/**
	 * 设置分销商分享购页面信息
	 * 
	 * @param channelId
	 *            --分销商编号
	 * @param configuration
	 *            --分享购页面信息集合
	 * @throws DaoException
	 */
	public void setShareDesign(Long channelId, String configuration)
			throws DaoException {
		// TODO Auto-generated method stub
		DistributeDistributorDO channel = new DistributeDistributorDO();
		channel.setId(channelId);
		channel.setConfiguration(configuration);
		super.executeUpdate("distribute_distributor.setShareDesign", channel);
	}
}
