package com.yuwang.pinju.core.refund.dao.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseDAO;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.refund.dao.RefundQueryDAO;
import com.yuwang.pinju.domain.refund.RefundDO;

/** <p>Discription:退款查询， 用于超时使用  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-17
 */
public class RefundQueryDAOImpl extends BaseDAO implements RefundQueryDAO {

	private static final String TRADE_REFUND_QUERY_NAMESPANCE = "trade_refund_query."; 
	private static final String QUERY_REFUND_BYBUYERNOREPLY ="queryTradeRefundByBuyerNoReply";
	@SuppressWarnings("unchecked")
	@Override
	public List<RefundDO> queryRefundByBuyerNoReply(Map<String, Object> map)
			throws DaoException {
		// TODO Auto-generated method stub
		return (List<RefundDO>)super.executeQueryForList(TRADE_REFUND_QUERY_NAMESPANCE.concat(QUERY_REFUND_BYBUYERNOREPLY), map);
	}

	/**
	 * <p> Discription: 获取所有买家申请，卖家没有响应，5天超时的退款</p>
	 * @return
	 * @throws DaoException
	 * @author:[shihongbo]
	 * @version 1.0
	 * @create:2011-10-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> querySellerNoReplyRefund() throws DaoException{
		return (List<RefundDO>)super.executeQueryForList(TRADE_REFUND_QUERY_NAMESPANCE.concat("querySellerNoReplyRefund"), null);
	}
	
	/**
	 * <p> Discription: 获取卖家不确认收货 -- 平邮，30天超时的退款</p>
	 * @return
	 * @throws DaoException
	 * @author:[shihongbo]
	 */
	public List<RefundDO> sellerNotConfirmPost() throws DaoException{
		return (List<RefundDO>)super.executeQueryForList(TRADE_REFUND_QUERY_NAMESPANCE.concat("sellerNotConfirmPost"), null);
	}
	
	/**
	 * <p> Discription: 获取卖家不确认收货 -- 快递，10天超时的退款</p>
	 * @return
	 * @throws DaoException
	 * @author:[shihongbo]
	 */
	public List<RefundDO> sellerNotConfirmExpress() throws DaoException{
		return (List<RefundDO>)super.executeQueryForList(TRADE_REFUND_QUERY_NAMESPANCE.concat("sellerNotConfirmExpress"), null);
	}
}


