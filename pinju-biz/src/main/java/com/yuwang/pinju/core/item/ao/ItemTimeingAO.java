/**
 * 
 */
package com.yuwang.pinju.core.item.ao;

import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.core.common.Result;

/**  
 * @Project: pinju-biz
 * @Title: ItemFeaturesAO.java
 * @Package com.yuwang.pinju.core.item.ao
 * @Description: 用于处理时间服务器的AO
 * @author liuboen liuboen@zba.com
 * @date 2011-7-30 下午04:18:55
 * @version V1.0  
 */

public interface ItemTimeingAO {
	Result emptyFeture(String itemIds,long actDiscountId,ItemFeaturesEnum featuresEnum);
}
