package com.yuwang.pinju.core.logistics.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.logistics.dao.LogisticsTemplateDAO;
import com.yuwang.pinju.core.logistics.manager.LogisticsTemplateManager;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateQuery;

/**
 * <p>物流模板</p>
 *
 * @author shihongbo
 * @since   2011-07-09
 */

public class LogisticsTemplateManagerImpl implements LogisticsTemplateManager{
	
	private LogisticsTemplateDAO logisticsTemplateDAO;
	

	/**
	 * 取得物流模板信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 物流模板
	 */
	public LogisticsTemplateDO loadLogisticsTemplate(Long templateId)throws ManagerException{
		try {
			return logisticsTemplateDAO.loadLogisticsTemplate(templateId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsTemplateManagerImpl#loadLogisticsTemplate]取得物流模板信息", e);
		}
		
	}
	
	/**
	 * 保存物流模板信息
	 * 
	 * @param LogisticsTemplateDO 模板
	 * 
	 * @return 更新或者保存的模板id
	 */
	public Long saveLogisticsTemplate(LogisticsTemplateDO logisticsTemplateDO)throws ManagerException{
		try {
			return logisticsTemplateDAO.saveLogisticsTemplate(logisticsTemplateDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsTemplateManagerImpl#saveLogisticsTemplate]保存物流模板信息", e);
		}
	}
	
	/**
	 * 删除物流模板信息
	 * 
	 * @param templateId 模板id
	 * @param sellerId 卖家id
	 * 
	 * @return 更新或者保存的默认物流运费id
	 */
	public void deleteLogisticsTemplate(Long templateId, Long sellerId)throws ManagerException{
		try {
			LogisticsTemplateDO template = new LogisticsTemplateDO();
			template.setSellerId(sellerId);
			template.setId(templateId);
				
			logisticsTemplateDAO.deleteLogisticsTemplate(template);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsTemplateManagerImpl#templateId]删除物流模板信息", e);
		}
	}

	/**
	 * 获取卖家物流模板列表
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表
	 */
	public List<LogisticsTemplateDO> getLogisTicsTemplateList(LogisticsTemplateQuery logisticsTemplateQuery)throws ManagerException{
		try {
			return logisticsTemplateDAO.getLogisTicsTemplateList(logisticsTemplateQuery);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsTemplateManagerImpl#getLogisTicsTemplateList]获取卖家物流模板列表", e);
		}
	}
	
	/**
	 * 获取卖家物流模板数量
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表数量
	 */
	public Integer getLogisTicsTemplateCount(LogisticsTemplateQuery logisticsTemplateQuery)throws ManagerException{
		try {
			return logisticsTemplateDAO.getLogisTicsTemplateCount(logisticsTemplateQuery);
		}catch (DaoException e) {
			throw new ManagerException("Event=[LogisticsTemplateManagerImpl#getLogisTicsTemplateCount]卖家物流模板列表数量", e);
		}
	}
	
	
	public void setLogisticsTemplateDAO(LogisticsTemplateDAO logisticsTemplateDAO) {
		this.logisticsTemplateDAO = logisticsTemplateDAO;
	}

}
