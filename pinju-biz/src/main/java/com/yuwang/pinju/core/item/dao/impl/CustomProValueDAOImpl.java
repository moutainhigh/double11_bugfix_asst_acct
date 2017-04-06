/**
 * 
 */
package com.yuwang.pinju.core.item.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.item.dao.CustomProValueDAO;
import com.yuwang.pinju.domain.item.CustomProValueDO;

/**
 * @Project com.yuwang.pinju.core.item.dao.impl.pinju-biz
 * @Description: 自定义SKU属性值实现类
 * @author <a href="mailto:liuboen@pinju.com">liuboen</a>
 * @date 2011-11-17 下午7:23:42
 * @version V1.0
 */

public class CustomProValueDAOImpl extends BaseDAO implements CustomProValueDAO {

	/**
	 * 批量插入最大值
	 */
	private final static int INSERT_MAX_COUNT = 100;
	
	private final static String CUSTOM_PRO_VALUE_SELECT_ONE = "item_custom_pro_value.selectCustomProValueByThreeId";
	
	private final static String CUSTOM_PRO_VALUE_SELECT_LIST = "item_custom_pro_value.selectCustomProValueByItemId";
	
	private final static String CUSTOM_PRO_VALUE_INSERT_ONE = "item_custom_pro_value.insertCustomProValue";
	
	//private final static String CUSTOM_PRO_VALUE_INSERT_BATCH = "item_custom_pro_value.batchInsertCustomProValue";
	
	private final static String CUSTOM_PRO_VALUE_UPDATE_ONE = "item_custom_pro_value.updateCustomProValue";

	private final static String CUSTOM_PRO_VALUE_DELETE_BY_ITEMID = "item_custom_pro_value.deleteCustomProValueByItemId";

	private final static String CUSTOM_PRO_VALUE_SELECT_LIST_COUNT = "item_custom_pro_value.selectCustomProValueCountByItemId";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.CustomProValueDAO#selectItemCustomProValue
	 * (long, long, long)
	 */
	@Override
	public CustomProValueDO selectItemCustomProValue(long itemID, long cateProID, long cateProValueID)
			throws DaoException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("itemId", itemID);
		map.put("categoryPropertyId", cateProID);
		map.put("cateProValueId", cateProValueID);
		return (CustomProValueDO) executeQueryForObject(
				CUSTOM_PRO_VALUE_SELECT_ONE, map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.CustomProValueDAO#selectItemCustomProValueList
	 * (long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomProValueDO> selectItemCustomProValueList(long itemID)
			throws DaoException {
		return executeQueryForList(CUSTOM_PRO_VALUE_SELECT_LIST, itemID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemItemDao#selectItemDObyId(long,
	 * long)
	 */
	@Override
	public int selectItemCustomProValueCountList(long itemID) throws DaoException {
		return (Integer) executeQueryForObject(CUSTOM_PRO_VALUE_SELECT_LIST_COUNT, itemID);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.CustomProValueDAO#insertItemCustomProValue
	 * (com.yuwang.pinju.domain.item.ItemCustomProValue)
	 */
	@Override
	public long insertItemCustomProValue(CustomProValueDO itemCustomProValue)
			throws DaoException {
		return (Long) executeInsert(CUSTOM_PRO_VALUE_INSERT_ONE,
				itemCustomProValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.CustomProValueDAO#insertItemCustomProValueList
	 * (java.util.List)
	 */
	@Override
	public int batchInsertItemCustomProValue(List<CustomProValueDO> list)
			throws DaoException {
		int size = list.size();
		if (size > INSERT_MAX_COUNT) {
			return -1;
		}
		executeBatchInsert(CUSTOM_PRO_VALUE_UPDATE_ONE, list,
				INSERT_MAX_COUNT);
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.CustomProValueDAO#updateItemCustomProValue
	 * (com.yuwang.pinju.domain.item.ItemCustomProValue)
	 */
	@Override
	public int updateItemCustomProValue(CustomProValueDO itemCustomProValue)
			throws DaoException {
		return executeUpdate(CUSTOM_PRO_VALUE_UPDATE_ONE, itemCustomProValue);
	}

	/*
	 * (non-Javadoc)
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#deleteItemSku(java.lang.Long)
	 */
	@Override
	public void deleteCustomSku(Long itemId) throws DaoException {
		executeUpdate(CUSTOM_PRO_VALUE_DELETE_BY_ITEMID, itemId);
	}
}
