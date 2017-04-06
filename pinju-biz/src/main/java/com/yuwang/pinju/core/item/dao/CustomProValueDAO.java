/**
 * 
 */
package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.CustomProValueDO;

/**
 * @Project com.yuwang.pinju.core.item.dao.pinju-biz
 * @Description: 自定义属性值DAO
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a>
 * @date 2011-11-17 下午7:06:17
 * @version V1.0
 */

public interface CustomProValueDAO {

	/**
	 * 通过商品ID、属性ID和属性值ID查找自自定义SKU属性值
	 * 
	 * @param itemID
	 * @param cateProID
	 * @param cateProValueID
	 * @return
	 * @throws DaoException
	 */
	public CustomProValueDO selectItemCustomProValue(long itemID, long cateProID, long cateProValueID) 
			throws DaoException;
	/**
	 * 通过商品ID查找自定义SKU属性值列表
	 * 
	 * @param itemID
	 * @return
	 * @throws DaoException
	 */
	public List <CustomProValueDO> selectItemCustomProValueList(long itemID) 
			throws DaoException;
	
	/**
	 * 通过商品ID查找自定义SKU属性值数量
	 * 
	 * @param itemID
	 * @return
	 * @throws DaoException
	 */
	public int selectItemCustomProValueCountList(long itemID) 
			throws DaoException;
	
	/**
	 * 插入一条自定义SKU属性值
	 * @param itemCustomProValue
	 * @return
	 * @throws DaoException
	 */
	public long insertItemCustomProValue(CustomProValueDO itemCustomProValue)
			throws DaoException;

	/**
	 * 批量插入自定义SKU属性值
	 * @param list
	 * @return
	 * @throws DaoException
	 */
	public int batchInsertItemCustomProValue(List<CustomProValueDO> list)
			throws DaoException;
	
	/**
	 * 更新一条自定义SKU属性值
	 * @param itemCustomProValue
	 * @return
	 * @throws DaoException
	 */
	public int updateItemCustomProValue(CustomProValueDO itemCustomProValue)
			throws DaoException;

	/**
	 * 根据商品编号 删除自定义SKU (更新SKU状态)
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public void deleteCustomSku(Long itemId) throws DaoException;
}
