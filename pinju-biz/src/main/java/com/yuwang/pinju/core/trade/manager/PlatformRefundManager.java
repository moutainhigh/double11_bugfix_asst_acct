package com.yuwang.pinju.core.trade.manager;

import java.util.List;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.domain.order.OrderDO;
import com.yuwang.pinju.domain.order.OrderItemDO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;

/**
 * @see
 * <p>Discription: 
 * 	 
 * </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-9-15
 */
public interface PlatformRefundManager {

	/**
	 * <p>Discription: 退款成功的业务处理</p>
	 * @param orderId
	 * @param operateIp
	 * @param isAll
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-23
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean updateAllRefundNotifyForSuccess(Long orderId,boolean isAll,String successReason)throws ManagerException;
	/**
	 * <p>Discription: 退款失败插入工单业务处理</p>
	 * @param orderId
	 * @param operateIp
	 * @param manualDO
	 * @return
	 * @throws ManagerException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-9-23
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public boolean updateAllRefundNotifyForManual(Long orderId,TradeRefundManualDO manualDO,boolean isAll) throws ManagerException;
	
}


