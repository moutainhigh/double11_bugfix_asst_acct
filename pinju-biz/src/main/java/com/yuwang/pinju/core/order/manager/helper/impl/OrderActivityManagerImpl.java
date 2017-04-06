package com.yuwang.pinju.core.order.manager.helper.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.Constant.ItemFeaturesEnum;
import com.yuwang.pinju.Constant.ItemXianGouConstant;
import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderItemAttributesEnum;
import com.yuwang.pinju.Constant.VouchPayConstant;
import com.yuwang.pinju.common.ItemXianGouUtil;
import com.yuwang.pinju.common.Money;
import com.yuwang.pinju.common.MoneyUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.active.manager.ActivityDiscountManager;
import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.common.resultcode.XianGouResultCode;
import com.yuwang.pinju.core.constant.activity.ActivityConstant;
import com.yuwang.pinju.core.item.manager.ItemManager;
import com.yuwang.pinju.core.order.manager.helper.OrderActivityManager;
import com.yuwang.pinju.core.order.manager.helper.OrderDiscountManager;
import com.yuwang.pinju.core.sales.manager.ItemXianGouManager;
import com.yuwang.pinju.domain.item.ItemDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
import com.yuwang.pinju.domain.sales.ItemXianGouDO;
import com.yuwang.pinju.domain.sales.ItemXianGouUseDO;

/**
 * Created on 2011-9-19
 * @see
 * <p>Discription: </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public class OrderActivityManagerImpl extends BaseManager implements OrderActivityManager {
	/**
	 * 
	 * 商品管理
	 */
	private ItemManager itemManager;
	/**
	 * 活动管理
	 */
	private ActivityDiscountManager activityDiscountManager;
	
	/**
	 * 限购管理
	 */
	private ItemXianGouManager itemXianGouManager;
	/**
	 * 订单限购管理
	 */
	private OrderDiscountManager orderDiscountManager;
	
	@Override
	public Map<OrderDO, List<OrderItemDO>> activityBeforeProcess(OrderCreationVO orderCreation,
			OrderDO orderDO, List<OrderItemDO> list) throws ManagerException {
		Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
		Long priceAmount = new Long(0);
		List<OrderItemDO> list_clone = list;
		for (OrderItemDO orderItemDO : list) {
			//如果有优惠券就没有限时特供
			Long couponId = orderCreation.getCouponId(list.get(0).getItemId());
			if(couponId != null &&  couponId.compareTo(0l)>0){
				map.put(orderDO, list);
				return map;
			}
		
			
			
			// 是限时折扣
			if (OrderUtilMothed.isDicountRate(orderItemDO.getBussinessType())
					|| OrderUtilMothed.isGroup(orderItemDO.getBussinessType())) {
				ItemDO itemDO = itemManager.getItemDOById(orderItemDO
						.getItemId());
				String activeInfoById = itemDO
						.getFeatureByEnum(ItemFeaturesEnum.ITEM_LIMIT_TIME_DISCOUNT);
				Long dicountRate = 0L;
				if (activeInfoById != null) {
					// 得到活动信息
					Map<String, Object> maps = activityDiscountManager
							.getActivityDiscountInfo(Long
									.valueOf(activeInfoById), itemDO.getId());
					if (maps != null) {
					
						
							Date endDate = (Date) maps
									.get(ActivityConstant.END_TIME);
							Date startDate = (Date) maps
							.get(ActivityConstant.START_TIME);
							// 判断活动时间
							if (OrderUtilMothed.compareDate(startDate,endDate)) {
								// 折扣信息
								dicountRate = Long.valueOf((String) maps
										.get(ActivityConstant.DICOUNT_RATE));
							}
						
					}
				}
				if (dicountRate != 0) {
					list_clone.remove(orderItemDO);
					OrderItemAttributesEnum enums = OrderItemAttributesEnum.ITEM_LIMIT_TIME_DISCOUNT;
					String value = activeInfoById.concat(VouchPayConstant.SPLITVALUES).concat(String.valueOf(dicountRate));
					String key = enums.getFeatureName();
					Long dicountAmount = Math.round((orderItemDO.getOriginalPrice().doubleValue() * dicountRate.doubleValue())/ OrderConstant.ITEM_LIMIT_TIME_DISCOUNT_RATE);
					orderItemDO.setOrderItemPrice(dicountAmount);
					orderItemDO.setTotalAmount(orderItemDO.getBuyNum()*orderItemDO.getOrderItemPrice());
					orderItemDO.setOrderItemAttributes(OrderUtilMothed.markingAttributes(orderItemDO.getOrderItemAttributes(),key,value));
					list_clone.add(orderItemDO);
				}
				// 订单总价/不包含物流费用
				priceAmount = priceAmount + orderItemDO.getOrderItemPrice()
						* orderItemDO.getBuyNum();

			}
		}
		orderDO.setPriceAmount(priceAmount);
		map.put(orderDO, list_clone);
		return map;
	}

	
	/**
	 * 
	 * Created on 2011-8-31
	 * <p>Discription: 活动效验</p>
	 * @param orderCreation 生成订单相关对象
	 * @return
	 * @author:[贺泳]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result activityCheck(OrderCreationVO orderCreation){
		Result checkResult = new ResultSupport();
		try {
			//特供码
			if(StringUtil.isNotEmpty(orderCreation.getXianGuoCode())){
				/******用户领取的限购码是否可以买该商品Add By ShiXing@2011.09.16 ******/
				Long code;
				try {
					code = ItemXianGouUtil.decode(orderCreation.getXianGuoCode());
				} catch (Exception e) {
					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_CHECKCODE_FAIL);
					return checkResult;
				}
				ItemXianGouUseDO itemXianGouUseDO = itemXianGouManager.getItemXianGouUseDOByCode(code);
				if (itemXianGouUseDO==null) {
					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_CHECKCODE_FAIL);
					return checkResult;
				}
				if (itemXianGouUseDO.getCodeSource()==null || !itemXianGouUseDO.getCodeSource().equals(orderCreation.getXianGuoCode())) {
					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_CHECKCODE_ILLEGAL);
					return checkResult;
				}
				
