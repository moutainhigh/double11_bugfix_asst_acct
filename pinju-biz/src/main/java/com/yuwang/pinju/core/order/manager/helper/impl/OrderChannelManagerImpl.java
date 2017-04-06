package com.yuwang.pinju.core.order.manager.helper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderItemAttributesEnum;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.distribute.manager.DistributorManager;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.manager.OrderCreationManager;
import com.yuwang.pinju.core.order.manager.helper.OrderChannelManager;
import com.yuwang.pinju.domain.distribute.ItemInfo;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.order.OrderChannelDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
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
public class OrderChannelManagerImpl implements OrderChannelManager {
	/**
	 * 分销管理
	 */
	private DistributorManager distributorManager;
	
	/**
	 * 
	 * 商品管理
	 */
	private ItemManager itemManager;
	
	/**
	 * 订单生成
	 */
	private OrderCreationManager orderCreationManager;
	
	/**
	 * 会员管理
	 */
	private MemberManager memberManager;
	
	//分销
	private final String CHANNELSIGN = OrderItemAttributesEnum.SHOP_DISTRIBUTION.getFeatureName();
	
	@Override
	public Map<OrderDO, List<OrderItemDO>> channelBeforeProcess(
			OrderCreationVO orderCreation, OrderDO orderDO,
			List<OrderItemDO> list) {
		Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
		if (list.isEmpty()){
			map.put(orderDO, list);
			return map;
		}
		List<OrderItemDO> list_clone = new ArrayList<OrderItemDO>();
		list_clone.addAll(list);
		// 处理分销
		int size = 0;
		for (OrderItemDO orderItemDO : list) {
			String orderItemAttributes = orderItemDO.getOrderItemAttributes(); 
			// 分销商标记
			String channelId = orderCreation.getChannelId(size);
			// 处理分销
			if (!StringUtil.isEmpty(channelId)&&!channelId.equals("0")) {
				orderItemAttributes = OrderUtilMothed.markingAttributes(orderItemAttributes,CHANNELSIGN,String.valueOf(channelId));
				list_clone.remove(orderItemDO);
				orderItemDO.setOrderItemAttributes(orderItemAttributes);
				list_clone.add(orderItemDO);
			}
			size ++;
		}
	
		
		map.put(orderDO, list_clone);
		return map;
	}

	@Override
	public void channelAfterProcess(String channelId, OrderItemDO orderItemDO)
			throws ManagerException {
		ItemInfo itemInfo = distributorManager.findItemInfoByCondition(channelId,orderItemDO.getItemId());
		if (itemInfo != null && itemInfo.getStatus()) {
				MemberDO memberDO = memberManager.findMember(itemInfo.getMemberId());
				OrderChannelDO orderChannelDO = new OrderChannelDO();
				orderChannelDO.setOrderId(orderItemDO.getOrderId());
				orderChannelDO.setChannelIds(itemInfo.getMemberId());
				ItemDO itemDO = itemManager.getItemDOById(itemInfo
						.getItemId());
				orderChannelDO.setSellerId(itemDO.getSellerId());
				// 获取传入折扣需修改参数
				orderChannelDO.setReward(itemInfo.getReward());
				// 获取分销商昵称
				orderChannelDO.setChannelNickName(memberDO.getNickname());
				orderChannelDO.setOrderItemId(orderItemDO.getOrderItemId());
				Long reAmount = orderItemDO.getBuyNum()*orderItemDO.getOrderItemPrice()*itemInfo.getReward()/OrderConstant.ITEM_CHANNEL_RATE;
				orderChannelDO.setReAmount(reAmount);
				orderCreationManager
						.insertOrderChannelDO(orderChannelDO);
		}
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	public void setDistributorManager(DistributorManager distributorManager) {
		this.distributorManager = distributorManager;
	}

	public void setOrderCreationManager(OrderCreationManager orderCreationManager) {
		this.orderCreationManager = orderCreationManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}
	
	
}

