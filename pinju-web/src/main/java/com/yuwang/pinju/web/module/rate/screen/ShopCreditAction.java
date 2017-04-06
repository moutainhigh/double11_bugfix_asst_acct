package com.yuwang.pinju.web.module.rate.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.MemberDO;
import com.yuwang.pinju.web.module.PinjuControl;
import com.yuwang.pinju.web.module.error.screen.ErrorPage;
import com.yuwang.pinju.web.module.member.control.MemberSellerQualityControl;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>店铺信用信息</p>
 *
 * @author gaobaowen
 * 2011-6-21 下午04:13:27
 */
public class ShopCreditAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(ShopCreditAction.class);

	private MemberAO memberAO;
	private String encodeMemberId;
	private String invokeMessage;
	private String _disable_cache_;

	@Override
	public String execute() throws Exception {
		long memberId = MemberIdPuzzle.decode(encodeMemberId);

		if(memberId < 1) {
			log.info("encodeMemberId is invalid, encodeMemberId: [" + encodeMemberId + "]");
			String message = ServletUtil.getRequestUrl() + "<br /><br />无效的 URL";
			ErrorPage.addErrorMessage(message);
			return PAGE_404;
		}

		MemberDO member = memberAO.findMember(memberId);
		if(member == null) {
			log.info("cannot find Member by member id, member id: [" + memberId + "]");
			String message = ServletUtil.getRequestUrl() + "<br /><br />无效的 URL";
			ErrorPage.addErrorMessage(message);
			return PAGE_404;
		}

		PinjuControl pc = new MemberSellerQualityControl(memberId);
		pc.doControl("true".equalsIgnoreCase(_disable_cache_));
		if(log.isDebugEnabled()) {
			log.debug("encodeMemberId: " + MemberIdPuzzle.decode(encodeMemberId));
		}

		if (!"true".equalsIgnoreCase(_disable_cache_)) {
			ServletUtil.setHeaderMaxAge(300);
		}

		return SUCCESS;
	}

	public void setMemberAO(MemberAO memberAO) {
		this.memberAO = memberAO;
	}

	public String getInvokeMessage() {
		return invokeMessage;
	}

	public String getEncodeMemberId() {
		return encodeMemberId;
	}

	public void setEncodeMemberId(String encodeMemberId) {
		this.encodeMemberId = encodeMemberId;
	}

	public void set_disable_cache_(String _disable_cache_) {
		this._disable_cache_ = _disable_cache_;
	}
}
