package com.yuwang.pinju.web.module.member.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionContext;
import com.yuwang.pinju.core.common.Result;
import com.yuwang.pinju.core.common.SpringBeanFactory;
import com.yuwang.pinju.core.member.ao.MemberAO;
import com.yuwang.pinju.domain.member.SellerQualityInfoDO;
import com.yuwang.pinju.web.freemarker.share.PinjuEncoder;
import com.yuwang.pinju.web.module.PinjuControl;

public class MemberSellerQualityControl implements PinjuControl {

	private final static Log log = LogFactory.getLog(MemberSellerQualityControl.class);

	private long memberId;
	private MemberAO memberAO;

	public MemberSellerQualityControl(long memberId) {
		this.memberId = memberId;
		this.memberAO = SpringBeanFactory.getBean(MemberAO.class);
	}

	public boolean doControl() {
		return doControl(false);
	}

	@Override
	public boolean doControl(boolean debug) {
		Result result = memberAO.getMemberShopQuality(memberId, debug);
		if(!result.isSuccess()) {
			log.debug("doControl execute result is incorrect, member id: [" + memberId + "], result code: [" + result.getResultCode() + "]");
			return false;
		}
		SellerQualityInfoDO sqi = result.getModel(SellerQualityInfoDO.class);
		ActionContext.getContext().put("sellerQuality", sqi.getSellerQuality());
		ActionContext.getContext().put("dsrStats", sqi.getDsrStats());
		ActionContext.getContext().put("mid", PinjuEncoder.getInstance().fixedEncodeMemberId(sqi.getSellerQuality()));
		return true;
	}
}
