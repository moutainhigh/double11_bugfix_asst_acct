package com.yuwang.pinju.core.refund.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuwang.pinju.core.common.BaseManager;
import com.yuwang.pinju.core.common.DaoException;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.refund.dao.RefundDAO;
import com.yuwang.pinju.core.refund.dao.RefundLogisticsDAO;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.refund.TradeRefundLogisticsDO;

public class RefundManagerImpl extends BaseManager implements RefundManager{


	private RefundDAO refundDAO;
	private RefundLogisticsDAO refundLogisticsDAO;
	/**
	 * <p>保存退款信息</p>
	 * @param refund
	 * @return 更新或者保存的信息id
	 * @throws DaoException
	 * @author shihongbo
	 */
	public Long persist(RefundDO refund) throws ManagerException{
		try {
			return refundDAO.persist(refund);
		}catch (DaoException e) {

			throw new ManagerException("Event=[RefundManager#persist]保存退款信息", e);
		}
	}
	
	/**
	 * 加载退款申请信息
	 * @param refundId 退款申请信息id
	 */
	public RefundDO loadRefund(Long refundId)throws ManagerException{
		try {
			return refundDAO.loadRefund(refundId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#loadRefund]加载退款信息",  e);
		}

	}
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param orderItemId 子订单id
	 */
	public RefundDO loadRefundByOrderItem(Long orderItemId)throws ManagerException{
		try {
			return refundDAO.loadRefundByOrderItem(orderItemId);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#loadRefundByOrderItem]加载退款信息",  e);
		}
	}
	
	/**
	 * 加载退款申请信息
	 * 
	 * @param map 退款申请查询条件
	 */
	public List<RefundDO> loadRefund(Map<String, Object> map)throws ManagerException{
		try {
			return refundDAO.loadRefund(map);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#loadRefund]加载退款信息",  e);
		}

	}
	
	/**
	 * 加载退款申请信息 记录数
	 * @param map 退款申请查询条件
	 */
	public int loadRefundCount(Map<String, Object> map) throws ManagerException{
		try {
			return refundDAO.loadRefundCount(map);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#loadRefundCount]加载退款信息",  e);
		}
	}
	
	/**
	 * 更新退款申请信息
	 * 
	 * @param refundDO 退款申请信息
	 */
	public void updateRefundApplyInfo(RefundDO refundDO)throws ManagerException{
		try {
			refundDAO.updateRefundApplyInfo(refundDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#updateRefundApplyInfo]更新退款申请信息",  e);
		}

	}
	
	/**
	 * <p>保存退货物流信息</p>
	 *
	 * @param tradeRefundLogisticsDO
	 * @return
	 * @throws ManagerException
	 *
	 * @author shihongbo
	 */
	public Long persistRefundLogistics(TradeRefundLogisticsDO tradeRefundLogisticsDO) throws ManagerException{
		try {
			return refundLogisticsDAO.persist(tradeRefundLogisticsDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#persistRefundLogistics]保存退货物流信息",  e);
		}
	}
	
	public void setRefundDAO(RefundDAO refundDAO) {
		this.refundDAO = refundDAO;
	}

	public void setRefundLogisticsDAO(RefundLogisticsDAO refundLogisticsDAO) {
		this.refundLogisticsDAO = refundLogisticsDAO;
	}

	/**
	 * 卖家家确认收货
	 * 
	 * @param refundId 退款id
	 */
	public void sellerConfirmReceiveGoods(Long refundId)throws ManagerException{
		try {
			Date now = new Date();
			RefundDO refundDO = refundDAO.loadRefund(refundId);
			refundDO.setGmtModified(now);
			refundDO.setStateModified(now);
			refundDO.setRefundState(RefundDO.STATUS_REFUND_PROTOCAL_AGREE);
			refundDAO.updateRefundApplyInfo(refundDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#confirmReceiveGoods]卖家家确认收货",  e);
		}
	}
	
	/**
	 * 卖家拒绝退款申请
	 * 
	 * @param refundId 退款id
	 */
	public void sellerRejectRefundApply(Long refundId)throws ManagerException{
		try {
			Date now = new Date();
			RefundDO refundDO = refundDAO.loadRefund(refundId);
			refundDO.setGmtModified(now);
			refundDO.setReplyDate(now);
			refundDO.setStateModified(now);
			refundDO.setRefundState(RefundDO.STATUS_SELLER_REFUSE_BUYER);
			refundDAO.updateRefundApplyInfo(refundDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#sellerRejectRefundApply]卖家拒绝退款申请",  e);
		}
	}
	
	
	/**
	 * 卖家同意退款申请
	 * 
	 * @param refundId 退款id
	 */
	public void sellerAgreeRefundApply(Long refundId)throws ManagerException{
		try {
			Date now = new Date();
			RefundDO refundDO = refundDAO.loadRefund(refundId);
			refundDO.setGmtModified(now);
			refundDO.setStateModified(now);
			refundDO.setReplyDate(now);
			
			//需要退货	
			if(refundDO.getNeedSalesReturn().compareTo(RefundDO.NEED_SALES_RETURN_YES) == 0){
				refundDO.setRefundState(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS);

			//不需要退货	
			}else {
				refundDO.setRefundState(RefundDO.STATUS_REFUND_PROTOCAL_AGREE);
			}
			
			refundDAO.updateRefundApplyInfo(refundDO);
			
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#sellerRejectRefundApply]卖家同意退款申请",  e);
		}
	}
	
    /**
     * 根据 订单编号 查询退款总额
     */
    public Long getRefundFee(Long orderId)throws ManagerException{
    	List<RefundDO> refundList = queryRefundByOrderId(orderId);
    	
    	Long sum = 0L;
    	
    	for(RefundDO refund:refundList){
    		sum += refund.getApplySum();
    	}

    	return sum;
    }
    
	/**
	 * 卖家申请客服介入
	 * 
	 * @param refundId 退款id
	 */
	public void sellerApplyCustProcess(Long refundId)throws ManagerException{
		try {
			Date now = new Date();
			RefundDO refundDO = refundDAO.loadRefund(refundId);
			refundDO.setGmtModified(now);
			refundDO.setStateModified(now);
			refundDO.setRefundState(RefundDO.STATUS_CS_PROCESS);
			refundDO.setIsCustProcessed(RefundDO.CUST_PROCESS_YES);
			refundDAO.updateRefundApplyInfo(refundDO);
			
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#sellerRejectRefundApply]卖家申请客服介入",  e);
		}
	}
	
	/**
	 * 买家申请客服介入
	 * 
	 * @param refundId 退款id
	 */
	public void custProcess(Long refundId)throws ManagerException{
		try {
			RefundDO refundDO = refundDAO.loadRefund(refundId);
			refundDO.setRefundState(RefundDO.STATUS_CS_PROCESS);
			Date now = new Date();
			refundDO.setGmtModified(now);
			refundDO.setStateModified(now);
			refundDO.setIsCustProcessed(RefundDO.CUST_PROCESS_YES);
			refundDAO.updateRefundApplyInfo(refundDO);
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#buyerDeliverGoods]买家确认发货",  e);
		}
	}
	
	/**
	 * 买家确认发货
	 * 
	 * @param refundId 退款id
	 */
	public boolean buyerDeliverGoods(Long refundId,Long logistcsId)throws ManagerException{
		try {
			RefundDO refundDO = refundDAO.loadRefund(refundId);
			if(refundDO == null) return false;
			refundDO.setRefundLogisticsId(logistcsId);
			refundDO.setRefundState(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS);
			return refundDAO.updateRefundApplyInfo(refundDO) > 0;
		}catch (DaoException e) {
			throw new ManagerException("Event=[RefundManager#buyerDeliverGoods]买家确认发货",  e);
		}
	}
	

	/*********
	 * 关闭退款
	 *@author MaYuanChao
	 *@throws ManagerException
	 *@Date   2011-10-21
	 */
	public boolean cancelRefundApply(final Long refundId) throws ManagerException {
		try {
			RefundDO refundDO = refundDAO.loadRefund(refundId);
			if (refundDO != null) {
				refundDO.setRefundState(RefundDO.STATUS_CLOSED);
				return refundDAO.updateRefundApplyInfo(refundDO) > 0;
			}
		} catch (DaoException e) {
			throw new ManagerException("execute RefundManagerImpl.cancelRefundApply() error:",e);
			
		}
		return false;
	}
	
	/**
	 * 根据主订单号 获取所有的退款记录
	 */
	public List<RefundDO> queryRefundByOrderId(Long orderId)
			throws ManagerException {
		try {
			return refundDAO.queryRefundByOrderId(orderId);
		} catch (DaoException e) {
			// TODO: handle exception
			throw new ManagerException("execute RefundManager.queryRefundByOrderId () error:",  e);
		}
	}
}
