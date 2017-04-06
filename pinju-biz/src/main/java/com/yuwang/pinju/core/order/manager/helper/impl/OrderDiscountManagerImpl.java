package com.yuwang.pinju.core.order.manager.helper.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.yuwang.pinju.Constant.ItemXianGouConstant;
import com.yuwang.pinju.Constant.OrderItemAttributesEnum;
import com.yuwang.pinju.common.ItemXianGouUtil;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupport;
import com.yuwang.pinju.core.common.resultcode.TradeResultCode;
import com.yuwang.pinju.core.order.manager.helper.OrderDiscountManager;
import com.yuwang.pinju.core.sales.manager.ItemXianGouManager;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;
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
public class OrderDiscountManagerImpl implements OrderDiscountManager {
	/**
	 * 特供码管理
	 */
	private ItemXianGouManager itemXianGouManager;
	
	//特供
	private final String DISCOUNTCODE = OrderItemAttributesEnum.DISCOUNT_CODE.getFeatureName();
	
	@Override
	public Map<OrderDO, List<OrderItemDO>> discountBeforeProcess(
			OrderCreationVO orderCreation, OrderDO orderDO,
			List<OrderItemDO> list) throws ManagerException {
		Map<OrderDO, List<OrderItemDO>> map = new HashMap<OrderDO, List<OrderItemDO>>();
		final boolean isDiscountCode = orderCreation.getDiscountCode()==null||StringUtil.isEmpty(orderCreation.getDiscountCode()[0]);
		if (list.isEmpty()||isDiscountCode){
			map.put(orderDO, list);
			return map;
		}
		
		List<OrderItemDO> list_clone = list;
		// 处理特供
		for (OrderItemDO orderItemDO : list) {
			String orderItemAttributes = orderItemDO.getOrderItemAttributes();
			// 特供码
			String discountCode = orderCreation.getDiscountCode()[0];
			
			//调用特供接口判断特供码是否有效
			if(compareCodeIsNull(discountCode).isSuccess()){
				orderItemAttributes = OrderUtilMothed.markingAttributes(orderItemAttributes,DISCOUNTCODE,discountCode.toString());
				list_clone.remove(orderItemDO);
				orderItemDO.setOrderItemAttributes(orderItemAttributes);
				list_clone.add(orderItemDO);
			}
		}
		map.put(orderDO, list_clone);
		return map;

	}
	/**
	 * 
	 * Created on 2011-09-02
	 * <p>Discription: 判断特供码是否被使用</p>
	 * @param discountCode：限购码
	 * @return
	 * @author:[贺泳]
	 * @version 1.0
	 * @throws ManagerException 
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@Override
	public Result compareCodeIsNull(String discountCode){
		Result checkResult = new ResultSupport();
		try {
			ItemXianGouUseDO xianGouUseDO = itemXianGouManager.getItemXianGouUseDOByCode(ItemXianGouUtil.decode(discountCode));
			if(xianGouUseDO == null){
				OrderUtilMothed.setResult(false, checkResult,TradeResultCode.ORDER_CHECK_DISCOUNTCODE_FAIL);
				return checkResult;
			}
			//0-未使用,1-已使用
			if(xianGouUseDO.getIsUse() == 1){
				OrderUtilMothed.setResult(false, checkResult,TradeResultCode.ORDER_CHECK_DISCOUNTCODE_NULLITY);
				return checkResult;
			}
		} catch (Exception e) {
			OrderUtilMothed.setResult(false, checkResult,TradeResultCode.ORDER_CHECK_DISCOUNTCODE_FAIL);
		}
		return checkResult;
	}

	@Override
	public void discountAfterProcess(String discountCode,
			OrderItemDO orderItemDO) throws ManagerException {
		ItemXianGouUseDO xianGouUseDO = new ItemXianGouUseDO();
		Long discount = ItemXianGouUtil.decode(discountCode);
		xianGouUseDO.setCode(discount);   						//限购码
		xianGouUseDO.setBuyerId(orderItemDO.getBuyerId());      //买家ID
		xianGouUseDO.setBuyerNick(orderItemDO.getBuyerNick());	//买家昵称
		xianGouUseDO.setOrderId(orderItemDO.getOrderId());      //订单ID
		xianGouUseDO.setItemId(orderItemDO.getItemId());        //商品ID
		xianGouUseDO.setItemTitle(orderItemDO.getItemTitle());  
		xianGouUseDO.setIsUse(ItemXianGouConstant.XIANGOU_CODE_HAD_USE);  //限购码状态：0:为使用  1:已使用
		xianGouUseDO.setGmtModified(new Date());                //记录修改时间
		itemXianGouManager.updateItemXianGouUse(xianGouUseDO);
	}
	
	

	public void setItemXianGouManager(ItemXianGouManager itemXianGouManager) {
		this.itemXianGouManager = itemXianGouManager;
	}
}

