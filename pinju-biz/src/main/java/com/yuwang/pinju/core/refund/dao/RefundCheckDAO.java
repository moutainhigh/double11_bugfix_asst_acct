package com.yuwang.pinju.core.refund.dao;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.domain.refund.RefundDO;

public interface RefundCheckDAO {
	/**
	 * 查询订单中处于退款中状态的子订单数量
	 * <p>除了订单状态为“协议达成”，“退款成功”，“退款关闭”，其他状态均为退款中</p>
	 * 
	 * @param orderId 订单id
	 * @return  退款中状态的子订单数量
	 */
	public Integer getRefundingCount(Long orderId) throws DaoException;
}
