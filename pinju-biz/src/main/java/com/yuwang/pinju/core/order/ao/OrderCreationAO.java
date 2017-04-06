package com.yuwang.pinju.core.order.ao;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.order.query.OrderCreationVO;

/**
 * Created on 2011-6-10
 * <p>买家订单管理</p>
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface OrderCreationAO {
	
	
	
	/**
	 * Created on 2011-6-10
	 * @see
	 * 生成订单(前期无客户优惠信息)
	 * @param buyerId 买家id
	 * @param buyIp 买家ip
	 * @return Result 
	 * 状态1
	 * ResultCode  : OrderConstant.CREATION_ORDER_3
	 * Success :true
	 * key:orderIdList  订单id集合
	 * 状态2
	 * Success = false
	 * ResultCode  : OrderConstant.CREATION_ORDER_2
	 * key:errorItemList ：错误商品id 集合
	 * key:itemList ：商品最新信息集合
	 * key:sellerList ：商品卖家集合
	 * 状态3
	 * Success ：false
	 * ResultCode :OrderConstant.CREATION_ORDER_1
	 * 其他错误
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result creationOrder(OrderCreationVO orderCreation,Long buyerId,String buyIp);
	
	
	/**
	 * Created on 2011-6-10
	 * @see 生成订单结算页面
	 * @param orderCreation 
	 * @param buyerId 买家会员编号
	 * @return Result
	 * 状态1
	 * ResultCode  : OrderConstant.CREATION_ORDER_1
	 * Success :false
	 * 状态2
	 * Success = false
	 * ResultCode  : OrderConstant.CREATION_ORDER_2
	 * key:errorItemList ：错误商品id 集合
	 * key:itemList ：商品最新信息集合
	 * key:sellerList ：商品卖家集合
	 * 状态3
	 * Success ：true
	 * ResultCode :OrderConstant.CREATION_ORDER_3
	 * key:itemList  商品最新信息集合
	 * key:sellerList ：商品卖家集合
	 * key:sumPrice ：商品购买总价
	 * key:deliveriesList 买家收货地址集合
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result creationSettling(OrderCreationVO orderCreation);

	
	/**
	 * Created on 2011-6-10
	 * @see 生成立即结算页面
	 * @param orderCreation 
	 * @param buyerId 买家会员编号
	 * @return Result
	 * 状态1
	 * ResultCode  : OrderConstant.CREATION_ORDER_1
	 * Success :false
	 * 状态2
	 * Success = false
	 * ResultCode  : OrderConstant.CREATION_ORDER_2
	 * key:errorItemList ：错误商品id 集合
	 * key:itemList ：商品最新信息集合
	 * key:sellerList ：商品卖家集合
	 * 状态3
	 * Success ：true
	 * ResultCode :OrderConstant.CREATION_ORDER_3
	 * key:itemList  商品最新信息集合
	 * key:sellerList ：商品卖家集合
	 * key:sumPrice ：商品购买总价
	 * key:deliveriesList 买家收货地址集合
	 * 状态3 立即购买或团购
	 * Success ：true
	 * ResultCode :OrderConstant.CREATION_ORDER_3
	 * key:itemList  商品最新信息集合
	 * key:sellerList ：商品卖家集合
	 * key:sumPrice ：商品购买总价
	 * key:deliveriesList 买家收货地址集合
	 * key:lastTimeNum
	 * key:buyNum
	 * key:dicountRate
	 * key:ratePrice
	 * 
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result creationLastTimeSettling(OrderCreationVO orderCreation);
	
	

	/**
	 * @Description: Ajax异步校验验证码输入是否正确
	 * @author [贺泳]
	 * @date 2011-9-14 上午09:35:42
	 * @version 1.0
	 * @param sid：验证码
	 * @param validateCode：用户输入的验证码
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result ajaxCheckCode(String sid,String validateCode);
	
	
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
	public Result activityCheck(OrderCreationVO orderCreation);

}
