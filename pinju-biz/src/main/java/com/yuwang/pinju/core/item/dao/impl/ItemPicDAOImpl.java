package com.yuwang.pinju.core.item.dao.impl;

import java.util.List;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ReadBaseDAO;
import com.yuwang.pinju.core.item.dao.ItemPicDAO;
import com.yuwang.pinju.domain.item.ItemPicDO;


public class ItemPicDAOImpl extends BaseDAO implements ItemPicDAO {
	private ReadBaseDAO readBaseDAOForOracle ;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.ItemDAO#insertItemItemSnapshot(com.yuwang.pinju.domain.item.ItemSnapshotDO)
	 */
	@Override
	public long insertItemPic(ItemPicDO itemPicDO) throws DaoException {
		return (Long) executeInsert("item_pic.insertItemPic", itemPicDO);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#getItemSkuByItemId(java.lang.Long)
	 */
	@Override
	public List<ItemPicDO> getItemPicByItemId(Long itemId) throws DaoException {
		return executeQueryForList("item_pic.getItemPicByItemId", itemId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yuwang.pinju.core.item.dao.SkuDAO#getItemSkuByItemId(java.lang.Long)
	 */
	@Override
	public int deleteItemPic(ItemPicDO itemPicDO) throws DaoException {
		return super.executeUpdate("item_pic.deleteItemPic", itemPicDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItemPicDO> getReadItemPicByItemId(Long itemId)
			throws DaoException {
		return readBaseDAOForOracle.executeQueryForList("item_pic.getItemPicByItemId", itemId);
	}

	public void setReadBaseDAOForOracle(ReadBaseDAO readBaseDAOForOracle) {
		this.readBaseDAOForOracle = readBaseDAOForOracle;
	}


	
}
