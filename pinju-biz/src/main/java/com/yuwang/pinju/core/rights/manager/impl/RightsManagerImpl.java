package com.yuwang.pinju.core.rights.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.rights.dao.RightsDAO;
import com.yuwang.pinju.core.rights.manager.RightsManager;
import com.yuwang.pinju.domain.rights.RightsDO;
import com.yuwang.pinju.domain.rights.RightsLogisticsDO;
import com.yuwang.pinju.domain.rights.RightsMessageDO;
import com.yuwang.pinju.domain.rights.RightsMsgQuery;
import com.yuwang.pinju.domain.rights.RightsQuery;

/**  
 * @Project: pinju-biz
 * @Description: 消保维权Manager
 * @author 石兴 shixing@zba.com
 * @date 2011-6-29 下午02:10:32
 * @update 2011-6-29 下午02:10:32
 * @version V1.0  
 */
public class RightsManagerImpl extends BaseManager implements RightsManager {

	private RightsDAO rightsDAO;
	
	@Override
	public List<RightsDO> getApplyRightsDOs(RightsQuery rightsQuery) throws ManagerException {
		try {
			return rightsDAO.getRightsDOs(rightsQuery);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public RightsDO getApplyRightsDO(RightsDO rightsDO) throws ManagerException {
		try {
			return rightsDAO.getApplyRightsDO(rightsDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public RightsDO getRightsDOById(Long id) throws ManagerException {
		try {
			return rightsDAO.getRightsDOById(id);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void insertRightsRecord(RightsDO rightsDO) throws ManagerException {
		try {
			rightsDAO.insertRightsRecord(rightsDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void updateRightsRecord(RightsDO rightsDO) throws ManagerException {
		try {
			rightsDAO.updateRightsRecord(rightsDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}

	}

	@Override
	public RightsLogisticsDO getRightsLogisticsDO(RightsLogisticsDO rightsLogisticsDO) throws ManagerException {
		try {
			return rightsDAO.getRightsLogisticsDO(rightsLogisticsDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public List<RightsMessageDO> getRightsMessageDOs(RightsMsgQuery rightsMsgQuery) throws ManagerException {
		try {
			return rightsDAO.getRightsMessageDOs(rightsMsgQuery);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}

	@Override
	public void insertLogisticsRecord(RightsLogisticsDO rightsLogisticsDO)
			throws ManagerException {
		try {
			rightsDAO.insertLogisticsRecord(rightsLogisticsDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}		
	}

	@Override
	public void insertMessageRecord(RightsMessageDO rightsMessageDO)
			throws ManagerException {
		try {
			rightsDAO.insertMessageRecord(rightsMessageDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
		
	}

	@Override
	public int updateRightsLogisticsRecord(RightsLogisticsDO rightsLogisticsDO)throws ManagerException {
		try {
			return rightsDAO.updateRightsLogisticsRecord(rightsLogisticsDO);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	@Override
	public int getRightsCount(RightsQuery rightsQuery) throws ManagerException {
		try {
			return rightsDAO.getRightsCount(rightsQuery);
		} catch (Exception e) {
			throw new ManagerException(e);
		}
	}
	
	public void setRightsDAO(RightsDAO rightsDAO) {
		this.rightsDAO = rightsDAO;
	}


}
