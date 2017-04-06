package com.yuwang.pinju.core.order.ao;


/**
 * Created on 2011-7-22
 * @see
 * <p>Discription: 
 * 	  订单相关定时任务
 * </p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface HessianOrderAO {

	/**
	 * 
	 * Created on 2011-7-22
	 * <p>Discription: 定时任务订单自动确认</p>
	 * @param orderId
	 * @return
	 * @author:[杜成]
	 * @version 1.0
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	void confirmReceipt();
	
}

