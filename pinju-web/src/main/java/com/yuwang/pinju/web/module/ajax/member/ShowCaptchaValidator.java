package com.yuwang.pinju.web.module.ajax.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.PinjuMemberAO;
import com.yuwang.pinju.web.struts2.PinjuAction;

/**
 * <p>登录时根据会员输入的登录名确定是否需要呈现验证码输入</p>
 *
 * @author gaobaowen
 * @since 2011-8-1 09:56:36
 */
public class ShowCaptchaValidator implements PinjuAction {

	private final static Log log = LogFactory.getLog(ShowCaptchaValidator.class);

	private PinjuMemberAO pinjuMemberAO;
	private Boolean result = Boolean.FALSE;
	private String check;

	@Override
	public String execute() throws Exception {

		if (StringUtil.widthLength(check) < 4) {
			log.debug("show captcha, check name: " + check + ", width length lesser than 4, need not check");
			return SUCCESS;
		}

		if (check.length() > 100) {
			log.debug("show captcha, check name: " + check + ", length is too long, need not check");
			return SUCCESS;
		}

		Result rs = pinjuMemberAO.showCaptcha(check);

		if (log.isDebugEnabled()) {
			log.debug("show captcha, check name: " + check + ", check result: " + rs.getResultCode());
		}

		if (rs.isSuccess()) {
			result = (Boolean)rs.getModel(MemberKeyConstant.KEY_BOOLEAN);
			if (result == null) {
				result = true;
			}
			log.debug("show captcha, validate capatch result is success, result: " + result);
			return SUCCESS;
		}

		if (MemberResultConstant.RESULT_PARAMETERS_ERROR.equals(rs.getResultCode())) {
			log.debug("show captcha, login name parameter error, need not show captcha");
			return SUCCESS;
		}

		if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(rs.getResultCode())) {
			log.debug("show captcha, login name is not exist, need not show captcha");
			return SUCCESS;
		}

		if (MemberResultConstant.RESULT_MEMBER_INNER_ERROR.equals(rs.getResultCode())) {
			log.debug("show captcha, login name is not exist, need show captcha");
			result = true;
			return SUCCESS;
		}

		return SUCCESS;
	}

	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public boolean getResult() {
		return result;
	}
	public void setPinjuMemberAO(PinjuMemberAO pinjuMemberAO) {
		this.pinjuMemberAO = pinjuMemberAO;
	}
}
