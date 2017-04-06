package com.yuwang.pinju.web.module.refund.action;

import java.util.List;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.refund.ao.RefundCheckAO;
import com.yuwang.pinju.core.refund.ao.RefundManageAO;
import com.yuwang.pinju.core.refund.ao.SellerRefundLogAO;
import com.yuwang.pinju.core.refund.ao.SellerRefundManageAO;
import com.yuwang.pinju.core.refund.ao.SellerRefundTimeoutAO;
import com.yuwang.pinju.core.trade.ao.TenPlatformRefundAO;
import com.yuwang.pinju.domain.order.ItemPropertyVO;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.trade.refund.PlatformRefundParamDO;
import com.yuwang.pinju.web.annotatioin.AssistantPermission;


/**
 * 卖家管理退款
 * 
 * @author shihongbo
 * @date 2011-6-28
 * @update:[2011-10-24] [MaYuanChao]
 */
public class SellerRefundManageAction extends RefundBaseAction implements Action, SellerRefund {
	
	private static final long serialVersionUID = -5493325157466009763L;
	private RefundApplyAO refundApplyAO;
	private SellerRefundManageAO sellerRefundManageAO;
	private RefundCheckAO refundCheckAO;
	
	List<ItemPropertyVO> itemPropertyList;
	
	private Long refundId = 0L;
	
	private RefundManageAO refundManageAO;
	private TenPlatformRefundAO tenPlatformRefundAO;
	private SellerRefundTimeoutAO sellerRefundTimeoutAO;
	private SellerRefundLogAO sellerRefundLogAO;

	private RefundDO refundDO;
	private String sellerReplyTimeoutUrl;
	private String sellerConfirmGoodsTimeoutUrl;
	
	
	/**
	 * 卖家确认收货
	 * @update:[2011-10-24] [MaYuanChao]
	 */
	@AssistantPermission(target = "crm", action = "refund")
	public String confirmReceiveGoods() {
		try {
			//退款状态不是等待卖家确认收货
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS) != 0) {
				this.sellerTipMessage(refundDO, REFUND_DEFAULT_MESSAGE);
				return INPUT;
			}
			
			//检查卖家是否确认收货超时
			Boolean isSellerConfirmGoodsTimeout = sellerRefundTimeoutAO.isSellerConfirmGoodsTimeout(refundId);
			if(isSellerConfirmGoodsTimeout){
				Result sellerConfirmGoodsTimeoutResult = sellerRefundTimeoutAO.sellerConfirmGoodsTimeout(refundId, true);
				sellerConfirmGoodsTimeoutUrl = (String)sellerConfirmGoodsTimeoutResult.getModel("sellerConfirmGoodsTimeoutUrl");
				return "sellerConfirmGoodsTimeout";
			}
			
			//记录日志
			sellerRefundLogAO.confirmReceiveGoods(refundId);
			
