package com.yuwang.pinju.core.trade.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;

/**
 * @see
 * <p>Discription: 
 * 	 
 * </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-15
 */
public interface RefundLogDAO {


	 /**
	  * <p>Discription:插入退款日志表 </p>
	  * @param paySendLogDO
	  * @return
	  * @throws DaoException
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-9-15
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	public Long insertTradeRefundLog(RefundLogDO refundLogDO)throws DaoException;
	
	/**
	 * <p>Discription: 更新退款日志表</p>
	 * @param tenpayRefundLogDO
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-15
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long updateTradeRefundLog(RefundLogDO refundLogDO)throws DaoException;
	
	/**
	 * <p>Discription: </p>
	 * @param id
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-16
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public RefundLogDO queryRefundLogById(Long id) throws DaoException;
	
	/**
	 * <p>Discription: 根据退款Id 查询</p>
	 * @param refundId
	 * @param cmdno
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-16
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public RefundLogDO queryRefundLogByRefundAndCmdnoId(String refundId , Integer cmdno) throws DaoException;
	
	/**
	 * <p>Discription: 根据财付通交易ID，内部支付ID，业务号ID 查询退款日志</p>
	 * @param transactionId
	 * @param payOrderId
	 * @param cmdno
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-16
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public RefundLogDO queryRefundLog(String transactionId ,String payOrderId, Integer cmdno) throws DaoException;
	
	/**
	 * <p>Discription: 根据OrderId 和业务号获取 退款日志</p>
	 * @param orderId
	 * @param cmdno
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-23
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public RefundLogDO queryRefundLogByOrderId(Long orderId, Integer cmdno) throws DaoException;
}


