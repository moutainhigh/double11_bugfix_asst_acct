package com.yuwang.pinju.core.member.service.impl;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.common.DateUtil;
import com.yuwang.pinju.core.common.ManagerException;
import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.manager.sequence.MemberIdGenerator;
import com.yuwang.pinju.core.member.manager.ticket.MemberOrigin;
import com.yuwang.pinju.core.member.service.SndaMemberService;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.api.SndaRegisterNotifyDO;
import com.yuwang.pinju.domain.member.MemberDO;

public class SndaMemberServiceImpl implements SndaMemberService {

	private final static Log log = LogFactory.getLog(SndaMemberServiceImpl.class);

	private final static Pattern NICKNAME_PATTERN = Pattern.compile("[0-9a-zA-Z\u4e00-\u9faf_]+");

	private MemberManager memberManager;
	private MemberIdGenerator memberIdGenerator;

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public void setMemberIdGenerator(MemberIdGenerator memberIdGenerator) {
		this.memberIdGenerator = memberIdGenerator;
	}

	@Override
	public String checkNickname(String nickname) {

		if (log.isInfoEnabled()) {
			log.info("checkNickname, check nickname: [" + nickname + "]");
		}

		// 昵称为空
		if (EmptyUtil.isBlank(nickname)) {
			log.info("checkNickname, nickname is null or empty, response: " + CHECK_NICKNAME_LENGTH_ERROR);
			return CHECK_NICKNAME_LENGTH_ERROR;
		}

		nickname = nickname.trim();

		// 昵称长度不正确
		if (nickname.length() < MemberDO.NICKNAME_LENGTH_MIN || nickname.length() > MemberDO.NICKNAME_LENGTH_MAX) {
			log.info("checkNickname, nickname length error, nickname: " + nickname + ", response: "
					+ CHECK_NICKNAME_LENGTH_ERROR);
			return CHECK_NICKNAME_LENGTH_ERROR;
		}

		// 昵称格式不正确
		if (!NICKNAME_PATTERN.matcher(nickname).matches()) {
			log.info("checkNickname, nickname format error, nickname: " + nickname + ", response: "
					+ CHECK_NICKNAME_FORMAT_ERROR);
			return CHECK_NICKNAME_FORMAT_ERROR;
		}

		try {
			MemberDO member = memberManager.findMemberByNickname(nickname);

			// 昵称已经被其他会员使用
			if (member != null) {
				log.info("checkNickname, nickname: " + nickname + " has been used by member id: "
						+ member.getMemberId());
				return CHECK_NICKNAME_USED;
			}

			return CHECK_NICKNAME_SUCCESS;
		} catch (ManagerException e) {
			log.error("invoke isExistNickname error, nickname: " + nickname + ", response: " + CHECK_NICKNAME_USED, e);
			return CHECK_NICKNAME_USED;
		}
	}

	@Override
	public String sndaRegisterNotify(SndaRegisterNotifyDO data) {

		if (log.isInfoEnabled()) {
			log.info("sndaRegisterNotify, " + data);
		}

		if (data == null || EmptyUtil.isBlank(data.getSdid())) {
			log.warn("sndaRegisterNotify, parameter is null or sdid is empty");
			return SNDA_REGISTER_NOTIFY_PRAM_ERROR;
		}

		String dataSign = data.getDataSign(PinjuConstant.API_SNDA_REGISTER_NOTIFY_SIGN_KEY, PinjuConstant.DEFAULT_CHARSET);
		if(!dataSign.equals(data.getSign())) {
			log.warn("sndaRegisterNotify, sign invalid, data sign: " + dataSign + ", param sign: " + data.getSign());
			return SNDA_REGISTER_NOTIFY_SIGN_ERROR;
		}

		// 再次检查昵称是否可用
		String checkNickResult = checkNickname(data.getNickname());
		if (!CHECK_NICKNAME_SUCCESS.equals(checkNickResult)) {
			log.warn("sndaRegisterNotify, nickname check error, nickname: " + data.getNickname() + ", check result: "
					+ checkNickResult);
			return checkNickResult;
		}

		try {
			MemberDO member = memberManager.getMemberByAccount(data.getSdid(), MemberDO.MEMBER_ORIGIN_SDO);
			// SDID 会员已经存在，不再处理
			if (member != null) {
				log.warn("sndaRegisterNotify, sdid has exists, sdid: " + data.getSdid() + ", member id: "
						+ member.getMemberId());
				return SNDA_REGISTER_NOTIFY_SDID_EXISTS;
			}
		} catch (ManagerException e) {
			log.error("sndaRegisterNotify, execute getMemberByAccount error, sdid: " + data.getSdid() + ", nickname: "
					+ data.getNickname() + ", sndapt: " + data.getSndapt(), e);
			return SNDA_REGISTER_NOTIFY_ERROR;
		}

		MemberDO member = saveMember(data);

		if (member == null) {
			log.error("sndaRegisterNotify, save member error, sdid: " + data.getSdid() + ", nickname: "
					+ data.getNickname() + ", sndapt: " + data.getSndapt());
			return SNDA_REGISTER_NOTIFY_ERROR;
		}

		log.info("sndaRegisterNotify, save member success, id: " + member.getMemberId() + ", member id: "
				+ member.getMemberId());

		return SNDA_REGISTER_NOTIFY_SUCCESS;
	}

	private MemberDO saveMember(SndaRegisterNotifyDO data) {
		long memberId = memberIdGenerator.nextMemberId(MemberOrigin.SDO);
		if (log.isInfoEnabled()) {
			log.info("newMember, generate a new member id, MemberId: [" + memberId + "]");
		}
		MemberDO member = new MemberDO();
		member.setMemberId(memberId);
		member.setAccountName(data.getSdid());
		member.setMemberOrigin(MemberDO.MEMBER_ORIGIN_SDO);
		member.setNickname(data.getNickname().trim());
		member.setRegisterStatus(MemberDO.REGISTER_STATUS_SNDA_CUSTOM);		
		member.setAgreeAgreement(MemberDO.AGREE_AGREEMENT_YES);
		Date current = DateUtil.current();
		member.setAgreeAgreementTime(current);
		member.setCreateTime(current);

		try {
			return memberManager.saveMember(member);
		} catch (ManagerException e) {
			log.error("saveMember error, " + member, e);
			return null;
		}
	}
}
