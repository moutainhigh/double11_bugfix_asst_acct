package com.yuwang.pinju.core.order.manager.cache;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;



/**
 * Created on 2011-10-8
 * @see
 * <p>Discription: [订单相关缓存管理]</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface ItemBuyNumCacheManager {
	
	/**
	 * 
	 * Created on 2011-10-8
	 * <p>Discription: [从cache中获取商品购买数量]</p>
	 * @param queryOrderItem
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	Long queryCacheItemBuyNum(QueryOrderItem queryOrderItem)throws ManagerException;

	/**
	 * 
	 * Created on 2011-11-25
	 * <p>Discription: 更新缓存购买数量</p>
	 * @param itemId
	 * @param buyNum
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	boolean setCacheItemBuyNum(Long itemId,Long buyNum);
}

