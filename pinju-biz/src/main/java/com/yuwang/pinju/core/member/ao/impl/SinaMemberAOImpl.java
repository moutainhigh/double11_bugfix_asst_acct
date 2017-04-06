package com.yuwang.pinju.core.member.ao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.StringUtil;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.ResultSupportExt;
import com.yuwang.pinju.core.constant.member.MemberResultConstant;
import com.yuwang.pinju.core.constant.word.SensitiveWordConstants;
import com.yuwang.pinju.core.filter.FilterResult;
import com.yuwang.pinju.core.filter.manager.FilterManager;
import com.yuwang.pinju.core.member.ao.SinaMemberAO;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.manager.MemberSecurityManager;
import com.yuwang.pinju.core.member.manager.MemberSinaAccountManager;
import com.yuwang.pinju.core.member.manager.PinjuMemberManager;
import com.yuwang.pinju.core.member.manager.register.RegisterCallback;
import com.yuwang.pinju.core.member.manager.register.SinaRegisterCallback;
import com.yuwang.pinju.core.util.filter.WordFilterFacade;
import com.yuwang.pinju.core.weibo.AccessToken;
import com.yuwang.pinju.core.weibo.SinaUser;
import com.yuwang.pinju.core.weibo.WeiboClient;
import com.yuwang.pinju.core.weibo.WeiboException;
import com.yuwang.pinju.core.weibo.json.JSONException;
import com.yuwang.pinju.core.weibo.json.JSONObject;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberRegisterDO;
import com.yuwang.pinju.domain.member.MemberSinaRegisterDO;
import com.yuwang.pinju.domain.member.login.MemberSinaAccountDO;

public class SinaMemberAOImpl implements SinaMemberAO, MemberResultConstant {

	private final static Log log = LogFactory.getLog(SinaMemberAOImpl.class);

	private MemberSinaAccountManager memberSinaAccountManager;
	private FilterManager loginNameFilterManager;
	private MemberManager memberManager;
	private MemberSecurityManager memberSecurityManager;
	private PinjuMemberManager pinjuMemberManager;

	public void setMemberSecurityManager(MemberSecurityManager memberSecurityManager) {
		this.memberSecurityManager = memberSecurityManager;
	}

	public void setLoginNameFilterManager(FilterManager loginNameFilterManager) {
		this.loginNameFilterManager = loginNameFilterManager;
	}

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setMemberSinaAccountManager(
			MemberSinaAccountManager memberSinaAccountManager) {
		this.memberSinaAccountManager = memberSinaAccountManager;
	}

	public void setPinjuMemberManager(PinjuMemberManager pinjuMemberManager) {
		this.pinjuMemberManager = pinjuMemberManager;
	}

	@Override
	public Result sinaLogin(String code) {
		WeiboClient client = new WeiboClient();
		try {
			AccessToken at = client.getAccessTokenByCode(code);
			if (StringUtil.isBlank(at.getAccessToken())) {
				log.error("sina no oauth error, code=" + code);
				return ResultSupportExt.createError(RESULT_SINA_NO_OAUTH);
			}

			String uid = client.getUid(at.getAccessToken());
			if (StringUtil.isBlank(uid)) {
				log.error("sina uid is no exist, code = " + code + ", accessToken = " + at.getAccessToken());
				return ResultSupportExt.createError(RESULT_SINA_USER_NO_EXIST);
			}

			MemberSinaAccountDO memberSinaAccount = memberSinaAccountManager.getSinaAccountBySinaUid(uid);

			if (memberSinaAccount == null) {
				String sinaUser = client.getUserJsonString(at.getAccessToken(), uid);
				if (StringUtil.isBlank(sinaUser)) {
					log.error("sina user is not exist, code = " + code + ", accessToken = " + at.getAccessToken() + ", uid = " + uid);
					return ResultSupportExt.createError(RESULT_SINA_USER_NO_EXIST);
				}
				String filterSinaUser = memberSinaAccountManager.getFilterSinaUser(sinaUser);
				MemberSinaRegisterDO msrd = new MemberSinaRegisterDO();
				msrd.setAj0(memberSecurityManager.encryptMessageBase64(filterSinaUser));
				msrd.setAj1(memberSecurityManager.digestMessageHex(filterSinaUser, null));
				Result result = ResultSupportExt.createError(RESULT_SINA_NOT_NICKNAME_ERROR);
				result.setModel(msrd);
				return result;
			}

			MemberDO member = memberManager.findMember(memberSinaAccount.getMemberId());
			if (member == null) {
				log.error("member is null, memberId=" + memberSinaAccount.getMemberId());
				return ResultSupportExt.createError(RESULT_SINA_LOGIN_ERROR);
			}
			Result result = ResultSupportExt.createSuccess();
			result.setModel(member);
			return result;
		} catch (Exception e) {
			log.error("sinaLogin is error, code=" + code, e);
			return ResultSupportExt.createError(RESULT_SINA_LOGIN_ERROR);
		}
	}

