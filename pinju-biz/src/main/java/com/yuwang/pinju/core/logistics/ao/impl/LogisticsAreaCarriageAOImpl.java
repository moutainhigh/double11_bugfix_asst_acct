package com.yuwang.pinju.core.logistics.ao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.logistics.ao.LogisticsAreaCarriageAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsAreaCarriageManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsCarriageManager;
import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;

public class LogisticsAreaCarriageAOImpl implements LogisticsAreaCarriageAO {
	private final static Log log = LogFactory.getLog(LogisticsAreaCarriageAOImpl.class);
	
										 
	private LogisticsAreaCarriageManager logisticsAreaCarriageManager;
	private LogisticsCarriageManager logisticsCarriageManager;

	public void setLogisticsCarriageManager(
			LogisticsCarriageManager logisticsCarriageManager) {
		this.logisticsCarriageManager = logisticsCarriageManager;
	}

	/**
	 * 查询地区物流运费信息列表
	 * @param logisticsCarriageDO
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public Result selectLogisticsAreaCarriageList(LogisticsAreaCarriageDO logisticsAreaCarriageDO){
		Result result = new ResultSupport();
		try {
			result.setModel("list", logisticsAreaCarriageManager.selectLogisticsAreaCarriageList(logisticsAreaCarriageDO));
			result.setSuccess(true);
		} catch (ManagerException e) {
			log.error("LogisticsAreaCarriageAOImpl selectLogisticsAreaCarriageList error = ", e);
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 删除地区物流运费信息
	 * @param logisticsAreaCarriageDO
	 * @return 受影响的记录数
	 */
	public int deleteLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO){
		try {
			return logisticsAreaCarriageManager.deleteLogisticsAreaCarriage(logisticsAreaCarriageDO);
		} catch (ManagerException e) {
			log.error("LogisticsAreaCarriageAOImpl deleteLogisticsAreaCarriage error = ", e);
			return 0;
		}
	}
	
	/**
	 * 修改地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list){
		try {
			return logisticsAreaCarriageManager.updateLogisticsAreaCarriageList(list);
		} catch (ManagerException e) {
			log.error("LogisticsAreaCarriageAOImpl updateLogisticsAreaCarriageList error = ", e);
			return 0;
		}
	}
	
	/**
	 * 修改地区物流运费
	 * @param List<LogisticsAreaCarriageDO>
	 * @return 受影响的记录数
	 */
	public int updateLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO){
		try {
			return logisticsAreaCarriageManager.updateLogisticsAreaCarriage(logisticsAreaCarriageDO);
		} catch (ManagerException e) {
			log.error("LogisticsAreaCarriageAOImpl updateLogisticsAreaCarriage error = ", e);
			return 0;
		}
	}


	/**
	 * 保存地区物流运费 批量
	 * @param List<LogisticsAreaCarriageDO>
	 */
	public boolean insertLogisticsAreaCarriageList(List<LogisticsAreaCarriageDO> list){
		try {
			logisticsAreaCarriageManager.insertLogisticsAreaCarriageList(list);
			return true;
		} catch (ManagerException e) {
			log.error("LogisticsAreaCarriageAOImpl insertLogisticsAreaCarriageList error = ", e);
			return false;
		}
	}

	
	/**
	 * 保存地区物流运费
	 * @param logisticsCarriageDO
	 * @return ID
	 */
	public Long insertLogisticsAreaCarriage(LogisticsAreaCarriageDO logisticsAreaCarriageDO){
		try {
			return logisticsAreaCarriageManager.insertLogisticsAreaCarriage(logisticsAreaCarriageDO);
		} catch (ManagerException e) {
			log.error("LogisticsAreaCarriageAOImpl insertLogisticsAreaCarriage error = ", e);
			return 0L;
		}	
	}
	
	@Override
	public List<LogisticsAreaCarriageDO> queryLACarriageDO(Long templateId,
			Integer areaId) {
		LogisticsAreaCarriageDO logisticsAreaCarriageDO = new LogisticsAreaCarriageDO();
		logisticsAreaCarriageDO.setTemplateId(templateId);
		logisticsAreaCarriageDO.setAreaId(areaId);
		try {
			return logisticsAreaCarriageManager.selectLogisticsAreaCarriageList(logisticsAreaCarriageDO);
		} catch (ManagerException e) {
			log.error("LogisticsAreaCarriageAOImpl selectLogisticsAreaCarriageList error = ", e);
		}
		return new ArrayList<LogisticsAreaCarriageDO>();
	}
	
	
	@Override
	public List<LogisticsAreaCarriageDO> queryLACarriageDO(
			Long templateId, 
			Integer areaId, 
			String areaName) {
		
		LogisticsAreaCarriageDO logisticsAreaCarriageDO = new LogisticsAreaCarriageDO();
		logisticsAreaCarriageDO.setAreaName(areaName);
		logisticsAreaCarriageDO.setAreaId(areaId);
		logisticsAreaCarriageDO.setTemplateId(templateId);
		
		try {
			List<LogisticsAreaCarriageDO> list = logisticsAreaCarriageManager.selectLogisticsAreaCarriageList(logisticsAreaCarriageDO);
			//定义运送方式：1、平邮   2、快递  3、EMS
			Integer type[] = {1, 2, 3};
			for(Integer t:type){
				boolean exist = false;
				for(LogisticsAreaCarriageDO a:list){
					if(t.compareTo(a.getLogisticsTypeId()) == 0){
						exist = true;
						break;
					}
				}
				
				if(!exist){
					LogisticsCarriageDO logisticsCarriageDO = logisticsCarriageManager.getLogisticsCarriage(templateId, t);
					if(logisticsCarriageDO != null){
						LogisticsAreaCarriageDO areaCarriageDO = new LogisticsAreaCarriageDO();
						areaCarriageDO.setAreaCarriage(logisticsCarriageDO.getDefaultCarriage());
						areaCarriageDO.setAreaCarriageIncrease(logisticsCarriageDO.getCarriageIncrease());
						areaCarriageDO.setLogisticsTypeId(t);
						areaCarriageDO.setTemplateId(templateId);
						list.add(areaCarriageDO);
					}
				}
			}
			Collections.sort(list);
			return list;
		} catch (ManagerException e) {
			log.error("LogisticsAreaCarriageAOImpl selectLogisticsAreaCarriageList error = ", e);
		};
		return new ArrayList<LogisticsAreaCarriageDO>();
	}

	
	
	public void setLogisticsAreaCarriageManager(
			LogisticsAreaCarriageManager logisticsAreaCarriageManager) {
		this.logisticsAreaCarriageManager = logisticsAreaCarriageManager;
	}


}
