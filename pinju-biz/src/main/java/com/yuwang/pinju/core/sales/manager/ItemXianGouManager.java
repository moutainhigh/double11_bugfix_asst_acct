package com.yuwang.pinju.core.sales.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;
import com.yuwang.pinju.domain.sales.query.ItemXianGouQuery;

public interface ItemXianGouManager {

	/**
	 * 添加一个商品参加限购活动
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemXianGouDO
	 * @throws ManagerException
	 */
	public void addItemXianGouDO(ItemXianGouDO itemXianGouDO)throws ManagerException;
	
	/**
	 * 根据限购Id查询该商品限购详情
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param id
	 * @throws ManagerException
	 */
	public ItemXianGouDO getItemXianGouDOById(Long id)throws ManagerException;
	
	/**
	 * 根据商品Id查询该商品的限购活动详情
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemId
	 * @throws ManagerException
	 */
	public ItemXianGouDO getItemXianGouDOByItemId(Long itemId)throws ManagerException;
	
	/**
	 * 修改该商品限购活动部分条件
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemXianGouDO
	 * @return
	 * @throws ManagerException
	 */
	public boolean updateItemXianGouDO(ItemXianGouDO itemXianGouDO)throws ManagerException;
	
	/**
	 * xianGouId 必选，status，itemId可选
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemXianGouUseDO
	 * @return
	 * @throws ManagerException
	 */
	public Long getItemXianGouUseCount(ItemXianGouUseDO itemXianGouUseDO)throws ManagerException;
	
	/**
	 * 插入一条限购码领取记录
	 * @Project:pinju-biz
	 * @author: lixingquan lixingquan@zba.com
	 * @date:2011-8-31
	 * @update:2011-8-31
	 * @param itemXianGouUseDO
	 * @throws ManagerException
	 */
	public void addItemXianGouUseDO(ItemXianGouUseDO itemXianGouUseDO) throws ManagerException;
	
	/**
	 * Create on: 2011-9-2下午02:40:07
	 * <p>Discription:[修改限购码领用记录]</p>
	 * @param: 
	 * @return: int 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	int updateItemXianGouUse(ItemXianGouUseDO itemXianGouUseDO) throws ManagerException;	
	
	/**
	 * Create on: 2011-9-2下午05:24:11
	 * <p>Discription:[根据code查询限购码领用记录]</p>
	 * @param: Long
	 * @return: ItemXianGouUseDO 
	 * @author:[凌建涛]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	ItemXianGouUseDO getItemXianGouUseDOByCode(Long code) throws ManagerException;	
	
	/**
	 * 根据限购活动
	 *  Created on 2011-12-1 
	 * <p>Discription:[方法功能中文描述]</p>
	 * @author:[lixingquan]
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @return List<ItemXianGouDO> .
	 */
	List<ItemXianGouDO> getItemXianGouDOs(ItemXianGouQuery itemXianGouQuery) throws ManagerException;
	
}
