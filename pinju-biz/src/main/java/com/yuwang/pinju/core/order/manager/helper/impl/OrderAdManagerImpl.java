package com.yuwang.pinju.core.order.manager.helper.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.Constant.OrderItemAttributesEnum;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.manager.OrderCreationManager;
import com.yuwang.pinju.core.order.manager.helper.OrderAdManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderTradeAdDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;

/**
 * Created on 2011-9-19
 * @see
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderAdManagerImpl implements OrderAdManager {
	
	//广告
	private final String AD = OrderItemAttributesEnum.AD.getFeatureName();
	/**
	 * 订单生成
	 */
	private OrderCreationManager orderCreationManager;
	
	@Override
	public Map<OrderDO, List<OrderItemDO>> adBeforeProcess(
			OrderCreationVO orderCreation, OrderDO orderDO,
			List<OrderItemDO> list) {
		Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
		if (list.isEmpty()){
			map.put(orderDO, list);
			return map;
		}
		List<OrderItemDO> list_clone = list;
		// 处理广告
		int size = 0;
		for (OrderItemDO orderItemDO : list) {
			String orderItemAttributes = orderItemDO.getOrderItemAttributes();
			// 广告商
			String ad = orderCreation.getAd(size);
			// 处理广告
			if (!StringUtil.isEmpty(ad)&&!ad.equals("0")&&!ad.equals("undefined")) {
				orderItemAttributes = OrderUtilMothed.markingAttributes(orderItemAttributes,AD,ad);
				list_clone.remove(orderItemDO);
				orderItemDO.setOrderItemAttributes(orderItemAttributes);
				list_clone.add(orderItemDO);
			}
			size++;
		}
		map.put(orderDO, list_clone);
		return map;

	}

	@Override
	public void aDAfterProcess(String ad, OrderItemDO orderItemDO)
			throws ManagerException {
		OrderTradeAdDO orderTradeAdDO = new OrderTradeAdDO();
		orderTradeAdDO.setAdCode(ad);						//广告代码
		orderTradeAdDO.setItemId(orderItemDO.getItemId());	//商品ID
		orderTradeAdDO.setOrderItemId(orderItemDO.getOrderItemId());	//子订单编号
		orderCreationManager.insertTradeAd(orderTradeAdDO);
	}

	public void setOrderCreationManager(OrderCreationManager orderCreationManager) {
		this.orderCreationManager = orderCreationManager;
	}

}

