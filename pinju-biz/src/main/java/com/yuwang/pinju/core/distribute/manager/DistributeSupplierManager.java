package com.yuwang.pinju.core.distribute.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public interface DistributeSupplierManager {

	/**
	 * 根据ID取得供应商
	 * 
	 * @param id
	 * @return DistributeSupplierDO
	 * @throws ManagerException
	 */
	public DistributeSupplierDO getDistributeSupplierById(long id) throws ManagerException;
	
	/**
	 * 根据条件取得供应商
	 * 
	 * @param id
	 * @return DistributeSupplierDO
	 * @throws ManagerException
	 */
	public DistributeSupplierDO getDistributeSupplier(DistributeSupplierParamDO distributeSupplierDO) throws ManagerException;

	/**
	 * 保存供应商
	 * 
	 * @param distribureChannelDO
	 * @throws ManagerException
	 */
	public Integer saveDistributeSupplier(DistributeSupplierDO distributeSupplierDO) throws ManagerException;

	/**
	 * 更新供应商
	 * 
	 * @param distribureChannelDO
	 * @throws ManagerException
	 */
	public int updateDistributeSupplier(DistributeSupplierDO distributeSupplierDO) throws ManagerException;
}