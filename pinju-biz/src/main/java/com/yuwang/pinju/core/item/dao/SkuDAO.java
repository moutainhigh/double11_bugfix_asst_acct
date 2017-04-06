package com.yuwang.pinju.core.item.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.item.SkuDO;

/**
 * 
 * @author liming
 * @since 2011-06-16
 * 
 */
public interface SkuDAO {

	/**
	 * 创建SKU
	 * 
	 * @param skuDO
	 * @return
	 * @throws DaoException
	 */
	public long createItemSku(SkuDO skuDO) throws DaoException;

	/**
	 * 根据商品编号 删除SKU (更新SKU状态)
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public void deleteItemSku(Long itemId) throws DaoException;

	/**
	 * 获得SKU
	 * 
	 * @param skuDO
	 * @return
	 * @throws DaoException
	 */
	public List<SkuDO> getItemSku(SkuDO skuDO) throws DaoException;

	/**
	 * 根据商品编号 获得商品SKU
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public SkuDO getItemSkuById(Long id) throws DaoException;

	/**
	 * 通过商品编号 获得SKU
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<SkuDO> getItemSkuByItemId(Long itemId) throws DaoException;

	/**
	 * 更新SKU
	 * 
	 * @param skuDO
	 * @return
	 * @throws DaoException
	 */
	public int updateItemSku(SkuDO skuDO) throws DaoException;
	
	/**
	 * 通过商品编号 获得SKU(read database)
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<SkuDO> getReadItemSkuByItemId(Long itemId) throws DaoException;

}
