package com.yuwang.pinju.core.refund.ao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.order.manager.OrderUpDateManager;
import com.yuwang.pinju.core.refund.ao.RefundManageAO;
import com.yuwang.pinju.core.refund.ao.RefundTimingAO;
import com.yuwang.pinju.core.refund.manager.RefundCheckManager;
import com.yuwang.pinju.core.refund.manager.RefundManager;
import com.yuwang.pinju.core.refund.manager.RefundQueryManager;
import com.yuwang.pinju.core.trade.ao.TenPlatformRefundAO;
import com.yuwang.pinju.core.user.ao.BaseAO;
import com.yuwang.pinju.domain.refund.RefundDO;

/** <p>Description:退款超时业务处理 </p>
 * @author:[MaYuanChao]
 * @version 1.0
 * @create:2011-10-17
 */
public class RefundTimingAOImpl extends BaseAO implements RefundTimingAO {

	private RefundQueryManager refundQueryManager;
	private RefundManager refundManager;
	private RefundCheckManager refundCheckManager;
	private TenPlatformRefundAO tenPlatformRefundAO;
	private OrderUpDateManager orderUpDateManager;
	private RefundManageAO refundManageAO;
	

	/**
	 * 卖家拒绝退款，买家5天没反应  --- >退款关闭
	 * 1.查出所有卖家拒绝退款并且超过5天的退款单的退款单，2.关闭该退款 和子订单的退款状态
	 */
	
