package com.yuwang.pinju.web.module.my.action;

import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_PAYMENT_REBOUND;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.InputPayAccountDO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.LoginChecker;
import com.yuwang.pinju.web.struts2.LoginChecker.LoginCheckerResult;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

public class BoundPayAccountAction implements PinjuAction, ModelDriven<InputPayAccountDO> {

	private final static Log log = LogFactory.getLog(BoundPayAccountAction.class);

	private MemberAO memberAO;
	private MemberPaymentDO payment;
	private InputPayAccountDO model = new InputPayAccountDO();

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public MemberPaymentDO getPayment() {
		return payment;
	}

	@Override
	public String execute() throws Exception {

		LoginCheckerResult loginChecker = LoginChecker.check(log, false);
		if(!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}

		ActionInvokeResult invoke = new ActionInvokeResult(model);
		if(!invoke.validate()) {
			log.warn("bean validation failed, data: " + model);
			return INPUT;
		}

		MemberDO member = memberAO.findMember(loginChecker.getMemberId());
		payment = new MemberPaymentDO();
		payment.setMemberId(member.getMemberId());
		payment.setAccountNO("xxxxxx");
		payment.setAccountType(MemberPaymentDO.ACCOUNT_TYPE_PERSON);
		payment.setActiveStatus(MemberPaymentDO.ACTIVE_STATUS_ACTIVE);
		payment.setBondStatus(MemberPaymentDO.BOUND_STATUS_BOUND);
		payment.setOutUser("????");

		Result result = memberAO.boundPaymentAccount(payment);

		if (log.isDebugEnabled()) {
			log.debug("bound, invoke AO result is: " + result.getResultCode());
		}

		if(result.isSuccess()) {
			payment = result.getModel(MemberKeyConstant.KEY_MEMBER_PAYMENT, MemberPaymentDO.class);
			return SUCCESS;
		}

		if (RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("bound, cannot find member information, member id: " + loginChecker.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		if (RESULT_PAYMENT_REBOUND.equals(result.getResultCode())) {
			log.warn("bound, rebound payment account error, member id: " + loginChecker.getMemberId());
			ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_REBOUND);
			return INPUT;
		}

		log.warn("bound, unknow result [" + result.getResultCode() + "], response operate failed");
		ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_REBOUND);
		return INPUT;
	}

	@Override
	public InputPayAccountDO getModel() {
		return model;
	}
}
