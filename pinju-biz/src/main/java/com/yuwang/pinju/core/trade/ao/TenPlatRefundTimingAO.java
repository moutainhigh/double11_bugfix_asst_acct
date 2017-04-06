package com.yuwang.pinju.core.trade.ao;

public interface TenPlatRefundTimingAO {
	
	/**
	 * <p>Description: 手工单定时退款  1. 获取所有的手工, 2. 组装请求参数  3.发起请求 
	 * 4.获取返回结果   5. 处理 订单 和子订单 和退款状态
	 *  6. 处理退款日志状态  7. 处理手工单 退款状态</p>
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-21
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public void platformRefund();
	
	
}
