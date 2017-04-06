package com.yuwang.pinju.core.distribute.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public interface DistributeChannelManager {

	/**
	 * 取得分销商数
	 * 
	 * @param distribureChannelDO
	 * @throws ManagerException
	 */
	public Integer getDistributeCount(DistribureChannelParamDO param) throws ManagerException;

	/**
	 * 取得分销商
	 * 
	 * @param distribureChannelDO
	 * @throws ManagerException
	 */
	public List<DistribureChannelDO> queryDistributeList(DistribureChannelParamDO param) throws ManagerException;

	/**
	 * 更新分销商
	 * 
	 * @param distributeList
	 * @throws ManagerException
	 */
	public void updateDistribute(DistribureChannelDO param) throws ManagerException;

	/**
	 * 取得分销商信息
	 * 
	 * @return
	 * @throws ManagerException
	 */
	public DistributeDistributorDO getDistributorByUserId(long userId) throws ManagerException;

	/**
	 * 取得合作中分销商
	 * 
	 * @throws ManagerException
	 */
	abstract List<DistributeDistributorDO> findDistributeList(DistribureChannelParamDO param)
			throws ManagerException;
	
}