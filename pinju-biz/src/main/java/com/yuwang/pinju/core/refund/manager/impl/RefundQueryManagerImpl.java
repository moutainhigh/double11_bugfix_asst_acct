package com.yuwang.pinju.core.refund.manager.impl;

import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.refund.dao.RefundQueryDAO;
import com.yuwang.pinju.core.refund.manager.RefundQueryManager;
import com.yuwang.pinju.domain.refund.RefundDO;

/** <p>Discription: 	  </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-17
 */
public class RefundQueryManagerImpl implements RefundQueryManager {
	private RefundQueryDAO refundQueryDAO;
	@Override
	public List<RefundDO> queryRefundByBuyerNoReply(Map<String, Object> map)throws ManagerException {
		try {
			return refundQueryDAO.queryRefundByBuyerNoReply(map);
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	/**
	 * <p> Discription: 获取所有买家申请，卖家没有响应，5天超时的退款</p>
	 * @return
	 * @throws ManagerException
	 * @author:[shihongbo]
	 * @version 1.0
	 * @create:2011-10-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> querySellerNoReplyRefund() throws ManagerException{
		try {
			return refundQueryDAO.querySellerNoReplyRefund();
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	/**
	 * <p> Discription: 获取卖家不确认收货 -- 平邮，30天超时的退款</p>
	 * @return
	 * @throws ManagerException
	 * @author:[shihongbo]
	 * @version 1.0
	 * @create:2011-10-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> sellerNotConfirmPost() throws ManagerException{
		try {
			return refundQueryDAO.sellerNotConfirmPost();
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	/**
	 * <p> Discription: 获取卖家不确认收货 -- 快递，10天超时的退款</p>
	 * @return
	 * @throws ManagerException
	 * @author:[shihongbo]
	 * @version 1.0
	 * @create:2011-10-17
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	public List<RefundDO> sellerNotConfirmExpress() throws ManagerException{
		try {
			return refundQueryDAO.sellerNotConfirmExpress();
		} catch (DaoException e) {
			throw new ManagerException(e);
		}
	}
	
	public void setRefundQueryDAO(RefundQueryDAO refundQueryDAO) {
		this.refundQueryDAO = refundQueryDAO;
	}
}


