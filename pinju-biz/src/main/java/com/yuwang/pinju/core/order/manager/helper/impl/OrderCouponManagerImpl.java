package com.yuwang.pinju.core.order.manager.helper.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.Constant.OrderConstant;
import com.yuwang.pinju.Constant.OrderttributesEnum;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.coupon.manager.CouponManager;
import com.yuwang.pinju.core.order.manager.helper.OrderCouponManager;
import com.yuwang.pinju.core.util.NumberUtil;
import com.yuwang.pinju.domain.coupon.TradeCouponDO;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;

public class OrderCouponManagerImpl implements OrderCouponManager {
	
	private final String COUPONSIGN = OrderttributesEnum.IS_COUPON.getFeatureName();
	
	private CouponManager couponManager;

	
	public final static String SPLITKEY = OrderConstant.SPLITKEY;
	/**
	 * 标记描述key:value分隔
	 */
	public final static String SPLITATTRIBUTES = OrderConstant.SPLITATTRIBUTES;
	@Override
	public void couponAfterProcess(OrderDO orderDO)
			throws ManagerException {
		if(StringUtil.contains(orderDO.getOrderAttributes(), COUPONSIGN)){
			for (String splitValue : orderDO.getOrderAttributes().split(SPLITKEY)) {
				if(StringUtil.contains(splitValue, COUPONSIGN)){
					String value = StringUtil.split(splitValue, SPLITATTRIBUTES)[1];
					if(NumberUtil.isLong(value)){
						if(!couponManager.updateCouponOrder(Long.parseLong(value), orderDO.getOrderId())){
							throw new ManagerException("优惠券过期或无效!");
						}
						return;
					}
				}	
			}
		}

	}

	@Override
	public Map<OrderDO, List<OrderItemDO>> couponBeforeProcess(
			OrderCreationVO orderCreation, OrderDO orderDO,
			List<OrderItemDO> list) throws ManagerException {
		Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
		if (list.isEmpty()){
			map.put(orderDO, list);
			return map;
		}
		List<OrderItemDO> list_clone = new ArrayList<OrderItemDO>();
		list_clone.addAll(list_clone);
		// 处理优惠券主订单
		if(list!=null && list.size()>0){
			String orderAttributes = orderDO.getOrderAttributes(); 
			if(StringUtil.isEmpty(orderAttributes))
				orderAttributes="";
			// 优惠券
			Long couponId = orderCreation.getCouponId(list.get(0).getItemId());
			if(couponId==null||couponId.compareTo(0l)==0){
				map.put(orderDO, list);
				return map;
			}
			if(couponId != null &&  couponId.compareTo(0l)>0){
				TradeCouponDO tradeCouponDO = couponManager.getTradeCouponDOById(couponId);
				Long orderAmount = 0l;
				//求当前商品所有子订单价格总和
				for(OrderItemDO orderItemDO : list){
					orderAmount += (orderItemDO.getOrderItemPrice()*orderItemDO.getBuyNum());
				}
				//判断优惠券使用金额
				if(orderAmount.compareTo(tradeCouponDO.getUseCondition()) < 0){
					throw new ManagerException("优惠券使用金额无效!");
				}else if(tradeCouponDO.getSellerId().compareTo(orderDO.getSellerId())!=0){
					throw new ManagerException("优惠券使用店铺无效!");
				}
				// 处理优惠券子订单
				Long tempAmount = 0L;
				for(int i = 0;i<list.size();i++){
					OrderItemDO orderItemDO = list.get(i);
					list_clone.remove(orderItemDO);
					//子订单总金额
					Long orderItemTotalAmount = orderItemDO.getOrderItemPrice()*orderItemDO.getBuyNum();
					list_clone.remove(orderItemDO);
					if(i < list.size()-1){
						//计算优惠券比率后子订单优惠金额
						Long orderItemCouponAmount = Math.round(orderItemTotalAmount.doubleValue()/orderAmount.doubleValue()* tradeCouponDO.getCouponMoney());
						tempAmount += orderItemCouponAmount;
						//重置子订单优惠后金额
						orderItemDO.setTotalAmount(orderItemTotalAmount-orderItemCouponAmount);
					//最后一个直接使用所有剩余优惠券金额
					}else if (i == list.size()-1){
						orderItemDO.setTotalAmount(orderItemTotalAmount-(tradeCouponDO.getCouponMoney()-tempAmount));
					}
					list_clone.add(orderItemDO);
				}
				orderAttributes = OrderUtilMothed.markingAttributes(orderAttributes,COUPONSIGN,String.valueOf(couponId));
				orderDO.setOrderAttributes(orderAttributes);
			}
		}
		map.put(orderDO, list_clone);
		return map;
	}

	public void setCouponManager(CouponManager couponManager) {
		this.couponManager = couponManager;
	}

}
