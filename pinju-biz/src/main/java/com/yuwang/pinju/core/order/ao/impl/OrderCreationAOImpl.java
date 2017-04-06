package com.yuwang.pinju.core.order.ao.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.Constant.OrderItemAttributesEnum;
import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.active.manager.ActivityDiscountManager;
import com.yuwang.pinju.core.captcha.CaptchaManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.constant.activity.ActivityConstant;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.item.manager.SkuManager;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.order.ao.OrderCreationAO;
import com.yuwang.pinju.core.order.manager.OrderCreationManager;
import com.yuwang.pinju.core.order.manager.OrderQueryManager;
import com.yuwang.pinju.core.order.manager.check.OrderCheckManager;
import com.yuwang.pinju.core.order.manager.helper.OrderActivityManager;
import com.yuwang.pinju.core.order.manager.helper.OrderAdManager;
import com.yuwang.pinju.core.order.manager.helper.OrderCategoryManager;
import com.yuwang.pinju.core.order.manager.helper.OrderChannelManager;
import com.yuwang.pinju.core.order.manager.helper.OrderCouponManager;
import com.yuwang.pinju.core.order.manager.helper.OrderDiscountManager;
import com.yuwang.pinju.core.order.manager.helper.OrderSplitAssistantManager;
import com.yuwang.pinju.core.order.manager.helper.OrderSplitManager;
import com.yuwang.pinju.core.order.manager.helper.impl.OrderUtilMothed;
import com.yuwang.pinju.core.shop.manager.ShopShowInfoManager;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.item.SkuDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberDeliveryDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.OrderLogisticsDO;
import com.yuwang.pinju.domain.order.query.CartSellCreation;
import com.yuwang.pinju.domain.order.query.ImmediatelySellCreation;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
import com.yuwang.pinju.domain.order.query.QueryOrderItem;
import com.yuwang.pinju.domain.shop.ShopInfoDO;
import com.yuwang.pinju.domain.trade.DirectPayParamDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * 
 * @author 杜成
 * @date 2011-6-4
 * @version 1.0
 */
public class OrderCreationAOImpl extends BaseAO implements OrderCreationAO {

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
	 * 订单效验管理
	 */
	private OrderCheckManager orderCheckManager;

	/**
	 * 会员管理
	 */
	private MemberManager memberManager;

	/**
	 * 店铺管理
	 */
	private ShopShowInfoManager shopShowInfoManager;

	/**
	 * Sku管理
	 */
	private SkuManager skuManager;

	/**
	 * 订单查询
	 */
	private OrderQueryManager orderQueryManager;

	/**
	 * 活动管理
	 */
	private ActivityDiscountManager activityDiscountManager;
	
	/**
	 * 订单活动管理
	 */
	private OrderActivityManager orderActivityManager;
	
	/**
	 * 订单分账管理
	 */
	private OrderSplitAssistantManager orderSplitAssistantManager;
	
	/**
	 * 订单分销处理
	 */
	private OrderChannelManager orderChannelManager;
	
	/**
	 * 订单广告处理
	 */
	private OrderAdManager orderAdManager;
	
	/**
	 * 订单特供码管理
	 */
	private OrderDiscountManager orderDiscountManager;

	/**
	 * 订单拆单管理
	 */
	private OrderSplitManager orderSplitManager;
	
	/**
	 * 订单类目管理
	 */
	private OrderCategoryManager orderCategoryManager;
	
	private CaptchaManager captchaManager;

	/**
	 * 优惠券订单辅助类
	 */
	private OrderCouponManager orderCouponManager;
	
	//分销
	private final String CHANNELSIGN = OrderItemAttributesEnum.SHOP_DISTRIBUTION.getFeatureName();
	//特供
	private final String DISCOUNTCODE = OrderItemAttributesEnum.DISCOUNT_CODE.getFeatureName();
	//广告
	private final String AD = OrderItemAttributesEnum.AD.getFeatureName();
	

