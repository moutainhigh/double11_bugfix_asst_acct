package com.yuwang.pinju.core.sales.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;
import com.yuwang.pinju.domain.sales.query.ItemXianGouQuery;


public interface ItemXianGouDAO {
	
	
	/**
	 * 生成限购DO
	 * @Project:crm-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemXianGouDO
	 * @throws DaoException
	 */
	public void insertItemXianGouDO(ItemXianGouDO itemXianGouDO) throws DaoException;
	
	/**
	 * 根据Id查询限购DO
	 * @Project:crm-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param id
	 * @return
	 * @throws DaoException
	 */
	public ItemXianGouDO getItemXianGouDOById(Long id) throws DaoException;
	
	/**
	 * 更新限购DO
	 * @Project:crm-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public boolean updateItemXianGouDO(ItemXianGouDO itemXianGouDO)throws DaoException;
	
	/**
	 * 根据商品Id查询该商品的限购特供详情
	 * @Project:crm-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemId
	 * @return
	 * @throws DaoException
	 */
	public ItemXianGouDO getItemXianGouDOByItemId(Long itemId)throws DaoException;
	
	/**
	 * 根据batchNum查询一条记录
	 *  Created on 2011-12-1 
	 * <p>Discription:[根据batchNum查询一条记录]</p>
	 * @author:[lixingquan]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return List<ItemXianGouDO> .
	 */
	public List<ItemXianGouDO> getItemXianGouDOs(ItemXianGouQuery itemXianGouQuery) throws DaoException;
	
	/**
	 * 根据batchNum查询记录数量
	 *  Created on 2011-12-1 
	 * <p>Discription:[方法功能中文描述]</p>
	 * @author:[lixingquan]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return int .
	 */
	public Integer getItemXianGouDOsCount(ItemXianGouQuery itemXianGouQuery) throws DaoException;
	
	
}
