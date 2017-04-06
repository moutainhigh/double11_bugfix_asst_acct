package com.yuwang.pinju.core.logistics.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.logistics.dao.LogisticsTemplateDAO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateQuery;

/**
 * <p>物流模板</p>
 *
 * @author shihongbo
 * @since   2011-07-09
 */

public class LogisticsTemplateDAOImpl extends BaseDAO implements LogisticsTemplateDAO{
	private final String LOGISTICS_NAME_SPACE = "trade_logistics_template.";
	
	/**
	 * 取得物流模板信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @throws DaoException
	 * 
	 * @return 物流模板
	 */
	public LogisticsTemplateDO loadLogisticsTemplate(Long templateId)throws DaoException{
		return (LogisticsTemplateDO)super.executeQueryForObject(LOGISTICS_NAME_SPACE + "selectTradeLogisticsTemplateByid", templateId);		
	}
	
	/**
	 * 保存物流模板信息
	 * 
	 * @param LogisticsTemplateDO 模板
	 * 
	 * @throws DaoException
	* 
	 * @return 更新或者保存的模板id
	 */
	public Long saveLogisticsTemplate(LogisticsTemplateDO template)throws DaoException{
		if(template.getId() == null){
			return (Long)super.executeInsert(LOGISTICS_NAME_SPACE + "insertTradeLogisticsTemplate", template);	
		}else{
			super.executeUpdate(LOGISTICS_NAME_SPACE + "updateTradeLogisticsTemplate", template);
			return template.getId();
		}
		
	}
	
	/**
	 * 删除物流模板信息
	 * 
	 * @param logisticsTemplateDO 模板
	 * 
	 * @return 更新或者保存的默认物流运费id
	 */
	public void deleteLogisticsTemplate(LogisticsTemplateDO logisticsTemplateDO)throws DaoException{
		super.executeUpdate(LOGISTICS_NAME_SPACE + "deleteTradeLogisticsTemplate", logisticsTemplateDO);
	}
	
	/**
	 * 获取卖家物流模板列表
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表
	 */
	public List<LogisticsTemplateDO> getLogisTicsTemplateList(LogisticsTemplateQuery logisticsTemplateQuery)throws DaoException{
		return (List<LogisticsTemplateDO>)super.executeQueryForList(LOGISTICS_NAME_SPACE + "selectTradeLogisticsTemplateList", logisticsTemplateQuery);	
	}
	
	/**
	 * 获取卖家物流模板数量
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表数量
	 */
	public Integer getLogisTicsTemplateCount(LogisticsTemplateQuery logisticsTemplateQuery)throws DaoException{
		return (Integer)super.executeQueryForObject(LOGISTICS_NAME_SPACE + "selectTradeLogisticsTemplateCount", logisticsTemplateQuery);
	}
}
