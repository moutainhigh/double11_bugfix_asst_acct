/**
 * 
 */
package com.yuwang.pinju.core.item.manager;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.SkuDO;

/**
 * @Project: pinju-biz
 * @Title: SkuManager.java
 * @Package com.yuwang.pinju.core.item.manager
 * @Description: Sku 业务类
 * @author liuboen liuboen@zba.com
 * @date 2011-6-27 下午01:49:33
 * @version V1.0
 */

public interface SkuManager {

	/**
	 * 根据编号获得 商品SKU
	 * 
	 * @param id
	 * @return
	 * @throws ManagerException
	 */
	public SkuDO getItemSkuById(Long id) throws ManagerException;

	/**
	 * 通过商品编号 获得SKU
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<SkuDO> getItemSkuByItemId(Long itemId) throws ManagerException;
	
	/**
	 * 通过商品编号 获得SKU(read database)
	 * 
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public List<SkuDO> getReadItemSkuByItemId(Long itemId) throws ManagerException;
}
