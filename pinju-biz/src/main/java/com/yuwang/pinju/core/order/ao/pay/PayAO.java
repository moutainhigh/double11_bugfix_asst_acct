package com.yuwang.pinju.core.order.ao.pay;


import com.yuwang.pinju.core.common.Result;



/**
 * 买家支付
 * @author 杜成
 * @date   2011-6-2
 * @version 1.0
 */
public interface PayAO {
	
	/**
	 * 
	 * Created on 2011-9-20
	 * <p>Discription: 封装担保交易支付参数</p>
	 * @param orderId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Result getPayParams(Long[] orderId,String buyerIp);
	
	
	/**
	 * @see   临时使用 下个版本作废,其它人士请不要使用
	 * @param orderId
	 * @param orderState
	 * @param buyerId
	 */
	public Result affirmPay(Long[] orderId , int orderState, Long buyerId);
	
	/**
	 * @Description: ajax异步校验用户登录密码，用于买家在确认收货页面对输入的密码进行校验
	 * @author [heyong]
	 * @date 2011-10-9 下午08:03:05
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public Result checkPassWord(String loginName, String passWord, String tid);
}