	@Override
	public Result creationOrder(OrderCreationVO orderCreation, Long buyerId,
			String buyIp) {
		Result result = new ResultSupport();
		try {
			//效验收货地址
			result = orderMemberDeliveryCheck(orderCreation.getMemberDeliveryId(),buyerId);
			
			MemberDeliveryDO memberDeliveryDO = null;
			
		
			//效验参数
			if( !result.isSuccess()){
				return result;
			}else{
				 memberDeliveryDO = (MemberDeliveryDO) result.getModel("memberDeliveryDO");
			}

			//基本效验	
			result = getErrorMap(orderCreation);
			if(!result.isSuccess()){
				return result;
			}

			//订单效验
			result = creationOrderCheck(orderCreation, buyerId);
			if(!result.isSuccess()){
				return result;
			}
			//活动效验
			result = orderCheckManager.activityCheck(orderCreation);
			if(!result.isSuccess()){
				return result;
			}
			//优惠券效验
			result = orderCheckManager.couponCheck(orderCreation);
			if(!result.isSuccess()){
				return result;
			}

			MemberDO buyer = memberManager.findMember(buyerId);
			//获取结算信息
			CartSellCreation sellCreation = this.SellCreationCreation(orderCreation, buyer);
			
			
			List<Long> orderIdList = new ArrayList<Long>();

			Date date = new Date();
			for (OrderDO sell_orderDO : sellCreation.getOrderMap().keySet()) {
				
				List<OrderItemDO> orderItemlist = sellCreation.getOrderMap().get(sell_orderDO);
				
				Long itemIdKey = orderItemlist.get(0).getItemId();
				
				ShopInfoDO shopInfoDO = getShopBusinessInfoDO(sell_orderDO.getSellerId());
				/**
				 * *********** 订单  前置处理   *************
				 */
				Map<OrderDO, List<OrderItemDO>> orderItemMap = orderItemBeforeProcess(orderCreation, sell_orderDO, orderItemlist);
				OrderDO orderDO = orderItemMap.keySet().iterator().next();
				
				List<OrderItemDO> list =orderItemMap.get(orderDO);
				
				orderDO.setBuyIp(buyIp);
				orderDO.setBuyerMeMo(orderCreation.getMemo(itemIdKey));
				orderDO.setShopId(Long.valueOf(shopInfoDO.getShopId()));
				orderDO.setShopName(shopInfoDO.getName());
				orderDO.setStateModifyTime(date);
				Long orderAmount = 0L;
				for (OrderItemDO orderItemDO : list){
					orderAmount = orderAmount + orderItemDO.getTotalAmount();
				}
				orderAmount = orderCreation.getLogisticsPrice(itemIdKey) + orderAmount;
				//订单金额
				orderDO.setPriceAmount(orderAmount);
				OrderLogisticsDO orderLogisticsDO = OrderLogisticsDO.creationOrderLogisticsDO(orderCreation.getLogisticsPrice(itemIdKey), memberDeliveryDO.getAddress(), memberDeliveryDO
						.getReceiverName(), memberDeliveryDO
						.getZipCode(), memberDeliveryDO.getPhone(),memberDeliveryDO.getMobile(),
						memberDeliveryDO.getProvince(), memberDeliveryDO.getCity(), memberDeliveryDO.getDistrict(), date, date,
						orderCreation.getLogisticsMode(itemIdKey).toString(), memberDeliveryDO
						.getPcdCode(),
						OrderLogisticsDO.LOGISTICS_STATE_1);
				/**
				 * *********** 支付处理      *************
				 */
				VouchPayDO vouchPayDO = orderSplitAssistantManager.creationVouchPayDO(orderDO);
				//生成订单
				Map<Long, List<String>> returnOrderMap = orderCreationManager.creationOrder(orderDO,list,orderLogisticsDO,vouchPayDO);
				//判断订单是否处理成功
				if(returnOrderMap==null||returnOrderMap.isEmpty()){
					throw new ManagerException();
				}
				//生成后的订单主键
				Long orderID = returnOrderMap.keySet().iterator().next();
				//生成订单后处理打标记的子定单
				for(OrderItemDO orderItemDO :list){
					String key = orderItemDO.getOrderItemAttributes();
					for(String value:returnOrderMap.get(orderID)){
						if(orderItemDO.getItemId().equals(Long.valueOf(value.split(":")[1]))){
								orderItemDO.setOrderId(orderID);
								orderItemDO.setOrderItemId(Long.valueOf(value.split(":")[0]));
								/**
								 * *********** 子订单  后置处理 *************
								 */
								orderItemAfterProcess(key,orderItemDO);
						}
					}
					
					/**
					 * 支付处理 TODO 先走即时到账交易  目前只有团购 临时使用一个主订单只会有一个子订单
					 */
					DirectPayParamDO directPayParamDO = new DirectPayParamDO();
					directPayParamDO.setBizType(orderItemDO.getBussinessType());
					directPayParamDO.setBuyAmount(orderDO.getPriceAmount().intValue());
					directPayParamDO.setCreateTime(date);
					directPayParamDO.setBuyerId(buyerId);
					directPayParamDO.setBuyerNick(orderItemDO.getBuyerNick());
					directPayParamDO.setItemId(orderItemDO.getItemId());
					directPayParamDO.setItemPrice(orderItemDO.getOrderItemPrice());
					directPayParamDO.setItemTitle(orderItemDO.getItemTitle());
					directPayParamDO.setSellerId(orderItemDO.getSellerId());
					directPayParamDO.setSellerNick(orderItemDO.getSellerNick());
					directPayParamDO.setOrderPrice(orderDO.getPriceAmount());
					directPayParamDO.setOrderId(orderID);
					result.setModel("directPayParamDO",directPayParamDO);
				}
				/** 主订单  后置处理 **/
				orderDO.setOrderId(orderID);
				this.orderAfterProcess(orderDO);
				
				orderIdList.add(orderID);
			}
			// 订单生成成功
			result.setModel("orderIdList", orderIdList);
		} catch (ManagerException e) {
			log.error("Event=[creationOrder#creationOrder]订单生成错误:", e);
			return errorResult();
		}catch (Exception e) {
			log.error("Event=[creationOrder#creationOrder]订单生成异常:", e);
			return errorResult();
		}
		return result;
	}

	
	
	
	
