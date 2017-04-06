package com.yuwang.pinju.web.module.my.screen;

import com.opensymphony.xwork2.ModelDriven;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileDO;
import com.yuwang.pinju.domain.member.security.SmsCodeValidatorVO;
import com.yuwang.pinju.domain.message.SmsMessageType;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.MemberCheckAction;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

/**
 * <p>
 * 手机绑定
 * </p>
 * 
 * @author gongjiayun
 * 
 */
public class SecurityMobileAction extends MemberCheckAction implements PinjuAction, ModelDriven<SmsCodeValidatorVO> {

	private MemberSecurityAO memberSecurityAO;
	private MemberSecurityMobileDO mem;
	private SmsCodeValidatorVO scv;
	private final static String BLANDED = "blanded";
	private final static String BLANDNO = "brandno";
	private final static String TO_BLAND = "to_bland";
	private String member;
	private String mob;
	private String mid;

	public SecurityMobileAction() {
		scv = new SmsCodeValidatorVO();
		mem = new MemberSecurityMobileDO();
	}

	/**
	 * <p>
	 * 跳转到手机绑定界面
	 * </p>
	 * 
	 * @throws Exception
	 */
	@Override
	public String execute() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		ServletUtil.forbidBrowserCache();
		Result result = memberSecurityAO.findMemberSecurityMobileByMemberId(login.getMemberId());
		if (!result.isSuccess()) {
			if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
				return LOGIN;
			}
			if (MemberResultConstant.RESULT_MEMBER_INNER_ERROR.equals(result.getResultCode())) {
				return input(MessageName.OPERATE_INVALID);
			}
			if (MemberResultConstant.RESULT_MEMBER_MOBILE_CHECKED.equals(result.getResultCode())) {
				mem = (MemberSecurityMobileDO) result.getModel("SECRITY_MOBILE");
				return BLANDED;
			}
		}
		return INPUT;
	}

	/**
	 * <p>
	 * 添加手机绑定
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addMobileCode() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		ServletUtil.forbidBrowserCache();
		if (EmptyUtil.isBlank(scv.getMobile()) || EmptyUtil.isBlank(scv.getCode()) || EmptyUtil.isBlank(mid)) {
			return TO_BLAND;
		}
		if (!super.isSameMember(login)) {
			return input(MessageName.OPERATE_MEMBER_NOT_MATCH, login.getNickname());
		}
		mem.setMobile(scv.getMobile());
		scv.setClientIp(ServletUtil.getRemoteIp());
		scv.setType(SmsMessageType.BOUNDING_CODE);
		scv.setMessageId(mid);
		Result result = memberSecurityAO.checkMobileAndCode(login.getMemberId(), scv);
		if (result.isSuccess()) {
			MemberSecurityMobileDO securityMobile = new MemberSecurityMobileDO();
			securityMobile.setLoginName(login.getNickname());
			securityMobile.setMemberId(login.getMemberId());
			securityMobile.setMessageId(scv.getMessageId());
			securityMobile.setMobile(scv.getMobile());
			securityMobile.setValidationIp(ServletUtil.getRemoteIp());
			Result resul = memberSecurityAO.addMemberSecurityMobile(securityMobile);
			if (MemberResultConstant.RESULT_MEMBER_INNER_ERROR.equals(resul.getResultCode())) {
				return input(MessageName.OPERATE_INVALID);
			}
			member = login.getNickname();
			mob = StringUtil.hideMobile(scv.getMobile());
			return SUCCESS;
		}
		if (MemberResultConstant.RESULT_PARAMETERS_ERROR.equals(result.getResultCode())) {
			return input(MessageName.OPERATE_SUBMIT_PARAMETER_ERROR);
		}
		if (MemberResultConstant.RESULT_MOBILE_INVALID.equals(result.getResultCode())) {
			return input(MessageName.MEMBER_MOBILE_FAILURE, scv.getMobile());
		}
		if (MemberResultConstant.RESULT_MEMBER_MOBILE_HAS_EXIST.equals(result.getResultCode())) {
			return input(MessageName.MEMBER_MOBILE_HAS_USED, scv.getMobile());
		}
		if (MemberResultConstant.RESULT_UNCONFIM_CODE_FAILURE.equals(result.getResultCode())) {
			return input(MessageName.MEMBER_MOBILE_UNCONFIM_CODE_FAILURE);
		}
		return input(MessageName.OPERATE_INVALID);
	}

	/**
	 * <p>
	 * 手机解绑跳转
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String unMobileCode() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		ServletUtil.forbidBrowserCache();
		Result result = memberSecurityAO.findMemberSecurityMobileByMemberId(login.getMemberId());
		if (!result.isSuccess()) {
			if (MemberResultConstant.RESULT_MEMBER_MEMBER_NOT_EXIST.equals(result.getResultCode())) {
				return LOGIN;
			}
			if (MemberResultConstant.RESULT_MEMBER_INNER_ERROR.equals(result.getResultCode())) {
				return input(MessageName.OPERATE_INVALID);
			}
			if (MemberResultConstant.RESULT_MEMBER_SECURITY_NO_EXIST.equals(result.getResultCode())) {
				return BLANDNO;
			}
			member = login.getNickname();
			mem = (MemberSecurityMobileDO) result.getModel("SECRITY_MOBILE");
		}
		return INPUT;
	}

	/**
	 * <p>
	 * 手机解绑
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deltetMobileCode() throws Exception {
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (!login.isLogin()) {
			ServletUtil.loginCurrentResultUrl();
			return LOGIN;
		}
		ServletUtil.forbidBrowserCache();
		if (!super.isSameMember(login)) {
			return input(MessageName.OPERATE_MEMBER_NOT_MATCH, login.getNickname());
		}
		mem.setMobile(scv.getMobile());
		scv.setClientIp(ServletUtil.getRemoteIp());
		scv.setType(SmsMessageType.UNBOUNDING_CODE);
		scv.setMessageId(mid);
		Result result = memberSecurityAO.checkMobileAndCode(login.getMemberId(), scv);
		if (result.isSuccess()) {
			MemberSecurityMobileDO securityMobile = new MemberSecurityMobileDO();
			securityMobile.setLoginName(login.getNickname());
			securityMobile.setMemberId(login.getMemberId());
			securityMobile.setMessageId(scv.getMessageId());
			securityMobile.setMobile(scv.getMobile());
			securityMobile.setValidationIp(ServletUtil.getRemoteIp());
			Result resul = memberSecurityAO.unBindMemberSecurityMobile(securityMobile);
			if (MemberResultConstant.RESULT_UNBLAND_MOBILE_ERROR.equals(resul.getResultCode())) {
				return input(MessageName.OPERATE_SUBMIT_PARAMETER_ERROR);
			}
			member = login.getNickname();
			mob = StringUtil.hideMobile(scv.getMobile());
			return SUCCESS;
		}
		if (MemberResultConstant.RESULT_PARAMETERS_ERROR.equals(result.getResultCode())) {
			return input(MessageName.OPERATE_SUBMIT_PARAMETER_ERROR);
		}
		if (MemberResultConstant.RESULT_MOBILE_INVALID.equals(result.getResultCode())) {
			return input(MessageName.MEMBER_MOBILE_FAILURE, scv.getMobile());
		}
		if (MemberResultConstant.RESULT_UNCONFIM_CODE_FAILURE.equals(result.getResultCode())) {
			return input(MessageName.MEMBER_MOBILE_UNCONFIM_CODE_FAILURE);
		}
		return input(MessageName.OPERATE_INVALID);

	}

	public String unblandSuc() {
		return SUCCESS;
	}

	private String input(String messageName, Object... args) {
		ActionInvokeResult.setInvokeMessage(Message.getMessage(messageName, args));
		return INPUT;
	}

	public void setMemberSecurityAO(MemberSecurityAO memberSecurityAO) {
		this.memberSecurityAO = memberSecurityAO;
	}

	@Override
	public SmsCodeValidatorVO getModel() {
		return scv;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public MemberSecurityMobileDO getMem() {
		return mem;
	}

	public void setMem(MemberSecurityMobileDO mem) {
		this.mem = mem;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
}
