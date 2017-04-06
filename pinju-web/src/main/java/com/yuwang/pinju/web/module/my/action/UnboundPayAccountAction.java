package com.yuwang.pinju.web.module.my.action;

import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST;
import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_PAYMENT_NOT_EXISTS;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.struts2.LoginChecker;
import com.yuwang.pinju.web.struts2.LoginChecker.LoginCheckerResult;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

public class UnboundPayAccountAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(UnboundPayAccountAction.class);

	private MemberAO memberAO;

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	@Override
	public String execute() throws Exception {
		LoginCheckerResult loginChecker = LoginChecker.check(log, true);
		if (!loginChecker.isSuccess()) {
			return loginChecker.getResult();
		}

		Result result = memberAO.unboundPaymentAccount(loginChecker.getMemberId());

		if (log.isInfoEnabled()) {
			log.info("unbound, invoke AO result is: " + result.getResultCode());
		}

		// 处理成功了
		if (result.isSuccess()) {
			Integer count = result.getModel(MemberKeyConstant.KEY_MEMBER_UPDATE_COUNT, Integer.class);
			if (log.isDebugEnabled()) {
				log.debug("unbound, invoke AO result is success, update count: " + count);
			}
			ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_OUBOUND_SUCCESS);
			return SUCCESS;
		}

		if (RESULT_PAYMENT_NOT_EXISTS.equals(result.getResultCode())) {
			log.warn("unbound, invoke AO result is RESULT_PAYMENT_NOT_EXISTS, current user has not bound," +
					"need not unbound it, redired to payment account bound page");
			ActionInvokeResult.setInvokeMessageKey(MessageName.PAY_ACCOUNT_OUBOUND_SUCCESS);
			return SUCCESS;
		}

		/**
		 *  处理各种调用错误
		 */

		// 不存在该会员，需要让会员重新登录
		if (RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("bound, cannot find member information, member id: " + loginChecker.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		log.warn("unbound, invoke AO result [" + result.getResultCode() + "], response operate failed");
		ActionInvokeResult.setInvokeMessageKey(MessageName.OPERATE_FAILED);
		return INPUT;
	}
}
