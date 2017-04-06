package com.yuwang.pinju.web.module.my.screen;

import static com.yuwang.pinju.core.constant.member.MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.MemberPaymentDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>
 * 盛付通账号管理
 * </p>
 *
 * @author gaobaowen
 * @since 2011-7-20 14:56:00
 */
public class MyPaymentAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(MyPaymentAction.class);

	private MemberAO memberAO;
	private MemberPaymentDO payment;

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public MemberPaymentDO getPayment() {
		return payment;
	}

	@Override
	public String execute() throws Exception {
		// 获取登录数据
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		// 未登录时跳至登录页面
		if (!login.isLogin()) {
			log.warn("execute, current member not logged, member id: " + login);
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		Result result = memberAO.getPaymentAccount(login.getMemberId());

		if (log.isDebugEnabled()) {
			log.debug("execute, invoke AO result is: " + result.getResultCode());
		}

		if (RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
			log.warn("cannot find member information, member id: " + login.getMemberId());
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}

		if (!result.isSuccess()) {
			log.warn("execute, invoked AO result is not success, return to page 500, result: " + result.getResultCode());
			return PAGE_500;
		}
		payment = result.getModel(MemberKeyConstant.KEY_MEMBER_PAYMENT, MemberPaymentDO.class);
		return (payment == null ? "unbound" : "bound");
	}
}
