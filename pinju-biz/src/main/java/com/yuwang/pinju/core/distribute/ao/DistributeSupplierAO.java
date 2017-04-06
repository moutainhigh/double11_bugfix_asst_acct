package com.yuwang.pinju.core.distribute.ao;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public interface DistributeSupplierAO {

	/**
	 * 申请供应商
	 * 
	 * @param distribureChannelDO
	 */
	public DistributeSupplierDO applySupplier(DistributeSupplierParamDO distributeSupplierDO);

	/**
	 * 发布招募书
	 * 
	 * @param distributeSupplierDO
	 * @return
	 */
	public DistributeSupplierDO releaseSupplier(DistributeSupplierDO distributeSupplierDO);

	/**
	 * 根据userID取得供应商
	 * 
	 * @param userId
	 * @return DistributeSupplierDO
	 * @throws ManagerException
	 */
	public DistributeSupplierDO getDistributeSupplier(long userId);
}