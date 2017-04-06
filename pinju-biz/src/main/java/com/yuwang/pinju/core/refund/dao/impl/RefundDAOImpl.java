package com.yuwang.pinju.core.refund.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.refund.dao.RefundDAO;
import com.yuwang.pinju.domain.refund.RefundDO;

/**
 * 退款申请
 * @author shihongbo
 * @date   2011-6-24
 * @version 1.0
 */
public class RefundDAOImpl extends BaseDAO implements RefundDAO{
	
	private final String REFUND_NAME_SPACE="trade_refund.";
	
	/**
	 * <p>保存退款信息</p>
	 *
	 * @param refund
	 * @return 更新或者保存的信息id
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	public Long persist(RefundDO refund) throws DaoException{
		if(refund.getId() == null){
			return (Long)super.executeInsert(REFUND_NAME_SPACE + "insertTradeRefund", refund);	
		}else{
			super.executeUpdate(REFUND_NAME_SPACE + "updateTradeRefund", refund);
			return refund.getId();
		}
		
	}
	
	/**
	 * <p>加载退款信息</p>
	 *
	 * @param refundId
	 * @return
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	public RefundDO loadRefund(Long refundId) throws DaoException{
		return (RefundDO)super.executeQueryForObject(REFUND_NAME_SPACE + "selectTradeRefundByid", refundId);
	}
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param orderItemId 子订单id
	 * @return
	 * @throws DaoException
	 *
	 * @author shihongbo
	 */
	public RefundDO loadRefundByOrderItem(Long orderItemId) throws DaoException{
		return (RefundDO)super.executeQueryForObject(REFUND_NAME_SPACE + "selectTradeRefundByOrderItemId", orderItemId);
	}
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param map 退款申请查询条件
	 */
	public List<RefundDO> loadRefund(Map<String, Object> map) throws DaoException{
		return (List<RefundDO>)super.executeQueryForList(REFUND_NAME_SPACE + "selectRefundList", map);
	}
	
	/**
	 * 加载退款申请信息 记录数
	 * @param map 退款申请查询条件
	 */
	public int loadRefundCount(Map<String, Object> map) throws DaoException{
		return (Integer)super.executeQueryForObject(REFUND_NAME_SPACE + "selectRefundListCount", map);
	}
	
	/**
	 * 更新退款申请信息
	 * 
	 * @param refundDO 退款申请信息
	 */
	public int updateRefundApplyInfo(RefundDO refundDO) throws DaoException{
		return super.executeUpdate(REFUND_NAME_SPACE + "updateTradeRefund", refundDO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RefundDO> queryRefundByOrderId(Long orderId)
			throws DaoException {
		// TODO Auto-generated method stub
		return (List<RefundDO>)super.executeQueryForList(REFUND_NAME_SPACE + "selectTradeRefundByOrderId", orderId);
	}

	@Override
	public int updateRefundDO(RefundDO refundDO) throws DaoException {
		// TODO Auto-generated method stub
		return  super.executeUpdate(REFUND_NAME_SPACE + "updateTradeRefund", refundDO);
	}
}
