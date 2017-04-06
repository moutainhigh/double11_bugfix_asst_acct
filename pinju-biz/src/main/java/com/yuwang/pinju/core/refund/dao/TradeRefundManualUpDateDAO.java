package com.yuwang.pinju.core.refund.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.refund.TradeRefundManualDO;

/** <p>Description: 对手工单的操作  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-14
 */
public interface TradeRefundManualUpDateDAO {
	
	/**
	 * <p>Description:保存手工单 </p>
	 * @param refundManualDO
	 * @return
	 * @throws DaoException
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-14
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public Long saveRefundManual(TradeRefundManualDO refundManualDO) throws DaoException;
	
	/**
	 * 更新手动退款单的状态 为 1
	 * @param refundManualDO
	 * @return
	 * @throws DaoException
	 */
	public Long updateRefundManual(TradeRefundManualDO refundManualDO) throws DaoException;
}


