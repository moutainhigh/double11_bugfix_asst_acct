package com.yuwang.pinju.domain.member.security;

import java.util.HashMap;
import java.util.Map;

import com.yuwang.pinju.domain.member.security.MemberSecurityEmailLinkDO.LinkType;

public class SecurityEmailMessageDO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	
	private long memberId;
	
	private String ip;
	
	private String email;
	
	private String titleTemplateName;
	
	private String contentTemplateName;
	
	private Map<String, String> titleParam;
	
	private Map<String, String> contentParam;
	
	private Integer sendType;
	
	private Integer channel;

	private LinkType linkType;
	
	private String emailLike;

	public SecurityEmailMessageDO() {
		this.titleParam = new HashMap<String, String>();
		this.contentParam = new HashMap<String, String>();
	}
	
	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitleTemplateName() {
		return titleTemplateName;
	}

	public void setTitleTemplateName(String titleTemplateName) {
		this.titleTemplateName = titleTemplateName;
	}

	public String getContentTemplateName() {
		return contentTemplateName;
	}

	public void setContentTemplateName(String contentTemplateName) {
		this.contentTemplateName = contentTemplateName;
	}

	public Map<String, String> getTitleParam() {
		return titleParam;
	}

	public void setTitleParam(Map<String, String> titleParam) {
		this.titleParam = titleParam;
	}

	public Map<String, String> getContentParam() {
		return contentParam;
	}

	public void setContentParam(Map<String, String> contentParam) {
		this.contentParam = contentParam;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	
	public String getEmailLike() {
		return emailLike;
	}

	public void setEmailLike(String emailLike) {
		this.emailLike = emailLike;
	}
	
	public LinkType getLinkType() {
		return linkType;
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType;
	}

	public void addTitleParam(String key, String value) {
		this.titleParam.put(key, value);
	}
	
	public void addContentParam(String key, String value) {
		this.contentParam.put(key, value);
	}
}
