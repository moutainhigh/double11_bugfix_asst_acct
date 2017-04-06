/**
 * 
 */
package com.yuwang.pinju.core.item.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.CustomProValueDO;

/**  
 * @Project com.yuwang.pinju.core.item.manager.pinju-biz
 * @Description: 商品自定义属性manager类
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a> 
 * @date 2011-11-18 下午2:55:48
 * @version V1.0  
 */

public interface CustomCateProValueManager {
	/**
	 * 通过商品ID、属性ID和属性值ID查找自自定义SKU属性值
	 * 
	 * @param itemID
	 * @param cateProID
	 * @param cateProValueID
	 * @return
	 * @throws ManagerException
	 */
	public CustomProValueDO selectItemCustomProValue(long itemID, long cateProID, long cateProValueID) 
			throws ManagerException;
	/**
	 * 通过商品ID查找自定义SKU属性值列表
	 * 
	 * @param itemID
	 * @return
	 * @throws ManagerException
	 */
	public List <CustomProValueDO> selectItemCustomProValueList(long itemID) 
			throws ManagerException;

	/**
	 * 通过商品ID查找自定义SKU属性值数量
	 * 
	 * @param itemID
	 * @return
	 * @throws DaoException
	 */
	public int selectItemCustomProValueCountList(long itemID) 
			throws ManagerException;
	
	/**
	 * 插入一条自定义SKU属性值
	 * @param itemCustomProValue
	 * @return
	 * @throws ManagerException
	 */
	public long insertItemCustomProValue(CustomProValueDO itemCustomProValue)
			throws ManagerException;

	/**
	 * 批量插入自定义SKU属性值
	 * @param list
	 * @return
	 * @throws ManagerException
	 */
	public int batchInsertItemCustomProValue(List<CustomProValueDO> list)
			throws ManagerException;
	
	/**
	 * 更新一条自定义SKU属性值
	 * @param itemCustomProValue
	 * @return
	 * @throws ManagerException
	 */
	public int updateItemCustomProValue(CustomProValueDO itemCustomProValue)
			throws ManagerException;

}
