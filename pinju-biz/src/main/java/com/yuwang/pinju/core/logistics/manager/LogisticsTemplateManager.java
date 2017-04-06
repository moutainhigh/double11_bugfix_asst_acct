package com.yuwang.pinju.core.logistics.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateQuery;

/**
 * <p>物流模板</p>
 *
 * @author shihongbo
 * @since   2011-07-09
 */
public interface LogisticsTemplateManager {
	/**
	 * 取得物流模板信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 物流模板
	 */
	public LogisticsTemplateDO loadLogisticsTemplate(Long templateId)throws ManagerException;
	
	/**
	 * 保存物流模板信息
	 * 
	 * @param LogisticsTemplateDO 模板
	 * 
	 * @return 更新或者保存的模板id
	 */
	public Long saveLogisticsTemplate(LogisticsTemplateDO logisticsTemplateDO)throws ManagerException;
	
	/**
	 * 删除物流模板信息
	 * 
	 * @param templateId 模板id
	 * @param sellerId 卖家id
	 * 
	 * @return 更新或者保存的默认物流运费id
	 */
	public void deleteLogisticsTemplate(Long templateId, Long sellerId)throws ManagerException;
	
	/**
	 * 获取卖家物流模板列表
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表
	 */
	public List<LogisticsTemplateDO> getLogisTicsTemplateList(LogisticsTemplateQuery logisticsTemplateQuery)throws ManagerException;
	
	/**
	 * 获取卖家物流模板数量
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表数量
	 */
	public Integer getLogisTicsTemplateCount(LogisticsTemplateQuery logisticsTemplateQuery)throws ManagerException;
	
}
