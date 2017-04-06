package com.yuwang.pinju.core.logistics.ao;

import java.util.List;

import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateQuery;

/**
 * 物流模板
 * @author shihongbo
 * @since   2011-07-09
 */

public interface LogisticsTemplateAO {

	/**
	 * 取得物流模板信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 物流模板
	 */
	public LogisticsTemplateDO loadLogisticsTemplate(Long templateId);
	
	/**
	 * 取得默认运费信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 默认运费信息
	 */
	public List<LogisticsCarriageDO> loadLogisticsCarriage(Long templateId);
	
	/**
	 * 保存物流模板信息
	 * 
	 * @param LogisticsTemplateDO 模板
	 * 
	 * @return 
	 */
	public Long saveLogisticsTemplate(LogisticsTemplateDO logisticsTemplateDO);
	
	/**
	 * 删除物流模板信息
	 * 
	 * @param templateId 模板id
	 * @param sellerId 卖家id
	 * 
	 * @return 
	 */
	public void deleteLogisticsTemplate(Long templateId, Long sellerId);
	
	/**
	 * 判断物流模板是否被商品或其它模块引用
	 * 
	 * @param tempalateId 模板id
	 * 
	 * @return 
	 */
	public boolean checkLogisticsTemplate(Long tempalateId);
	
	/**
	 * 删除物流模板运费信息
	 * 
	 * @param templateId 模板id
	 * @param sellerId 卖家id
	 * 
	 * @return 
	 */
	public void deleteCarriageByTemplate(Long templateId, Long sellerId);
	
	/**
	 * 设置默认运费
	 * 
	 * @param logisticsCarriageDO 默认运费
	 * 
	 * @return 
	 */
	public void addDefaultCarriage(LogisticsCarriageDO logisticsCarriageDO);
	
	/**
	 * 获取卖家物流模板列表
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表
	 */
	public List<LogisticsTemplateDO> getLogisTicsTemplateList(LogisticsTemplateQuery logisticsTemplateQuery);
	
	/**
	 * 获取模板相关的默认运费
	 * 
	 * @param templateId 
	 * 
	 * @return 默认运费列表
	 */
	public List<LogisticsCarriageDO> getLogisticsCarriageList(Long templateId);
	
	/**
	 * 获取卖家物流模板数量
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表数量
	 */
	public Integer getLogisTicsTemplateCount(LogisticsTemplateQuery logisticsTemplateQuery);
	
	/**
	 * 获取地区运费信息数量
	 * 
	 * @param templateId 模板id
	 * @param logisticsTypeId 物流类型
	 * 
	 * @return 地区运费信息数量
	 */
	public Integer getAreaCarriageCount(Long templateId, Integer logisticsTypeId);
	
	/**
	 * 查询地区物流运费信息列表
	 * @param templateId
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> loadAreaCarriageByTemplate(Long templateId);
}
