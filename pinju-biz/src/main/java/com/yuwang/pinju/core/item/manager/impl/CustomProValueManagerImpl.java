/**
 * 
 */
package com.yuwang.pinju.core.item.manager.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.dao.CustomProValueDAO;
import com.yuwang.pinju.core.item.manager.CustomCateProValueManager;
import com.yuwang.pinju.domain.item.CustomProValueDO;
import com.yuwang.pinju.domain.item.ItemQuery;

/**  
 * @Project com.yuwang.pinju.core.item.manager.impl.pinju-biz
 * @Description: 商品自定义属性manager实现类
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-11-18 下午2:59:26
 * @version V1.0  
 */

public class CustomProValueManagerImpl extends BaseManager implements
		CustomCateProValueManager {

	private CustomProValueDAO customProValueDAO;
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CustomCateProValueManager#selectItemCustomProValue(long, long, long)
	 */
	@Override
	public CustomProValueDO selectItemCustomProValue(long itemID,
			long cateProID, long cateProValueID) throws ManagerException {
		try {
			return customProValueDAO.selectItemCustomProValue(itemID, cateProID, cateProValueID);
		} catch (DaoException e) {
			StringBuilder errorBuilder = new StringBuilder("获取商品自定义属性出错,");
			errorBuilder.append("itemID=").append(itemID).append(",")
						.append("cateProID=").append(cateProID).append(",")
						.append("cateProValueID=").append(cateProValueID).append(",");
			throw new ManagerException(errorBuilder.toString(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CustomCateProValueManager#selectItemCustomProValueList(long)
	 */
	@Override
	public List<CustomProValueDO> selectItemCustomProValueList(long itemID)
			throws ManagerException {

		try {
			return customProValueDAO.selectItemCustomProValueList(itemID);
		} catch (DaoException e) {
			String error = "获取商品自定义属性列表出错,itemID=" + itemID;
			throw new ManagerException(error, e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CustomCateProValueManager#selectItemCustomProValueCountList(long)
	 */
	@Override
	public int selectItemCustomProValueCountList(long itemID) throws ManagerException {
		try {
			return customProValueDAO.selectItemCustomProValueCountList(itemID);
		} catch (DaoException e) {
			throw new ManagerException("Event=[ItemManager#selectItemCustomProValueCountList]获得商品自定义SKU属性值数量错误", e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.manager.CustomCateProValueManager#insertItemCustomProValue(com.yuwang.pinju.domain.item.CustomProValueDO)
	 */
	@Override
	public long insertItemCustomProValue(CustomProValueDO itemCustomProValue)
			throws ManagerException {
		if (itemCustomProValue==null) {
			if (log.isWarnEnabled()) {
				log.warn("CustomProValueDO 为空, 调用插入自定义属性接口直接返回0,检查调用该接口处");
			}
			return 0L;
		}
		try {
			return customProValueDAO.insertItemCustomProValue(itemCustomProValue);
		} catch (DaoException e) {
			String error = "插入商品自定义属性列表出错,"+itemCustomProValue==null?"null":itemCustomProValue.toString();
			throw new ManagerException(error, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CustomCateProValueManager#
	 * batchInsertItemCustomProValue(java.util.List)
	 */
	@Override
	public int batchInsertItemCustomProValue(List<CustomProValueDO> list)
			throws ManagerException {
		if (list == null || list.size() <= 0) {
			if (log.isWarnEnabled()) {
				log.warn("CustomProValueDO list 为空, 调用批量插入自定义属性接口直接返回0,检查调用该接口处");
			}
			return 0;
		}
		try {
			return customProValueDAO.batchInsertItemCustomProValue(list);
		} catch (DaoException e) {
			StringBuilder errorBuilder = new StringBuilder("批量插入商品自定义属性列表出错,list=[");
			for (CustomProValueDO customProValueDO : list) {
				errorBuilder.append(customProValueDO == null ? "null" : customProValueDO.toString());
			}
			errorBuilder.append("]");
			throw new ManagerException(errorBuilder.toString(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.manager.CustomCateProValueManager#
	 * updateItemCustomProValue(com.yuwang.pinju.domain.item.CustomProValueDO)
	 */
	@Override
	public int updateItemCustomProValue(CustomProValueDO itemCustomProValue)
			throws ManagerException {
		if (itemCustomProValue == null) {
			if (log.isWarnEnabled()) {
				log.warn("CustomProValueDO 为空, 调用更新自定义属性接口直接返回0,检查调用该接口处");
			}
			return 0;
		}
		try {
			return customProValueDAO.updateItemCustomProValue(itemCustomProValue);
		} catch (DaoException e) {
			String error = "更新商品自定义属性失败," + itemCustomProValue.toString();
			throw new ManagerException(error, e);
		}
	}

	public void setCustomProValueDAO(CustomProValueDAO customProValueDAO) {
		this.customProValueDAO = customProValueDAO;
	}

	
}
