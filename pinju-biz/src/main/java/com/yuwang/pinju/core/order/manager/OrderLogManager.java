package com.yuwang.pinju.core.order.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.OrderLogDO;

/**
 * <p>订单、支付相关日志处理</p>
 * @author 杜成
 * @date   2011-6-2
 * @version 1.0
 * @本地日志、盛大日志接口(待确定)
 */
public interface OrderLogManager {
	
	/**
	 * @see
	 * <p>生成发送支付日志</p>
	 * @return 返回生成日志主键
	 * @TODO 参数待定
	 * @author 杜成
	 */
	public long createSendPayLog();
	
	/**
	 * @see
	 * <p>生成接收支付日志</p>
	 * @return 返回生成日志主键
	 * @TODO 参数待定
	 * @author 杜成 
	 */
	public long createAcceptPayLog();
	/**
	 * @see
	 * <p>生成订单日志</p>
	 * @return 返回生成日志主键
	 * @TODO 参数待定
	 * @author 杜成 
	 */
	public long createOrderLog();

	/**
	 * 
	 * Created on 2011-8-19
	 * <p>Discription:添加卖家对未付款订单修改价格操作的日志记录 </p>
	 * @return
	 * @author:[李鑫]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public long createOrderLog(OrderLogDO orderLogDO)throws ManagerException ;
	
}
