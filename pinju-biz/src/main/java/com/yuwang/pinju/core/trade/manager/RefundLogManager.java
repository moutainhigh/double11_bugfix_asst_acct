package com.yuwang.pinju.core.trade.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.trade.refund.RefundLogDO;

/**
 * <p>Discription: 退款日志表  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-15
 */
public interface RefundLogManager {
	
	 /**
	  * <p>Discription:插入退款日志表 </p>
	  * @param paySendLogDO
	  * @return
	  * @throws ManagerException
	  * @author:[MaYuanChao]
	  * @version 1.0
	  * @create:2011-9-15
	  * @update:[日期YYYY-MM-DD] [更改人姓名]
	  */
	public boolean insertTradeRefundLog(RefundLogDO refundLogDO)throws ManagerException;
	
	/**
	 * <p>Discription: 更新退款日志表</p>
	 * @param tenpayRefundLogDO
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-15
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean updateTradeRefundLog(RefundLogDO refundLogDO)throws ManagerException;
	
	/**
	 * <p>Discription: 根据订单号获取退款日志</p>
	 * @param OrderId
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-15
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public RefundLogDO queryRefundLogById(Long logId) throws ManagerException;

	/**
	 * <p>Discription: 根据退款ID 获取退款日志</p>
	 * @param refundId
	 * @param cmdno
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-16
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public RefundLogDO queryRefundLogByRefundAndCmdnoId(String refundId,Integer cmdno)throws ManagerException;
	
	/**
	 * <p>Discription:  根据财付通交易ID，内部支付ID，业务号ID 查询退款日志</p></p>
	 * @param transactionId
	 * @param payOrderId
	 * @param cmdno
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-16
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public RefundLogDO queryRefundLog(String transactionId, String payOrderId,Integer cmdno)throws ManagerException;
}


