package com.yuwang.pinju.core.member.ao.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.message.constants.MessageConstants;
import com.yuwang.message.domain.message.SenderModel;
import com.yuwang.message.jms.manager.MessageJmsManager;
import com.yuwang.pinju.common.Base61Code;
import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.captcha.CaptchaManager;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.constant.member.MemberKeyConstant;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.constant.system.PinjuConstantUtil;
import com.yuwang.pinju.core.member.ao.MemberSecurityAO;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.member.manager.PinjuMemberManager;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberPinjuLoginDO;
import com.yuwang.pinju.domain.member.security.ForgotPasswordDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityCenterDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO.LinkType;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileCodeDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityMobileDO;
import com.yuwang.pinju.domain.member.security.MemberSecurityParam.DefaultSecurityParam;
import com.yuwang.pinju.domain.member.security.RetrievePasswordDO;
import com.yuwang.pinju.domain.member.security.SecurityEmailMessageDO;
import com.yuwang.pinju.domain.member.security.SecurityLinkDO;
import com.yuwang.pinju.domain.member.security.SmsCodeValidatorVO;
import com.yuwang.pinju.domain.member.security.SmsSenderVO;

public class MemberSecurityAOImpl implements MemberSecurityAO, MemberResultConstant, MemberKeyConstant {

	private final static Log log = LogFactory.getLog(MemberSecurityAOImpl.class);

	private MemberSecurityManager memberSecurityManager;
	private PinjuMemberManager pinjuMemberManager;
	private CaptchaManager captchaManager;
	private MessageJmsManager messageJmsManager;
	private MemberManager memberManager;

	public void setCaptchaManager(CaptchaManager captchaManager) {
		this.captchaManager = captchaManager;
	}

	public void setMessageJmsManager(MessageJmsManager messageJmsManager) {
		this.messageJmsManager = messageJmsManager;
	}

