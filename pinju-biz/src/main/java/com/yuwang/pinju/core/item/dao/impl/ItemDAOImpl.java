package com.yuwang.pinju.core.item.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.item.dao.ItemDAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.ItemQueryEx;

/**
 * 
 * @author liming
 * 
 */
public class ItemDAOImpl extends BaseDAO implements ItemDAO {
	private ReadBaseDAO readBaseDAOForOracle;
	private final String PRICE_ASC = "PRICE ASC";
	private final String PRICE_DESC = "PRICE DESC";
	private final String GMT_CREATE_DESC = "GMT_CREATE DESC";
	/**
	 * 获取商品简单对象列表
	 */
	private final static String ITEM_SELECT_SIMPLE_ITEMLIST_BY_ITEMIDLIST="item_item.selectSimpleItemListByItemIdList";
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemDAO#selectItemList(java.util.Map,
	 * int)
	 */
	@Override
	public List<ItemDO> getItemList(ItemQuery itemQuery) throws DaoException {
		return readBaseDAOForOracle.executeQueryForList("item_item.getItemList", itemQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.ItemDAO#getItemListCount(com.yuwang.pinju
	 * .domain.item.ItemQuery)
	 */
	@Override
	public int getItemListCount(ItemQuery itemQuery) throws DaoException {
		return (Integer) readBaseDAOForOracle.executeQueryForObject("item_item.getItemListCount", itemQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemDAO#getNewItemId()
	 */
	@Override
	public long getNewItemId() throws DaoException {
		return (Long) executeQueryForObject("item_item.getNewItemId", "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.ItemItemDao#insertItemItem(com.yuwang.
	 * pinju.domain.item.ItemItemDO)
	 */
	@Override
	public void insertItemItem(ItemDO obj) throws DaoException {
		try {
			executeInsert("item_item.insertItemItem", obj);
		} catch (DaoException e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemDO> queryItemListEx(ItemQueryEx itemQuery) throws DaoException {
		String order = itemQuery.getOrderBy();
		if (!StringUtil.isBlank(itemQuery.getOrderBy())) {
			if (PRICE_ASC.equalsIgnoreCase(order) || PRICE_DESC.equalsIgnoreCase(order)
					|| GMT_CREATE_DESC.equalsIgnoreCase(order)) {
				return readBaseDAOForOracle.executeQueryForList("item_item.queryItemListEx", itemQuery);
			}
		} else {//排序为空时
			itemQuery.setOrderBy(GMT_CREATE_DESC);
			return readBaseDAOForOracle.executeQueryForList("item_item.queryItemListEx", itemQuery);
		}

		if (log.isWarnEnabled()) {
			log.warn("itemQuery.setOrderBy is not right,OrderBy is [" + itemQuery.getOrderBy() + "]");
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemItemDao#selectItemDObyId(long)
	 */
	@Override
	public ItemDO selectItemDObyId(long id) throws DaoException {
		return (ItemDO) executeQueryForObject("item_item.selectItemById", id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemItemDao#selectItemDObyId(long,
	 * long)
	 */
	@Override
	public List<ItemDO> selectItemList(long sellerId) throws DaoException {
		return readBaseDAOForOracle.executeQueryForList("item_item.selectItemListBySellerId", sellerId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemItemDao#selectItemDObyId(long,
	 * long)
	 */
	@Override
	public int getItemCountByfreeTemplateId(ItemQuery itemQuery) throws DaoException {
		return (Integer) executeQueryForObject("item_item.getItemCountByfreeTemplateId", itemQuery);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.ItemItemDao#delItemByUser(java.lang.String
	 * )
	 */
	@Override
	public int updateItem(ItemDO itemDO) throws DaoException {
		return executeUpdate("item_item.updateItem", itemDO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yuwang.pinju.core.item.dao.ItemDAO#updateItemStatusByFreight(long)
	 */
	@Override
	public int updateItemStatusByFreight(long freightId) throws DaoException {
		return executeUpdate("item_item.shelvesItemByFreight", freightId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemDAO#updateItemFeatures(long,
	 * java.lang.String)
	 */
	@Override
	public int updateItemFeatures(long itemId, String toFeatures, long version) throws DaoException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", itemId);
		map.put("features", toFeatures);
		map.put("versionNow", version);
		map.put("gmtModified", new Date());
		// 默认将version加1
		map.put("version", version + 1);
		return executeUpdate("item_item.updateFeatures", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemDAO#updateItemStock(long, long,
	 * long)
	 */
	@Override
	public int updateItemCurrentStock(long itemId, long currentStock, long version, Long toStauts) throws DaoException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", itemId);
		map.put("currentStock", currentStock);
		map.put("versionNow", version);
		if (toStauts != null) {
			map.put("toStatus", toStauts);
		}
		map.put("gmtModified", new Date());
		// 默认将version加1
		map.put("version", version + 1);
		return executeUpdate("item_item.updateCurrentStock", map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemItemDao#selectItemDObyId(long)
	 */
	@Override
	public ItemDO selectReadItemDObyId(long id) throws DaoException {
		return (ItemDO) readBaseDAOForOracle.executeQueryForObject("item_item.selectItemById", id);
	}
	@Override
	public List<ItemDO> selectReadSimpleItemDOListByIds(List<Long> ids,long sellerId)
			throws DaoException {
		ItemQueryEx queryEx =new ItemQueryEx();
		queryEx.setItemIdList(ids);
		queryEx.setSellerId(sellerId);
		return readBaseDAOForOracle.executeQueryForList(ITEM_SELECT_SIMPLE_ITEMLIST_BY_ITEMIDLIST, queryEx);
	}
	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}

	@Override
	public List<ItemDO> getItemTitles(ItemQueryEx itemQuery) throws DaoException {
		return readBaseDAOForOracle.executeQueryForList("item_item.getItemTitles", itemQuery);
	}

}
