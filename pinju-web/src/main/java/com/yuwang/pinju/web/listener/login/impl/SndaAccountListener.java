package com.yuwang.pinju.web.listener.login.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.web.listener.login.AfterLoginEvent;
import com.yuwang.pinju.web.listener.login.AfterLoginListener;

public class SndaAccountListener implements AfterLoginListener {

	private final static Log log = LogFactory.getLog(SndaAccountListener.class);

	@Override
	public boolean afterLogin(AfterLoginEvent event) {
		log.info("fire the listener...");
		MemberDO member = event.getMember();
		if (!member.isSndaMember()) {
			log.info("member is not snda user, need not fire the listener, member id: " + member.getMemberId() + ", member origin: " + member.getMemberOrigin());
			return true;
		}
		MemberAO memberAO = event.getMemberAO();		
		boolean result = memberAO.setMemberSndaAccount(member);
		log.info("fired listener result: " + result);
		return result;
	}
}
