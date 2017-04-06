package com.yuwang.pinju.web.module.api.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.member.service.SndaMemberService;
import com.yuwang.pinju.domain.api.SndaRegisterNotifyDO;
import com.yuwang.pinju.web.struts2.PinjuAction;

/**
 * <p>盛大通行证注册昵称通知接口</p>
 *
 * @author gaobaowen
 * @since 2011-7-19 11:31:14
 */
public class SndaRegisterNotifyAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(SndaRegisterNotifyAction.class);

	private InputStream inputStream;
	private SndaMemberService sndaMemberService;
	private SndaRegisterNotifyDO reg;

	public SndaRegisterNotifyDO getReg() {
		return reg;
	}

	public void setReg(SndaRegisterNotifyDO reg) {
		this.reg = reg;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setSndaMemberService(SndaMemberService sndaMemberService) {
		this.sndaMemberService = sndaMemberService;
	}

	@Override
	public String execute() throws Exception {
		if(reg == null) {
			log.warn("reg data is null");
			return response(SndaMemberService.SNDA_REGISTER_NOTIFY_PRAM_ERROR);
		}

		String result = sndaMemberService.sndaRegisterNotify(reg);

		return response(result);
	}

	private String response(String responseData) {
		try {
			inputStream = new ByteArrayInputStream(responseData.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			log.error(e);
		}
		return SUCCESS;
	}
}