package com.yuwang.pinju.core.rights.manager.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.rights.dao.RightsWorkOrderDAO;
import com.yuwang.pinju.core.rights.manager.RightsWorkOrderManager;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderQuery;

/**
 * @Project: 品聚财务处理工单管理类
 * @Description: <p>业务描述</p>
 * @author 石兴 shixing@zba.com
 * @date 2011-10-11 上午10:49:55
 * @update 石兴 2011-10-11 上午10:49:55
 * @version V1.0
 */
public class RightsWorkOrderManagerImpl extends BaseManager implements RightsWorkOrderManager{
	
	private RightsWorkOrderDAO rightsWorkOrderDAO;
	
	@Override
	public List<FinanceWorkOrderDO> getRightsWorkOrderDOs(FinanceWorkOrderQuery rightsWorkOrderQuery) throws ManagerException {
		try{
			return rightsWorkOrderDAO.getRightsWorkOrderDOs(rightsWorkOrderQuery);
		}catch(DaoException e){
			throw new ManagerException("getRightsWorkOrderDOs",e); 
		}
	}

	@Override
	public FinanceWorkOrderDO getRightsWorkOrderDOByBizType(FinanceWorkOrderQuery rightsWorkOrderQuery) throws ManagerException{
		try {
			return rightsWorkOrderDAO.getRightsWorkOrderDOByBizType(rightsWorkOrderQuery);
		} catch (DaoException e) {
			throw new ManagerException("getRightsWorkOrderDOByBizType",e); 
		}
	}
	
	@Override
	public void insertRightsWorkOrderDO(FinanceWorkOrderDO rightsWorkOrderDO)throws ManagerException {
		try {
			rightsWorkOrderDAO.insertRightsWorkOrderDO(rightsWorkOrderDO);
		} catch (DaoException e) {
			throw new ManagerException("insertRightsWorkOrderDO,error:" ,e);
		}
		
	}

	@Override
	public boolean updateRightsWorkOrderDO(FinanceWorkOrderDO rightsWorkOrderDO)throws ManagerException {
		boolean flag = false;
		try {
			Integer cou = rightsWorkOrderDAO.updateRightsWorkOrderDO(rightsWorkOrderDO);
			if (cou==1)	flag=true;
		} catch (DaoException e) {
			throw new ManagerException("updateItemXianGouDO,error:" ,e);
		}
		return flag;
	}
	
	public void setRightsWorkOrderDAO(RightsWorkOrderDAO rightsWorkOrderDAO) {
		this.rightsWorkOrderDAO = rightsWorkOrderDAO;
	}

	@Override
	public List<FinanceWorkOrderDO> queryRightsWorkOrderDOByFail(Map<String, Object> map) throws ManagerException {
		try{
			return rightsWorkOrderDAO.queryRightsWorkOrderDOByFail(map);
		}catch(DaoException e){
			throw new ManagerException("queryRightsWorkOrderDOByFail",e); 
		}
	}

	@Override
	public FinanceWorkOrderDO getFinanceWorkOrderDOByOrderId(FinanceWorkOrderQuery rightsWorkOrderQuery) throws ManagerException {
		try {
			return rightsWorkOrderDAO.getFinanceWorkOrderDOByOrderId(rightsWorkOrderQuery);
		} catch (DaoException e) {
			throw new ManagerException("getFinanceWorkOrderDOByOrderId",e); 
		}
	}

}
