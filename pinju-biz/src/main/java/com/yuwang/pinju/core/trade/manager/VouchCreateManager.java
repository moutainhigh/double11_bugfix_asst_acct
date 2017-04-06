package com.yuwang.pinju.core.trade.manager;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

/**
 * @Discription: 担保交易日志操作Manager类
 * @Project: pinju-biz
 * @Package: com.yuwang.pinju.core.trade.manager
 * @Title: VouchCreateManager.java
 * @author: [贺泳]
 * @date 2011-9-15 上午11:19:57
 * @version 1.0
 * @update [日期YYYY-MM-DD] [更改人姓名]
 */
public interface VouchCreateManager {
	/**
	 * @Description: 插入发送日志信息
	 * @author [贺泳]
	 * @date 2011-9-15 上午11:11:28
	 * @version 1.0
	 * @param paySendLogDO: 发送日志对象
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean insertTradePaySendLog(PaySendLogDO paySendLogDO)throws ManagerException;
	
	/**
	 * @Description: 修改发送日志信息
	 * @author [贺泳]
	 * @date 2011-9-15 上午11:11:40
	 * @version 1.0
	 * @param paySendLogDO: 发送日志对象
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean updateTradePaySendLog(PaySendLogDO paySendLogDO)throws ManagerException;
	
	/**
     * @Description: 插入接收日志
     * @author [贺泳]
     * @date 2011-9-15 下午01:16:02
     * @version 1.0
     * @param payBackLogDO: 接收日志对象
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public boolean insertTradePayBackLog(PayBackLogDO payBackLogDO)throws ManagerException;
    
    /**
     * @Description: 修改接收日志
     * @author [贺泳]
     * @date 2011-9-15 下午01:16:35
     * @version 1.0
     * @param payBackLogDO: 接收日志对象
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public boolean updateTradePayBackLog(PayBackLogDO payBackLogDO)throws ManagerException;
    
    /**
     * @Description: 插入支付记录
     * @author [贺泳]
     * @date 2011-9-15 下午04:01:31
     * @version 1.0
     * @param orderPayDO
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public boolean insertTrderOrderPay(VouchPayDO vouchPayDO)throws ManagerException;
    
    /**
     * @Description: 修改订单支付记录
     * @author [贺泳]
     * @date 2011-9-22 下午07:33:13
     * @version 1.0
     * @param orderPayDO
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public boolean updateOrderPay(VouchPayDO vouchPayDO)throws ManagerException;
    
}
