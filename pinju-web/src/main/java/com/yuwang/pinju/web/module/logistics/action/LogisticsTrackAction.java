package com.yuwang.pinju.web.module.logistics.action;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.logistics.ao.LogisticsCorpAO;
import com.yuwang.pinju.core.order.ao.LogisticsServiceAO;
import com.yuwang.pinju.domain.logistics.LogisticsCorpDO;
import com.yuwang.pinju.domain.order.LogisticsServiceDO;
import com.yuwang.pinju.web.module.BaseAction;

public class LogisticsTrackAction extends BaseAction implements Action{

	/**
	 * 
	 */
	private final static Log log = LogFactory.getLog(LogisticsTrackAction.class);
	private static final long serialVersionUID = 1L;
	private String outLogisticsId;
	private String logisticsType;
	private LogisticsServiceDO logisticsServiceDO;
	private LogisticsServiceAO logisticsServiceAO;
	
	private LogisticsCorpAO logisticsCorpAO;
	private List<LogisticsCorpDO> logisticsCorpDOList;
	
	public String getLogisticsInfo(){
		try {
			if(StringUtils.isNotBlank(logisticsType)){
				LogisticsCorpDO logisticsCorpDO=new LogisticsCorpDO();
				logisticsCorpDO.setCorpCode(logisticsType);
				logisticsCorpDO=logisticsCorpAO.getLogisticsCorpByStatus(logisticsCorpDO).get(0);	
				logisticsServiceDO = logisticsServiceAO.getTrackBybillcode
				(outLogisticsId, logisticsType,logisticsCorpDO.getCorpHCode());
				logisticsServiceDO.setLogisticsUrl(logisticsCorpDO.getCorpUrl());
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Event=[LogisticsTrackAction.getLogisticsInfo()] error",e);
		}
		return SUCCESS;
	}
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if (log.isInfoEnabled()) {
			log.info("LogisticsTrackAction.execute() start...");
		}
		try {
			logisticsCorpDOList = logisticsCorpAO.getLogisticsCorpByStatus(new LogisticsCorpDO());
		} catch (Exception e) {
			log.error("Event=[LogisticsTrackAction.getLogisticsInfo()] error",e);
		}
		return SUCCESS;
	}


	public String getOutLogisticsId() {
		return outLogisticsId;
	}
	public void setOutLogisticsId(String outLogisticsId) {
		this.outLogisticsId = outLogisticsId;
	}
	public String getLogisticsType() {
		return logisticsType;
	}
	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public LogisticsServiceDO getLogisticsServiceDO() {
		return logisticsServiceDO;
	}

	public void setLogisticsServiceDO(LogisticsServiceDO logisticsServiceDO) {
		this.logisticsServiceDO = logisticsServiceDO;
	}

	public LogisticsServiceAO getLogisticsServiceAO() {
		return logisticsServiceAO;
	}

	public void setLogisticsServiceAO(LogisticsServiceAO logisticsServiceAO) {
		this.logisticsServiceAO = logisticsServiceAO;
	}

	public LogisticsCorpAO getLogisticsCorpAO() {
		return logisticsCorpAO;
	}

	public void setLogisticsCorpAO(LogisticsCorpAO logisticsCorpAO) {
		this.logisticsCorpAO = logisticsCorpAO;
	}

	public List<LogisticsCorpDO> getLogisticsCorpDOList() {
		return logisticsCorpDOList;
	}

	public void setLogisticsCorpDOList(List<LogisticsCorpDO> logisticsCorpDOList) {
		this.logisticsCorpDOList = logisticsCorpDOList;
	}
}
