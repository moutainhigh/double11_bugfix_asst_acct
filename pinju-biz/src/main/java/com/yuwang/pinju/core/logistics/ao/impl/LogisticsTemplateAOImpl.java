package com.yuwang.pinju.core.logistics.ao.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.logistics.ao.LogisticsTemplateAO;

import com.yuwang.pinju.core.logistics.manager.LogisticsAreaCarriageManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsCarriageManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsCityIpManager;
import com.yuwang.pinju.core.logistics.manager.LogisticsTemplateManager;
import com.yuwang.pinju.core.user.ao.BaseAO;

import com.yuwang.pinju.domain.logistics.LogisticsAreaCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsCarriageDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateDO;
import com.yuwang.pinju.domain.logistics.LogisticsTemplateQuery;

/**
 * <p>物流模板</p>
 *
 * @author shihongbo
 * @since   2011-07-09
 */

public class LogisticsTemplateAOImpl extends BaseAO implements LogisticsTemplateAO{
	
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	private LogisticsTemplateManager logisticsTemplateManager;
	private LogisticsCarriageManager logisticsCarriageManager;
	private LogisticsAreaCarriageManager logisticsAreaCarriageManager;
	private ItemManager itemManager;
	private LogisticsCityIpManager logisticsCityIpManager;

	/**
	 * 取得物流模板信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 物流模板
	 */
	public LogisticsTemplateDO loadLogisticsTemplate(Long templateId){
		try {
			return logisticsTemplateManager.loadLogisticsTemplate(templateId);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#loadLogisticsTemplate] 取得物流模板信息", e);
		}
		return new LogisticsTemplateDO();
	}
	
	/**
	 * 取得默认运费信息
	 * 
	 * @param templateId 模板编号
	 * 
	 * @return 默认运费信息
	 */
	public List<LogisticsCarriageDO> loadLogisticsCarriage(Long templateId){
		try {
			return logisticsCarriageManager.loadLogisticsCarriageByTemplate(templateId);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#loadLogisticsCarriage] 取得默认运费信息", e);
		}
		return new LinkedList<LogisticsCarriageDO>();
	}
	
	/**
	 * 保存物流模板信息
	 * 
	 * @param LogisticsTemplateDO 模板
	 * 
	 * @return 更新或者保存的默认物流运费id
	 */
	public Long saveLogisticsTemplate(LogisticsTemplateDO logisticsTemplateDO){
		try {
			return logisticsTemplateManager.saveLogisticsTemplate(logisticsTemplateDO);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#loadLogisticsTemplate] 取得物流模板信息", e);	
		}
		
		return null;

	}
	
	/**
	 * 删除物流模板信息
	 * 
	 * @param templateId 模板id
	 * @param sellerId 卖家id
	 * 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public void deleteLogisticsTemplate(final Long templateId, final Long sellerId){
		
		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					logisticsTemplateManager.deleteLogisticsTemplate(templateId, sellerId);
					logisticsCarriageManager.deleteCarriageByTemplate(templateId);
					logisticsAreaCarriageManager.deleteLogisticsAreaCarriage(templateId);
					itemManager.shelvesItemByFreight(templateId);
					
				} catch (ManagerException e) {
					log.error("Event=[LogisticsTemplateAOImpl#loadLogisticsTemplate] 删除物流模板信息", e);
					
					status.setRollbackOnly();
				}
				return null;
			}
		});

	}

	/**
	 * 判断物流模板是否被商品或其它模块引用
	 * 
	 * @param tempalateId 模板id
	 * 
	 * @return 
	 */
	public boolean checkLogisticsTemplate(Long tempalateId) {
		try {
			return itemManager.getItemCountByfreeTemplateId(tempalateId);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#checkLogisticsTemplate] 删除当前物流模板前判断当前物流是否被其它模板使用", e);
			return false;
		}
	}
	
