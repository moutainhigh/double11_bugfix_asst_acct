package com.yuwang.pinju.core.order.manager.check;

import com.yuwang.pinju.core.common.ManagerException;

/**
 * @see 订单状态管理
 * @author 杜成
 * @date   2011-6-24
 * @version 1.0
 */
public interface OrderStateManager {
	
	/**
	 * @see 修改订单状态
	 * 条件判断
	 * 1. 要修改的订单状态从属于 (1：买家,2：卖家)
	 * 2. orderId 是否从属于 optMemberId  
	 * 3. editOrderState 与orderId对应订单的当前状态的从属关系
	 * 以上条件成立更新订单状态;插入订单日志
	 * @param optMemberId 操作会员编号 
	 * @param orderId	订单编号
	 * @param editOrderState 订单要修改的状态
	 * @return 返回操作是否成功
	 * @throws ManagerException editOrderState 从属关系不对抛出异常;数据库操作异常
	 */
	boolean checkOrderState(long optMemberId,long orderId,int editOrderState)throws ManagerException;
	
}
