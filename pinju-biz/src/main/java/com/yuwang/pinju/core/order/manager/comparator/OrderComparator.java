package com.yuwang.pinju.core.order.manager.comparator;

import java.util.Comparator;
import com.yuwang.pinju.domain.order.OrderDO;

/**
 * Created on 2011-10-21
 * @see
 * <p>Discription: 订单集合排序规则</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderComparator implements Comparator<OrderDO> {

	@Override
	public int compare(OrderDO o1, OrderDO o2) {
		if(o1==null || o2 == null){
			return 0;
		}
		if(o1.getSellerId().compareTo(o2.getSellerId())> 0)
			return 1;
		return 0;
	}

}

