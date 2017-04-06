package com.yuwang.pinju.core.distribute.ao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.distribute.ao.DistributeChannelAO;
import com.yuwang.pinju.core.distribute.manager.DistributeChannelManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.distribute.DistribureChannelDO;
import com.yuwang.pinju.domain.distribute.DistribureChannelParamDO;
import com.yuwang.pinju.domain.distribute.DistributeDistributorDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:45
 */
public class DistributeChannelAOImpl extends BaseAO implements DistributeChannelAO {

	private DistributeChannelManager distributeChannelManager;

	/**
	 * 取得申请中分销商数
	 */
	@Override
	public int getApplyDistributeCount(int supplierId) {
		DistribureChannelParamDO param = new DistribureChannelParamDO();
		param.setStatus((short) 0);
		param.setSupplierId(supplierId);
		try {
			return distributeChannelManager.getDistributeCount(param);
		} catch (ManagerException e) {
			log.error("取得申请中分销商数", e);
		}
		return 0;
	}

	/**
	 * 取得合作中分销商数
	 */
	@Override
	public int getDistributeCount(int supplierId) {
		DistribureChannelParamDO param = new DistribureChannelParamDO();
		param.setStatus((short) 1);
		param.setSupplierId(supplierId);
		try {
			return distributeChannelManager.getDistributeCount(param);
		} catch (ManagerException e) {
			log.error("取得合作中分销商数", e);
		}
		return 0;
	}

	/**
	 * 取得合同终止分销商数
	 */
	@Override
	public int getExpiredDistributeCount(int supplierId) {
		DistribureChannelParamDO param = new DistribureChannelParamDO();
		param.setStatus((short) -3);
		param.setSupplierId(supplierId);
		try {
			return distributeChannelManager.getDistributeCount(param);
		} catch (ManagerException e) {
			log.error("取得合同终止分销商数", e);
		}
		return 0;
	}

	/**
	 * 取得申请中分销商
	 */
	@Override
	public List<DistribureChannelDO> queryApplyDistribute(DistribureChannelParamDO param) {
		try {
			if (null == param || 0 == param.getItems()) {
				return new ArrayList<DistribureChannelDO>();
			}
			param.setStatus((short) 0);
			return distributeChannelManager.queryDistributeList(param);
		} catch (ManagerException e) {
			log.error("取得申请中分销商", e);
		}
		return null;
	}

	/**
	 * 取得已合作分销商
	 */
	@Override
	public List<DistribureChannelDO> queryDistributeList(DistribureChannelParamDO param) {
		try {
			if (null == param || 0 == param.getItems()) {
				return new ArrayList<DistribureChannelDO>();
			}
			param.setStatus((short) 1);
			return distributeChannelManager.queryDistributeList(param);
		} catch (ManagerException e) {
			log.error("取得已合作分销商", e);
		}
		return null;
	}
	
	/**
	 * 取得已合作分销商
	 */
	@Override
	public List<DistributeDistributorDO> findDistributeList(DistribureChannelParamDO param) {
		try {
			if (null == param || 0 == param.getItems()) {
				return new ArrayList<DistributeDistributorDO>();
			}
			param.setStatus((short) 1);
			return distributeChannelManager.findDistributeList(param);
		} catch (ManagerException e) {
			log.error("取得已合作分销商", e);
		}
		return null;
	}

	/**
	 * 取得合同终止分销商
	 */
	@Override
	public List<DistribureChannelDO> queryExpiredDistribute(DistribureChannelParamDO param) {
		try {
			if (null == param || 0 == param.getItems()) {
				return new ArrayList<DistribureChannelDO>();
			}
			param.setStatus((short) -3);
			return distributeChannelManager.queryDistributeList(param);
		} catch (ManagerException e) {
			log.error("取得合同终止分销商", e);
		}
		return null;
	}

	/**
	 * 同意分销商申请List
	 */
	@Override
	public void setDistributePass(List<DistribureChannelDO> distributeList) {
		try {
			if (!CollectionUtils.isEmpty(distributeList)) {
				for (DistribureChannelDO param : distributeList) {
					// 页面checked
					if (ObjectUtils.nullSafeEquals(param.getChecked(), new Integer(1))) {
						distributeChannelManager.updateDistribute(param);
					}
				}
			}
		} catch (ManagerException e) {
			log.error("同意分销商申请List", e);
		}
	}

	/**
	 * 拒绝分销商申请List
	 */
	@Override
	public void setDistributeRefuse(List<DistribureChannelDO> distributeList) {
		try {
			for (DistribureChannelDO param : distributeList) {
				// 页面checked
				if (ObjectUtils.nullSafeEquals(param.getChecked(), new Integer(1))) {
					distributeChannelManager.updateDistribute(param);
				}
			}
		} catch (ManagerException e) {
			log.error("拒绝分销商申请List", e);
		}
	}

	/**
	 * 同意分销商申请
	 */
	@Override
	public void setDistributePass(DistribureChannelDO distribute) {
		try {
			distributeChannelManager.updateDistribute(distribute);
		} catch (ManagerException e) {
			log.error("同意分销商申请", e);
		}
	}

	/**
	 * 拒绝分销商申请
	 */
	@Override
	public void setDistributeRefuse(DistribureChannelDO distribute) {
		try {
			distributeChannelManager.updateDistribute(distribute);
		} catch (ManagerException e) {
			log.error("拒绝分销商申请", e);
		}
	}

	public DistributeChannelManager getDistributeChannelManager() {
		return distributeChannelManager;
	}

	public void setDistributeChannelManager(DistributeChannelManager distributeChannelManager) {
		this.distributeChannelManager = distributeChannelManager;
	}
}