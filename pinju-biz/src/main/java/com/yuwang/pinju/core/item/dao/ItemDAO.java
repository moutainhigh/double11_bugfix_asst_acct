package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.ItemQuery;
import com.yuwang.pinju.domain.item.ItemQueryEx;

/**
 * 
 * 
 * @author liming
 * 
 */
public interface ItemDAO {

	/**
	 * 获得商品列表
	 * 
	 * @param m
	 *            过滤列表
	 * 
	 * @return
	 * @throws DaoException
	 */
	public List<ItemDO> getItemList(ItemQuery itemQuery) throws DaoException;

	/**
	 * 获得商品列表数量
	 * 
	 * @param itemQuery
	 * @return
	 * @throws DaoException
	 */
	public int getItemListCount(ItemQuery itemQuery) throws DaoException;

	/**
	 * 获得新商品编号
	 * 
	 * @return
	 * @throws DaoException
	 */
	public long getNewItemId() throws DaoException;

	/**
	 * 插入商品
	 * 
	 * @param obj
	 * @throws DaoException
	 */
	public void insertItemItem(ItemDO obj) throws DaoException;

	/**
	 * 获取商品列表（提供店铺调用）
	 * 
	 * @author liyouguo
	 * @param itemQuery
	 * @return
	 * @throws DaoException
	 */
	public List<ItemDO> queryItemListEx(ItemQueryEx itemQuery) throws DaoException;

	/**
	 * 通过ID取得单条商品信息
	 * 
	 * @author liuboen
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public ItemDO selectItemDObyId(long id) throws DaoException;

	/**
	 * 获得商品列表
	 * 
	 * @param sellerId
	 * @param status
	 * @return
	 * @throws DaoException
	 */
	public List<ItemDO> selectItemList(long sellerId) throws DaoException;

	/**
	 * 更新商品
	 * 
	 * @param itemDO
	 * @return
	 * @throws DaoException
	 */
	public int updateItem(ItemDO itemDO) throws DaoException;
	/**
	 * 更新商品
	 * 
	 * @param itemDO
	 * @return
	 * @throws DaoException
	 */
	public int updateItemStatusByFreight(long freightId) throws DaoException;
	/**
	 * 更新商品features
	 * 
	 * @param itemDO
	 * @return
	 * @throws DaoException
	 */
	public int updateItemFeatures(long itemId,String toFeatures,long version) throws DaoException;

	/**
	 * 更新商品库存
	 * 
	 * @param itemDO
	 * @return
	 * @throws DaoException
	 */
	public int updateItemCurrentStock(long itemId,long currentStock,long version,Long toStauts) throws DaoException;
	
	/**
	 * 获得商品列表
	 * 
	 * @param sellerId
	 * @param status
	 * @return
	 * @throws DaoException
	 */
	public int getItemCountByfreeTemplateId(ItemQuery itemQuery) throws DaoException;

	/**
	 * 获取读库的单个商品
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	ItemDO selectReadItemDObyId(long id) throws DaoException;
	
	/**
	 * 获取读库商品列表简单对象(无descrption)
	 * @param ids
	 * @return
	 * @throws DaoException
	 */
	public List<ItemDO> selectReadSimpleItemDOListByIds(List<Long> ids,long sellerId)throws DaoException;
	
	/**
	 * 获取商品标题记录日志
	 */
	public List<ItemDO> getItemTitles(ItemQueryEx itemQuery) throws DaoException;
}
