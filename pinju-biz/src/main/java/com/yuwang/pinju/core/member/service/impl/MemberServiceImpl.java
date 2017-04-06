package com.yuwang.pinju.core.member.service.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.constant.system.PinjuConstant;
import com.yuwang.pinju.core.member.manager.MemberManager;
import com.yuwang.pinju.core.member.service.MemberService;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.api.MemberReaderResponseDO;
import com.yuwang.pinju.domain.member.MemberDO;

public class MemberServiceImpl implements MemberService {

	private final static Log log = LogFactory.getLog(MemberServiceImpl.class);

	private MemberManager memberManager;

	public void setMemberManager(MemberManager memberManager) {
		this.memberManager = memberManager;
	}

	public boolean checkSign(String sign, String... params) {
		if(EmptyUtil.isBlank(sign)) {
			log.warn("sign data is empty, cannot check sign validity");
			return false;
		}
		if(params == null || params.length == 0) {
			log.warn("data is null or empty, cannot check sign validity, sign: " + sign + ", params: " + params);
			return false;
		}
		for(int i = 0; i < params.length; i++) {
			if(EmptyUtil.isBlank(params[i])) {
				log.warn("No." + (i + 1) + " sign params is empty or null");
				return false;
			}
		}
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < params.length; i++) {
			builder.append(params[i]);
		}
		builder.append(PinjuConstant.API_SNS_SIGN_KEY);
		String md5 = md5(builder.toString(), PinjuConstant.DEFAULT_CHARSET);
		boolean checkResult = sign.equals(md5);
		if(!checkResult && log.isDebugEnabled()) {
			log.debug("checkSign failed, sign: [" + sign + "], data sign: [" + md5 + "]");
		}
		return checkResult;
	}

	@Override
	public MemberReaderResponseDO snsRequestMemberInfo(long memberId) {
		if (log.isInfoEnabled()) {
			log.info("getMemberSnsInfo, memberId: [" + memberId + "]");
		}
		if(!memberManager.isCorrectMemberId(memberId)) {
			log.warn("getMemberSnsInfo, memberId is invalid, memberId: [" + memberId + "]");
			return MemberReaderResponseDO.create(MemberReaderResponseDO.MEMBER_CODE_INVALID);
		}
		try {
			MemberDO member = memberManager.findMember(memberId);
			if(member == null) {
				log.warn("getMemberSnsInfo, member not exists, memberId: [" + memberId + "]");
				return MemberReaderResponseDO.create(MemberReaderResponseDO.MEMBER_NOT_EXISTS);
			}
			MemberReaderResponseDO response = MemberReaderResponseDO.createSuccess();
			response.setFileServer(PinjuConstant.FILE_SERVER);
			response.addMember(member);
			response.addMemberInfo(memberManager.findMemberInfo(memberId));
			response.addMemberSnsInfo(memberManager.getMemberSnsInfo(memberId));
			return response;
		} catch (Exception e) {
			log.error("getMemberSnsInfo cause error, memberId: [" + memberId + "]", e);
			return MemberReaderResponseDO.create(MemberReaderResponseDO.SERVICE_UNAVAILABLE);
		}
	}

	private String md5(String value, String charset) {
		try {
			return DigestUtils.md5Hex(value.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			return "UnsupportedEncodingException";
		}
	}
}
