package com.yuwang.pinju.web.module.refund.action;

import java.util.Date;

import com.yuwang.pinju.Constant.RightsConstant;
import com.yuwang.pinju.core.refund.ao.RefundApplyAO;
import com.yuwang.pinju.core.rights.manager.RightsWorkOrderManager;
import com.yuwang.pinju.domain.refund.RefundDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderDO;
import com.yuwang.pinju.domain.rights.FinanceWorkOrderQuery;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

public class RefundManualAction extends RefundBaseAction implements BuyerRefund{
	private static final long serialVersionUID = 5734734756896867978L;
	
	private Long refundId;
	
	//银行户名
	private String bankUsername;
	
	//开户行
	private String bankName;
	
	//银行账号
	private String bankAccount;
	
	private String errorMessage;

	private RefundApplyAO refundApplyAO;
	
	private RightsWorkOrderManager rightsWorkOrderManager;
	

	public String saveWorkOrder(){
		RefundDO refundDO = refundApplyAO.loadRefundApplyInfo(refundId);
		
		//判断当前退款是否用户自己申请的
		Long memberId = CookieLoginInfo.getCookieLoginInfo().getMemberId();
		if(refundDO.getBuyerId().compareTo(memberId) != 0){
			errorMessage = "该笔退款不是您的。";
			return ERROR;
		}
		
		//判断退款是否失败状态
		if(refundDO.getRefundState().compareTo(RefundDO.STATUS_REFUND_FAIL) != 0){
			errorMessage = "当前不是退款失败状态。";
			return ERROR;
		}
		
		FinanceWorkOrderQuery rightsWorkOrderQuery = new FinanceWorkOrderQuery();
		rightsWorkOrderQuery.setOrderId(refundDO.getOrderId());
		rightsWorkOrderQuery.setBizType(RightsConstant.BIZ_TYPE_RREFUND);
		
		try{
			FinanceWorkOrderDO  r =  rightsWorkOrderManager.getFinanceWorkOrderDOByOrderId(rightsWorkOrderQuery);
			
			if(r == null){
				errorMessage = "没有手工退款工单。";
				return ERROR;
			}
			
			
			Date now = new Date();
			r.setGmtModified(now);

			//银行户名
			r.setBuyerBankAccount(bankUsername);
			
			//银行账号
			r.setBuyerBankCode(bankAccount);
			
			//开户行
			r.setBuyerBankOpen(bankName);
			
			
			rightsWorkOrderManager.updateRightsWorkOrderDO(r);
			
		}catch (Exception e) {
			e.printStackTrace();
			errorMessage = "保存手工退款工单失败。";
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public Long getRefundId() {
		return refundId;
	}

	public void setRefundId(Long refundId) {
		this.refundId = refundId;
	}

	public String getBankUsername() {
		return bankUsername;
	}

	public void setBankUsername(String bankUsername) {
		this.bankUsername = bankUsername;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public void setRefundApplyAO(RefundApplyAO refundApplyAO) {
		this.refundApplyAO = refundApplyAO;
	}

	public void setRightsWorkOrderManager(RightsWorkOrderManager rightsWorkOrderManager) {
		this.rightsWorkOrderManager = rightsWorkOrderManager;
	}

	
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public Long getOrderItemId() {
		// TODO Auto-generated method stub
		return null;
	}

}
