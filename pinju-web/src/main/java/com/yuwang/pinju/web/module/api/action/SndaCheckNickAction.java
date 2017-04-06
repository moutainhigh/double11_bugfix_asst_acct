package com.yuwang.pinju.web.module.api.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yuwang.pinju.core.member.service.SndaMemberService;
import com.yuwang.pinju.web.struts2.PinjuAction;
import com.yuwang.pinju.web.system.ServletUtil;

/**
 * <p>盛大通行证注册昵称检查接口</p>
 *
 * @author gaobaowen
 * @since 2011-7-19 11:30:25
 */
public class SndaCheckNickAction implements PinjuAction {

	private final static Log log = LogFactory.getLog(SndaCheckNickAction.class);

	private SndaMemberService sndaMemberService;
	private String name;
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSndaMemberService(SndaMemberService sndaMemberService) {
		this.sndaMemberService = sndaMemberService;
	}

	@Override
	public String execute() throws Exception {
		log.info("snda check nick, request ip: " + ServletUtil.getRemoteIp() + ", nick: " + name);
		String checkResult = sndaMemberService.checkNickname(name);
		log.info("snda check nick, check result: " + checkResult);
		inputStream = new ByteArrayInputStream(checkResult.getBytes());
		return SUCCESS;
	}
}