	@Override
	public void buyerNoReply() {
		// 获取所有卖家拒绝退款并且超过5天的退款单的退款单
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("refundState", RefundDO.STATUS_SELLER_REFUSE_BUYER);
			Date timeOutDate = DateUtils.addDays(new Date(), -5);
			param.put("timeOutDate", timeOutDate);
			if(log.isInfoEnabled()){
				log.info("RefundTimingAOImpl.buyerNoReply begin exexute at：" + timeOutDate.toString());
			}
			final List<RefundDO> list = refundQueryManager.queryRefundByBuyerNoReply(param);
			if (list != null && list.size() > 0) {
				for (final RefundDO refundDO : list) {
					refundDO.setRefundState(RefundDO.STATUS_CLOSED);
					this.updateData(refundDO);
					
					//结束退款
					refundManageAO.endRefund(refundDO);
				}
			}
			
		} catch (ManagerException e) {
			log.error("Event=[RefundTimingAOImpl.buyerNoReply() acquire RefundDO error]", e);
			
		}
		if(log.isInfoEnabled()){
			log.info("RefundTimingAOImpl.buyerNoReply exexute end at：" + new Date().toString());
		}

	}

	/**
	 * 买家发起退款，卖家5天没反应
	 * 
	 */
	public void sellerNoReply(){
		log.info("RefundTimingAOImpl.sellerNoReply begin exexute at " + new Date());
		
		try {
			List<RefundDO> refundList =  refundQueryManager.querySellerNoReplyRefund();
			for(RefundDO refundDO:refundList){
				try {
					Long refundId = refundDO.getId();
					Long orderId = refundDO.getOrderId();
					
					Integer needSalesReturn = refundDO.getNeedSalesReturn();
					
					Date now = new Date();
					
					//需要退货
					if(needSalesReturn.compareTo(RefundDO.NEED_SALES_RETURN_YES) == 0){
						//超时则修改状态为等待买家退货
						refundDO.setRefundState(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS);
						refundDO.setReplyDate(now);
						
						updateData(refundDO);
						
					//不需要退货
					}else{
						//修改状态为协议达成
						refundDO.setRefundState(RefundDO.STATUS_REFUND_PROTOCAL_AGREE);
						refundDO.setReplyDate(now);
						
						//结束退款
						refundManageAO.endRefund(refundDO);
						
						updateData(refundDO);
						
						Boolean needPlatRefund = refundCheckManager.needPlatRefund(refundId);
						
						//需要全额退款
						if(needPlatRefund){
							log.info("orderId:" + orderId + " exuecute platform refund.");
							tenPlatformRefundAO.platformRefundForOneOrder(orderId);
							
						}else{
							log.info("from RefundTimingAOImpl#sellerNoReply, refund = " + refundId + " need no platform refund.");
						}
					}
				} catch (Exception e) {
					log.error("Event=[RefundTimingAOImpl#sellerNoReply]", e);
					continue;
				}
			}
			
		} catch (ManagerException e) {
			
			log.error("Event=[RefundTimingAOImpl#sellerNoReply] timing process error", e);
		}
		
		log.info("RefundTimingAOImpl.sellerNoReply end exexute at " + new Date());

		
	}
	

	/**
	 * 卖家不确认收货 -- 平邮
	 * 
	 * @param refundId 退款id
	 */
	public void sellerNotConfirmPost(){
		log.info("RefundTimingAOImpl.sellerNotConfirmPost begin exexute at " + new Date());
		
		try {
			List<RefundDO> refundList =  refundQueryManager.sellerNotConfirmPost();
			for(RefundDO refundDO:refundList){
				try {

					//超时则修改状态为客服介入中
					refundDO.setRefundState(RefundDO.STATUS_CS_PROCESS);
					
					//客服介入过状态
					refundDO.setIsCustProcessed(RefundDO.CUST_PROCESS_YES);
					
					updateData(refundDO);
				} catch (Exception e) {
					log.error("Event=[RefundTimingAOImpl#sellerNotConfirmPost] timing process error, refundId = " + refundDO.getId(), e);
					continue;
				}
			}
			
		} catch (ManagerException e) {
			
			log.error("Event=[RefundTimingAOImpl#sellerNotConfirmPost] timing process error", e);
		}
		log.info("RefundTimingAOImpl.sellerNotConfirmPost end exexute at " + new Date());

	}
	
	/**
	 * 卖家不确认收货 -- 快递
	 * 
	 * @param refundId 退款id
	 */
	public void sellerNotConfirmExpress(){
		log.info("RefundTimingAOImpl.sellerNotConfirmExpresst begin exexute at " + new Date());
		
		try {
			List<RefundDO> refundList =  refundQueryManager.sellerNotConfirmExpress();
			for(RefundDO refundDO:refundList){
				try {

					//超时则修改状态为客服介入中
					refundDO.setRefundState(RefundDO.STATUS_CS_PROCESS);
					
					//客服介入过状态
					refundDO.setIsCustProcessed(RefundDO.CUST_PROCESS_YES);
					
					updateData(refundDO);
				} catch (Exception e) {
					// TODO: handle exception
					continue;
				}
			}
			
		} catch (ManagerException e) {
			
			log.error("Event=[RefundTimingAOImpl#sellerNotConfirmExpress] timing process error", e);
		}
		log.info("RefundTimingAOImpl.sellerNotConfirmExpress end exexute at " + new Date());

	}
	
	
	/**
	 * 卖家同意退款，而买家在5天内不退还商品。
	 * 1.获取所有超时 状态为等待买家退还商品的退款单
	 * 2.关闭该退款 和子订单的退款状态
	 */
	@Override
	public void buyerNoReturnGoods() {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("refundState", RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS);
			Date timeOutDate = DateUtils.addDays(new Date(), -5);
			param.put("timeOutDate", timeOutDate);
			if (log.isInfoEnabled()) {
				log.info("RefundTimingAOImpl.buyerNoReturnGoods begin exexute at：" + timeOutDate.toString());	
			}
			final List<RefundDO> list = refundQueryManager.queryRefundByBuyerNoReply(param);
			if (list != null && list.size() > 0) {
				for (final RefundDO refundDO : list) {
					refundDO.setRefundState(RefundDO.STATUS_CLOSED);
					
					updateData(refundDO);
					RefundDO _refundDo = refundManager.loadRefund(refundDO.getId());
					//结束退款
					if(_refundDo != null){
						refundManageAO.endRefund(refundDO);
					}
				}
			}
			
		} catch (ManagerException e) {
			log.error("Event=[RefundTimingAOImpl.buyerNoReturnGoods()->获取退款记录失败]", e);
			
		}
		if (log.isInfoEnabled()) {
			log.info("RefundTimingAOImpl.buyerNoReturnGoods exexute end at：" + new Date().toString());		
		}

	}
	
	/**
	 * <p>Description: 更改退款状态</p>
	 * @param refundDO
	 * @param refundStatus
	 * @return  true
	 * @author:[MaYuanChao]
	 * @version 1.0
	 * @create:2011-10-20
	 * @update:[日期YYYY-MM-DD] [更改人姓名]
	 */
	@SuppressWarnings( { "unchecked", "rawtypes" })
	private boolean updateData(final RefundDO refundDO){
		Boolean upflag =(Boolean)getZizuTransactionTemplate().execute(new TransactionCallback() {
			@Override
			public Object doInTransaction(TransactionStatus status) {
				try {
					orderUpDateManager.upRefundState(refundDO.getOrderItemId(), refundDO.getRefundState());

					refundManager.updateRefundApplyInfo(refundDO);
				} catch (ManagerException e) {
					status.setRollbackOnly();
					log.error("Event=[RefundTimingAOImpl.updateData() update refund status error, refund ID:"+refundDO.getId()+"]", e);
					return false;
				}
				return true;
			}});
		return upflag;
	}
	
	public void setRefundQueryManager(RefundQueryManager refundQueryManager) {
		this.refundQueryManager = refundQueryManager;
	}
	
	public void setRefundManager(RefundManager refundManager) {
		this.refundManager = refundManager;
	}

	public void setRefundCheckManager(RefundCheckManager refundCheckManager) {
		this.refundCheckManager = refundCheckManager;
	}

	public void setTenPlatformRefundAO(TenPlatformRefundAO tenPlatformRefundAO) {
		this.tenPlatformRefundAO = tenPlatformRefundAO;
	}

	public void setOrderUpDateManager(OrderUpDateManager orderUpDateManager) {
		this.orderUpDateManager = orderUpDateManager;
	}

	public void setRefundManageAO(RefundManageAO refundManageAO) {
		this.refundManageAO = refundManageAO;
	}
	
}