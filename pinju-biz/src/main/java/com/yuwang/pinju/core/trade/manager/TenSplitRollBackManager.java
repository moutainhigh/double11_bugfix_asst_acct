package com.yuwang.pinju.core.trade.manager;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.domain.trade.TenSplitRollBackDO;
import com.yuwang.pinju.domain.trade.VouchPayDO;

public interface TenSplitRollBackManager {
	/**
	 * 更新退款表状态
	 * 更新子订单退款状态
	 * 插入订单日志表
	 * 插入工单表
	 * @return
	 * @throws ManagerException
	 */
	public boolean updateSplitRollbackStatus(VouchPayDO orderPay, TenSplitRollBackDO tenSplitRollbackDO, String CMDNO) throws ManagerException;
}
