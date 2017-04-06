package com.yuwang.pinju.core.distribute.manager.impl;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.distribute.dao.DistributeSupplierDao;
import com.yuwang.pinju.core.distribute.manager.DistributeSupplierManager;
import com.yuwang.pinju.domain.distribute.DistributeSupplierDO;
import com.yuwang.pinju.domain.distribute.DistributeSupplierParamDO;

/**
 * @author xiazhenyu
 * @version 1.0
 * @created 16-07-2011 10:55:46
 */
public class DistributeSupplierManagerImpl extends BaseManager implements DistributeSupplierManager {

	private DistributeSupplierDao distributeSupplierDAO;

	/**
	 * 根据ID取得供应商
	 * 
	 * @param id
	 * @return DistributeSupplierDO
	 * @throws ManagerException
	 */
	@Override
	public DistributeSupplierDO getDistributeSupplierById(long id) throws ManagerException {
		try {
			return distributeSupplierDAO.getDistributeSupplierById(id);
		} catch (DaoException e) {
			throw new ManagerException("查询供应商流程出错", e);
		}
	}

	/**
	 * 保存供应商
	 * 
	 * @param distribureChannelDO
	 * @throws ManagerException
	 */
	@Override
	public Integer saveDistributeSupplier(DistributeSupplierDO distributeSupplierDO)
			throws ManagerException {
		try {
			return distributeSupplierDAO.saveDistributeSupplier(distributeSupplierDO);
		} catch (DaoException e) {
			throw new ManagerException("供应商申请流程出错", e);
		}
	}

	/**
	 * 更新供应商
	 * 
	 * @param distribureChannelDO
	 * @throws ManagerException
	 */
	@Override
	public int updateDistributeSupplier(DistributeSupplierDO distributeSupplierDO) throws ManagerException {
		try {
			return distributeSupplierDAO.updateDistributeSupplier(distributeSupplierDO);
		} catch (DaoException e) {
			throw new ManagerException("供应商更新流程出错", e);
		}
	}

	/**
	 * 根据条件取得供应商
	 * 
	 * @param id
	 * @return DistributeSupplierDO
	 * @throws ManagerException
	 */
	@Override
	public DistributeSupplierDO getDistributeSupplier(DistributeSupplierParamDO distributeSupplierDO)
			throws ManagerException {
		try {
			return distributeSupplierDAO.getDistributeSupplier(distributeSupplierDO);
		} catch (DaoException e) {
			throw new ManagerException("按条件查询供应商信息出错", e);
		}
	}

	public DistributeSupplierDao getDistributeSupplierDAO() {
		return distributeSupplierDAO;
	}

	public void setDistributeSupplierDAO(DistributeSupplierDao distributeSupplierDAO) {
		this.distributeSupplierDAO = distributeSupplierDAO;
	}

}