	@Override
	public Result creationSettling(OrderCreationVO orderCreation) {
		Result result = new ResultSupport();
		try {
			//效验参数
			if(orderCreation.check()){
				return errorResult();
			}
			
			result = getErrorMap(orderCreation);
			if(!result.isSuccess()){
				return result;
			}
			MemberDO buyerMemberDO = memberManager.findMember(orderCreation
					.getBuyerMemberId());
			CartSellCreation cartSellCreation = this.SellCreationCreation(
					orderCreation, buyerMemberDO);
			result.setModel("sellCreation", cartSellCreation);
		} catch (ManagerException e) {
			log.error("Event=[creationOrder#creationSettling]购物车结算生成错误:", e);
			return errorResult();
		}catch (Exception e) {
			log.error("Event=[creationOrder#creationSettling]购物车结算生成异常:", e);
			return errorResult();
		}
		return result;

	}
	
	

	
	@Override
	public Result creationLastTimeSettling(OrderCreationVO orderCreation) {
		Result result = new ResultSupport();
		try {
			// 基本参数效验
			if (orderCreation.check()) {
				return errorResult();
			}
			Long itemSkuId = null;
			String skuAttributes = null;
			// 判断是否带sku
			final boolean isSku = orderCreation.getItemSkuId() != null
					&& orderCreation.getItemSkuId()[0].compareTo(0L) > 0;
			if (isSku){
				itemSkuId = orderCreation.getItemSkuId()[0];
				skuAttributes =  orderCategoryManager.getSkuDOAttributes(skuManager.getItemSkuById(itemSkuId));
			}

			//活动效验
			result = orderActivityManager.activityCheck(orderCreation);
			if(!result.isSuccess()){
				return result;
			}
			
			// 效验参数是否正确
			result = this.check(orderCreation.getItemId()[0], orderCreation
					.getBuyerMemberId(), itemSkuId, orderCreation.getNum()[0]);
			
			// 出错回跳
			if (!result.isSuccess())
				return result;
			// 封装立即购买需要参数
			ImmediatelySellCreation immediatelySellCreation = this.immediatelySellCreation(orderCreation, itemSkuId, skuAttributes);

			// 团购或限时折扣
			if (OrderUtilMothed.isGroupOrDicountRate(orderCreation.getBussinessType()[0])) {
				result = this.setGroupOrDicountRate(immediatelySellCreation, result);
			}
			
	
			result.setModel("immediatelySellCreation", immediatelySellCreation);
		} catch (ManagerException e) {
			log.error("Event=[creationOrder#creationLastTimeSettling]立即结算生成错误:", e);
			return errorResult();
		}catch (Exception e) {
			log.error("Event=[creationOrder#creationLastTimeSettling]立即结算生成异常:", e);
			return errorResult();
		}
		return result;
	}

	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * Discription: 限时折扣或团购   结算专用  生成参数封装
	 * </p>
	 * 
	 * @param immediatelySellCreation
	 * @param result
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Result setGroupOrDicountRate(
			ImmediatelySellCreation immediatelySellCreation, Result result)
			throws ManagerException {
		// 活动编号
		String activeInfoById = immediatelySellCreation.getItemDO()
				.getFeatureByEnum(ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT);
		// 取得商品活动编号
		if (activeInfoById == null || activeInfoById.equals("null")) {
			result.setSubResultCode(TradeResultCode.ORDER_CHECK_LASTTIME_FAIL);
			return result;
		}
		final Map<String, Object> map = activityDiscountManager
				.getActivityDiscountInfo(Long.valueOf(activeInfoById),
						immediatelySellCreation.getItemDO().getId());
		// 判断获取活动信息
		if (map == null) {
			result.setSubResultCode(TradeResultCode.ORDER_CHECK_LASTTIME_FAIL);
			return result;
		}
		// 判断活动状态
		final Long activityState = (Long) map.get(ActivityConstant.STATUS
				.toString());
		if (activityState != 1) {
			result.setSubResultCode(TradeResultCode.ORDER_CHECK_LASTTIME_BUYTIME_FAIL);
		}
		// 从活动信息中获取每个人限购数量
		final Long limitCount = Long.valueOf((String) map
				.get(ActivityConstant.LIMIT_COUNT));
		// 折扣信息
		final Long dicountRate = Long.valueOf((String) map
				.get(ActivityConstant.DICOUNT_RATE));
		// 开始时间
		final Date startDate = (Date) map.get(ActivityConstant.START_TIME);
		// 结束时间
		final Date endDate = (Date) map.get(ActivityConstant.END_TIME);
		// 已购买数量
		final long buyNum = this.queryBuyNum(immediatelySellCreation
				.getBuyMemberDO().getMemberId(), immediatelySellCreation
				.getItemDO().getId(), startDate, endDate);

		// 效验购买数量与活动时间
		 result = orderCheckManager.checkLastTimeBuy(startDate, endDate,limitCount, buyNum);

		if (!result.isSuccess()) {
			return result;
		}
		
		immediatelySellCreation.setLastTimeNum(limitCount);

		immediatelySellCreation.setBuyNum(buyNum);

		immediatelySellCreation.setDicountRate(dicountRate);
		
		result.setModel("immediatelySellCreation", immediatelySellCreation);
		
		return result;
	}