	/**
	 * 删除物流模板运费信息
	 * 
	 * @param templateId 模板id
	 * @param sellerId 卖家id
	 * 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public void deleteCarriageByTemplate(final Long templateId, final Long sellerId){
		getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					logisticsCarriageManager.deleteCarriageByTemplate(templateId);
					logisticsAreaCarriageManager.deleteLogisticsAreaCarriage(templateId);
					logisticsCityIpManager.deleteMemcachedByKey(templateId);
				} catch (ManagerException e) {
					log.error("Event=[LogisticsTemplateAOImpl#deleteCarriageByTemplate] 删除物流模板运费信息", e);
					status .setRollbackOnly();
				}
				return null;
			}
		});
	}
	
	/**
	 * 设置默认运费
	 * 
	 * @param logisticsCarriageDO 默认运费
	 * 
	 * @return 
	 */
	public void addDefaultCarriage(LogisticsCarriageDO logisticsCarriageDO){
		try {
			logisticsCarriageManager.saveLogisticsCarriage(logisticsCarriageDO);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#loadLogisticsTemplate] 设置默认运费", e);
		}
	}
	
	/**
	 * 获取卖家物流模板列表
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表
	 */
	public List<LogisticsTemplateDO> getLogisTicsTemplateList(LogisticsTemplateQuery logisticsTemplateQuery){
		try {
			return logisticsTemplateManager.getLogisTicsTemplateList(logisticsTemplateQuery);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#getLogisTicsTemplateList] 获取卖家物流模板列表", e);	
		}
		return new LinkedList<LogisticsTemplateDO>();
	}

	/**
	 * 获取卖家物流模板数量
	 * 
	 * @param logisticsTemplateQuery 
	 * 
	 * @return 卖家物流模板列表数量
	 */
	public Integer getLogisTicsTemplateCount(LogisticsTemplateQuery logisticsTemplateQuery){
		try {
			return logisticsTemplateManager.getLogisTicsTemplateCount(logisticsTemplateQuery);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#getLogisTicsTemplateCount] 获取卖家物流模板数量", e);	
		}
		return 0;
	}
	
	/**
	 * 获取地区运费信息数量
	 * 
	 * @param templateId 模板id
	 * @param logisticsTypeId 物流类型
	 * 
	 * @return 地区运费信息数量
	 */
	public Integer getAreaCarriageCount(Long templateId, Integer logisticsTypeId){
		try {
			return logisticsAreaCarriageManager.getAreaCarriageCount(templateId, logisticsTypeId);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#getLogisTicsTemplateCount] 获取卖家物流模板数量", e);	
		}
		return 0;
	}
	
	/**
	 * 获取模板相关的默认运费
	 * 
	 * @param templateId 
	 * 
	 * @return 默认运费列表
	 */
	public List<LogisticsCarriageDO> getLogisticsCarriageList(Long templateId){
		try {
			return logisticsCarriageManager.getLogisticsCarriageList(templateId);
		} catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#getLogisticsCarriageList] 获取模板相关的默认运费", e);	
		}
		return new LinkedList<LogisticsCarriageDO>();
		
	}
	
	/**
	 * 查询地区物流运费信息列表
	 * @param templateId
	 * @return List<LogisticsAreaCarriageDO> 列表
	 */
	public List<LogisticsAreaCarriageDO> loadAreaCarriageByTemplate(Long templateId){
		try {
			return logisticsAreaCarriageManager.loadAreaCarriageByTemplate(templateId);
		}
		catch (ManagerException e) {
			log.error("Event=[LogisticsTemplateAOImpl#getLogisticsCarriageList] 获取模板相关的默认运费", e);	
		}
		return new LinkedList<LogisticsAreaCarriageDO>();
	}
	
	public void setLogisticsTemplateManager(LogisticsTemplateManager logisticsTemplateManager) {
		this.logisticsTemplateManager = logisticsTemplateManager;
	}

	public void setLogisticsCarriageManager(LogisticsCarriageManager logisticsCarriageManager) {
		this.logisticsCarriageManager = logisticsCarriageManager;
	}

	public void setLogisticsAreaCarriageManager(
			LogisticsAreaCarriageManager logisticsAreaCarriageManager) {
		this.logisticsAreaCarriageManager = logisticsAreaCarriageManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	public void setZizuTransactionTemplate(
			TransactionTemplate zizuTransactionTemplate) {
		this.zizuTransactionTemplate = zizuTransactionTemplate;
	}

	public void setLogisticsCityIpManager(
			LogisticsCityIpManager logisticsCityIpManager) {
		this.logisticsCityIpManager = logisticsCityIpManager;
	}

}
