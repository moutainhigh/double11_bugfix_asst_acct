package com.yuwang.pinju.web.module.ajax.member;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.Action;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.web.cookie.CookieLoginInfo;

/**
 * <p>
 * 验证用户的昵称是否可用
 * </p>
 *
 * @author gaobaowen
 * @since 2011-6-8 12:41:05
 */
public class NicknameValidator implements Action {

	private final static Log log = LogFactory.getLog(NicknameValidator.class);

	private MemberAO memberAO;

	private String nickname;
	private boolean result = false;

	@Override
	public String execute() throws Exception {
		if (EmptyUtil.isBlank(nickname)) {
			log.warn("nickname validator, but parameter nickname is null or empty, nickname = [" + nickname + "]");
			return SUCCESS;
		}

		long memberId = 0;
		CookieLoginInfo login = CookieLoginInfo.getCookieLoginInfo();
		if (login.isLogin()) {
			memberId = login.getMemberId();
			log.warn("current user has been logon, need ignore self's nickname, nickname: [" + nickname + "]");
		} else {
			log.info("current user has not login, need not ignore self's nickname, nickname: [" + nickname + "]");
		}

		MemberDO member = memberAO.findMemberByNickname(nickname);

		if (member != null) {
			if (memberId > 0 && !member.getMemberId().equals(login.getMemberId())) {
				if (log.isInfoEnabled() ) {
					log.warn("current user want using nickname: " + nickname + ", but the nickname is used by " +
							"member that member id: [" + member.getMemberId() + "]");
					return SUCCESS;
				}
			}
			if (memberId == 0) {
				if (log.isInfoEnabled()) {
					log.debug("current user want using nickname: " + nickname + ", but the nickname is used by " +
							"member that member id: [" + member.getMemberId() + "]");
				}
				return SUCCESS;
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("current user nickname can use, nickname: " + nickname + ", current mid: " + memberId);
		}
		result = true;
		return SUCCESS;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
