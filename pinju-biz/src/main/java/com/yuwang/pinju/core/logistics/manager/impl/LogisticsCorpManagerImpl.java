package com.yuwang.pinju.core.logistics.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.dao.LogisticsCorpDAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsCorpManager;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;

public class LogisticsCorpManagerImpl implements LogisticsCorpManager {

	private LogisticsCorpDAO logisticsCorpDAO;

	public void setLogisticsCorpDAO(LogisticsCorpDAO logisticsCorpDAO) {
		this.logisticsCorpDAO = logisticsCorpDAO;
	}
	
	@Override
	public List<LogisticsCorpDO> getLogisticsCorpByStatus(LogisticsCorpDO corpDo) throws ManagerException {
		try {
			return logisticsCorpDAO.getLogisticsCorpByStatus(corpDo);
		} catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCorpDAOImpl#getLogisticsCorpByStatus]取得状态正常的所有物流公司失败", e);
		}
	}

	@Override
	public void addLogisticsCorp(LogisticsCorpDO corpDO) throws ManagerException {
		try {
			logisticsCorpDAO.addLogisticsCorp(corpDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCorpDAOImpl#addLogisticsCorp]新增物流公司失败", e);
		}
	}

	@Override
	public void updateLogisticsCorp(LogisticsCorpDO corpDO) throws ManagerException {
		try {
			logisticsCorpDAO.updateLogisticsCorp(corpDO);
		} catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCorpDAOImpl#updateLogisticsCorp]更新物流公司失败", e);
		}

	}

	@Override
	public int delLogisticsCorp(Long id) throws ManagerException {
		try {
			return logisticsCorpDAO.delLogisticsCorp(id);
		} catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsCorpDAOImpl#delLogisticsCorp]删除物流公司失败", e);
		}
	}

}
