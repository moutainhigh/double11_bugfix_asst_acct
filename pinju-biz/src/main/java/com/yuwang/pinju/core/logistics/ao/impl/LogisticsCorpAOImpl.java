package com.yuwang.pinju.core.logistics.ao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsCorpManager;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;

public class LogisticsCorpAOImpl implements LogisticsCorpAO {

	protected final Log log = LogFactory.getLog(this.getClass().getName());
	
	private LogisticsCorpManager logisticsCorpManager;
	

	public LogisticsCorpManager getLogisticsCorpManager() {
		return logisticsCorpManager;
	}

	public void setLogisticsCorpManager(LogisticsCorpManager logisticsCorpManager) {
		this.logisticsCorpManager = logisticsCorpManager;
	}

	@Override
	public List<LogisticsCorpDO> getLogisticsCorpByStatus(LogisticsCorpDO corpDo) {
		List<LogisticsCorpDO> list = null;
		try {
			list = logisticsCorpManager.getLogisticsCorpByStatus(corpDo);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsCorpAOImpl#getLogisticsCorpByStatus]获取物流公司信息", e);
		}
		return list;
	}

	@Override
	public void addLogisticsCorp(LogisticsCorpDO corpDO) {
		try {
			logisticsCorpManager.addLogisticsCorp(corpDO);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsCorpAOImpl#addLogisticsCorp]新增物流公司信息", e);
		}
	}

	@Override
	public void updateLogisticsCorp(LogisticsCorpDO corpDO) {
		try {
			logisticsCorpManager.updateLogisticsCorp(corpDO);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsCorpAOImpl#updateLogisticsCorp]更新物流公司信息", e);
		}
	}

	@Override
	public int delLogisticsCorp(Long id) {
		int i=0;
		try {
			i = logisticsCorpManager.delLogisticsCorp(id);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsCorpAOImpl#delLogisticsCorp]删除物流公司信息", e);
		}
		return i;
	}

}
