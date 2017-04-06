package com.yuwang.pinju.web.module.my.screen.link;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.yuwang.cookie.util.CodeUtil;
import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.web.message.Message;
import com.yuwang.pinju.web.message.MessageName;
import com.yuwang.pinju.web.module.my.screen.MySnsInfoAction;
import com.yuwang.pinju.web.struts2.HashLink;
import com.yuwang.pinju.web.valitation.ActionInvokeResult;

public class MySnsInfoHashLink implements HashLink {

	private static Log log = LogFactory.getLog(HashLink.class);

	private MySnsInfoAction action;
	private String salt;

	private String memberIdCode;
	private String hash;
	private String id;

	public MySnsInfoHashLink(MySnsInfoAction action, String salt) {
		this.action = action;
		this.salt = salt;
	}

	@Override
	public void onHash(long memberId) {
		memberIdCode = MemberIdPuzzle.encode(memberId);
		id = action.getMs().getId() == null ? null : CodeUtil.toBase62(action.getMs().getId());
		hash = CodeUtil.hash(salt, id, memberIdCode);
		if(log.isDebugEnabled()) {
			log.debug("[MySnsInfoHashLink] memberId: " + memberId + ", salt: " + salt + ", memberIdCode: " + memberIdCode + ", hash: " + hash);
		}
	}

	@Override
	public String onValidate(long memberId) {

		if(!EmptyUtil.isBlank(id) && CodeUtil.base62ToLong(id) < 1L) {
			log.warn("[MySnsInfoHashLink] id validate failed, id is invalid, id: " + id);
			return Message.getMessage(MessageName.OPERATE_INVALID);
		}

		if(memberId != MemberIdPuzzle.decode(memberIdCode)) {
			log.warn("[MySnsInfoHashLink] memberId validate failed, current memberId: " + memberId + ", hash member id: " + MemberIdPuzzle.decode(memberIdCode));
			return Message.getMessage(MessageName.OPERATE_INVALID);
		}

		String h = CodeUtil.hash(salt, id, memberIdCode);
		if(!h.equals(hash)) {
			log.warn("[MySnsInfoHashLink] hash validate failed, current h: " + h + ", hash: " + hash);
			return Message.getMessage(MessageName.OPERATE_INVALID);
		}
		return VALIDATE_SUCCESS;
	}

	@Override
	public void onConvertHash() {
		action.getMs().setMemberId(MemberIdPuzzle.decode(memberIdCode));
		if(!EmptyUtil.isBlank(id)) {
			action.getMs().setId(CodeUtil.base62ToLong(id));
		}
	}

	@Override
	public String onError(String message) {
		ActionInvokeResult.setInvokeMessage(message);
		return Action.SUCCESS;
	}

	public String getMemberIdCode() {
		return memberIdCode;
	}

	public void setMemberIdCode(String memberIdCode) {
		this.memberIdCode = memberIdCode;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
