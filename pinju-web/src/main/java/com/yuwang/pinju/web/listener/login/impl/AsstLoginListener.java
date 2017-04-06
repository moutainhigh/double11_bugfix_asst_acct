package com.yuwang.pinju.web.listener.login.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.MemberLoginHisDO;
import com.yuwang.pinju.web.listener.login.AfterLoginEvent;
import com.yuwang.pinju.web.listener.login.AfterLoginListener;

/**
 * <p>子账号登录处理</p>
 *
 * @author gaobaowen
 * @since 2011-12-22 15:29:08
 */
public class AsstLoginListener implements AfterLoginListener {
	
	private final static Log log = LogFactory.getLog(AsstLoginListener.class);

	@Override
	public boolean afterLogin(AfterLoginEvent event) {
		MemberDO member = event.getMember();
		if (!member.isAssistantMember()) {
			// 非子账号，不需要处理
			return true;
		}
		MemberLoginHisDO his = event.getLoginHis();
		boolean result = event.getMemberAO().asstLogin(his);
		if (!result) {
			log.warn("assistant member listener result: [" + result + "], " + his);
		}
		return true;
	}
}