			//确认收货更新状态
			sellerRefundManageAO.sellerConfirmReceiveGoods(refundId);
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			//结束退款
			refundManageAO.endRefund(refundDO);
			//判断是否需要执行平台退款
			Result result = refundCheckAO.needPlatRefund(refundId);
			if (result.isSuccess()) {
				Boolean needPlatRefund = (Boolean) result.getModel("needPlatRefund");

				//需要执行平台退款
				if (needPlatRefund) {

					Long orderId = refundDO.getOrderId();

					Result paramResult = refundManageAO.getPlatformRefundParam(orderId);

					PlatformRefundParamDO sendParamDO = (PlatformRefundParamDO) paramResult.getModel("platformRefundParam");
					
					Result _result = tenPlatformRefundAO.platformRefund(sendParamDO);
					if(_result.isSuccess()){
						//跳到平台退款 action
						String flag = (String) _result.getModel("refundFlag");
						// 财付通退款成功
						if(flag.compareTo("0")== 0){
							return "refundRequest";
							
						// 手工插单成功
						}else if(flag.compareTo("1")== 0){
							return "manaulRequest";
						}
					}

				}

			}
		} catch (Exception e) {
			log.error("execute SellerRefundManageAction.confirmReceiveGoods() error", e);
			return INVALID_SELLER_REFUND;
		}
		return SUCCESS;
	}

	/**
	 * 卖家拒绝退款申请
	 * @update:[2011-10-24] [MaYuanChao]
	 */
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerRejectRefundApply() {
		 try {
			 // 当前状态不是 买家已经申请退款等待卖家同意
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE) != 0) {
				this.sellerTipMessage(refundDO, REFUND_DEFAULT_MESSAGE);
				return INPUT;
			}
			
			//判断超时
			Boolean isSellerReplyTimeout = sellerRefundTimeoutAO.isSellerReplyTimeout(refundDO);
			
			//已经超时
			if(isSellerReplyTimeout){
				
				Result timeoutResult = sellerRefundTimeoutAO.sellerReplyTimeout(refundId, true);
				
				sellerReplyTimeoutUrl = (String)timeoutResult.getModel("sellerReplyTimeoutUrl");
				
				return "sellerReplyTimeout";	
			}
			
			//没有超时
			
			//记录日志
			sellerRefundLogAO.sellerRejectRefundApply(refundId);
			
			sellerRefundManageAO.sellerRejectRefundApply(refundId);
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			
		} catch (Exception e) {
			log.error("execute SellerRefundManageAction.sellerRejectRefundApply() error", e);
			return INVALID_SELLER_REFUND;
		}
		return SUCCESS;
	}

	/**
	 * 卖家同意退款申请
	 * @update:[2011-10-24] [MaYuanChao]
	 */
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerAgreeRefundApply() {
		 try {
			 // 同意之前状态已经改变  不是 买家已经申请退款，等待卖家同意
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_AGREE) != 0) {
				this.sellerTipMessage(refundDO, REFUND_DEFAULT_MESSAGE);
				return INPUT;
			}
			
			//记录日志
			sellerRefundLogAO.sellerAgreeRefundApply(refundId);
			
			//卖家同意退款申请,更新退款状态，子订单状态
			sellerRefundManageAO.sellerAgreeRefundApply(refundId);
			RefundDO refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			//等待买家退货
			if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_BUYER_RETURN_GOODS) == 0) {
				return "wait";
			}
			//协议达成，更新主订单退款时间
			if (refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_PROTOCAL_AGREE) == 0) {
				//结束退款
				refundManageAO.endRefund(refundDO);
			}
			//判断是否需要执行平台退款
			Result result = refundCheckAO.needPlatRefund(refundId);
			if (result.isSuccess()) {
				Boolean needPlatRefund = (Boolean) result.getModel("needPlatRefund");

				//需要执行平台退款
				if (needPlatRefund) {

					Long orderId = refundDO.getOrderId();

					Result paramResult = refundManageAO.getPlatformRefundParam(orderId);

					PlatformRefundParamDO sendParamDO = (PlatformRefundParamDO) paramResult.getModel("platformRefundParam");
					
					Result _result = tenPlatformRefundAO.platformRefund(sendParamDO);
					if(_result.isSuccess()){
						//跳到平台退款 action
						String flag = (String) _result.getModel("refundFlag");
						// 财付通退款成功
						if(flag.compareTo("0")== 0){
							return "refundRequest";
							
						// 手工插单成功
						}else if(flag.compareTo("1")== 0){
							return "manaulRequest";
						}
					}

				}

			}
		} catch (Exception e) {
			log.error("execute SellerRefundManageAction.sellerAgreeRefundApply() error", e);
			return INVALID_SELLER_REFUND;
		}
		//查看退款协议达成
		return SUCCESS;
	}
	
	/**
	 * 卖家申请客服介入
	 * @update:[2011-10-24] [MaYuanChao]
	 */
	@AssistantPermission(target = "crm", action = "refund")
	public String sellerApplyCustProcess() {
		try {
			//退款状态发生了改变  不是     买家已经退货，等待卖家确认收货
			refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
			if (refundDO.getRefundState().compareTo(RefundDO.STATUS_WAIT_SELLER_CONFIRM_GOODS) != 0) {
				this.sellerTipMessage(refundDO, REFUND_DEFAULT_MESSAGE);
				return INPUT;
			}
			
			//检查卖家是否确认收货超时
			Boolean isSellerConfirmGoodsTimeout = sellerRefundTimeoutAO.isSellerConfirmGoodsTimeout(refundId);
			if(isSellerConfirmGoodsTimeout){
				Result sellerConfirmGoodsTimeoutResult = sellerRefundTimeoutAO.sellerConfirmGoodsTimeout(refundId, true);
				sellerConfirmGoodsTimeoutUrl = (String)sellerConfirmGoodsTimeoutResult.getModel("sellerConfirmGoodsTimeoutUrl");
				return "sellerConfirmGoodsTimeout";
			}
			
			//没有超时
			
			//记录日志
			sellerRefundLogAO.sellerApplyCustProcess(refundId);
			
			//卖家申请客服介入
			sellerRefundManageAO.sellerApplyCustProcess(refundId);

		} catch (Exception e) {
			log.error("execute SellerRefundManageAction.sellerApplyCustProcess() error", e);
			return INVALID_SELLER_REFUND;
		}
		return SUCCESS;
	}


	/**
	 * 字符串标签替换
	 * 
	 * @param str
	 * @return str
	 */
	public String repChar(String str) {
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		return str;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}
	
	public List<ItemPropertyVO> getItemPropertyList() {
		return itemPropertyList;
	}

	public void setSellerRefundManageAO(SellerRefundManageAO sellerRefundManageAO) {
		this.sellerRefundManageAO = sellerRefundManageAO;
	}

	public RefundDO getRefundDO() {
		return refundDO;
	}

	public void setRefundDO(RefundDO refundDO) {
		this.refundDO = refundDO;
	}
	
	public String getSellerReplyTimeoutUrl() {
		return sellerReplyTimeoutUrl;
	}
	
	public String getSellerConfirmGoodsTimeoutUrl() {
		return sellerConfirmGoodsTimeoutUrl;
	}
	
	public Long getRefundId() {
		if(getLong("id") != 0) return getLong("id");
		return refundId;
	}
	public Long getOrderItemId() {
		if(getLong("oid") != 0) return getLong("oid");
		return null;
	}
	
	public void setRefundCheckAO(RefundCheckAO refundCheckAO) {
		this.refundCheckAO = refundCheckAO;
	}

	public void setRefundManageAO(RefundManageAO refundManageAO) {
		this.refundManageAO = refundManageAO;
	}

	public void setTenPlatformRefundAO(TenPlatformRefundAO tenPlatformRefundAO) {
		this.tenPlatformRefundAO = tenPlatformRefundAO;
	}
	
	public void setSellerRefundTimeoutAO(SellerRefundTimeoutAO sellerRefundTimeoutAO) {
		this.sellerRefundTimeoutAO = sellerRefundTimeoutAO;
	}
	
	public void setSellerRefundLogAO(SellerRefundLogAO sellerRefundLogAO) {
		this.sellerRefundLogAO = sellerRefundLogAO;
	}

}
