package com.yuwang.pinju.core.distribute.ao;

import java.util.List;

import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:45
 */
public interface DistributeChannelAO {

	/**
	 * 取得申请中分销商数
	 */
	public int getApplyDistributeCount(int supplierId);

	/**
	 * 取得合作中分销商数
	 */
	public int getDistributeCount(int supplierId);

	/**
	 * 取得合同终止分销商数
	 */
	public int getExpiredDistributeCount(int supplierId);

	/**
	 * 取得申请中分销商
	 * 
	 * @param DistribureChannelParamDO
	 */
	public List<DistribureChannelDO> queryApplyDistribute(DistribureChannelParamDO param);

	/**
	 * 取得合作中分销商
	 * 
	 * @param param
	 * @return
	 */
	public List<DistribureChannelDO> queryDistributeList(DistribureChannelParamDO param);

	/**
	 * 取得合同终止分销商
	 * 
	 * @param param
	 */
	public List<DistribureChannelDO> queryExpiredDistribute(DistribureChannelParamDO param);

	/**
	 * 同意分销商申请
	 * 
	 * @param distributeList
	 */
	public void setDistributePass(List<DistribureChannelDO> distributeList);

	/**
	 * 拒绝分销商申请
	 * 
	 * @param distributeList
	 */
	public void setDistributeRefuse(List<DistribureChannelDO> distributeList);

	/**
	 * 同意分销商申请
	 * 
	 * @param distributeList
	 */
	public void setDistributePass(DistribureChannelDO distribute);

	/**
	 * 拒绝分销商申请
	 * 
	 * @param distributeList
	 */
	public void setDistributeRefuse(DistribureChannelDO distribute);

	/**
	 * 取得已合作分销商
	 */
	abstract List<DistributeDistributorDO> findDistributeList(DistribureChannelParamDO param);

}