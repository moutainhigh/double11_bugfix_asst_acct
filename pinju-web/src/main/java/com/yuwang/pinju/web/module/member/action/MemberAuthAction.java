/**
 *
 */
package com.yuwang.pinju.web.module.member.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.core.util.EmptyUtil;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.domain.member.ticket.MemberTicketDO;
import com.yuwang.pinju.web.listener.EventProcess;
import com.yuwang.pinju.web.listener.login.AfterLoginProcess;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>
 * </p>
 *
 * @author gaobaowen 2011-5-31 下午01:06:11
 */
public class MemberAuthAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(MemberAuthAction.class);

	private MemberAO memberAO;

	private String returnUrl;
	private String ticket;
	private String ptid;
	private String regname;
	private String sdid;
	private boolean authResult;

	public boolean isAuthResult() {
		return authResult;
	}

	public void setAuthResult(boolean authResult) {
		this.authResult = authResult;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getPtid() {
		return ptid;
	}

	public void setPtid(String ptid) {
		this.ptid = ptid;
	}

	public String getRegname() {
		return regname;
	}

	public void setRegname(String regname) {
		this.regname = regname;
	}

	public String getSdid() {
		return sdid;
	}

	public void setSdid(String sdid) {
		this.sdid = sdid;
	}

	@Override
	public String execute() throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("invoke member auth, " + this + ", memberAO=" + memberAO);
		}
		returnUrl = ServletUtil.processReturnUrl(returnUrl);
		if (EmptyUtil.isBlank(ticket)) {
			return SUCCESS;
		}
		MemberDO member = login();
		
		EventProcess event = new AfterLoginProcess(memberAO, member, ServletUtil.getHttpReferer(), returnUrl);
		authResult = event.process(log);
		if(returnUrl.indexOf('?') > -1) {
			returnUrl += "&r=" + (authResult ? '1' : '0');
		} else {
			returnUrl += "?r=" + (authResult ? '1' : '0');
		}

		if(authResult && MemberDO.AGREE_AGREEMENT_NO.equals(member.getAgreeAgreement())) {
			log.info("auth result: " + authResult + ", current agree agreement status is NO, " +
					"need redirect to agreement page, member id: " + member.getMemberId());
			return AGREEMENT;
		}

		log.info("process member auth, login write cookie status: " + authResult + ", login ip: " + ServletUtil.getRemoteIp(ServletActionContext.getRequest()) + ", returnUrl: " + returnUrl);
		return SUCCESS;
	}

	private MemberDO login() {
		MemberTicketDO memberTicket = new MemberTicketDO();
		memberTicket.setOrigin(MemberDO.MEMBER_ORIGIN_SDO);
		memberTicket.setTicket(ticket);
		MemberDO member = memberAO.login(memberTicket);
		return member;
	}

	@Override
	public String toString() {
		return "MemberAuthAction [hash=" + hashCode() + ", ticket=" + ticket + ", returnUrl=" + returnUrl + ", ptid="
				+ ptid + ", regname=" + regname + ", sdid=" + sdid + "]";
	}
}