	public void setMemberSecurityManager(MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	public void setPinjuMemberManager(PinjuMemberManager pinjuMemberManager) {
		this.pinjuMemberManager = pinjuMemberManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	@Override
	public Result findMemberSecurityEmailByMemberId(Long memberId) {

		Result result = null;
		try {
			MemberDO member = memberManager.findMember(memberId);
			if (member == null) {
				log.warn("findMemberSecurityEmailByMemberId, cannot find member info of member id: [" + memberId + "]");
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}

			MemberSecurityEmailDO memberSecurityEmailDO = memberSecurityManager
					.findMemberSecurityEmailByMemberId(memberId);
			if (memberSecurityEmailDO != null) {
				String loginName = memberSecurityEmailDO.getLoginName();
				if (loginName == null || !loginName.equals(member.getNickname())) {
					return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
				}

				result = ResultSupportExt.createError(RESULT_MEMBER_EMAIL_CHECKED);
				result.setModel(memberSecurityEmailDO);
			} else {
				result = ResultSupportExt.createSuccess();
			}
		} catch (ManagerException e) {
			log.error("find member security email error memberId:" + memberId, e);
			result = ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
		return result;
	}

	@Override
	public Result checkEmail(String email, long memberId) {
		try {
			if (EmptyUtil.isBlank(email)) {
				log.warn("check email, parameter email is null or empty");
				return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
			}
			MemberSecurityEmailDO memberSecurityEmailDO = memberSecurityManager.findMemberSecurityEmailDOByEmail(email);

			if (memberSecurityEmailDO != null) {
				if (memberId == 0 || memberSecurityEmailDO.getMemberId() != memberId) {
					log.info("check email, email [" + email + "] has been used by member id: "
							+ memberSecurityEmailDO.getMemberId());
					return ResultSupportExt.createError(RESULT_MEMBER_EMAIL_HAS_EXIST);
				}
			}
			return ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("check email failed, email: " + email + ", memberId: " + memberId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result confirmLink(SecurityLinkDO link) {
		if (link == null) {
			log.error("confirmLink, parameter link is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		if (log.isDebugEnabled()) {
			log.debug("confirmLink, parameter: " + link);
		}
		try {
			MemberSecurityEmailLinkDO ek = memberSecurityManager.confirmEmailLink(link);
			if (ek == null) {
				log.warn("confirmLink, link: " + link.getParam() + " was not found");
				return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_LINK_NOT_FOUND);
			}
			if (log.isInfoEnabled()) {
				log.info("confirmLink, link: " + link.getParam() + ", EmailLink info: " + ek);
			}
			return ResultSupportExt.createSuccess(ek);
		} catch (Exception e) {
			log.error("confirmLink, process error, param: " + link, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result sendEmail(SecurityEmailMessageDO message) {
		try {
			MemberSecurityEmailLinkDO link = memberSecurityManager.generateEmailLink(message.getMemberId(),
					message.getEmail(), message.getIp(), message.getLinkType());
			SenderModel senderModel = new SenderModel();
			senderModel.setFromAddress(PinjuConstant.EMAIL_SYSTEM_ADDRESS);
			senderModel.setFromName(PinjuConstant.EMAIL_SYSTEM_FROM_NAME);
			senderModel.setSendType(MessageConstants.CHANNEL_SENDTYPE_MAIL);
			senderModel.addDest(message.getEmail(), message.getEmail());
			senderModel.setTitleTemplateName(message.getTitleTemplateName());
			senderModel.setContentTemplateName(message.getContentTemplateName());
			senderModel.setChannel(message.getChannel());
			senderModel.setMessageId(link.getMessageId());
			senderModel.setSendTime(link.getSendTime());
			Map<String, String> contentParamMap = message.getContentParam();
			if (!contentParamMap.isEmpty()) {
				for (Map.Entry<String, String> entry : contentParamMap.entrySet()) {
					senderModel.addContentParameters(entry.getKey(), entry.getValue());
				}
			}
			senderModel.addContentParameters("emailLink", message.getEmailLike() + link.getLinkParam());
			messageJmsManager.sendEmailSend(senderModel);
			return ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("send email error, email:" + message.getEmail() + ", memberId: " + message.getMemberId()
					+ ", linkType: " + message.getLinkType().getType(), e);
			return ResultSupportExt.createError(RESULT_MEMBER_EMAIL_SEND_ERROR);
		}
	}

	@Override
	public Result sendUnboundEmail(SecurityEmailMessageDO message) {
		try {
			MemberSecurityEmailDO memberSecurityEmailDO = memberSecurityManager
					.findMemberSecurityEmailByMemberId(message.getMemberId());
			if (memberSecurityEmailDO == null) {
				log.warn("memberSecurityEmail is null, no bound");
				return ResultSupportExt.createError(RESULT_MEMBER_EMAIL_NOT_EXIST);
			}

			MemberSecurityEmailLinkDO link = memberSecurityManager.generateEmailLink(message.getMemberId(),
					memberSecurityEmailDO.getEmailAddr(), message.getIp(), message.getLinkType());

			message.setEmail(memberSecurityEmailDO.getEmailAddr());
			message.addContentParam("email", memberSecurityEmailDO.getEmailAddr());

			SenderModel senderModel = new SenderModel();
			senderModel.setFromAddress(PinjuConstant.EMAIL_SYSTEM_ADDRESS);
			senderModel.setFromName(PinjuConstant.EMAIL_SYSTEM_FROM_NAME);
			senderModel.setSendType(MessageConstants.CHANNEL_SENDTYPE_MAIL);
			senderModel.addDest(memberSecurityEmailDO.getEmailAddr(), memberSecurityEmailDO.getEmailAddr());
			senderModel.setTitleTemplateName(message.getTitleTemplateName());
			senderModel.setContentTemplateName(message.getContentTemplateName());
			senderModel.setChannel(message.getChannel());
			senderModel.setMessageId(link.getMessageId());
			senderModel.setSendTime(link.getSendTime());
			Map<String, String> contentParamMap = message.getContentParam();
			if (!contentParamMap.isEmpty()) {
				for (Map.Entry<String, String> entry : contentParamMap.entrySet()) {
					senderModel.addContentParameters(entry.getKey(), entry.getValue());
				}
			}
			senderModel.addContentParameters("emailLink", message.getEmailLike() + link.getLinkParam());
			messageJmsManager.sendEmailSend(senderModel);
			return ResultSupportExt.createSuccess();
		} catch (Exception e) {
			log.error("send email error, email:" + message.getEmail() + ", memberId: " + message.getMemberId()
					+ ", linkType: " + message.getLinkType().getType(), e);
			return ResultSupportExt.createError(RESULT_MEMBER_EMAIL_SEND_ERROR);
		}
	}

	@Override
	public Result retrievePassword(RetrievePasswordDO retrieve) {
		if (retrieve == null) {
			log.warn("retrievePassword, parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}

		try {

			// check retrieve password, mac value validity
			if (StringUtils.isBlank(retrieve.getParam5())) {
				log.warn("retrievePassword, param5(token + mid mac value) is empty, parameter: " + retrieve);
				return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_LINK_NOT_FOUND);
			}

			// calculate mac value according to param3(token) and param4(message
			// id)
			String mac = macRetrievePassword(retrieve);
			if (!retrieve.getParam5().equals(mac)) {
				log.warn("retrievePassword, param5(token + mid mac value) mac calcuate error, calculate mac: " + mac
						+ ", parameter: " + retrieve);
				return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_LINK_NOT_FOUND);
			}

			// according messageId, token, securityType retrieve MemberId
			// data
			DefaultSecurityParam param = new DefaultSecurityParam(retrieve.getParam4(), retrieve.getParam3(),
					retrieve.getParam6());

			Result result = memberSecurityManager.confirmToken(param);

			if (!result.isSuccess()) {
				log.warn("[retrievePassword] confirm token result is unsuccess, " + retrieve);
				return result;
			}

			Long memberId = result.getModel(Long.class);

			if (log.isInfoEnabled()) {
				log.info("[retrievePassword] member id: [" + memberId + "], param: " + retrieve);
			}

			// according member id that from MemberSecurityEmailLink retrieve
			// MemberSecurityEmailLink data
			MemberPinjuLoginDO pinjuLogin = pinjuMemberManager.getMemberPinjuLoginByMemberId(memberId);

			// can not find pinjuLogin object, member is not exist
			if (pinjuLogin == null) {
				log.warn("retrievePassword, can not find member login data, messageId: [" + retrieve.getParam4()
						+ "], token: [" + retrieve.getParam3() + "], member id: [" + memberId + "]");
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}

			// login information is invalid
			if (!pinjuLogin.isValid()) {
				log.warn("retrievePassword, can not find member login data, messageId: [" + retrieve.getParam4()
						+ "], token: [" + retrieve.getParam3() + "], member id: [" + memberId + "] is invalid");
				return ResultSupportExt.createError(RESULT_MEMBER_INVALID);
			}

			// update login password
			int updateCount = pinjuMemberManager.updatePinjuLoginPassowrd(pinjuLogin, retrieve.getNewPassword());
			if (log.isInfoEnabled()) {
				log.info("retrievePassword, modify new password finished, update count: " + updateCount
						+ ", member id: " + pinjuLogin.getMemberId() + ", message id: " + retrieve.getParam4()
						+ ", token: " + retrieve.getParam3());
			}
			if (updateCount < 1) {
				return ResultSupportExt.createError(RESULT_UPDATE_COUNT_ERROR);
			}

			return ResultSupportExt.createSuccess(pinjuLogin);
		} catch (Exception e) {
			log.error("retrievePassword, process error, param: " + retrieve);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result addEmailLink(MemberSecurityEmailLinkDO link) {
		Result result = null;
		try {
			if (memberSecurityManager.findMemberSecurityEmailDOByEmail(link.getEmailAddr()) != null) {
				result = ResultSupportExt.createError(RESULT_MEMBER_SECURITY_LINK_NOT_FOUND);
			} else {
				MemberSecurityEmailDO memberSecurityEmailDO = new MemberSecurityEmailDO();
				memberSecurityEmailDO.setMemberId(link.getMemberId());
				memberSecurityEmailDO.setEmailAddr(link.getEmailAddr());
				memberSecurityEmailDO.setLoginName(link.getLoginName());
				memberSecurityEmailDO.setMessageId(link.getMessageId());
				memberSecurityEmailDO.setValidationIp(link.getLinkIp());
				memberSecurityManager.addMemberSecurityEmail(memberSecurityEmailDO);
				result = ResultSupportExt.createSuccess();
				result.setModel(memberSecurityEmailDO);
			}
		} catch (ManagerException e) {
			log.error("MemberSecurityAOImpl.addEmailLink is error, param: " + link, e);
			result = ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
		return result;
	}

	@Override
	public Result sendRetrievePasswordEmail(ForgotPasswordDO forgot) {
		// check parameter
		if (forgot == null) {
			log.error("sendRetrievePasswordEmail, parameter forgot object is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		// business logic operation
		try {

			// get member security email information according to email that
			// inputed by member
			MemberSecurityEmailDO email = memberSecurityManager.findMemberSecurityEmailDOByEmail(forgot.getEmail());
			if (email == null) {
				log.warn("sendRetrievePasswordEmail, can not find MemberSecurityEmail data, email: "
						+ forgot.getEmail());
				return ResultSupportExt.createError(RESULT_MEMBER_EMAIL_NOT_EXIST);
			}

			// get security email link information using send email
			MemberSecurityEmailLinkDO link = memberSecurityManager.generateEmailLink(email.getMemberId(),
					email.getEmailAddr(), forgot.getIp(), LinkType.EMAIL_RETRIEVE_PASSWORD);

			// send retrieve email
			memberSecurityManager.sendRetrievePasswordEmail(email, link);
			return ResultSupportExt.createSuccess(link);
		} catch (Exception e) {
			log.error("sendRetrievePasswordEmail, cause exception, parameter: " + forgot, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public String macRetrievePassword(RetrievePasswordDO retrieve) {
		if (log.isDebugEnabled()) {
			log.debug("macRetrievePassword, parameter: " + retrieve);
		}
		try {
			if (retrieve == null) {
				log.warn("hashRetrievePassword, parameter is null, cannot calculate token and message id hash value");
				return null;
			}
			String data = retrieve.getMacData();
			if (log.isInfoEnabled()) {
				log.info("macRetrievePassword, mac data: " + data);
			}
			byte[] bys = memberSecurityManager.macMessage(data);
			if (bys == null) {
				log.warn("hashRetrievePassword, calculate mac error, data: " + data);
				return null;
			}
			return Base61Code.encode(bys);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Result sendSmsCode(SmsSenderVO smsSender) {

		if (!checkSmsSender(smsSender)) {
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {
			if (smsSender.getSmsType().isNeedLogin()) {
				if (log.isInfoEnabled()) {
					log.info("sendSmsCode, using login send, " + smsSender);
				}
				return memberSecurityManager.sendSmsCodeToLogin(smsSender);
			} else {
				if (log.isInfoEnabled()) {
					log.info("sendSmsCode, using unlogin send, " + smsSender);
				}
				return memberSecurityManager.sendSmsCodeToUnlogin(smsSender);
			}
		} catch (Exception e) {
			log.error("sendSmsCode, cause exception, " + smsSender, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	private boolean checkSmsSender(SmsSenderVO smsSender) {
		if (smsSender == null) {
			log.warn("sendSmsCode, parameter SmsSenderVO object is null");
			return false;
		}
		if (smsSender.getSmsType() == null) {
			log.warn("sendSmsCode, parameter SmsSenderVO.smsType is null. " + smsSender);
			return false;
		}
		if (smsSender.getSmsType().isNeedLogin()) {
			if (memberManager.isCorrectMemberId(smsSender.getMemberId())
					&& StringUtils.isNotBlank(smsSender.getLoginName())) {
				return true;
			}
		} else {
			if (PinjuConstantUtil.isMobile(smsSender.getMobile())) {
				return true;
			}
		}
		log.warn("checkSmsSender parameter, check failed, data: " + smsSender);
		return false;
	}

	@Override
	public Result checkMobileAndCode(long memberId, SmsCodeValidatorVO scv) {

		if (scv == null || EmptyUtil.isBlank(scv.getMobile()) || EmptyUtil.isBlank(scv.getCode())
				|| EmptyUtil.isBlank(scv.getMessageId()) || scv.getType() == null) {
			log.warn("[checkMobileAndCode], parameter scv:[" + scv + "]");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		if (!PinjuConstantUtil.isMobile(scv.getMobile())) {
			return ResultSupportExt.createError(RESULT_MOBILE_INVALID);
		}
		try {
			MemberSecurityMobileCodeDO code = memberSecurityManager.validateMobileCode(scv);
			if (code == null) {
				log.warn("[checkMobileAndCode] mobile code is failure scv:[" + scv + "]");
				return ResultSupportExt.createError(RESULT_UNCONFIM_CODE_FAILURE);
			}
			MemberSecurityMobileDO memberSecurityMobileDO = memberSecurityManager.getMemberSecurityMobileDOByMobile(scv.getMobile());
			if (memberSecurityMobileDO != null) {
				if (memberSecurityMobileDO.getMemberId() != memberId) {
					log.warn("[checkMobileAndCode] error, mobile [" + scv.getMobile() + "] has been used by member id: " + memberId + "scv:[" + scv + "]");
					return ResultSupportExt.createError(RESULT_MEMBER_MOBILE_HAS_EXIST);
				}
			}
		} catch (Exception e) {
			log.error("[checkMobileAndCode], cause exception, parameter: scv:[" + scv + "]",e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
		return ResultSupportExt.createSuccess();
	}

	@Override
	public Result findMemberSecurityMobileByMemberId(Long memberId) {

		Result result = null;
		try {
			MemberDO member = memberManager.findMember(memberId);
			if (member == null) {
				log.warn("findMemberSecurityEmailByMemberId, cannot find member info of member id: [" + memberId + "]");
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}
			MemberSecurityMobileDO memberSecurityMobile = memberSecurityManager
					.findMemberSecurityMobileByMemberId(memberId);
			if (memberSecurityMobile != null) {
				String loginName = memberSecurityMobile.getLoginName();
				if (loginName == null || !loginName.equals(member.getNickname())) {
					return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
				}
				result = ResultSupportExt.createError(RESULT_MEMBER_MOBILE_CHECKED);
				result.setModel("SECRITY_MOBILE", memberSecurityMobile);
			} else {
				result = ResultSupportExt.createError(RESULT_MEMBER_SECURITY_NO_EXIST);
			}
		} catch (ManagerException e) {
			log.error("find member security email error memberId:" + memberId, e);
			result = ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
		return result;
	}

	@Override
	public Result addMemberSecurityMobile(MemberSecurityMobileDO memberSecurityMobileDO) {
		MemberSecurityMobileDO securityMobileDO;
		try {
			securityMobileDO = memberSecurityManager.addMemberSecurityMobile(memberSecurityMobileDO);
			if (securityMobileDO == null) {
				log.warn("add MemberSecurityMobile error" + memberSecurityMobileDO.toString());
				return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
			}
		} catch (ManagerException e) {
			log.warn("add MemberSecurityMobile error" + memberSecurityMobileDO.toString(), e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
		return ResultSupportExt.createSuccess(securityMobileDO);
	}

	@Override
	public Result unBindMemberSecurityMobile(MemberSecurityMobileDO memberSecurityMobileDO) {
		try {
			int k = memberSecurityManager.unblandMemberSecurityMobile(memberSecurityMobileDO);
			if (k == 0) {
				log.warn("unblandMobile error" + memberSecurityMobileDO.toString());
				return ResultSupportExt.createError(RESULT_UNBLAND_MOBILE_ERROR);
			}
		} catch (ManagerException e) {
			log.warn("unBindMemberSecurityMobile error " + memberSecurityMobileDO.toString(), e);
			return ResultSupportExt.createError(RESULT_UNBLAND_MOBILE_ERROR);
		}
		return ResultSupportExt.createSuccess();
	}

	@Override
	public Result unboundEmail(MemberSecurityEmailLinkDO link) {
		try {
			if (memberSecurityManager.findMemberSecurityEmailDOByEmail(link.getEmailAddr()) == null) {
				return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_LINK_NOT_FOUND);
			}
			MemberSecurityEmailDO memberSecurityEmail = memberSecurityManager.unboundEmail(link.getMemberId());
			if (memberSecurityEmail == null) {
				log.warn("email link memberid is not exist, memberId=" + link.getMemberId());
				return ResultSupportExt.createError(RESULT_MEMBER_SECURITY_LINK_NOT_FOUND);
			}

			Result result = ResultSupportExt.createSuccess();
			result.setModel(memberSecurityEmail);
			return result;
		} catch (Exception e) {
			log.error("member security email unboundEmail error, memberId =" + link.getMemberId(), e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result getSecurityCenter(long memberId) {
		try {
			MemberSecurityDO memberSecurity = memberSecurityManager.getMemberSecurity(memberId);
			if (memberSecurity == null) {
				return ResultSupportExt.createError(RESULT_MEMBER_INVALID);
			}

			MemberSecurityCenterDO memberSecurityCenter = new MemberSecurityCenterDO();
			memberSecurityCenter.setLastLoginIp(memberSecurity.getLastLoginIp());
			memberSecurityCenter.setLastLoginTime(memberSecurity.getLastLoginTime());
			memberSecurityCenter.setLoginName(memberSecurity.getLoginName());
			memberSecurityCenter.setSecurityAuthMask(memberSecurity.getSecurityAuthMask());
			memberSecurityCenter.setLevel(memberSecurity.getLevel());

			MemberSecurityEmailDO memberSecurityEmail = memberSecurityManager
					.findMemberSecurityEmailByMemberId(memberId);
			if (memberSecurityEmail != null) {
				memberSecurityCenter.setEmail(memberSecurityEmail.getEmailAddr());
			}

			MemberSecurityMobileDO memberSecurityMobile = memberSecurityManager
					.findMemberSecurityMobileByMemberId(memberId);
			if (memberSecurityMobile != null) {
				memberSecurityCenter.setMobile(memberSecurityMobile.getMobile());
			}
			return ResultSupportExt.createSuccess(memberSecurityCenter);
		} catch (ManagerException e) {
			log.error("member getSecurityCenter error, memberId =" + memberId, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result checkViewLoginMobileAndEmail(ForgotPasswordDO forgot) {
		return checkMobileAndEmail(forgot);
	}

	@Override
	public Result checkLoginNameMobileEmail(ForgotPasswordDO forgot) {
		// check captcha
		if (!captchaManager.validate(forgot.getSid(), forgot.getCaptcha())) {
			log.warn("sendRetrievePasswordEmail, captcha validate failed");
			return ResultSupportExt.createError(RESULT_CAPTCHA_ERROR);
		}
		return checkMobileAndEmail(forgot);
	}

	private Result checkMobileAndEmail(ForgotPasswordDO forgot) {
		String loginName = forgot.getLoginName();
		try {
			if (StringUtil.isBlank(loginName)) {
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}

			MemberDO member = memberManager.findMemberByNickname(loginName);
			if (member == null) {
				return ResultSupportExt.createError(RESULT_MEMBER_MEMBER_NOT_EXIST);
			}
			if (ForgotPasswordDO.SEL_EMIAL.equals(forgot.getSel())) {
				MemberSecurityEmailDO MemberSecurityEmail = memberSecurityManager
						.findMemberSecurityEmailDOByEmail(forgot.getEmail());
				if (MemberSecurityEmail == null) {
					return ResultSupportExt.createError(RESULT_MEMBER_EMAIL_NOT_EXIST);
				}
				if (!loginName.equals(MemberSecurityEmail.getLoginName())) {
					return ResultSupportExt.createError(RESULT_MEMBER_EMAIL_NOT_EQUAL);
				}
			}
			if (ForgotPasswordDO.SEL_MOBILE.equals(forgot.getSel())) {
				MemberSecurityMobileDO memberSecurityMobile = memberSecurityManager
						.findMemberSecurityMobileByMobile(forgot.getMobile());
				if (memberSecurityMobile == null) {
					return ResultSupportExt.createError(RESULT_MOBILE_NOT_EXISTS);
				}
				if (!loginName.equals(memberSecurityMobile.getLoginName())) {
					return ResultSupportExt.createError(RESULT_MEMBER_MOBILE_NOT_EQUAL);
				}
			}
			return ResultSupportExt.createSuccess();
		} catch (ManagerException e) {
			log.error("member checkMobileAndEmail error, forgotPasswordValidator =" + forgot, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public Result checkCode(SmsCodeValidatorVO scv, String nick) {
				
		if (scv == null || EmptyUtil.isBlank(nick) || EmptyUtil.isBlank(scv.getMobile()) || EmptyUtil.isBlank(scv.getCode())
				|| EmptyUtil.isBlank(scv.getMessageId()) || scv.getType() == null) {
			log.warn("[checkCode] check mobile and code, parameter mobile or code is null or empty nick [" + nick + "], scv: " + scv);
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		if (!PinjuConstantUtil.isMobile(scv.getMobile())) {
			log.warn("[checkCode] mobile is [" + scv.getMobile() + "]");
			return ResultSupportExt.createError(RESULT_MOBILE_INVALID);
		}
		try {
			MemberSecurityMobileDO memberSecurityMobileDO = memberSecurityManager.getMemberSecurityMobileDOByMobile(scv.getMobile());
			if (memberSecurityMobileDO == null) {
				log.warn("[checkCode] memberSecurityMobileDO was not found, " + scv);
				return ResultSupportExt.createError(RESULT_MOBILE_NOT_EXISTS);
			}
			String loginName = macLoginName(memberSecurityMobileDO.getLoginName());
			if (!loginName.equals(nick)) {
				log.warn("[checkCode] memberNick is not right, nick [" + nick + "], loginName [" + loginName + "], scv: [" + scv + "]");
				return ResultSupportExt.createError(RESULT_MEMBER_INVALID);
			}
			MemberSecurityMobileCodeDO memberSecurityMobileCodeDO = memberSecurityManager.validateMobileCode(scv);
			if (memberSecurityMobileCodeDO == null) {
				log.warn("[checkCode] mobile code is failure, " + scv);
				return ResultSupportExt.createError(RESULT_UNCONFIM_CODE_FAILURE);
			}
			return ResultSupportExt.createSuccess(memberSecurityMobileCodeDO);
		} catch (ManagerException e) {
			log.warn("checkCode] is error, paramater is [" + scv + "]", e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	@Override
	public String macLoginName(String loginName) {
		if (StringUtil.isBlank(loginName)) {
			return null;
		}
		return memberSecurityManager.digestMessageHex(loginName, null);
	}

}
