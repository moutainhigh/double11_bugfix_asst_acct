package com.yuwang.pinju.core.trade.dao;

import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.order.pay.PayBackLogDO;
import com.yuwang.pinju.domain.order.pay.PaySendLogDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;


/**
 * Created on 2011-9-15
 * @see
 * <p>Discription: 担保交易日志操作</p>
 * @return
 * @author:[杜成]
 * @version 1.0
 * @update:[日期YYYY-MM-DD] [更改人姓名]
 */
public interface VouchCreateDAO {
	/**
	 * @Description: 插入发送日志信息
	 * @author [贺泳]
	 * @date 2011-9-15 上午11:11:28
	 * @version 1.0
	 * @param paySendLogDO: 发送日志对象
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long insertTradePaySendLog(PaySendLogDO paySendLogDO)throws DaoException;
	
	/**
	 * @Description: 修改发送日志信息
	 * @author [贺泳]
	 * @date 2011-9-15 上午11:11:40
	 * @version 1.0
	 * @param paySendLogDO: 发送日志对象
	 * @return
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long updateTradePaySendLog(PaySendLogDO paySendLogDO)throws DaoException;

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
    public Long insertTradePayBackLog(PayBackLogDO payBackLogDO)throws DaoException;
   
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
    public Long updateTradePayBackLog(PayBackLogDO payBackLogDO)throws DaoException;
    
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
    public Long insertTrderOrderPay(VouchPayDO orderPayDO)throws DaoException;
    
    /**
     * @Description: 根据内部 支付编号 查询支付记录
     * @author [贺泳]
     * @date 2011-9-15 下午04:09:47
     * @version 1.0
     * @param id
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public VouchPayDO selectOrderPayById(Long id)throws DaoException;
    
    /**
     * @Description: 根据内部 订单编号 查询支付记录
     * @author [贺泳]
     * @date 2011-9-15 下午04:09:47
     * @version 1.0
     * @param id
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public VouchPayDO selectOrderPayByOrderId(Long OrderId)throws DaoException;
    
    /**
     * @Description:查询订单支付记录
     * @author [曹晓]
     * @date 2011-9-22 下午07:32:00
     * @version 1.0
     * @param orderPayDO
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public VouchPayDO selectOrderPay(VouchPayDO orderPayDO) throws DaoException;
    
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
    public Long updateOrderPay(VouchPayDO orderPayDO)throws DaoException;

    /**
     * @Description:查询订单支付记录
     * @author [lixin]
     * @date 2011-10-08 
     * @version 1.0
     * @param map
     * @return
     * @throws DaoException
     * @update:[日期YYYY-MM-DD] [更改人姓名]
     */
    public VouchPayDO selectOrderPay(Map map) throws DaoException;
}