	@Override
	public Result registerSinaMember(MemberSinaRegisterDO register) {
		if (log.isDebugEnabled()) {
			log.debug("register member, " + register);
		}
		if (register == null) {
			log.warn("register member, parameter is null");
			return ResultSupportExt.createError(RESULT_PARAMETERS_ERROR);
		}
		try {

			if (!validateSinaUser(register)) {
				log.warn("sina user is different input user");
				return ResultSupportExt.createError(RESULT_MEMBER_SINA_USER_DIFFERENT);
			}

			FilterResult filterResult = loginNameFilterManager.doFilter(register.getLoginName());
			if (!filterResult.isSuccess()) {
				log.warn("register login name [" + register.getLoginName() + "] contains invalid words");
				return ResultSupportExt.createError(RESULT_MEMBER_NICKNAME_WORDS_INVALID);
			}

			if (WordFilterFacade.scan(register.getLoginName(), SensitiveWordConstants.SENSITIVE_WORD_TYPE_MEMBER)) {
				log.warn("register login name [" + register.getLoginName() + "] contains insensive words");
				return ResultSupportExt.createError(RESULT_INSENSIVE_WORDS);
			}

			// 检查会员名是否存在
			MemberDO member = memberManager.findMemberByNickname(register.getLoginName());
			if (member != null) {
				log.warn("register member, login name [" + register.getLoginName() + "] has bean used by member id: "
						+ member.getMemberId());
				return ResultSupportExt.createError(RESULT_MEMBER_NICKNAME_HAS_EXIST);
			}

			// 检查sin 会员uid是否存在
			member = memberManager.getMemberByAccount(register.getSinaUid(), MemberDO.MEMBER_ORIGIN_SINA);
			if (member != null) {
				log.warn("register member, sin uid [" + register.getSinaUid() + "] has bean used by member id: "
						+ member.getMemberId());
				return ResultSupportExt.createError(RESULT_MEMBER_SIN_UID_HAS_EXIST);
			}
			
			MemberSinaAccountDO memberSinaAccount = register.createMemberSinaAccount();
			MemberRegisterDO memberRegister = register.createMemberRegister();

			RegisterCallback callback = new SinaRegisterCallback(memberSinaAccountManager, memberSinaAccount);
			return pinjuMemberManager.registerPinjuMember(memberRegister, callback);
		} catch (Exception e) {
			log.error("registerMember error, parameter: " + register, e);
			return ResultSupportExt.createError(RESULT_MEMBER_INNER_ERROR);
		}
	}

	private boolean validateSinaUser(MemberSinaRegisterDO msrd) throws WeiboException, JSONException {

		String sinaUserJsonString = memberSecurityManager.decryptMessage(msrd.getAj0());
		if (StringUtil.isBlank(sinaUserJsonString)) {
			return false;
		}

		if (!memberSecurityManager.digestMessageHex(sinaUserJsonString, null).equals(msrd.getAj1())) {
			return false;
		}

		SinaUser sinaUser = new SinaUser(new JSONObject(sinaUserJsonString));

		msrd.setProvince(sinaUser.getProvince());
		msrd.setCity(sinaUser.getCity());
		if (StringUtil.isNotBlank(sinaUser.getLocation()))
		    msrd.setLocation(sinaUser.getLocation());
		if (StringUtil.isNotBlank(sinaUser.getUrl()))
		    msrd.setWeiboUrl(sinaUser.getUrl());
		msrd.setSinaUid(sinaUser.getUid());
		if (StringUtil.isNotBlank(sinaUser.getDescription()))
		    msrd.setDescription(sinaUser.getDescription());
		if (StringUtil.isNotBlank(sinaUser.getScreenName()))
		    msrd.setSinaNickname(sinaUser.getScreenName());
		if (StringUtil.isNotBlank(sinaUser.getAvatarLarge()))
		    msrd.setAvatarLarge(sinaUser.getAvatarLarge());
		if (StringUtil.isNotBlank(sinaUser.getUserDomain()))
		    msrd.setUserDomain(sinaUser.getUserDomain());
		if (StringUtil.isNotBlank(sinaUser.getProfileImageUrl()))
		    msrd.setProfileImage(sinaUser.getProfileImageUrl());
		Integer genderInt = 9;
		if (SinaUser.SINA_GENDER_MALE.equals(sinaUser.getGender())) {
			genderInt = 1;
		} else if (SinaUser.SINA_GENDER_FEMALE.equals(sinaUser.getGender())) {
			genderInt = 0;
		} else {
			genderInt = 9;
		}
		msrd.setGender(genderInt);
		return true;
	}
}
