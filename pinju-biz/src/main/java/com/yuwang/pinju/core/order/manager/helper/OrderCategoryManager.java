package com.yuwang.pinju.core.order.manager.helper;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.item.SkuDO;

/**
 * Created on 2011-9-19
 * @see
 * <p>Discription: 订单使用的类目管理</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderCategoryManager {
	/**
	 * 
	 * Created on 2011-8-26
	 * <p>Discription: 
	 *  1.现行方式为取缓存中的商品sku目录及描述可能会存在15分钟的延迟问题
	 *  2.取得某个SKU商品的所有中文描述
	 * </p>
	 * @param skuDO
	 * @return 
	 * @throws NumberFormatException
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	String getSkuDOAttributes(SkuDO skuDO) throws NumberFormatException, ManagerException;
}

