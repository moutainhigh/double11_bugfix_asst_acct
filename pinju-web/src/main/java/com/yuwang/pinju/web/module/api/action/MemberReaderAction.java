package com.yuwang.pinju.web.module.api.action;

import static com.yuwang.pinju.domain.api.MemberReaderResponseDO.SIGN_ERROR;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.yuwang.cookie.util.MemberIdPuzzle;
import com.yuwang.pinju.core.member.service.MemberService;
import com.yuwang.pinju.domain.api.MemberReaderResponseDO;
import com.yuwang.pinju.web.struts2.PinjuAction;

/**
 * <p>会员信息读取</p>
 *
 * @author gaobaowen
 * @since 2011-7-19 下午01:20:34
 */
public class MemberReaderAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(MemberReaderAction.class);

	private MemberService memberService;
	private InputStream inputStream;
	private String code;
	private String sign;

	@Override
	public String execute() throws Exception {
		if(!memberService.checkSign(sign, code)) {
			return response(MemberReaderResponseDO.create(SIGN_ERROR));
		}
		long memberId = MemberIdPuzzle.decode(code);
		return response(memberService.snsRequestMemberInfo(memberId));
	}

	private String response(MemberReaderResponseDO resp) {
		try {
			String json = new JSONObject(resp).toString();
			inputStream = new ByteArrayInputStream(json.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return SUCCESS;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
