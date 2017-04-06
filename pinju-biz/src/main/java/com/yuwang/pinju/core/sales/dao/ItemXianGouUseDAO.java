package com.yuwang.pinju.core.sales.dao;

import java.util.List;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;
import com.yuwang.pinju.domain.sales.query.ItemXianGouUseQuery;

/**  
 * @Project: crm-biz
 * @Discription: [限购码领用记录DAO]
 * @author 凌建涛  lingjiantao@zba.com
 * @date 2011-8-30 下午07:06:36
 * @update 2011-8-30 下午07:06:36
 * @version V1.0  
 */
public interface ItemXianGouUseDAO{

	/**
	 * Create on: 2011-8-30下午07:07:45
	 * <p>Discription:[插入限购码领用记录]</p>
	 * @param: ItemXianGouUseDO
	 * @return: void 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void insertItemXianGouUseRecord(ItemXianGouUseDO itemXianGouUseDO) throws DaoException;
	
	/**
	 * Create on: 2011-8-30下午07:09:03
	 * <p>Discription:[修改限购码领用记录]</p>
	 * @param: ItemXianGouUseDO
	 * @return: int 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	int updateItemXianGouUseRecord(ItemXianGouUseDO itemXianGouUseDO) throws DaoException;
	
	/**
	 * Create on: 2011-8-30下午07:10:26
	 * <p>Discription:[获取限购码领用记录数]</p>
	 * @param: ItemXianGouUseQuery
	 * @return: Integer 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Integer getItemXianGouUseDOsCount(ItemXianGouUseQuery itemXianGouUseQuery) throws DaoException;
	
	/**
	 * Create on: 2011-8-30下午07:11:49
	 * <p>Discription:[获取限购码领用记录]</p>
	 * @param: ItemXianGouUseQuery
	 * @return: List<ItemXianGouUseDO> 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	List<ItemXianGouUseDO> getItemXianGouUseDOs(ItemXianGouUseQuery itemXianGouUseQuery) throws DaoException;
	
	/**
	 * xianGouId 必选，status itemId可选
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemXianGouUseDO
	 * @return
	 * @throws ManagerException
	 */
	Long getItemXianGouUseDOCount(ItemXianGouUseDO itemXianGouUseDO) throws DaoException;
	
	/**
	 * Create on: 2011-9-2下午05:24:11
	 * <p>Discription:[根据code查询限购码领用记录]</p>
	 * @param: Long
	 * @return: ItemXianGouUseDO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	ItemXianGouUseDO getItemXianGouUseDOByCode(Long code) throws DaoException;
	
}