//				if (!itemXianGouUseDO.getItemId().equals(orderCreation.getItemId()[0])) {
//					//用户领取的限购码是否可以买该商品
//					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_CODE_IS_NOT_APPLY_THIS_ITEM);
//					checkResult.setModel("itemid", itemXianGouUseDO.getItemId());
//					return checkResult;
//				}
				/****** ShiXing End! ******/
				// 参数校验
				ItemXianGouDO xianGouDO = itemXianGouManager.getItemXianGouDOByItemId(orderCreation.getItemId()[0]);
				
				//第一次活动的限购码第二次不能使用
//				int version = xianGouDO.getVersion();
//				long ver = itemXianGouUseDO.getVersion();
//				if (version>ver) {
//					OrderUtilMothed.setResult(false, checkResult, XianGouResultCode.XIANGOU_OUT_OF_DATE);
//					return checkResult;
//				}
				
				//校验数量
				itemXianGouUseDO.setItemId(xianGouDO.getItemId());
                itemXianGouUseDO.setBatchNum(200);
                itemXianGouUseDO.setIsUse(ItemXianGouConstant.XIANGOU_CODE_HAD_USE);
                Long count = itemXianGouManager.getItemXianGouUseCount(itemXianGouUseDO);
                Long total = xianGouDO.getCount();
                Long free = total - count;
                if (free<=0)
                {
                    OrderUtilMothed.setResult(false, checkResult, TradeResultCode.ORDER_CHECK_LASTTIME_BUYNUM_FAIL);
                    return checkResult;
                }
                
				// 取得限购活动的状态：0-已开始 1-已结束
				if (xianGouDO == null || xianGouDO.getStatus() == 1) {
					OrderUtilMothed.setResult(false, checkResult, TradeResultCode.ORDER_CHECK_DISCOUNTCODE_FAIL);
					return checkResult;
				}
				// 活动开始时间
				Date startDate = xianGouDO.getExpiryStart();
				// 活动结束时间
				Date endDate = xianGouDO.getExpiryEnd();

				if (!OrderUtilMothed.compareDate(startDate, endDate)) {
					OrderUtilMothed.setResult(false, checkResult,TradeResultCode.ORDER_CHECK_DISCOUNTCODE_END);
				} else {
					checkResult = orderDiscountManager.compareCodeIsNull(orderCreation.getXianGuoCode());
				}
			}
		} catch (ManagerException e) {
			OrderUtilMothed.setResult(false, checkResult,TradeResultCode.ORDER_CHECK_DISCOUNTCODE_FAIL);
		}
		return checkResult;
	}
	
	
	
	public void setItemXianGouManager(ItemXianGouManager itemXianGouManager) {
		this.itemXianGouManager = itemXianGouManager;
	}

	public void setItemManager(ItemManager itemManager) {
		this.itemManager = itemManager;
	}

	public void setActivityDiscountManager(
			ActivityDiscountManager activityDiscountManager) {
		this.activityDiscountManager = activityDiscountManager;
	}


	public void setOrderDiscountManager(OrderDiscountManager orderDiscountManager) {
		this.orderDiscountManager = orderDiscountManager;
	}

	
}

