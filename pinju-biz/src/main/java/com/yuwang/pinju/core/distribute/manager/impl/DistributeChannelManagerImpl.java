package com.yuwang.pinju.core.distribute.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.distribute.dao.DistributeChannelDao;
import com.yuwang.pinju.core.distribute.manager.DistributeChannelManager;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public class DistributeChannelManagerImpl extends BaseManager implements DistributeChannelManager {

	private DistributeChannelDao distributeChannelDAO;

	/**
	 * 取得合作中分销商数
	 */
	@Override
	public Integer getDistributeCount(DistribureChannelParamDO param) throws ManagerException {
		try {
			return distributeChannelDAO.getDistributeCount(param);
		} catch (DaoException e) {
			throw new ManagerException("取得合作中分销商数", e);
		}
	}

	/**
	 * 取得合作中分销商
	 * 
	 * @throws ManagerException
	 */
	@Override
	public List<DistribureChannelDO> queryDistributeList(DistribureChannelParamDO param) throws ManagerException {
		try {
			return distributeChannelDAO.queryDistributeList(param);
		} catch (DaoException e) {
			throw new ManagerException("取得合作中分销商", e);
		}
	}
	
	/**
	 * 取得合作中分销商
	 * 
	 * @throws ManagerException
	 */
	@Override
	public List<DistributeDistributorDO> findDistributeList(DistribureChannelParamDO param) throws ManagerException {
		try {
			return distributeChannelDAO.findDistributeList(param);
		} catch (DaoException e) {
			throw new ManagerException("取得合作中分销商", e);
		}
	}

	/**
	 * 更新分销商
	 */
	@Override
	public void updateDistribute(DistribureChannelDO param) throws ManagerException {
		try {
			distributeChannelDAO.updateDistribute(param);
		} catch (DaoException e) {
			throw new ManagerException("更新分销商id:" + param.getId(), e);
		}
	}

	/**
	 * 取得分销商信息
	 */
	@Override
	public DistributeDistributorDO getDistributorByUserId(long userId) throws ManagerException {
		try {
			return distributeChannelDAO.getDistributorByUserId(userId);
		} catch (DaoException e) {
			throw new ManagerException("取得分销商信息", e);
		}
	}

	public DistributeChannelDao getDistributeChannelDAO() {
		return distributeChannelDAO;
	}

	public void setDistributeChannelDAO(DistributeChannelDao distributeChannelDAO) {
		this.distributeChannelDAO = distributeChannelDAO;
	}
}