	/**
	 * 
	 * Created on 2011-8-10
	 * <p>
	 * Discription: 获取某个商品在某个时间段的购买数量
	 * </p>
	 * 
	 * @param buyerId
	 * @param itemId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Long queryBuyNum(Long buyerId, Long itemId, Date startDate,
			Date endDate) throws ManagerException {
		QueryOrderItem queryOrderItem = new QueryOrderItem(buyerId, itemId,
				new int[] { OrderItemDO.ORDER_ITEM_TYPE_3,
						OrderItemDO.ORDER_ITEM_TYPE_4 }, new int[] {
						OrderItemDO.ORDER_ITEM_STATE_1,
						OrderItemDO.ORDER_ITEM_STATE_2,
						OrderItemDO.ORDER_ITEM_STATE_3,
						OrderItemDO.ORDER_ITEM_STATE_5 }, startDate, endDate);
		List<OrderItemDO> list =orderQueryManager.queryOrderItemDOList(queryOrderItem);
		Long buyNum = 0L;
		for(OrderItemDO orderItemDO : list){
			buyNum += orderItemDO.getBuyNum();
		}
		return buyNum;
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
	 * Created on 2011-8-10
	 * <p>
	 * Discription: 封装立即购买 结算专用  生成数据
	 * </p>
	 * 
	 * @param itemId
	 * @param sellerId
	 * @param buyerId
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private ImmediatelySellCreation immediatelySellCreation(OrderCreationVO orderCreation, Long skuId, String skuAttributes)
			throws ManagerException {
		ItemDO itemDO = itemManager.getItemDOById(orderCreation.getItemId()[0]);
		ImmediatelySellCreation immediatelySellCreation = new ImmediatelySellCreation();
	
		immediatelySellCreation.setSellerMember(memberManager.findMember(itemDO.getSellerId()));
		immediatelySellCreation.setBuyMemberDeliveryList(memberManager.findMemberDeliveries(orderCreation.getBuyerMemberId()));
		immediatelySellCreation.setBuyMemberDO(memberManager.findMember(orderCreation.getBuyerMemberId()));
		immediatelySellCreation.setBuyCount(orderCreation.getNum()[0]);
		immediatelySellCreation.setBussinessType(orderCreation.getBussinessType()[0]);
		immediatelySellCreation.setDiscountCode(orderCreation.getXianGuoCode());
		immediatelySellCreation.setShopId(shopShowInfoManager.queryShopBaseInfoByUser(itemDO.getSellerId(),null).getShopId());
		// 放入sku信息
		if (skuId != null && skuId.compareTo(0L) != 0){
			immediatelySellCreation.setSkuId(skuId);
			SkuDO skuDO = skuManager.getItemSkuById(skuId);
			itemDO.setCurStock(skuDO.getCurrentStock());
			if (skuAttributes != null && !skuAttributes.equals(""))
				immediatelySellCreation.setSkuAttributes(skuAttributes);
			//放入sku价格
			itemDO.setPrice(skuDO.getPrice());
		}
		// 设置分销
		if (orderCreation.getChannelId() != null){
			immediatelySellCreation.setChannelId(orderCreation.getChannelId(0));
		}
		if (orderCreation.getAd()!= null) {
			immediatelySellCreation.setAd(orderCreation.getAd(0));
		}
		if(immediatelySellCreation.getSkuId()==null){
			immediatelySellCreation.setSkuId(0l);
		}
			
		immediatelySellCreation.setItemDO(itemDO);
		return immediatelySellCreation;
	}

	/**
	 * Created on 2011-6-4
	 * <p>
	 * 结算专用  数据处理
	 * </p>
	 * 
	 * @param list
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private CartSellCreation SellCreationCreation(
			OrderCreationVO orderCreation, MemberDO buyerMemberDO)
			throws ManagerException {
		CartSellCreation sellCreation = new CartSellCreation();
		sellCreation.setBuyMemberDO(buyerMemberDO);
		List<ItemDO> itemDOList = new ArrayList<ItemDO>();
		for (int i = 0; i < orderCreation.getItemId().length; i++) {
			ItemDO itemDO = itemManager
					.getItemDOById(orderCreation.getItemId()[i]);
			itemDOList.add(itemDO);
		}

		// 买家送货地址集合
		sellCreation.setBuyMemberDeliveryList(memberManager
				.findMemberDeliveries(orderCreation.getBuyerMemberId()));
		// 商品列表
		sellCreation.setItemDOList(itemDOList);
		// 获取拆单后的数据结构
		sellCreation.setOrderMap(orderSplitManager.dismantleOrder(orderCreation, itemDOList));
				
		return sellCreation;
	}







	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 得到错误购买商品错误集合</p>
	 * @param orderCreation
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Result getErrorMap(OrderCreationVO orderCreation)
			throws ManagerException {
		Long[] skuIds = orderCreation.getSellerId();
		Result checkResult=new ResultSupport();
		for (int i = 0; i < orderCreation.getItemId().length; i++) {
			Long skuId = null;
			if (skuIds != null && skuIds[i] != null
					&& skuIds[i].compareTo(0L) > 0)
				skuId = skuIds[i];
			checkResult = this.check(orderCreation.getItemId()[i],
					orderCreation.getBuyerMemberId(), skuId, Long
							.valueOf(orderCreation.getNum()[i]));
			if (!checkResult.isSuccess())
				return checkResult;
		}
		return checkResult;
	}

	/**
	 * 
	 * Created on 2011-8-16
	 * <p>Discription: 调用效验Manager效验订单商品</p>
	 * @param itemId
	 * @param buyerId
	 * @param skuId
	 * @param buyNum
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Result check(Long itemId, Long buyerId, Long skuId, Long buyNum)
			throws ManagerException {
		ItemDO itemDO = itemManager.getItemDOById(itemId);
		MemberDO seller = memberManager.findMember(itemDO.getSellerId());
		MemberDO buyer = memberManager.findMember(buyerId);
		ShopInfoDO shopInfoDO = this
				.getShopBusinessInfoDO(itemDO.getSellerId());
		SkuDO skuDO = null;
		if (skuId != null)
			skuDO = skuManager.getItemSkuById(skuId);
		return orderCheckManager.checkCreateOrder(buyer, seller, shopInfoDO,
				itemDO, skuDO, buyNum);
	}

	
	
	/**
	 * 
	 * Created on 2011-9-23
	 * <p>Discription: 订单生成效验</p>
	 * @param orderCreation
	 * @param buyerId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Result creationOrderCheck(OrderCreationVO orderCreation, Long buyerId) throws ManagerException {
		Result result = new ResultSupport();
		MemberDO buyer = memberManager.findMember(buyerId);
		int size = 0;
		for(Long itemId : orderCreation.getItemId()){
			ItemDO itemDO = itemManager.getItemDOById(itemId);
			MemberDO seller = memberManager.findMember(itemDO.getSellerId());
			ShopInfoDO shopInfoDO = this.getShopBusinessInfoDO(itemDO.getSellerId());
			SkuDO skuDO = null;
			if (orderCreation.getItemSkuId(size) != null)
				skuDO = skuManager.getItemSkuById(orderCreation.getItemSkuId(size));
	
			result = orderCheckManager.checkCreateOrder(buyer, seller, shopInfoDO,
					itemDO, skuDO, orderCreation.getItemBuyNum(size));
			size ++ ;
			if (!result.isSuccess()) {
				return result;
			}
		}
		
		return result;
		
	}


	

	/**
	 * 
	 * Created on 2011-8-18
	 * <p>Discription: 生成订单效验收货地址</p>
	 * @param memberDeliveryId
	 * @param buyerId
	 * @return
	 * @throws ManagerException
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Result orderMemberDeliveryCheck(Long memberDeliveryId, Long buyerId)
			throws ManagerException {
		Result result = new ResultSupport();
		// 得到收货地址
		if (memberDeliveryId != null) {
			MemberDeliveryDO memberDeliveryDO = memberManager
					.getMemberDeliveryById(memberDeliveryId);
			// 效验收货地址与买家关系 失败
			if (!orderCheckManager.buyerDelivery(memberDeliveryDO, buyerId))
				return OrderUtilMothed.setResult(false, result,TradeResultCode.ORDER_CHECK_BUYERDELIVERY_FAIL);
			else {
				result.setModel("memberDeliveryDO", memberDeliveryDO);
			}
		}
		return result;
	}

	
	/**
	 * 
	 * Created on 2011-8-23
	 * <p>Discription: 效验出错返回</p>
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Result errorResult(){
		Result result = new ResultSupport();
		result.setSuccess(false);
		result.setResultCode(TradeResultCode.ORDER_CHECK_EXCEPTION);
		return result;
	}
	
	
	/**
	 * 
	 * Created on 2011-8-26
	 * <p>Discription: 子订单前置处理
	 * 			给每个子订单打上对应标记
	 * 			key 返回码 描述了被打了那些标记方便做后续处理(多个处理已:分隔),value被更新后的子订单集合
	 * 		    key: 有分销 shopChannel
	 * </p>
	 * @param orderCreation
	 * @param sellCreation
	 * @return 
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private Map<OrderDO, List<OrderItemDO>>   orderItemBeforeProcess(OrderCreationVO orderCreation,OrderDO orderDO,List<OrderItemDO> list) throws ManagerException{
		Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
		if (list.isEmpty()){
			return map;
		}
		//处理分销
		map = orderChannelManager.channelBeforeProcess(orderCreation, orderDO, list);
		
		orderDO = map.keySet().iterator().next();
		//处理广告
		map = orderAdManager.adBeforeProcess(orderCreation, orderDO, list);
		
		orderDO = map.keySet().iterator().next();
	
		// 处理活动
		map = orderActivityManager.activityBeforeProcess(orderCreation,orderDO, map.get(orderDO));
		
		orderDO = map.keySet().iterator().next();
		//处理特供
		map = orderDiscountManager.discountBeforeProcess(orderCreation,orderDO, map.get(orderDO));
		
		orderDO = map.keySet().iterator().next();
		//处理优惠
		map = orderCouponManager.couponBeforeProcess(orderCreation, orderDO, list);
		return map;
	}
	

	/**
	 * 
	 * Created on 2011-11-28
	 * <p>Discription: 主订单后续处理</p>
	 * @param orderItemAttributes
	 * @param orderItemId
	 * @param itemId
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void orderAfterProcess(OrderDO orderDO) throws ManagerException{

	}
	/**
	 * 
	 * Created on 2011-8-26
	 * <p>Discription: 子订单后续处理</p>
	 * @param orderItemAttributes
	 * @param orderItemId
	 * @param itemId
	 * @author:[杜成]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	private void orderItemAfterProcess(String keys,OrderItemDO orderItemDO) throws ManagerException{
		//分销处理
		if(StringUtil.isNotEmpty(keys)){
				for(String  key :keys.split(VouchPayConstant.SPLITKEY)){
					String temp_key = key.split(VouchPayConstant.SPLITATTRIBUTES)[0];
					//分销后续处理
					if(StringUtil.contains(temp_key, CHANNELSIGN)){
						orderChannelManager.channelAfterProcess(key.split(VouchPayConstant.SPLITATTRIBUTES)[1], orderItemDO);
					}
					//特供码
					if(StringUtil.contains(temp_key, DISCOUNTCODE)){
						orderDiscountManager.discountAfterProcess(key.split(VouchPayConstant.SPLITATTRIBUTES)[1], orderItemDO);
					}
					
					//广告
					if(StringUtil.contains(temp_key, AD)){
						orderAdManager.aDAfterProcess(key.split(VouchPayConstant.SPLITATTRIBUTES)[1], orderItemDO);
					}
			}
		}
	}
	
	
	/**
	 * @Description: Ajax异步校验验证码
	 * @author [贺泳]
	 * @date 2011-9-14 上午09:37:20
	 * @version 1.0
	 * @param sid: 验证码
	 * @param validateCode: 用户输入的验证
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result ajaxCheckCode(String sid,String validateCode){
		Result result = new ResultSupport();
		try {
			//校验验证码
			if (!captchaManager.validate(sid, validateCode)) {
				result.setSuccess(false);
				result.setResultCode(TradeResultCode.ORDER_CHECK_VALIDATECODE_FAIL);
			}
		} catch (Exception e) {
			log.error("Event=[CreationOrderAction#ajaxCheckCode] ", e);
		}
		return result;
	}
	
	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setOrderCheckManager(OrderCheckManager orderCheckManager) {
		this.orderCheckManager = orderCheckManager;
	}

	public void setShopShowInfoManager(ShopShowInfoManager shopShowInfoManager) {
		this.shopShowInfoManager = shopShowInfoManager;
	}

	public void setSkuManager(SkuManager skuManager) {
		this.skuManager = skuManager;
	}

	public void setOrderQueryManager(OrderQueryManager orderQueryManager) {
		this.orderQueryManager = orderQueryManager;
	}

	public void setOrderCreationManager(
			OrderCreationManager orderCreationManager) {
		this.orderCreationManager = orderCreationManager;
	}

	public void setActivityDiscountManager(
			ActivityDiscountManager activityDiscountManager) {
		this.activityDiscountManager = activityDiscountManager;
	}

	public void setCaptchaManager(CaptchaManager captchaManager) {
		this.captchaManager = captchaManager;
	}

	public void setOrderActivityManager(OrderActivityManager orderActivityManager) {
		this.orderActivityManager = orderActivityManager;
	}

	public void setOrderSplitAssistantManager(
			OrderSplitAssistantManager orderSplitAssistantManager) {
		this.orderSplitAssistantManager = orderSplitAssistantManager;
	}

	public void setOrderChannelManager(OrderChannelManager orderChannelManager) {
		this.orderChannelManager = orderChannelManager;
	}

	public void setOrderAdManager(OrderAdManager orderAdManager) {
		this.orderAdManager = orderAdManager;
	}

	public void setOrderDiscountManager(OrderDiscountManager orderDiscountManager) {
		this.orderDiscountManager = orderDiscountManager;
	}

	public void setOrderSplitManager(OrderSplitManager orderSplitManager) {
		this.orderSplitManager = orderSplitManager;
	}

	public void setOrderCategoryManager(OrderCategoryManager orderCategoryManager) {
		this.orderCategoryManager = orderCategoryManager;
	}

	@Override
	public Result activityCheck(OrderCreationVO orderCreation) {
		return orderActivityManager.activityCheck(orderCreation);
	}

	public void setOrderCouponManager(OrderCouponManager orderCouponManager) {
		this.orderCouponManager = orderCouponManager;
	}
	
}
