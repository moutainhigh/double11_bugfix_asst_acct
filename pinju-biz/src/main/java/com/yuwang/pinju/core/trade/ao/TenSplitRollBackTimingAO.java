package com.yuwang.pinju.core.trade.ao;

public interface TenSplitRollBackTimingAO {
	/**
	 * 查询未处理的退款工单记录
	 * 向财付通发起查询请求，查询该笔分账回退是否完成
	 * 若状态为完成则将工单记录更新为"已关闭"，且将此条记录插入平台退款工单表中
	 */
	public void splitRollbackRefund();
}
