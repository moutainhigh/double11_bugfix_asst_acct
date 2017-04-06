package com.yuwang.pinju.core.order.manager.helper.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.manager.comparator.OrderItemComparator;
import com.yuwang.pinju.core.order.manager.helper.OrderCategoryManager;
import com.yuwang.pinju.core.order.manager.helper.OrderSplitManager;
import com.yuwang.pinju.core.servicefee.manager.ServiceFeeManager;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
import com.yuwang.pinju.domain.servicefee.ServiceFeeResultDO;
import com.yuwang.pinju.domain.shop.ShopInfoDO;

/**
 * Created on 2011-9-19
 * @see
 * <p>Discription: 拆单管理</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderSplitManagerImpl extends BaseManager implements OrderSplitManager{
	/**
	 * 会员管理
	 */
	private MemberManager memberManager;
	/**
	 * sku管理
	 */
	private SkuManager skuManager;

	/**
	 * 商品管理
	 */
	private ItemManager itemManager;
	/**
	 * 店铺管理
	 */
	private ShopShowInfoManager shopShowInfoManager;
	/**
	 * 平台费率管理
	 */
	private ServiceFeeManager serviceFeeManager;
	
	
	private OrderCategoryManager orderCategoryManager;
	//排序
	private Comparator<OrderItemDO> comparator = new OrderItemComparator();
	
	
	@Override
	public Map<OrderDO, List<OrderItemDO>> dismantleOrder(
		OrderCreationVO orderCreation, List<ItemDO> itemDOList) throws ManagerException {
		// 按店铺拆单
		Map<OrderDO, List<OrderItemDO>> map = filterSellerItemMap(
				orderCreation, itemDOList, getsellerList(itemDOList));
		if(itemDOList!=null && itemDOList.size()>1){
			// 按物流拆单
			filterLogisticsItemMap(map);
			//分账拆单
			filterChannelMap(map);
		}
		return map;
	}
	
	
	
	/**
	 * 
	 * Created on 2011-9-19
	 * <p>Discription:[按店铺拆单] </p>
	 * @param orderCreation
	 * @param itemDOList
	 * @param sellerList
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Map<OrderDO, List<OrderItemDO>> filterSellerItemMap(
			OrderCreationVO orderCreation, List<ItemDO> itemDOList,
			List<MemberDO> sellerList) throws ManagerException {
		Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
		MemberDO buyer = memberManager.findMember(orderCreation.getBuyerMemberId());
		for (MemberDO memberDO : sellerList) {
			List<OrderItemDO> orderItemList = new ArrayList<OrderItemDO>();
			ShopInfoDO shopInfoDO = getShopBusinessInfoDO(memberDO.getMemberId());
			if(shopInfoDO==null){
				throw new  ManagerException("event[OrderSplitManagerImpl#filterSellerItemMap]ShopInfoDO is null.seller id is "+ memberDO.getMemberId()+"sellerNick is ".concat(memberDO.getNickname()));
			}
			int size = 0 ;
			for (ItemDO itemDO : itemDOList) {
				if (memberDO.getMemberId().compareTo(itemDO.getSellerId()) == 0) {
		
					Long skuId = orderCreation.getItemSkuId(size);
				
					String skuAttributes = "";
					Long price = itemDO.getPrice();
					if(!OrderUtilMothed.longIsNull(skuId)){
						SkuDO skuDO = skuManager.getItemSkuById(skuId);
						skuAttributes = this.getSkuDOAttributes(skuDO);
						//如果有Sku的话，获取对应的sku价格
						price = skuDO.getPrice();
						itemDO.setPrice(price);
					}
					//获取平台分账费率
					ServiceFeeResultDO serviceFeeResultDO = serviceFeeManager.queryServiceFeeByItem(itemDO,shopInfoDO.getShopId().longValue());
					OrderItemDO orderItemDO = OrderItemDO.creationSettlementOrderItemDO(itemDO.getId(),itemDO.getTitle(),
							price,orderCreation.getItemBuyNum(size),itemDO.getPicUrl(), 
							skuId	,skuAttributes, orderCreation.getBussinessType(size),
							price, orderCreation.isAnonymousBuy(itemDO.getId()),
							orderCreation.getChannelId(size),memberDO.getMemberId(),memberDO.getNickname(),buyer.getMemberId(),buyer.getNickname()				
							,serviceFeeResultDO.getServiceFeeRate(),serviceFeeResultDO.getServiceFee(),serviceFeeResultDO.getShopRate());//分账费率与金额
					//快照编号
					orderItemDO.setSnapId(itemManager
							.getItemSnapshotIdByItemId(itemDO.getId()));
					orderItemDO.setOrderItemPrice(itemDO.getPrice());
					orderItemDO.setTotalAmount(orderCreation.getItemBuyNum(size)*orderItemDO.getOrderItemPrice());
					orderItemList.add(orderItemDO);
					
				}
				size++;
			}
			OrderDO orderDO = OrderDO.createSettlementOrderDO(orderCreation.getBuyerMemberId(),buyer.getNickname(),
					memberDO.getMemberId(), memberDO.getNickname(), Long.valueOf(shopInfoDO.getShopId()), shopInfoDO.getName());
			sort(orderItemList);
			map.put(orderDO, orderItemList);
			orderItemList = null;
		}
		return map;
	}
	
	
	/**
	 * 
	 * Created on 2011-10-21
	 * <p>Discription: 子订单排序</p>
	 * @param list
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void sort(List<OrderItemDO> list){
		//排序
	    Collections.sort(list, comparator);
	}
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>
	 * Discription: 按物流拆单
	 * </p>
	 * 
	 * @param map
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void filterLogisticsItemMap(Map<OrderDO, List<OrderItemDO>> map)
			throws ManagerException {
		// 无运费模板拆单 临时数据保存
		List<OrderItemDO> freeCostList = new ArrayList<OrderItemDO>();
		// 有运费模板拆单 临时数据保存
		Map<ItemDO, OrderItemDO> logisticsTempletMap = new HashMap<ItemDO, OrderItemDO>();
		// 默认运费拆单 临时数据保存
		Map<ItemDO, OrderItemDO> defaultCostMap = new HashMap<ItemDO, OrderItemDO>();

		Map<OrderDO, List<OrderItemDO>> temp_map = new HashMap<OrderDO, List<OrderItemDO>>();

		temp_map.putAll(map);

		for (OrderDO orderDO : temp_map.keySet()) {
			logisticsTempletMap.clear();
			freeCostList.clear();
			defaultCostMap.clear();
			for (OrderItemDO orderItemDO : temp_map.get(orderDO)) {
				ItemDO itemDO = itemManager.getItemDOById(orderItemDO
						.getItemId());
				Long freeTemplateId = itemDO.getFreeTemplateId();
				// 无运费打标记
				if (isFreeCost(itemDO)) {
					freeCostList.add(orderItemDO);
					// 默认运费打标记
				} else if (isDefaultCost(itemDO)) {
					defaultCostMap.put(itemDO, orderItemDO);
					// 物流模板打标记
				} else if (!OrderUtilMothed.longIsNull(freeTemplateId)) {
					logisticsTempletMap.put(itemDO, orderItemDO);
				}
			}
			// 无运费拆单
			if (freeCostList.size() > 0 && temp_map.get(orderDO).size() > 1){
				dismantleMap(map, orderDO, freeCostList);
			}// 默认运费拆单
			if (defaultCostMap.size() > 0 && temp_map.get(orderDO).size() > 1) {
				filterDefaultCostSame(map, orderDO, defaultCostMap);
			}
			// 物流模板拆单
			if (logisticsTempletMap.size() > 0
					&& temp_map.get(orderDO).size() > 1) {
				filterlogisticsTemplet(map, orderDO, logisticsTempletMap);
			}
		}
		logisticsTempletMap = null;
		freeCostList = null;
		defaultCostMap = null;
	}

	/**
	 * 
	 * Created on 2011-9-21
	 * <p>Discription: 多方分账只支持6个账户,默认2个(平台,卖家)</p>
	 * @param map
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Map<OrderDO, List<OrderItemDO>> filterChannelMap(Map<OrderDO, List<OrderItemDO>> map){
			Map<OrderDO, List<OrderItemDO>> temp_map = new HashMap<OrderDO, List<OrderItemDO>>();
			temp_map.putAll(map);
			List<OrderItemDO> orderItemDOList = null;
			List<String> channelList = null;
			// 排序
			for (Map.Entry<OrderDO, List<OrderItemDO>> entry : temp_map
					.entrySet()) {
				orderItemDOList = new ArrayList<OrderItemDO>();
				channelList = new ArrayList<String>();
				Long channeNum = 0L;
				
				// 分账的分销拆单
				for (OrderItemDO orderItemDO : entry.getValue()) {
					
					if (orderItemDO.getChannelId() != null
							&& !orderItemDO.getChannelId().equals("0")
							&& !channelList
									.contains(orderItemDO.getChannelId())) {
						channelList.add(orderItemDO.getChannelId());
						channeNum += 1;
					}
					if (channeNum > 4) {
						orderItemDOList.add(orderItemDO);
					}
				}
				
				if (channeNum > 4) {
					// 拆单
					dismantleMap(map, entry.getKey(), orderItemDOList);
					//有拆单就递归,已防止拆完的单子中超过5个
					filterChannelMap(map);
				}
				channelList = null;
			}
			temp_map = null;
		return map;
	}
	
	
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到商品中的卖家集合，并过滤掉重复值</p>
	 * @param itemDOList
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private List<MemberDO> getsellerList(List<ItemDO> itemDOList)
			throws ManagerException {
		List<MemberDO> sellerList = new ArrayList<MemberDO>();
		Map<Long, MemberDO> sellMap = new HashMap<Long, MemberDO>();
		for (ItemDO itemDO : itemDOList) {
			MemberDO sellerMemberDO = null;
			sellerMemberDO = memberManager.findMember(itemDO.getSellerId());
			sellMap.put(sellerMemberDO.getId(), sellerMemberDO);
		
		}
		sellerList.addAll(sellMap.values());
		sellMap = null;
		return sellerList;
	}

	
	
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
	private String getSkuDOAttributes(SkuDO skuDO) throws NumberFormatException, ManagerException{
		String skuAttributes="";
		if(skuDO!=null){
			skuAttributes=orderCategoryManager.getSkuDOAttributes(skuDO);
		}
		return skuAttributes;
	}
	

	/**
	 * 根据店铺接口封装
	 * 
	 * @throws ManagerException
	 */
	private ShopInfoDO getShopBusinessInfoDO(Long sellerId)
			throws ManagerException {
		List<Long> userIdList = new ArrayList<Long>();
		userIdList.add(sellerId);
		ShopInfoDO shopInfoDO = null;
		List<ShopInfoDO> list = shopShowInfoManager
				.queryShopInfoByUserIdList(userIdList);
		if (list != null && list.size() > 0)
			shopInfoDO = list.get(0);
		return shopInfoDO;
	}
	
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 判断是否免运费 商品对象中无判断免运费字段 后续最好能商品发布中加入</p>
	 * @param itemDO
	 * @return 免运费返回true;
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isFreeCost(ItemDO itemDO) {
		// 判断是否有默认运费
		if (OrderUtilMothed.longIsNull(itemDO.getFreeTemplateId()) && !isDefaultCost(itemDO))
			return true;
		return false;
	}

	/**
	 * 
	 * Created on 2011-8-17
	 * <p>Discription: 判断是否默认运费商品</p>
	 * @param itemDO
	 * @return 是返回true
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private boolean isDefaultCost(ItemDO itemDO) {
		boolean flag = false;
		// 判断是否有默认运费
		if (!OrderUtilMothed.longIsNull(itemDO.getMailCosts())
				|| !OrderUtilMothed.longIsNull(itemDO.getEmsCosts())
				|| !OrderUtilMothed.longIsNull(itemDO.getDeliveryCosts()))
			flag = true;
		return flag;
	}

	/**
	 * 
	 * Created on 2011-8-16
	 * <p>
	 * Discription: 按默认运费拆单
	 * </p>
	 * 
	 * @param map
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void filterDefaultCostSame(Map<OrderDO, List<OrderItemDO>> map,
			OrderDO orderDO, Map<ItemDO, OrderItemDO> defaultCostMap) {
		List<OrderItemDO> orderItemDOList = null;
		// 用于存放过滤后的数据
		Map<String, List<OrderItemDO>> tempMap = new HashMap<String, List<OrderItemDO>>();
		for (ItemDO itemDO : defaultCostMap.keySet()) {
			String flag = String.valueOf(itemDO.getDeliveryCosts()).concat(",")
					+ String.valueOf(itemDO.getEmsCosts()).concat(",")
					+ String.valueOf(itemDO.getMailCosts());
			if (tempMap.get(flag) == null) {
				orderItemDOList = new ArrayList<OrderItemDO>();
				orderItemDOList.add(defaultCostMap.get(itemDO));
				tempMap.put(flag, orderItemDOList);
			} else {
				orderItemDOList = tempMap.get(flag);
				orderItemDOList.add(defaultCostMap.get(itemDO));
			}
		}
		// 拆单实现
		for (String key : tempMap.keySet()) {
			dismantleMap(map, orderDO, tempMap.get(key));
		}
		tempMap = null;
	}
	
	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 重新封装物流拆单数据</p>
	 * @param map
	 * @param orderDO
	 * @param itemDO
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void dismantleMap(Map<OrderDO, List<OrderItemDO>> map,
			OrderDO orderDO, List<OrderItemDO> orderItemDOList) {
		if (orderItemDOList != null && orderItemDOList.size() > 0) {
			List<OrderItemDO> list = map.get(orderDO);
			if (list.size() > orderItemDOList.size()) {
				// 从原有结构中移除
				list.removeAll(orderItemDOList);
				map.remove(orderDO);
				map.put(orderDO, list);
				// 添加拆分后的数据
				OrderDO order = OrderDO.createSettlementOrderDO(orderDO.getBuyerId(), orderDO.getBuyerNick(),orderDO
						.getSellerId(), orderDO.getSellerNick(), orderDO
						.getShopId(), orderDO.getShopName());
				sort(orderItemDOList);
				map.put(order, orderItemDOList);
			}
		}
	}
	
	/**
	 * 
	 * Created on 2011-8-17
	 * <p>Discription: 按物流模板拆单</p>
	 * @param map
	 * @param orderDO
	 * @param logisticsTempletMap
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void filterlogisticsTemplet(Map<OrderDO, List<OrderItemDO>> map,
			OrderDO orderDO, Map<ItemDO, OrderItemDO> logisticsTempletMap) {
		List<OrderItemDO> orderItemDOList = null;
		// 用于存放过滤后的数据
		Map<Long, List<OrderItemDO>> tempMap = new HashMap<Long, List<OrderItemDO>>();
		for (ItemDO itemDO : logisticsTempletMap.keySet()) {
			if (tempMap.get(itemDO.getFreeTemplateId()) == null) {
				orderItemDOList = new ArrayList<OrderItemDO>();
				orderItemDOList.add(logisticsTempletMap.get(itemDO));
				tempMap.put(itemDO.getFreeTemplateId(), orderItemDOList);
			} else {
				orderItemDOList = tempMap.get(itemDO.getFreeTemplateId());
				orderItemDOList
						.add(logisticsTempletMap.get(itemDO));
			}
		}
		// 拆单实现
		for (Long freeTemplateId : tempMap.keySet()) {
			dismantleMap(map, orderDO, tempMap.get(freeTemplateId));
		}
		tempMap = null;
	}
	
	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}
	public void setServiceFeeManager(ServiceFeeManager serviceFeeManager) {
		this.serviceFeeManager = serviceFeeManager;
	}



	public void setOrderCategoryManager(OrderCategoryManager orderCategoryManager) {
		this.orderCategoryManager = orderCategoryManager;
	}

}

